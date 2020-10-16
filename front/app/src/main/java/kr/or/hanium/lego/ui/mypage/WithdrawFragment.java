//탈퇴 완료 fragment

package kr.or.hanium.lego.ui.mypage;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment;
import com.beautycoder.pflockscreen.security.PFResult;
import com.beautycoder.pflockscreen.security.PFSecurityManager;
import com.beautycoder.pflockscreen.security.callbacks.PFPinCodeHelperCallback;

import kr.or.hanium.R;
import kr.or.hanium.lego.HolderDBHelper;
import kr.or.hanium.lego.JoinActivity;
import kr.or.hanium.lego.LoginActivity;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;

public class WithdrawFragment extends Fragment implements OnBackPressedListener {

    private Button button;

    private View root;
    private MainActivity activity;

    private PFLockScreenFragment fragment;

    private HolderDBHelper helper;
    private SQLiteDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mypage_withdraw, container, false);

        activity = (MainActivity)getActivity();

        fragment = new PFLockScreenFragment();

        helper = new HolderDBHelper(getContext());

        button = root.findViewById(R.id.btn_withdraw);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete password
                PFSecurityManager.getInstance().getPinCodeHelper().delete(new PFPinCodeHelperCallback<Boolean>() {
                    @Override
                    public void onResult(PFResult<Boolean> result) {
                        Log.d("encoded_pin_code", "코드 삭제 결과" + result.getResult());

                    }
                });

                SQLiteDatabase db = helper.getWritableDatabase();

                db.delete(HolderDBHelper.TABLE_NAME, null, null);
                helper.close();


                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.closeFab();
    }

    @Override
    public void onBackPressed() {
        PFSecurityManager.getInstance().getPinCodeHelper().delete(new PFPinCodeHelperCallback<Boolean>() {
            @Override
            public void onResult(PFResult<Boolean> result) {
                Log.d("encoded_pin_code", "코드 삭제 결과" + result.getResult());

            }
        });
        getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}