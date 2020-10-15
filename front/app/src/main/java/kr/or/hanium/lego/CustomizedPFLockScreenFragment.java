package kr.or.hanium.lego;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.beautycoder.pflockscreen.PFFLockScreenConfiguration;
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment;
import com.beautycoder.pflockscreen.security.PFResult;
import com.beautycoder.pflockscreen.viewmodels.PFPinCodeViewModel;
import com.beautycoder.pflockscreen.views.PFCodeView;

import kr.or.hanium.R;

public class CustomizedPFLockScreenFragment extends PFLockScreenFragment {
    private static final String TAG = PFLockScreenFragment.class.getName();

    private PFCodeView mCodeView;
    private OnPFLockScreenLoginListener mLoginListener;
    private boolean nIsCreateMode = false;

    private OnPFLockScreenCodeCreateListener mCodeCreateListener;

    private String mCode = "";
    private String mEncodedPinCode = "";
    private String nCodeValidation = "";
    private PFFLockScreenConfiguration nConfiguration;
    private TextView titleView;

    private final PFPinCodeViewModel mPFPinCodeViewModel = new PFPinCodeViewModel();

    public void setEncodedPinCode(String encodedPinCode) {
        mEncodedPinCode = encodedPinCode;
    }

    public void setCodeCreateListener(OnPFLockScreenCodeCreateListener listener) {
        mCodeCreateListener = listener;
    }

    public void setLoginListener(OnPFLockScreenLoginListener listener) {
        mLoginListener = listener;
    }

    public void setConfiguration2(PFFLockScreenConfiguration configuration) {
        this.nConfiguration = configuration;
    }

    private void cleanCode() {
        mCode = "";
        mCodeView.clearCode();
    }

    private final PFCodeView.OnPFCodeListener mCodeListener = new PFCodeView.OnPFCodeListener() {

        @Override
        public void onCodeCompleted(String code) {
            nIsCreateMode = nConfiguration.getMode() == PFFLockScreenConfiguration.MODE_CREATE;
            if (nIsCreateMode) {
                mCode = code;
                if (nConfiguration.isNewCodeValidation() && TextUtils.isEmpty(nCodeValidation)) {
                    nCodeValidation = mCode;
                    cleanCode();
                    titleView.setText(nConfiguration.getNewCodeValidationTitle());
                    return;
                }
                if (nConfiguration.isNewCodeValidation() && !TextUtils.isEmpty(nCodeValidation) && !mCode.equals(nCodeValidation)) {
                    mCodeCreateListener.onNewCodeValidationFailed();
                    titleView.setText(nConfiguration.getNewCodeValidationTitle());
                    cleanCode();
                    return;
                }
                nCodeValidation = "";
                mPFPinCodeViewModel.encodePin(getContext(), mCode).observe(
                        CustomizedPFLockScreenFragment.this,
                        new Observer<PFResult<String>>() {
                            @Override
                            public void onChanged(@Nullable PFResult<String> result) {
                                if (result == null) {
                                    return;
                                }
                                if (result.getError() != null) {
                                    Log.d(TAG, "Can not encode pin code");
                                    deleteEncodeKey();
                                    return;
                                }
                                final String encodedCode = result.getResult();
                                if (mCodeCreateListener != null) {
                                    mCodeCreateListener.onCodeCreated(encodedCode);
                                }
                            }
                        }
                );

                return;
            }
            mCode = code;
            mPFPinCodeViewModel.checkPin(getContext(), mEncodedPinCode, mCode).observe(
                    CustomizedPFLockScreenFragment.this,
                    new Observer<PFResult<Boolean>>() {
                        @Override
                        public void onChanged(@Nullable PFResult<Boolean> result) {
                            if (result == null) {
                                return;
                            }
                            if (result.getError() != null) {
                                return;
                            }
                            final boolean isCorrect = result.getResult();
                            if (mLoginListener != null) {
                                if (isCorrect) {
                                    mLoginListener.onCodeInputSuccessful();
                                } else {
                                    mLoginListener.onPinLoginFailed();
                                    errorAction();
                                }
                            }
                            if (!isCorrect && nConfiguration.isClearCodeOnError()) {
                                mCodeView.clearCode();
                            }
                        }
                    });

        }

        @Override
        public void onCodeNotCompleted(String code) {
            if (nIsCreateMode) {
                return;
            }
        }
    };

    private void deleteEncodeKey() {
        mPFPinCodeViewModel.delete().observe(
                this,
                new Observer<PFResult<Boolean>>() {
                    @Override
                    public void onChanged(@Nullable PFResult<Boolean> result) {
                        if (result == null) {
                            return;
                        }
                        if (result.getError() != null) {
                            Log.d(TAG, "Can not delete the alias");
                            return;
                        }

                    }
                }
        );
    }

    private void errorAction() {
        if (nConfiguration.isErrorVibration()) {
            final Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            if (v != null) {
                v.vibrate(400);
            }
        }

        if (nConfiguration.isErrorAnimation()) {
            final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake_pf);
            mCodeView.startAnimation(animShake);
        }

        cleanCode();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mCodeView = getView().findViewById(R.id.code_view);
        titleView = getView().findViewById(R.id.title_text_view);
        mCodeView.setListener(mCodeListener);
        super.onActivityCreated(savedInstanceState);
    }

}