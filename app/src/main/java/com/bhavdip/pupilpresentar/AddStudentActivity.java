package com.bhavdip.pupilpresentar;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bhavdip.pupilpresentar.activity.BaseActivity;
import com.bhavdip.pupilpresentar.dbsqlite.StudentModel;
import com.bhavdip.pupilpresentar.utility.Utility;

import java.io.IOException;

public class AddStudentActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CROP = 127;
    public static final int REQUEST_CAMERA = 128;
    public static final int REQUEST_GALLERY = 129;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 990;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 991;

    EditText roll_no;
    EditText first_name;
    EditText last_name;
    EditText email;
    EditText parent_mobile;
    EditText parent_occupation;
    RadioGroup radioGrp;
    Button btnRegister;
    ImageView imageViewProfilePic;
    private RadioButton radioButton;
    private Uri picUri;
    private Bitmap thePic;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Get Refferences of Views
        roll_no = (EditText) findViewById(R.id.roll_no);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        parent_mobile = (EditText) findViewById(R.id.parent_mobile);
        parent_occupation = (EditText) findViewById(R.id.parent_occupation);
        radioGrp = (RadioGroup) findViewById(R.id.radioGrp);

        btnRegister= (Button) findViewById(R.id.mobile_register_button);
        imageViewProfilePic = (ImageView) findViewById(R.id.imgViw_profile_pic);
        imageViewProfilePic.setOnClickListener(this);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (Utility.isValidEmail(email.getText().toString()) && email.getText().toString().length() > 0) {
                        showSnackbar("valid email address");
                        // or
//                    email.setError("valid email");
                    } else {
                        showSnackbar("Invalid email address");
                        //or
                        email.setError("invalid email");
                    }
                }
            }
        });


        btnRegister .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strRollno = roll_no.getText().toString();
                String strFirstname = first_name.getText().toString();
                String strLastname = last_name.getText().toString();
                String strEmail = email.getText().toString();
                String strMobile = parent_mobile.getText().toString();
                String strOccupation = parent_occupation.getText().toString();
                // get selected radio button from radioGroup
                int selectedId = radioGrp.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                String gender = radioButton == null ? "" : radioButton.getText().toString();

                // check if any of the fields are vaccant
                if (strFirstname.equals("") || strLastname.equals("") || strEmail.equals("") || strMobile.equals("")) {
                    showSnackbar("Field should not Empty");
                    return;
                }
                if (!Utility.isValidEmail(strEmail)){
                    showSnackbar("Please enter valid email id.");
                    return;
                }
                if (thePic==null && picUri==null){
                    showSnackbar("Please capture your picture.");
                    return;
                }
                // check if both password matches
                if (strMobile.length() != 10) {
                    showSnackbar("Enter correct mobile no.");
                    return;
                } else {

                    // Save the Data in Database
                    StudentModel studentModel = new StudentModel(AddStudentActivity.this);
                    byte[] image = new byte[0];
                    if (picUri!=null){
                        try {
                            thePic = MediaStore.Images.Media.getBitmap(getContentResolver(),picUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (thePic!=null){
                        image = Utility.getBitmapAsByteArray(thePic);
                    }
                    boolean inserted = studentModel.insertEntry(strRollno,gender,strFirstname,strLastname, strEmail, strMobile, strOccupation,image);
                    if (inserted) {
                        showSnackbar("Account Successfully Created.");


                        Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        showSnackbar("Something went Wrong!");
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(AddStudentActivity.this, R.style.DialogSlideAnim);

        dialog.setContentView(R.layout.dialog_profile_pic_menu);

        dialog.setTitle("Profile photo");

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        //Grab the window of the dialog, and change the width
        wlp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        // set the dialog_profile_pic_menu dialog components - text, image and button
        TextView btnGallery = (TextView) dialog.findViewById(R.id.btn_gallery);
        btnGallery.setText("Gallery");
        TextView btnCamera = (TextView) dialog.findViewById(R.id.btn_camera);
        btnCamera.setText("Camera");
        TextView btnRemovePhoto = (TextView) dialog.findViewById(R.id.btn_remove_photo);
        btnRemovePhoto.setText("Remove photo");

        if (picUri==null){
            btnRemovePhoto.setEnabled(false);
        }else{
            btnRemovePhoto.setEnabled(true);
        }

        btnRemovePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewProfilePic.setImageDrawable(getResources().getDrawable(R.drawable.img_profile_pic));

                picUri = null;
                if (dialog != null)
                    dialog.dismiss();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(AddStudentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AddStudentActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions(AddStudentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                            // Show an expanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(AddStudentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        ActivityCompat.requestPermissions(AddStudentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                } else {
                    launchCamera();

                }

                if (dialog != null)
                    dialog.dismiss();
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSnackbar("Oopps! This functionality coming Soon :)");

                if (dialog != null)
                    dialog.dismiss();
            }
        });

        dialog.show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    Intent intent = buildGalleryIntent(mCropParams);
//                    startActivityForResult(intent, REQUEST_CROP);
                } else {
                    showSnackbar("The app was not allowed to write to your storage. " +
                            "Hence, it cannot function properly. Please consider granting it this permission");
                }
                return;
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    launchCamera();
                } else {
                    showSnackbar("The app was not allowed to write to your storage. " +
                            "Hence, it cannot function properly. Please consider granting it this permission");
                }
                return;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                // get the Uri for the captured image

                if (data != null) {
                    if (data.getData() != null) {
                        picUri = data.getData();
                    } else if (data.getExtras().get("data") != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        picUri = getImageUri(this, bitmap);
                    }
                    performCrop();

                } else {
                    showSnackbar("Returned data is null");
                }
            }
            // user is returning from cropping the image
            else if (requestCode == REQUEST_CROP) {
                // get the returned data
                if (data != null) {
                    if (data.getData() != null) {

                        picUri = data.getData();
                        imageViewProfilePic.setImageURI(picUri);
                    } else if (data.getExtras() != null) {

                        Bundle extras = data.getExtras();
                        // get the cropped bitmap
                        thePic = extras.getParcelable("data");
                        imageViewProfilePic.setImageBitmap(thePic);
                    }
                } else {
                    showSnackbar("Returned data is null");
                }



            }
        }
    }

    /**
     * this function does the crop operation.
     */
    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQUEST_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            showSnackbar("This device doesn't support the crop action!");
        }
    }

    private void launchCamera() {
        try {
            //use standard intent to capture an image
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            //we will handle the returned data in onActivityResult

            startActivityForResult(captureIntent, REQUEST_CAMERA);

        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support capturing images!";

            showSnackbar(errorMessage);
        }
    }
}
