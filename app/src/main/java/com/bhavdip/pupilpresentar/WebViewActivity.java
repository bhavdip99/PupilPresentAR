package com.bhavdip.pupilpresentar;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bhavdip.pupilpresentar.activity.BaseActivity;


public class WebViewActivity extends BaseActivity {

    public static final String URL = "com.bhavdip.pupilpresentar.WebViewActivity.URL";
    String trackUrl;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            trackUrl = extras.getString(URL);
        }

        WebView wv = (WebView) findViewById(R.id.webview);
        wv.setWebViewClient(new MyBrowser());
        wv.getSettings().setBuiltInZoomControls(true);
//        wv.getSettings().setLoadWithOverviewMode(true);
//        wv.getSettings().setUseWideViewPort(true);

//        wv.getSettings().setDisplayZoomControls(false);
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.loadUrl(trackUrl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.push_left_in,
                R.anim.push_left_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
