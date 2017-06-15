package com.bhavdip.pupilpresentar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bhavdip.pupilpresentar.HomeActivity;
import com.bhavdip.pupilpresentar.R;

public class LoginActivity extends BaseActivity {
    static Context context;
    private EditText et_login_username;
    private EditText et_login_password;

    private void initalise() {
        context = this;
        et_login_username = (EditText) findViewById(R.id.et_login_username);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        ((LinearLayout) findViewById(R.id.l_login)).setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (paramAnonymousView.getId() != R.id.l_login) {
                    showToast("if");

                }
                while ((!LoginActivity.this.validateCredentails(et_login_username.getText().toString(), et_login_password.getText().toString()))) {
                    showToast("while");
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
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

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_login);
        setTitle(getResources().getString(R.string.activity_login));
        initalise();
    }
}