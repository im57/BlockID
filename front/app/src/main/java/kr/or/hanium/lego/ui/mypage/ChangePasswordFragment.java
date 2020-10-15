//변경할 비밀번호 입력 fragment

package kr.or.hanium.lego.ui.mypage;

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

public class ChangePasswordFragment extends Fragment implements OnBackPressedListener {

    private View root;
    private MainActivity activity;

    private PFLockScreenFragment fragment;
    private PFFLockScreenConfiguration.Builder builder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_password, container, false);

        activity = (MainActivity)getActivity();

        fragment = new PFLockScreenFragment();
        builder = new PFFLockScreenConfiguration.Builder(getContext())
                .setMode(PFFLockScreenConfiguration.MODE_CREATE)
                .setTitle("변경할 비밀번호 6자리를 \n입력해주십시오")
                .setNewCodeValidation(true)
                .setNewCodeValidationTitle("비밀번호를 다시 입력해주십시오")
                .setLeftButton("취소")
                .setUseFingerprint(false)
                .setCodeLength(6);

        fragment.setConfiguration(builder.build());
        fragment.setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_to_mypage);
            }
        });

        fragment.setCodeCreateListener(new PFLockScreenFragment.OnPFLockScreenCodeCreateListener() {
            @Override
            public void onCodeCreated(String encodedCode) {
                //TODO: save somewhere;
                PreferencesSettings.saveToPref(getActivity(), encodedCode);
                Navigation.findNavController(root).navigate(R.id.action_change_password_to_success_change_password);
            }

            @Override
            public void onNewCodeValidationFailed() {
                Toast.makeText(getContext(), "비밀번호가 불일치합니다.", Toast.LENGTH_SHORT).show();
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