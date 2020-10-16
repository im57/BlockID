package kr.or.hanium.lego;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import kr.or.hanium.R;
import kr.or.hanium.lego.ui.attendance.CheckAttendance;
import kr.or.hanium.lego.ui.attendance.ListAttendanceFragment;
import kr.or.hanium.lego.ui.join.PasswordFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;

    private OnBackPressedListener listener;

    private AlertDialog.Builder builder;

    private String idx;
    private String body;

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
                //navController.navigate(R.id.action_to_check);
                CheckAttendance checkAttendance = new CheckAttendance();
                checkAttendance.check(MainActivity.this);
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

        navigationView.getMenu().findItem(R.id.nav_attendance_check).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CheckAttendance checkAttendance = new CheckAttendance();
                checkAttendance.check(MainActivity.this);
                return true;
            }
        });

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

    public void onResume() {
        super.onResume();
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
        if(this.drawer.isDrawerOpen(GravityCompat.START)){
            this.drawer.closeDrawers();
        } else{
            if(listener!=null){
                listener.onBackPressed();
            }else{
                super.onBackPressed();
            }
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

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                //qrcode 결과가 있으면
                try {
                    String temp = result.getContents();

                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());

                    idx = obj.getString("idx");

                    parsing();

                } catch (JSONException e) {
                    e.printStackTrace();
                    String temp = result.getContents();

                    Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //fab버튼 클릭
    public void anim() {
        if (isFabOpen) {
            fabHome.startAnimation(fab_close);
            fabCheck.startAnimation(fab_close);
            fabHome.setClickable(false);
            fabCheck.setClickable(false);
            isFabOpen = false;
        }
        else {
            fabHome.startAnimation(fab_open);
            fabCheck.startAnimation(fab_open);
            fabHome.setClickable(true);
            fabCheck.setClickable(true);
            isFabOpen = true;
        }
    }

    public void closeFab(){
        if (isFabOpen) {
            fabHome.startAnimation(fab_close);
            fabCheck.startAnimation(fab_close);
            fabHome.setClickable(false);
            fabCheck.setClickable(false);
            isFabOpen = false;
        }
    }

    public void parsing() {
        try {
            //쿼리값 붙이기
            new RestAPITask().execute(getResources().getString(R.string.apiaddress)+getResources().getString(R.string.qr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class RestAPITask extends AsyncTask<String, Void, String> {

        //수행 전
        @Override
        protected void onPreExecute() {
            try {
                JSONObject json = new JSONObject();

                json.put("class_id", idx);
                json.put("holder_id", "1");

                body = json.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... Strings) {
            String result = null;

            try {
                result = downloadContents(Strings[0]);
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
            builder = new AlertDialog.Builder(MainActivity.this);

            builder.setMessage("강의 번호 " + idx + "\n출석체크 완료")
                    .setCancelable(false)
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

    /* 주소(address)에 접속하여 문자열 데이터를 수신한 후 반환 */
    protected String downloadContents(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    // URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        // 클라이언트 아이디 및 시크릿 그리고 요청 URL 선언
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("content-type", "application/json");

        writeStream(conn);

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }

    protected void writeStream(HttpURLConnection conn) {
        try {
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(body); //json 형식의 메세지 전달
            wr.flush();
            wr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    protected String readStreamToString(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("result", result.toString());
        return result.toString();
    }
}

