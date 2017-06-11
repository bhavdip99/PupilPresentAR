package com.bhavdip.pupilpresentar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bhavdip.pupilpresentar.dbsqlite.StudentModel;

/**
 * Created by bhavdip on 12/6/17.
 */

public class AddStudentActivity extends AppCompatActivity{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);


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
                    Snackbar.make(findViewById(android.R.id.content), "Field should not Empty", Snackbar.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if (strMobile.length() != 10) {
                    Snackbar.make(findViewById(android.R.id.content), "enter correct mobile no", Snackbar.LENGTH_LONG).show();
                    return;
                } else {

                    // Save the Data in Database
                    StudentModel studentModel = new StudentModel(AddStudentActivity.this);
                    studentModel.insertEntry(strRollno,gender,strFirstname,strLastname, strEmail, strMobile, strOccupation);
                    Snackbar.make(findViewById(android.R.id.content), "Account Successfully Created ", Snackbar.LENGTH_LONG).show();

                    Intent intent = new Intent(AddStudentActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

    }
}
