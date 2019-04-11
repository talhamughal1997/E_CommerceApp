package com.example.ecommerceapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class SignUpFragment extends Fragment {

    View mainView;
    EditText mEditText_Email, mEditText_Pswd, mEditText_CnfrmPswd;
    Button mSignUp;
    String email, pswd;

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
                signUpWithFirebase();
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
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }



}
