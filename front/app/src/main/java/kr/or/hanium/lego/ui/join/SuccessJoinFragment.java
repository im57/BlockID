//탈퇴 완료 fragment

package kr.or.hanium.lego.ui.join;

import android.content.Intent;
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

import kr.or.hanium.R;
import kr.or.hanium.lego.JoinActivity;
import kr.or.hanium.lego.LoginActivity;
import kr.or.hanium.lego.OnBackPressedListener;
import kr.or.hanium.lego.PreferencesSettings;

public class SuccessJoinFragment extends Fragment implements OnBackPressedListener {

    private Button button;

    private View root;

    private JoinActivity activity;
    private boolean first = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_join_success, container, false);

        activity = (JoinActivity)getActivity();

        button = root.findViewById(R.id.btn_join_success);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}