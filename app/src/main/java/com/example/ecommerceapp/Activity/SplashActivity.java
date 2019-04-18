package com.example.ecommerceapp.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.pm.PackageInfoCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.ecommerceapp.BroadcastReceiver.ConnectivityReceiver;
import com.example.ecommerceapp.R;
import com.google.android.gms.common.GoogleApiAvailability;

public class SplashActivity extends AppCompatActivity {

    boolean isConnected;
    AlertDialog alertDialog;
    long versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        checkInternet();

        try {
            checkUpdatedGooglePlayService();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //checkConnection();


    }

    void checkInternet() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new ConnectivityReceiver(), filter);
    }

    private void checkUpdatedGooglePlayService() throws PackageManager.NameNotFoundException {

        versionCode = PackageInfoCompat.getLongVersionCode(getPackageManager().getPackageInfo(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE, 0));

        if (versionCode < 11000000) {
            showDialog();
        } else {

            if (alertDialog != null) alertDialog.dismiss();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 1500);
        }


    }

    private void showDialog() {
        int vCode = (int) versionCode / 1000000;

        alertDialog = new AlertDialog.Builder(this)
                .setTitle("Google Play Services")
                .setMessage("Please Update Google Play Services \nYour Currently Version is \t" + vCode)
                .setCancelable(false)
                .create();
        alertDialog.show();
    }

}

