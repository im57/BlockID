//비밀번호 변경 완료 fragment

package kr.or.hanium.lego.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import kr.or.hanium.R;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;

<<<<<<< HEAD
<<<<<<< HEAD:front/java/hanium/mobile/did_student/ui/mypage/SuccessChangePasswordFragment.java
public class SuccessChangePasswordFragment extends Fragment {
=======
public class SuccessChangePasswordFragment extends Fragment implements OnBackPressedListener {
>>>>>>> feature-front-#21:front/app/src/main/java/kr/or/hanium/lego/ui/mypage/SuccessChangePasswordFragment.java
=======
public class SuccessChangePasswordFragment extends Fragment implements OnBackPressedListener {
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa

    private View root;
    private MainActivity activity;

    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mypage_password_change, container, false);

        activity = (MainActivity)getActivity();

        button = root.findViewById(R.id.btn_change_password);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(root).navigate(R.id.action_success_change_password_to_main);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getActivity(), "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(root).navigate(R.id.action_go_home);
    }
}