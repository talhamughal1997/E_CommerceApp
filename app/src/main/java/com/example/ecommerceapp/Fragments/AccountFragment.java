package com.example.ecommerceapp.Fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    View mainView;
    EditText mEditText_Name, mEditText_Lname, mEditText_DOB, mEditText_Phone, mEditText_Address;
    Button mBtnSave;
    ImageView mImg_Upload;
    Uri imageUri;
    ProgressDialog progressDialog;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_account, container, false);
        viewInits();
        viewListeners();


        return mainView;
    }

    private void emptyValidations() {
        String val1, val2, val3, val4, val5;
        val1 = mEditText_Name.getText().toString();
        val2 = mEditText_Lname.getText().toString();
        val3 = mEditText_DOB.getText().toString();
        val4 = mEditText_Phone.getText().toString();
        val5 = mEditText_Address.getText().toString();

        if (TextUtils.isEmpty(val1.trim()) || TextUtils.isEmpty(val2.trim()) || TextUtils.isEmpty(val3.trim()) || TextUtils.isEmpty(val4.trim()) || TextUtils.isEmpty(val5.trim())) {
            Toast.makeText(getActivity(), "Plz Fill All TextBoxes", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }

    private void viewInits() {
        mEditText_Name = mainView.findViewById(R.id.account_edttxt_name);
        mEditText_Lname = mainView.findViewById(R.id.account_edttxt_lname);
        mEditText_DOB = mainView.findViewById(R.id.account_edttxt_dob);
        mEditText_Phone = mainView.findViewById(R.id.account_edttxt_phone);
        mEditText_Address = mainView.findViewById(R.id.account_edttxt_address);
        mImg_Upload = mainView.findViewById(R.id.account_img_upload);
        mBtnSave = mainView.findViewById(R.id.account_btn_Save);
        progressDialog = new ProgressDialog(getActivity());
    }

    private void createFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void viewListeners() {
        mImg_Upload.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_img_upload: {
                pickImage();
                break;
            }
            case R.id.account_btn_Save: {
                progressDialog.show();
                emptyValidations();
                break;
            }
        }
    }

    private void saveInfo() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(getActivity()).load(imageUri).into(mImg_Upload);
            // mImg_Upload.setImageURI(imageUri);
        }
    }
}
