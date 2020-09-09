//비밀번호 변경 전 현재 비밀번호 확인 fragment
//탈퇴 전 현재 비밀번호 확인 fragment

package hanium.mobile.did_student.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import hanium.mobile.did_student.R;

public class CheckPasswordFragment extends Fragment {

    private EditInfoViewModel editInfoViewModel;

    private TextView tvTitle;
    private TextView etError;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editInfoViewModel =
                ViewModelProviders.of(this).get(EditInfoViewModel.class);
        root = inflater.inflate(R.layout.fragment_password, container, false);

        tvTitle = root.findViewById(R.id.text_password_title);
        etError = root.findViewById(R.id.text_password_error);

        tvTitle.setText("비밀번호 6자리를 입력해주십시오");

        //비밀번호 달라서 다시 돌아옴
        //etError.setText("비밀번호가 불일치합니다");

        //비밀번호 불일치
        //비밀번호 변경
        Navigation.findNavController(root).navigate(R.id.action_check_password_wrong);
        //탈퇴
        Navigation.findNavController(root).navigate(R.id.action_check_password_withdraw_wrong);

        //비밀번호 일치
        //비밀번호 변경
        Navigation.findNavController(root).navigate(R.id.action_mypage_to_password);
        //탈퇴
        Navigation.findNavController(root).navigate(R.id.action_mypage_to_password_withdraw);

        //final TextView textView = root.findViewById(R.id.text_gallery);
        editInfoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
        //        textView.setText(s);
            }
        });
        return root;
    }
}