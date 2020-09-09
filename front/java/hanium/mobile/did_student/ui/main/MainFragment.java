//메인 fragment

package hanium.mobile.did_student.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import hanium.mobile.did_student.R;
import hanium.mobile.did_student.ui.mypage.CheckPasswordFragment;

public class MainFragment extends Fragment {

    private MainViewModel mainViewModel;

    private IntentIntegrator qrScan;

    private TextView tvSchool;
    private TextView tvName;
    private TextView tvMajor;
    private TextView tvNum;
    private TextView tvDate;

    private FloatingActionButton fabCheck;
    private FloatingActionButton fabReissue;

    private AlertDialog.Builder builder;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        root = inflater.inflate(R.layout.fragment_main, container, false);

        tvSchool = root.findViewById(R.id.text_main_school);
        tvName = root.findViewById(R.id.text_main_name);
        tvMajor = root.findViewById(R.id.text_main_school);
        tvNum = root.findViewById(R.id.text_main_num);
        tvDate = root.findViewById(R.id.text_main_date);

        mainViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String m) {
                tvSchool.setText(m);
               /* tvSchool.setText((String) m.get("school"));
                tvName.setText((String) m.get("name"));
                tvMajor.setText((String) m.get("major"));
                tvNum.setText((String) m.get("num"));
                tvDate.setText((String) m.get("date"));*/
            }
        });

        qrScan = new IntentIntegrator(getActivity());

        fabCheck = root.findViewById(R.id.fab_main_check);
        fabReissue = root.findViewById(R.id.fab_main_reissue);

        //출석 버튼 클릭
        fabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permission is already available, start camera preview
                    Toast.makeText(getActivity(),"camera permission granted",Toast.LENGTH_LONG).show();

                } else {
                    // Permission is missing and must be requested.
                    requestCameraPermission();
                }

                //scan QR code
                qrScan.setPrompt("QR코드를 인식해주세요.");
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });

        //재발급 버튼 클릭
        fabReissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());

                    builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("출석체크 완료")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Toast.makeText(getActivity(), "Camera access is required to Scan The Barcode.",
                    Toast.LENGTH_LONG).show();

            // Request the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    100);

        } else {
            Toast.makeText(getActivity(),
                    "<b>Camera could not be opened.</b>\\nThis occurs when the camera is not available (for example it is already in use) or if the system has denied access (for example when camera access has been disabled).", Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, 100);
        }
    }
}