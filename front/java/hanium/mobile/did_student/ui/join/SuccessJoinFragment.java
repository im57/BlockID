//탈퇴 완료 fragment

package hanium.mobile.did_student.ui.join;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import hanium.mobile.did_student.R;

public class SuccessJoinFragment extends Fragment {

    private Button button;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_join_success, container, false);

        button = root.findViewById(R.id.btn_join_success);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_nav_join_success_to_nav_login);
            }
        });

        return root;
    }
}