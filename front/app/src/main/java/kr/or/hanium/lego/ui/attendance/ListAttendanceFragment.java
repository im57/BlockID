//출석조회 fragment

package kr.or.hanium.lego.ui.attendance;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import kr.or.hanium.R;
import kr.or.hanium.lego.HolderDBHelper;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;

public class ListAttendanceFragment extends Fragment implements OnBackPressedListener {

    private Spinner spinner;

    private ListView lvAttendance = null;
    private ArrayList<Attendance> attendances;
    private ArrayList<String> subjectList;

    private AttendanceAdapter attendanceAdapter;
    private ArrayAdapter<String> subjectAdapter;

    private View root;
    private MainActivity activity;

    private int idx =-1;
    private String type;

    private HolderDBHelper helper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String holder_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_attendance_list, container, false);

        activity = (MainActivity)getActivity();

        subjectList = new ArrayList<>();
        subjectList.add("수업을 선택해주세요");

        attendances = new ArrayList<>();

        helper = new HolderDBHelper(getContext());

        //메뉴 열려있으면 닫기
        activity.closeMenu();

        spinner = (Spinner)root.findViewById(R.id.spinner_subject);
        subjectAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, subjectList);
        spinner.setAdapter(subjectAdapter);

        //class 받아오기
        parsingClass();

        lvAttendance = root.findViewById(R.id.list_attendance);
        attendanceAdapter = new AttendanceAdapter(getActivity(), R.layout.listview_attendance, null);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           //과목 선택
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attendances.clear();

                idx = position;
                if(idx != 0) {
                    //출석이력 받아오기
                    parsingAttendance();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.closeFab();
    }

    @Override
    public void onBackPressed() {
        Navigation.findNavController(root).navigate(R.id.action_go_home);
    }

    //클래스 받아오기
    public void parsingClass() {
        try {
            type = "class";
            new RestAPITask().execute(getResources().getString(R.string.apiaddress)+getResources().getString(R.string.class_list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //출석이력 받아오기
    public void parsingAttendance() {
        try {
            db = helper.getReadableDatabase();

            cursor = db.rawQuery("select * from " + HolderDBHelper.TABLE_NAME, null);
            cursor.moveToNext();

            holder_id = cursor.getString(cursor.getColumnIndex("holder_id"));

            cursor.close();
            helper.close();

            type = "attendance";
            new RestAPITask().execute(getResources().getString(R.string.apiaddress)+getResources().getString(R.string.attendance_list)+"?class_id="+idx+"&holder_id=" + holder_id);
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
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
            }

            return result;
        }

        //작업 완료
        @Override
        protected void onPostExecute(String result) {
            if(type.equals("attendance")) {
                parse(result);

                attendanceAdapter.setList(attendances);
                lvAttendance.setAdapter(attendanceAdapter);
            } else{
                parse(result);
                subjectAdapter.notifyDataSetChanged();

                //QR스캔으로 이력조회 넘어온 경우
                if(getArguments() != null){
                    idx = Integer.parseInt(getArguments().getString("idx"));

                    if(idx != 0){
                        spinner.setSelection(idx);
                    }
                }
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
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setDoInput(true);
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

        return result.toString();
    }

    //json parsing
    public void parse(String json){
        try{
            JSONArray array = new JSONArray(json);
            JSONObject object;
            Attendance attendance;

            for(int i = 0; i< array.length(); i++) {
                object = array.getJSONObject(i);
                attendance = new Attendance();

                if (type.equals("class")) {
                    //클래스리스트 저장
                    subjectList.add(object.getString("name"));
                } else {
                    //출석리스트 저장
                    attendance.setAttendance_time(object.getString("time"));
                    attendance.setAttendance_state(object.getString("status"));

                    attendances.add(attendance);
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}