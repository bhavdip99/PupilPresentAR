package com.bhavdip.pupilpresentar.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.bhavdip.pupilpresentar.R;
import com.bhavdip.pupilpresentar.dbsqlite.UsersModel;
import com.bhavdip.pupilpresentar.model.User;

import static com.bhavdip.pupilpresentar.utility.Constants.KEY_PREFS_USERNAME;

/**
 * Created by Bhavdip Bhalodia on 6/16/2017.
 */

public class ShowProfileActivity extends BaseActivity{
    public static final String STUDENT = "com.bhavdip.pupilpresentar.activity.ShowProfileActivity.student";

    String username;
    User user;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_profile);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains(KEY_PREFS_USERNAME)) {
            username = prefs.getString(KEY_PREFS_USERNAME, "");
        }
        UsersModel usersModel = new UsersModel(ShowProfileActivity.this);
        user = usersModel.getSelectedEntry(username);


    }
}
