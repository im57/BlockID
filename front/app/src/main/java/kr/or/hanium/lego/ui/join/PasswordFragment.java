//회원가입 비밀번호 입력 fragment

package kr.or.hanium.lego.ui.join;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.beautycoder.pflockscreen.PFFLockScreenConfiguration;
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment;
import com.beautycoder.pflockscreen.security.PFResult;
import com.beautycoder.pflockscreen.security.PFSecurityManager;
import com.beautycoder.pflockscreen.security.callbacks.PFPinCodeHelperCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import kr.or.hanium.R;
import kr.or.hanium.lego.JoinActivity;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;
import kr.or.hanium.lego.PreferencesSettings;

public class PasswordFragment extends Fragment implements OnBackPressedListener {

    private View root;

    private PFLockScreenFragment fragment;
    private PFFLockScreenConfiguration.Builder builder;

    private JoinActivity activity;

    private Student student;
    private String body;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_password, container, false);

        activity = (JoinActivity) getActivity();

        student = (Student) getArguments().get("student");

        fragment = new PFLockScreenFragment();
        builder = new PFFLockScreenConfiguration.Builder(getContext())
                .setMode(PFFLockScreenConfiguration.MODE_CREATE)
                .setTitle("비밀번호 6자리를 입력해주십시오")
                .setNewCodeValidation(true)
                .setNewCodeValidationTitle("비밀번호를 다시 입력해주십시오")
                .setUseFingerprint(false)
                .setCodeLength(6);

        fragment.setConfiguration(builder.build());
        fragment.setCodeCreateListener(new PFLockScreenFragment.OnPFLockScreenCodeCreateListener() {
            @Override
            public void onCodeCreated(String encodedCode) {
                //TODO: save somewhere;
                PreferencesSettings.saveToPref(getActivity(), encodedCode);
                surParsing();
            }

            @Override
            public void onNewCodeValidationFailed() {
                Toast.makeText(getContext(), "비밀번호가 불일치합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.pincode_container, fragment).commit();

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(null);
    }

    @Override
    public void onBackPressed() {

    }

    public void surParsing() {
        try {
            //쿼리값 붙이기
            new RestAPITask().execute(getResources().getString(R.string.apiaddress)+getResources().getString(R.string.signup));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class RestAPITask extends AsyncTask<String, Void, String> {
        String jsonStudent;

        //수행 전
        @Override
        protected void onPreExecute() {
            try {
                JSONObject json = new JSONObject();

                json.put("name", student.getName());
                json.put("student_id", student.getStudent_id());
                json.put("university", student.getUniversity());
                json.put("department", student.getDepartment());

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
            } catch (Exception e) {
                // Error calling the rest api
                Log.e("REST_API", "POST method failed: " + e.getMessage());
                e.printStackTrace();
            }
            return result;
        }

        //작업 완료
        @Override
        protected void onPostExecute(String result) {
            Navigation.findNavController(root).navigate(R.id.action_nav_join_password_to_nav_join_success);
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