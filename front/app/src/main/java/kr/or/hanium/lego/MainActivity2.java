package kr.or.hanium.lego;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.or.hanium.R;

public class MainActivity2 extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;

    private OnBackPressedListener listener;

    private AlertDialog.Builder builder;

    private String idx;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab;
    private FloatingActionButton fabHome;
    private FloatingActionButton fabCheck;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fabCheck = (FloatingActionButton) findViewById(R.id.fab_main_check);
        fabHome = (FloatingActionButton) findViewById(R.id.fab_go_home);

        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });

        fabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_to_check);
            }
        });

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_go_home);
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main,
                R.id.nav_mypage, R.id.nav_mypage_edit, R.id.nav_mypage_edit_check_password,
                R.id.nav_mypage_edit_change_password, R.id.nav_mypage_edit_success_change_password,
                R.id.nav_mypage_edit_check_password_withdraw, R.id.nav_mypage_withdraw,
                R.id.nav_attendance, R.id.nav_attendance_check, R.id.nav_attendance_list,
                R.id.nav_notice, R.id.nav_notice_notice, R.id.nav_notice_notice_detail, R.id.nav_notice_developer)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

    //scan결과 받기
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                try {
                    String temp = result.getContents();

                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());
                    builder = new AlertDialog.Builder(this);

                    idx = obj.getString("idx"); //나중에 삭제

                    //surParsing();

                    builder.setMessage("강의 번호 " + idx + "\n출석체크 완료")    //나중에 삭제
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("idx", idx);
                                    navController.navigate(R.id.action_check_to_list, bundle);
                                }
                            }).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    String temp = result.getContents();

                    Toast.makeText(MainActivity2.this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onPaused() {
        close();
    }

    //fab버튼 클릭
    public void anim() {
        if (isFabOpen) {
            close();
        } else {
            fabHome.startAnimation(fab_open);
            fabCheck.startAnimation(fab_open);
            fabHome.setClickable(true);
            fabCheck.setClickable(true);
            isFabOpen = true;
        }
    }

    public void close(){
        fabHome.startAnimation(fab_close);
        fabCheck.startAnimation(fab_close);
        fabHome.setClickable(false);
        fabCheck.setClickable(false);
        isFabOpen = false;
    }

    public void surParsing() {
        try {
            //쿼리값 붙이기
            new RestAPITask().execute(getResources().getString(R.string.apiaddress)+getResources().getString(R.string.qr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class RestAPITask extends AsyncTask<String, Void, String> {
        String jsonStudent;

        //수행 전
        @Override
        protected void onPreExecute() {
            jsonStudent = "{'class_id':'" + idx + "'}";
        }

        @Override
        protected String doInBackground(String... Strings) {
            String result = null;

            try {
                // Open the connection
                URL url = new URL(Strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setDefaultUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("content-type", "application/json");

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(jsonStudent); //json 형식의 메세지 전달
                wr.flush();

                // Get the stream
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();

                // Set the result
                result = builder.toString();
                return result;
            }
            catch (Exception e) {
                // Error calling the rest api
                Log.e("REST_API", "POST method failed: " + e.getMessage());
                e.printStackTrace();
            }
            return result;
        }

        //작업 완료
        @Override
        protected void onPostExecute(String result) {
            builder = new AlertDialog.Builder(MainActivity2.this);

            builder.setMessage("강의 번호 " + idx + "\n출석체크 완료")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bundle = new Bundle();
                            bundle.putString("idx", idx);
                            navController.navigate(R.id.action_check_to_list, bundle);
                        }
                    }).show();
        }
    }
}

