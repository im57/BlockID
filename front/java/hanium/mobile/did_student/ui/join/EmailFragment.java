//회원가입 가장 처음 메일 인증 fragment

package hanium.mobile.did_student.ui.join;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import hanium.mobile.did_student.MainActivity;
import hanium.mobile.did_student.R;
import hanium.mobile.did_student.ui.mypage.CheckPasswordFragment;
import hanium.mobile.did_student.ui.notice.DeveloperViewModel;

public class EmailFragment extends Fragment {

    private DeveloperViewModel developerViewModel;

    private EditText etSchool;
    private EditText etEmail;
    private Button btnSearch;
    private Button btnSend;
    private Button btnNext;

    private View root;

    private AlertDialog.Builder builder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        developerViewModel =
                ViewModelProviders.of(this).get(DeveloperViewModel.class);
        root = inflater.inflate(R.layout.fragment_join_email, container, false);

        etSchool = root.findViewById(R.id.edit_school);
        etEmail = root.findViewById(R.id.edit_email);
        btnSearch = root.findViewById(R.id.btn_school_search);
        btnSend = root.findViewById(R.id.btn_email_send);
        btnNext = root.findViewById(R.id.btn_join_email_next);

        //검색
        btnSearch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //보내기
        btnSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());

                builder.setMessage("인증 메일이 전송되었습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        //다음
        btnNext.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_join_to_nav_join_password);
            }
        });

        // final TextView textView = root.findViewById(R.id.text_tools);
        developerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
        //        textView.setText(s);
            }
        });
        return root;
    }
}