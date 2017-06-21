package com.bhavdip.pupilpresentar.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    private Toast toast;
    private Snackbar snackbar;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
//        requestWindowFeature(FEATURE_NO_TITLE);
    }



    protected void showToast(String paramString) {
        if (this.toast != null) {
            this.toast.cancel();
        }
        this.toast = Toast.makeText(this, paramString, Toast.LENGTH_SHORT);
        this.toast.show();
    }
    protected void showSnackbar(String paramString) {
        if (this.snackbar != null) {
            this.snackbar.dismiss();
        }
        this.snackbar = Snackbar.make(findViewById(android.R.id.content), paramString, Snackbar.LENGTH_LONG);
        this.snackbar.show();
    }
}