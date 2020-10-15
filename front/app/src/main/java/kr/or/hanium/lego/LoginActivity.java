package kr.or.hanium.lego;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import kr.or.hanium.R;
import kr.or.hanium.lego.ui.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    private LoginFragment fragment;

    private OnBackPressedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void setOnBackPressedListener(OnBackPressedListener listener){
        this.listener = listener;
    }

    //back 버튼 클릭 처리
    @Override
    public void onBackPressed() {
        if(listener!=null){
            listener.onBackPressed();
        }else{
            super.onBackPressed();
        }
    }
}
