//비밀번호 변경 완료 fragment

package hanium.mobile.did_student.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import hanium.mobile.did_student.R;

public class SuccessChangePasswordFragment extends Fragment {

    private EditInfoViewModel editInfoViewModel;

    private View root;

    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editInfoViewModel =
                ViewModelProviders.of(this).get(EditInfoViewModel.class);
        root = inflater.inflate(R.layout.fragment_mypage_password_change, container, false);

        button = root.findViewById(R.id.btn_change_password);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_success_change_password_to_main);
            }
        });

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