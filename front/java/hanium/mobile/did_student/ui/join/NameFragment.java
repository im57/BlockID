//회원가입 이름,학번 입력 fragment (회원가입 마지막)

package hanium.mobile.did_student.ui.join;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import hanium.mobile.did_student.R;

public class NameFragment extends Fragment {

    private View root;
    private Button button;

    private EditText etName;
    private EditText etNum;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_join_name, container, false);

        etName = root.findViewById(R.id.edit_name);
        etNum = root.findViewById(R.id.edit_num);

        button = root.findViewById(R.id.btn_join_name);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_join_name_to_nav_login);
            }
        });

        return root;
    }
}