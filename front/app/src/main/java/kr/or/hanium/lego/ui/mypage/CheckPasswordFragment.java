//비밀번호 변경 전 현재 비밀번호 확인 fragment
//탈퇴 전 현재 비밀번호 확인 fragment

package kr.or.hanium.lego.ui.mypage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.beautycoder.pflockscreen.PFFLockScreenConfiguration;
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment;

import kr.or.hanium.R;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;
import kr.or.hanium.lego.PreferencesSettings;

public class CheckPasswordFragment extends Fragment implements OnBackPressedListener {

    private View root;
    private MainActivity activity;

    private String mypage;

    private PFLockScreenFragment fragment;
    private PFFLockScreenConfiguration.Builder builder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_password, container, false);

        activity = (MainActivity)getActivity();

        mypage = getArguments().getString("mypage");

        fragment = new PFLockScreenFragment();
        builder = new PFFLockScreenConfiguration.Builder(getContext())
                .setMode(PFFLockScreenConfiguration.MODE_AUTH)
                .setTitle("비밀번호 6자리를 입력해주십시오")
                .setMode(PFFLockScreenConfiguration.MODE_AUTH)
                .setUseFingerprint(false)
                .setClearCodeOnError(true)
                .setLeftButton("취소")
                .setCodeLength(6);

        fragment.setEncodedPinCode(PreferencesSettings.getCode(getContext()));
        fragment.setConfiguration(builder.build());
        fragment.setLoginListener(new PFLockScreenFragment.OnPFLockScreenLoginListener() {
            @Override
            public void onCodeInputSuccessful() {
                //비밀번호 변경
                if(mypage.equals("edit")) {
                    Navigation.findNavController(root).navigate(R.id.action_check_password_to_change_password);
                }
                //탈퇴
                else {
                    Navigation.findNavController(root).navigate(R.id.action_check_password_to_withdraw);
                }
            }

            @Override
            public void onFingerprintSuccessful() {
                Toast.makeText(getActivity(), "Fingerprint success", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPinLoginFailed() {
                Toast.makeText(getActivity(), "비밀번호를 잘못 입력하였습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFingerprintLoginFailed() {
                Toast.makeText(getActivity(), "Fingerprint failed", Toast.LENGTH_SHORT).show();

            }
        });

        fragment.setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_to_mypage);
            }
        });

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.pincode_container, fragment).commit();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
    }

    @Override
    public void onBackPressed() {
        Navigation.findNavController(root).navigate(R.id.action_to_mypage);
    }
}