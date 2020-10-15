//메인 fragment

package kr.or.hanium.lego.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import kr.or.hanium.R;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;
import kr.or.hanium.lego.ui.join.Student;

public class MainFragment extends Fragment  implements OnBackPressedListener {

    private TextView tvUniversity;
    private TextView tvName;
    private TextView tvDepartment;
    private TextView tvNum;
    private TextView tvDate;

    private FloatingActionButton fabReissue;

    private View root;
    private MainActivity activity;

    private boolean first = true;

    private Student student;
    private String type;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);

        activity = (MainActivity)getActivity();

        tvUniversity = root.findViewById(R.id.text_main_university);
        tvName = root.findViewById(R.id.text_main_name);
        tvDepartment = root.findViewById(R.id.text_main_department);
        tvNum = root.findViewById(R.id.text_main_num);
        tvDate = root.findViewById(R.id.text_main_date);

        fabReissue = root.findViewById(R.id.fab_main_reissue);

        student = new Student();

        surParsingIssue();

        //재발급 버튼 클릭
        fabReissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surParsingReissue();
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


    public void surParsingIssue() {
        try {
            type = "issue";
            new RestAPITask().execute(getResources().getString(R.string.apiaddress)+getResources().getString(R.string.idcard)+"1");  //수정
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surParsingReissue() {
        try {
            type = "reissue";
            new RestAPITask().execute(getResources().getString(R.string.apiaddress)+getResources().getString(R.string.reIssue)+"1");  //수정
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class RestAPITask extends AsyncTask<String, Void, String> {

        //수행 전
        @Override
        protected void onPreExecute() {
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
            parse(result);

            tvUniversity.setText(student.getUniversity());
            tvName.setText(student.getName());
            tvDepartment.setText(student.getDepartment());
            tvNum.setText(student.getStudent_id());
            tvDate.setText(student.getExpireDate());

            if(student.getStatus().equals("ACTIVATED")){
                fabReissue.hide();
            }
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
        if(type.equals("issue")) {
            conn.setRequestMethod("GET");
        }else{
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
        }
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setDoInput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("content-type", "application/json");

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
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


    //json parsing
    public void parse(String json){
        try{
            JSONObject object = new JSONObject(json);

            student.setName(object.getString("name"));
            student.setStudent_id(object.getString("studentId"));
            student.setUniversity(object.getString("university"));
            student.setDepartment(object.getString("department"));
            student.setExpireDate(object.getString("expireDate"));
            student.setHolder_id(object.getString("holder_id"));
            student.setStatus(object.getString("status"));


        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}