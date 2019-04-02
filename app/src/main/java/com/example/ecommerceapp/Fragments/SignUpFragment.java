package com.example.ecommerceapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ecommerceapp.R;

public class SignUpFragment extends Fragment {

    View mainView;
    EditText mEditText_Email, mEditText_Pswd, mEditText_CnfrmPswd;
    Button mSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        viewsInit();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new AccountFragment());
            }
        });


        return mainView;
    }

    private void viewsInit() {
        mEditText_Email = mainView.findViewById(R.id.signup_edttxt_email);
        mEditText_Pswd = mainView.findViewById(R.id.signup_edttxt_pswd);
        mEditText_CnfrmPswd = mainView.findViewById(R.id.signup_edttxt_cnfrm_pswd);
        mSignUp = mainView.findViewById(R.id.btn_SignUp);
    }

    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.mainActivity_container, fragment).addToBackStack(null).commit();
    }

}
