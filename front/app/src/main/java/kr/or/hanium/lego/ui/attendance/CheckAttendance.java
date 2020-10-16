//출석체크 fragment

package kr.or.hanium.lego.ui.attendance;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.zxing.integration.android.IntentIntegrator;

import kr.or.hanium.R;
import kr.or.hanium.lego.MainActivity;
import kr.or.hanium.lego.OnBackPressedListener;

public class CheckAttendance  {

    private IntentIntegrator qrScan;

    public void check(Activity activity) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            //Toast.makeText(getActivity(),"camera permission granted",Toast.LENGTH_LONG).show();

        } else {
            // Permission is missing and must be requested.
            requestCameraPermission(activity);
        }

        //scan QR code
        qrScan = new IntentIntegrator(activity);

        qrScan.setPrompt("QR코드를 인식해주세요.");
        qrScan.setOrientationLocked(false);
        qrScan.setBeepEnabled(false);
        qrScan.initiateScan();
    }

    //camera 퍼미션
    private void requestCameraPermission(Activity activity) {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            //Toast.makeText(getActivity(), "Camera access is required to Scan The Barcode.", Toast.LENGTH_LONG).show();

            // Request the permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    100);

        } else {
            //Toast.makeText(getActivity(),"<b>Camera could not be opened.</b>\\nThis occurs when the camera is not available (for example it is already in use) or if the system has denied access (for example when camera access has been disabled).", Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

}