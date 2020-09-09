//출석체크 fragment

package hanium.mobile.did_student.ui.attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import hanium.mobile.did_student.MainActivity;
import hanium.mobile.did_student.R;
import hanium.mobile.did_student.ui.mypage.CheckPasswordFragment;

public class CheckAttendanceFragment extends Fragment {

    private CheckAttendanceViewModel checkAttendanceViewModel;

    private IntentIntegrator qrScan;

    private AlertDialog.Builder builder;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        checkAttendanceViewModel =
                ViewModelProviders.of(this).get(CheckAttendanceViewModel.class);
        root = inflater.inflate(R.layout.fragment_attendance_check, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        checkAttendanceViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        //scan QR code
        qrScan = new IntentIntegrator(getActivity());

        qrScan.setPrompt("QR코드를 인식해주세요.");
        qrScan.setOrientationLocked(false);
        qrScan.initiateScan();
        return root;
    }

    //Getting the scan results
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(getActivity(), "스캔완료!", Toast.LENGTH_SHORT).show();

                builder = new AlertDialog.Builder(getContext());

                builder.setMessage("출석체크 완료")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}