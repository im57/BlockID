package kr.or.hanium.lego;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import kr.or.hanium.R;

public class JoinActivity extends AppCompatActivity {

    private OnBackPressedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

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
