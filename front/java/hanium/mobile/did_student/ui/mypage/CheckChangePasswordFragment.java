//변경할 비밀번호 재입력 fragment

package hanium.mobile.did_student.ui.mypage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class CheckChangePasswordFragment extends Fragment {

    private TextView tvTitle;

    private View root;

    private StringBuffer password;
    private StringBuffer input;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnBack;
    private Button btnClear;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_password, container, false);

        tvTitle = root.findViewById(R.id.text_password_title);

        tvTitle.setText("변경할 비밀번호를 다시 입력해주십시오");

        tv1 = root.findViewById(R.id.text_password1);
        tv2 = root.findViewById(R.id.text_password2);
        tv3 = root.findViewById(R.id.text_password3);
        tv4 = root.findViewById(R.id.text_password4);
        tv5 = root.findViewById(R.id.text_password5);
        tv6 = root.findViewById(R.id.text_password6);

        btn0 = root.findViewById(R.id.btn_password_0);
        btn1 = root.findViewById(R.id.btn_password_1);
        btn2 = root.findViewById(R.id.btn_password_2);
        btn3 = root.findViewById(R.id.btn_password_3);
        btn4 = root.findViewById(R.id.btn_password_4);
        btn5 = root.findViewById(R.id.btn_password_5);
        btn6 = root.findViewById(R.id.btn_password_6);
        btn7 = root.findViewById(R.id.btn_password_7);
        btn8 = root.findViewById(R.id.btn_password_8);
        btn9 = root.findViewById(R.id.btn_password_9);
        btnBack = root.findViewById(R.id.btn_password_back);
        btnClear = root.findViewById(R.id.btn_password_clear);

        password = new StringBuffer();

        //비밀번호 넘겨받음
        input = (StringBuffer) getArguments().getSerializable("password");

        btn0.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(0);
                input();
            }
        });
        btn1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(1);
                input();
            }
        });
        btn2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(2);
                input();
            }
        });
        btn3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(3);
                input();
            }
        });
        btn4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(4);
                input();
            }
        });
        btn5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(5);
                input();
            }
        });
        btn6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(6);
                input();
            }
        });
        btn7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(7);
                input();
            }
        });
        btn8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(8);
                input();
            }
        });
        btn9.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.append(9);
                Log.d("password", password.toString());
                input();
            }
        });
        btnBack.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.length() == 1){
                    tv1.setText("");
                }
                else if(password.length() == 2){
                    tv2.setText("");
                }
                else if(password.length() == 3){
                    tv3.setText("");
                }
                else if(password.length() == 4){
                    tv4.setText("");
                }
                else if(password.length() == 5){
                    tv5.setText("");
                }

                if(password.length() > 0) {
                    password.deleteCharAt(password.length() - 1);
                }
            }
        });
        btnClear.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.delete(0, password.length());
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                tv4.setText("");
                tv5.setText("");
                tv6.setText("");
            }
        });

        return root;
    }

    public void input(){
        if(password.length() == 1){
            tv1.setText("*");
        }
        else if(password.length() == 2){
            tv2.setText("*");
        }
        else if(password.length() == 3){
            tv3.setText("*");
        }
        else if(password.length() == 4){
            tv4.setText("*");
        }
        else if(password.length() == 5){
            tv5.setText("*");
        }
        else if(password.length() == 6){
            tv6.setText("*");

            //비밀번호 일치
            if(password.toString().equals(input.toString())){
                Navigation.findNavController(root).navigate(R.id.action_check_change_password_to_success_change_password);
            }
            //비밀번호 불일치
            else {
                //비밀번호 다르면
                Bundle bundle = new Bundle();
                bundle.putBoolean("error", true);
                Navigation.findNavController(root).navigate(R.id.action_check_change_password_wrong, bundle);
            }
        }
    }
}