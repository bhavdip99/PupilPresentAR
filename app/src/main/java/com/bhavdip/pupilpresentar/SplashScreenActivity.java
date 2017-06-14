package com.bhavdip.pupilpresentar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
//                    startActivity(intent);
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

