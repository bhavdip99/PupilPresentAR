package com.bhavdip.pupilpresentar.fragment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhavdip.pupilpresentar.FetchAddressIntentService;
import com.bhavdip.pupilpresentar.R;
import com.bhavdip.pupilpresentar.utility.Constants;
import com.bhavdip.pupilpresentar.utility.GPSLocationTracker;


public class LocationFragment extends Fragment {

    private GPSLocationTracker gpsTracker;
    private  AddressResultReceiver mResultReceiver;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // create class object
        gpsTracker = new GPSLocationTracker(getActivity());

        // check if GPS enabled
        if (gpsTracker.canGetLocation()) {

            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            startIntentService(gpsTracker.getLocation());
            // \n is for new line
            Toast.makeText(getActivity().getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }

    }

    protected void startIntentService(Location mLastLocation) {
        Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        getActivity().startService(intent);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactus, container, false);


        return view;
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

}
