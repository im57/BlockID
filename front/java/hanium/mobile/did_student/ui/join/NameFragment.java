//회원가입 이름,학번 입력 fragment (회원가입 마지막)

package hanium.mobile.did_student.ui.join;

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
import hanium.mobile.did_student.ui.notice.DeveloperViewModel;

public class NameFragment extends Fragment {

    private DeveloperViewModel developerViewModel;

    private View root;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        developerViewModel =
                ViewModelProviders.of(this).get(DeveloperViewModel.class);
        root = inflater.inflate(R.layout.fragment_join_name, container, false);

        button = root.findViewById(R.id.btn_join_name);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_join_name_to_nav_login);
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