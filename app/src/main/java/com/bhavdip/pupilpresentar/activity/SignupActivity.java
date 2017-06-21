package com.bhavdip.pupilpresentar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bhavdip.pupilpresentar.MainActivity;
import com.bhavdip.pupilpresentar.R;
import com.bhavdip.pupilpresentar.dbsqlite.UsersModel;
import com.bhavdip.pupilpresentar.model.User;

/**
 * Created by bhavdip on 30/5/17.
 */

public class SignupActivity extends BaseActivity {

    EditText edtFullName, edtEmail,edtMobile, edtUsername,edtPassword, edtConfirmPassword;
    RadioGroup radioGroup;
    Button btnSignUp;

    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);

        // Get Refferences of Views
        edtFullName = (EditText) findViewById(R.id.full_name);
        edtEmail = (EditText) findViewById(R.id.email);
        radioGroup = (RadioGroup) findViewById(R.id.radioGrp);
        edtMobile = (EditText) findViewById(R.id.mobile);
        edtUsername = (EditText) findViewById(R.id.username);
        edtPassword = (EditText) findViewById(R.id.password);
        edtConfirmPassword = (EditText) findViewById(R.id.comfirm_password);

        btnSignUp   = (Button) findViewById(R.id.mobile_sign_up_button);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                String fullName = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                String mobile = edtMobile.getText().toString();
                String userName = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();
                String gender = radioButton == null ? "" : radioButton.getText().toString();

                // check if any of the fields are vaccant
                if (userName.equals("") || password.equals("") || confirmPassword.equals("")
                        || mobile.equals("") || email.equals("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Field should not Empty", Snackbar.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if (mobile.length() != 10) {
                    Snackbar.make(findViewById(android.R.id.content), "enter correct mobile no", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (!password.equals(confirmPassword)) {
                    Snackbar.make(findViewById(android.R.id.content), "Password does not match", Snackbar.LENGTH_LONG).show();
                    return;

                } else if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    edtPassword.setError("between 4 and 10 alphanumeric characters");
                    return;
                } else {
//
//                    String fullName;
//                    String email;
//                    String gender;
//                    String mobile;
//                    String userName;
//                    String password;
//                    byte[] image;


                    User user = new User();
                    user.setFullName(fullName);
                    user.setEmail(email);
                    user.setGender(gender);
                    user.setMobile(mobile);
                    user.setUserName(userName);
                    user.setPassword(password);
                    // Save the Data in Database
                    Snackbar.make(findViewById(android.R.id.content), "Account Successfully Created ", Snackbar.LENGTH_LONG).show();

                    UsersModel usersModel = new UsersModel(SignupActivity.this);
                    boolean inserted  = usersModel.insertEntry(user);
                    if (inserted) {
                        showToast("Account Successfully Created.");

                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        intent.putExtra(MainActivity.USER, user);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        showSnackbar("Something went Wrong!");
                    }


//                    Intent intent = new Intent(SignupActivity.this,LoginActivity_new.class);
//                    startActivity(intent);
                }
            }
        });
    }
}
