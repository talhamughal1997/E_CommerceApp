package com.example.ecommerceapp.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ecommerceapp.Fragments.MainFragment;
import com.example.ecommerceapp.R;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String UID = "my_uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  generateServiceKey();
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ChangeFragment();
    }

    private void ChangeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.mainActivity_container, new MainFragment()).setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).commit();
    }

    private void generateServiceKey() {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("./Service_AccountKey.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://ecommerceapp-f696f.firebaseio.com")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);
    }
}
