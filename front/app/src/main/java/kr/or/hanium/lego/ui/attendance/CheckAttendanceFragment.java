//출석체크 fragment

package kr.or.hanium.lego.ui.attendance;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import kr.or.hanium.R;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;

public class CheckAttendanceFragment extends Fragment implements OnBackPressedListener {

    private IntentIntegrator qrScan;

    private AlertDialog.Builder builder;

    private View root;
    private MainActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_attendance_check, container, false);

        activity = (MainActivity)getActivity();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            //Toast.makeText(getActivity(),"camera permission granted",Toast.LENGTH_LONG).show();

        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }

        //scan QR code
        qrScan = new IntentIntegrator(getActivity());

        qrScan.setPrompt("QR코드를 인식해주세요.");
        qrScan.setOrientationLocked(false);
        qrScan.setBeepEnabled(false);
        qrScan.initiateScan();
        return root;
    }

    //camera 퍼미션
    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            //Toast.makeText(getActivity(), "Camera access is required to Scan The Barcode.", Toast.LENGTH_LONG).show();

            // Request the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    100);

        } else {
            //Toast.makeText(getActivity(),"<b>Camera could not be opened.</b>\\nThis occurs when the camera is not available (for example it is already in use) or if the system has denied access (for example when camera access has been disabled).", Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
    }

    @Override
    public void onBackPressed() {
        Navigation.findNavController(root).navigate(R.id.action_go_home);
    }
}