package com.bhavdip.pupilpresentar;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bhavdip.pupilpresentar.barcodescanning.BarcodeCaptureActivity;
import com.bhavdip.pupilpresentar.fragment.CameraFragment;
import com.bhavdip.pupilpresentar.fragment.NewsFragment;
import com.bhavdip.pupilpresentar.fragment.StudentFragment;
import com.bhavdip.pupilpresentar.utility.Constants;
import com.bhavdip.pupilpresentar.utility.GPSLocationTracker;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean viewIsAtHome;
    private GPSLocationTracker gpsTracker;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private AddressResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(intent);

            }
        });

        mResultReceiver = new AddressResultReceiver(null);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displayView(R.id.nav_manage);
    }

    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) { //if the current view is not the News fragment
            displayView(R.id.nav_manage); //display the News fragment
        } else {
            moveTaskToBack(true);  //If view is in News fragment, exit application
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {

            case R.id.nav_manage:
                fragment = new StudentFragment();
//                title = "News";
                viewIsAtHome = true;

                break;
            case R.id.nav_location:
                title = "Show Location";

                // create class object
                gpsTracker = new GPSLocationTracker(MainActivity.this);

                // check if GPS enabled
                if (gpsTracker.canGetLocation()) {

                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
                    startIntentService(gpsTracker.getLocation());
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gpsTracker.showSettingsAlert();
                }

                viewIsAtHome = false;

                break;
            case R.id.nav_barcode_scan:
//                fragment = new CameraFragment();
                title = "Attendance";



                // launch barcode activity.
                Intent intent = new Intent(this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                intent.putExtra(BarcodeCaptureActivity.UseFlash, true);

                startActivityForResult(intent, RC_BARCODE_CAPTURE);
                viewIsAtHome = false;

                break;

            case R.id.nav_contact:
                title = "Contact Us";
                fragment = new NewsFragment();

                viewIsAtHome = false;
                break;
            case R.id.nav_about:
                title = "About Us";
                fragment = new CameraFragment();

                viewIsAtHome = false;
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        displayView(item.getItemId());
        return true;
    }


    protected void startIntentService(Location mLastLocation) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }


    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            String mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                // \n is for new line

                // TODO Save address to the database from here
                Log.d("", "Your Location is " + mAddressOutput);
            }

        }
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    // TODO Save barcode to the database from here
                    Toast.makeText(MainActivity.this, barcode.displayValue, Toast.LENGTH_LONG).show();
                } else {
                    Log.d("", "No barcode captured, intent data is null");
                }
            } else {
                Toast.makeText(MainActivity.this, String.format(getString(R.string.barcode_error), CommonStatusCodes.getStatusCodeString(resultCode)), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
