package com.bhavdip.pupilpresentar.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.bhavdip.pupilpresentar.MainActivity;
import com.bhavdip.pupilpresentar.R;
import com.bhavdip.pupilpresentar.dbsqlite.UsersModel;
import com.bhavdip.pupilpresentar.model.User;

import static android.view.Window.FEATURE_NO_TITLE;
import static com.bhavdip.pupilpresentar.utility.Constants.KEY_PREFS_USERNAME;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    static Context context;
    private EditText et_login_username;
    private EditText et_login_password;
    private TextView wantRegister;
    private TextView loginButton;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.activity_login));

        context = this;
        et_login_username = (EditText) findViewById(R.id.et_login_username);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        loginButton = (TextView) findViewById(R.id.l_login);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        /*((TextView) findViewById(R.id.l_login)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                *//*if (view.getId() != R.id.l_login) {
                    showToast("if");
                }
                while ((!LoginActivity.this.validateCredentails(et_login_username.getText().toString(), et_login_password.getText().toString()))) {
                    showToast("while");
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);*//*
            }
        });
*/
        wantRegister = (TextView) findViewById(R.id.want_register);
        wantRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });


    }

    private boolean validateCredentails(String paramString1, String paramString2) {
        if (paramString1.trim().length() == 0) {
            showToast(getResources().getString(R.string.error_empty_username));
            return false;
        }
        if (paramString2.trim().length() == 0) {
            showToast(getResources().getString(R.string.error_empty_password));
            return false;
        }
        return true;
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = et_login_username.getText().toString();
        String password = et_login_password.getText().toString();

        // TODO: Implement your own authentication logic here.

        UsersModel usersModel = new UsersModel(LoginActivity.this);
        User user = usersModel.getSelectedEntry(username);

        // check if the Stored password matches with  Password entered by user
        if (password.equals(user.getPassword())) {
            onLoginSuccess(user);
            progressDialog.dismiss();
        } else {
            onLoginFailed();
            progressDialog.dismiss();
        }

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
        // On complete call either onLoginSuccess or onLoginFailed

//                    }
//                }, 500);
    }

    public void onLoginSuccess(User user) {
        loginButton.setEnabled(true);
        showToast("Congratulation!, successfully Login");

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        Bundle b = new Bundle();
//        b.putParcelable(MainActivity.USER, user);

        // fetch the Password form database for respective user name
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PREFS_USERNAME,user.getUserName());
        editor.apply();

        intent.putExtra(MainActivity.USER, user);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //                finish();
    }

    private void onLoginFailed() {

        Snackbar.make(findViewById(android.R.id.content), "Login failed!, Please check entered details", Snackbar.LENGTH_LONG)
                .show();

//        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile = et_login_username.getText().toString();
        String password = et_login_password.getText().toString();

//        if (email.length()!=10) {
//            return false;
//        } else {
//            return android.util.Patterns.PHONE.matcher(email).matches();
//        }

        if (mobile.isEmpty()) {
            et_login_username.setError("enter a your user name.");
            valid = false;
        } else {
            et_login_username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_login_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_login_password.setError(null);
        }

        return valid;
    }
}