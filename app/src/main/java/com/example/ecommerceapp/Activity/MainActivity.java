package com.example.ecommerceapp.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ecommerceapp.Fragments.MainFragment;
import com.example.ecommerceapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ChangeFragment();
    }

    private void ChangeFragment(){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.mainActivity_container, new MainFragment()).setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).commit();

    }


}
