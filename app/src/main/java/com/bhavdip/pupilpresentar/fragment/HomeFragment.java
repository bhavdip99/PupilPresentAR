package com.bhavdip.pupilpresentar.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bhavdip.pupilpresentar.MainActivity;
import com.bhavdip.pupilpresentar.R;
import com.bhavdip.pupilpresentar.activity.AttendanceActivity;
import com.bhavdip.pupilpresentar.activity.ShowProfileActivity;
import com.bhavdip.pupilpresentar.barcodescanning.BarcodeCaptureActivity;
import com.bhavdip.pupilpresentar.dbsqlite.AttendanceModel;
import com.bhavdip.pupilpresentar.dbsqlite.StudentModel;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final int NOTIFICATIONID = 143;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private LinearLayout llAttendance;
    private LinearLayout llBarcode;
    private LinearLayout llStudent;
    private LinearLayout llProfile;
    private Context context;
    private LinearLayout mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        context = getContext();

        intializeView(view);

        setOnClick();

        return view;
    }

    private void setOnClick() {
        llStudent.setOnClickListener(this);
        llBarcode.setOnClickListener(this);
        llAttendance.setOnClickListener(this);
        llProfile.setOnClickListener(this);
    }

    private void intializeView(View view) {

        mRoot = (LinearLayout) view.findViewById(R.id.root);

        llStudent = (LinearLayout) view.findViewById(R.id.ll_student);
        llBarcode = (LinearLayout) view.findViewById(R.id.ll_barcode);
        llAttendance = (LinearLayout) view.findViewById(R.id.ll_attendance);
        llProfile = (LinearLayout) view.findViewById(R.id.ll_profile);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.ll_student:
                fragment = new StudentFragment();

//                intent = new Intent(context,StudentActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_barcode:
                // launch barcode activity.
                intent = new Intent(context, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false);

                startActivityForResult(intent, RC_BARCODE_CAPTURE);

                break;
            case R.id.ll_attendance:

                intent = new Intent(context, AttendanceActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_profile:
                intent = new Intent(context, ShowProfileActivity.class);
                startActivity(intent);
//                fragment = new StudentFragment();

                break;
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
//                fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            MainActivity.viewIsAtHome = false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    // TODO Save barcode to the database from here
                    Toast.makeText(context, barcode.displayValue, Toast.LENGTH_LONG).show();

                    StudentModel studentModel = new StudentModel(context);
                    String storedRollNo = studentModel.getSinlgeEntry(barcode.displayValue);

                    if (barcode.displayValue.equals(storedRollNo)) {
                        AttendanceModel attendanceModel = new AttendanceModel(context);
                        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                        attendanceModel.insertEntry(barcode.displayValue, date, "PRESENT");
                        Snackbar.make(mRoot, "Attendance Presented for " + barcode.displayValue, Snackbar.LENGTH_LONG).show();

                        showNotification(barcode.displayValue);
                    } else {
                        Snackbar.make(mRoot, "No Student Found!", Snackbar.LENGTH_LONG).show();

                    }


                } else {
                    Log.d("", "No barcode captured, intent data is null");
                }
            } else {
                Toast.makeText(context, String.format(getString(R.string.barcode_error), CommonStatusCodes.getStatusCodeString(resultCode)), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showNotification(String displayValue) {


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Present Student Alert!");
        mBuilder.setContentText("Hi, This " + displayValue + " is Present in TechRefresh!");
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.

        mNotificationManager.notify(NOTIFICATIONID, mBuilder.build());
    }
}
