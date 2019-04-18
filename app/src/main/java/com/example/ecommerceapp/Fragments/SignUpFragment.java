package com.example.ecommerceapp.Fragments;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.pm.PackageInfoCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.support.constraint.Constraints.TAG;

public class SignUpFragment extends Fragment {

    View mainView;
    EditText mEditText_Email, mEditText_Pswd, mEditText_CnfrmPswd;
    Button mSignUp;
    String email, pswd;
    AlertDialog progressDialog;

    FirebaseAuth mFirebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_sign_up, container, false);


        viewsInit();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEditText_Email.getText().toString();
                pswd = mEditText_Pswd.getText().toString();
                if (checkTextBoxes()) {
                    showProgressDialog();
                    signUpWithFirebase();
                }

            }
        });
        return mainView;
    }

    private void viewsInit() {
        mEditText_Email = mainView.findViewById(R.id.signup_edttxt_email);
        mEditText_Pswd = mainView.findViewById(R.id.signup_edttxt_pswd);
        mEditText_CnfrmPswd = mainView.findViewById(R.id.signup_edttxt_cnfrm_pswd);
        mSignUp = mainView.findViewById(R.id.btn_SignUp);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.mainActivity_container, fragment).addToBackStack(null).commit();
    }

    private void signUpWithFirebase() {
        mFirebaseAuth.createUserWithEmailAndPassword(email, pswd)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (progressDialog != null) progressDialog.dismiss();
                            //FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            changeFragment(new AccountFragment());
                        } else {
                            // If sign in fails, display a message to the user.
                            if (progressDialog != null) progressDialog.dismiss();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }

    private boolean checkTextBoxes() {
        if (mEditText_Email.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "Email is Empty", Toast.LENGTH_SHORT).show();
            mEditText_Email.requestFocus();
            return false;

        } else if (mEditText_Pswd.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "Password is Empty", Toast.LENGTH_SHORT).show();
            mEditText_Pswd.requestFocus();
            return false;

        } else if (mEditText_CnfrmPswd.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "Confirm Password is Empty", Toast.LENGTH_SHORT).show();
            mEditText_CnfrmPswd.requestFocus();
            return false;
        } else if (!mEditText_Pswd.getText().toString().trim().equals(mEditText_CnfrmPswd.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Confirm Password Not Match", Toast.LENGTH_SHORT).show();
            mEditText_CnfrmPswd.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void showProgressDialog() {
        progressDialog = new AlertDialog.Builder(getActivity())
                .setView(R.layout.layout_loading_dialog)
                .setCancelable(false)
                .create();
        progressDialog.show();
    }

}



