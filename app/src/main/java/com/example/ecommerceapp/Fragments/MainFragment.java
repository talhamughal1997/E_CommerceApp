package com.example.ecommerceapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecommerceapp.Controllers.Utills;
import com.example.ecommerceapp.R;

public class MainFragment extends Fragment implements View.OnClickListener {
    Button mBtnSignIn, mBtnSignUp;
    View mainView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_main, container, false);
        clearFragmentStacks();
        viewInit();
        viewListners();
        return mainView;
    }

    private void viewInit() {
        mBtnSignIn = mainView.findViewById(R.id.main_btn_SignIn);
        mBtnSignUp = mainView.findViewById(R.id.main_btn_SignUp);
    }

    private void viewListners() {
        mBtnSignUp.setOnClickListener(this);
        mBtnSignIn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_SignIn: {
                changeFragment(new SignInFragment());
                break;
            }
            case R.id.main_btn_SignUp: {
                changeFragment(new SignUpFragment());
                break;
            }
        }
    }

    private void clearFragmentStacks() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.mainActivity_container, fragment).addToBackStack(null).commit();
    }


}
