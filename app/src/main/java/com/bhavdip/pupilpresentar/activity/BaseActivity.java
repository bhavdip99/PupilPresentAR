package com.bhavdip.pupilpresentar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static android.view.Window.FEATURE_NO_TITLE;

public abstract class BaseActivity extends AppCompatActivity {
    private Toast toast;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(FEATURE_NO_TITLE);
    }

    protected void showToast(String paramString) {
        if (this.toast != null) {
            this.toast.cancel();
        }
        this.toast = Toast.makeText(this, paramString, Toast.LENGTH_SHORT);
        this.toast.show();
    }
}