package com.example.ecommerceapp.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rey.material.widget.CheckBox;

public class SignInFragment extends Fragment implements View.OnClickListener {

    CheckBox chkbox;
    View mainView;
    FirebaseAuth mFirebaseAuth;

    String email, pswd;
    EditText mEditText_Email, mEditText_Password;
    Button mBtn_LogIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mFirebaseAuth = FirebaseAuth.getInstance();
        viewsInit();
        viewsEdit();
        viewsListeners();

        return mainView;
    }

    private void viewsInit() {
        mEditText_Email = mainView.findViewById(R.id.login_edttxt_email);
        mEditText_Password = mainView.findViewById(R.id.login_edttxt_pswd);
        mBtn_LogIn = mainView.findViewById(R.id.btn_Login);
        chkbox = mainView.findViewById(R.id.chkbox_RememberMe);
    }

    private void viewsEdit() {
        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.playball);
        chkbox.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Login: {
                signInWithFirebase();
                break;
            }
        }

    }

    private void signInWithFirebase() {

        email = mEditText_Email.getText().toString();
        pswd = mEditText_Password.getText().toString();

        mFirebaseAuth.signInWithEmailAndPassword(email,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void viewsListeners() {
        mBtn_LogIn.setOnClickListener(this);
    }

}
