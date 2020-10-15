//탈퇴 완료 fragment

<<<<<<< HEAD
<<<<<<< HEAD
package hanium.mobile.did_student.ui.join;

import android.os.Bundle;
=======
=======
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
package kr.or.hanium.lego.ui.join;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
=======
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.widget.Toast;
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
=======
import android.widget.Toast;
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

<<<<<<< HEAD
<<<<<<< HEAD
import hanium.mobile.did_student.R;

public class SuccessJoinFragment extends Fragment {
=======
=======
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
import kr.or.hanium.R;
import kr.or.hanium.lego.JoinActivity;
import kr.or.hanium.lego.LoginActivity;
import kr.or.hanium.lego.OnBackPressedListener;
import kr.or.hanium.lego.PreferencesSettings;

public class SuccessJoinFragment extends Fragment implements OnBackPressedListener {
<<<<<<< HEAD
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
=======
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa

    private Button button;

    private View root;

<<<<<<< HEAD
<<<<<<< HEAD
=======
    private JoinActivity activity;
    private boolean first = true;

>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
=======
    private JoinActivity activity;
    private boolean first = true;

>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_join_success, container, false);

<<<<<<< HEAD
<<<<<<< HEAD
=======
        activity = (JoinActivity)getActivity();

>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
=======
        activity = (JoinActivity)getActivity();

>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
        button = root.findViewById(R.id.btn_join_success);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
<<<<<<< HEAD
                Navigation.findNavController(root).navigate(R.id.action_nav_join_success_to_nav_login);
=======
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
=======
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
            }
        });

        return root;
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
    }

    @Override
    public void onBackPressed() {
        if(first){
            first = false;
            Toast.makeText(getActivity(), "한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            activity.finishAffinity();//종료
            System.runFinalization();
            System.exit(0);
        }
    }
<<<<<<< HEAD
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
=======
>>>>>>> 639c271f49286c11711b60597ecf15937c3777aa
}