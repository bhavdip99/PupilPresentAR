package com.bhavdip.pupilpresentar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bhavdip.pupilpresentar.activity.LoginActivity;

/**
 * Created by bhavdip on 14/6/17.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.v("tag","onCreate");


        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("tag","onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("tag","onResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("tag","onDestroy");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("tag","onStop");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("tag","onRestart");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.v("tag","onPause");

//        finish();
    }

}

