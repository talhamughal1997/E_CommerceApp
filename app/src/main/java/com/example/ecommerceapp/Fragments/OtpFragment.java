package com.example.ecommerceapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import static com.example.ecommerceapp.Fragments.AccountFragment.TAG;
import static com.example.ecommerceapp.Fragments.AccountFragment.VERTIFICATION_ID;

public class OtpFragment extends Fragment implements TextWatcher {

    EditText editText_one, editText_two, editText_three, editText_four, editText_five, editText_six;
    Button mBtnSubmit;
    String code;
    View mainView;

    FirebaseAuth mFirebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_otp, container, false);
        mFirebaseAuth = FirebaseAuth.getInstance();
        viewInit();
        viewListeners();


        mBtnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (editText_six.getText().length() > 0) {
                    createCode();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VERTIFICATION_ID, code);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
        return mainView;
    }

    private void createCode() {
        code = "";
        code += editText_one.getText().charAt(0);
        code += editText_two.getText().charAt(0);
        code += editText_three.getText().charAt(0);
        code += editText_four.getText().charAt(0);
        code += editText_five.getText().charAt(0);
        code += editText_six.getText().charAt(0);
    }

    private void viewInit() {
        editText_one = mainView.findViewById(R.id.editTextone);
        editText_two = mainView.findViewById(R.id.editTexttwo);
        editText_three = mainView.findViewById(R.id.editTextthree);
        editText_four = mainView.findViewById(R.id.editTextfour);
        editText_five = mainView.findViewById(R.id.editTextfive);
        editText_six = mainView.findViewById(R.id.editTextsix);
        mBtnSubmit = mainView.findViewById(R.id.otp_btn_submit);
    }

    private void viewListeners() {
        editText_one.addTextChangedListener(this);
        editText_two.addTextChangedListener(this);
        editText_three.addTextChangedListener(this);
        editText_four.addTextChangedListener(this);
        editText_five.addTextChangedListener(this);
        editText_six.addTextChangedListener(this);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                            //FirebaseUser user = task.getResult().getUser();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getActivity(), "Code Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void changeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.mainActivity_container, fragment).addToBackStack(null).commit();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 1) {
            if (editText_one.length() == 1) {
                editText_two.requestFocus();
            }

            if (editText_two.length() == 1) {
                editText_three.requestFocus();
            }
            if (editText_three.length() == 1) {
                editText_four.requestFocus();
            }
            if (editText_four.length() == 1) {
                editText_five.requestFocus();
            }
            if (editText_five.length() == 1) {
                editText_six.requestFocus();
            }
        } else if (editable.length() == 0) {
            if (editText_six.length() == 0) {
                editText_five.requestFocus();
            }
            if (editText_five.length() == 0) {
                editText_four.requestFocus();
            }
            if (editText_four.length() == 0) {
                editText_three.requestFocus();
            }
            if (editText_three.length() == 0) {
                editText_two.requestFocus();
            }
            if (editText_two.length() == 0) {
                editText_one.requestFocus();
            }


        }
    }
}