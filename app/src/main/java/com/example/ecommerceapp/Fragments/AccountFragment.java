package com.example.ecommerceapp.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.Models.UserModel;
import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    public static final int DOB_REQUEST_CODE = 11;
    public static final String TAG = "AccountFragment";
    Date date;

    View mainView;
    TextInputEditText mEditText_Name, mEditText_Lname, mEditText_Phone, mEditText_Address;
    Button mBtnSave, mBtn_DOB;
    ImageView mImg_Upload;
    Uri imageUri;
    AlertDialog progressDialog;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;

    String uid, imgEncode;
    String fName, lName, dob, phone, adress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_account, container, false);
        mFirebaseAuth = FirebaseAuth.getInstance();
        viewInits();
        viewListeners();

        return mainView;
    }

    private boolean emptyValidations() {
        String val1, val2, val3, val4, val5;
        val1 = mEditText_Name.getText().toString();
        val2 = mEditText_Lname.getText().toString();
        val3 = mBtn_DOB.getText().toString();
        val4 = mEditText_Phone.getText().toString();
        val5 = mEditText_Address.getText().toString();

        if (TextUtils.isEmpty(val1.trim()) || TextUtils.isEmpty(val2.trim()) || TextUtils.isEmpty(val3.trim()) || TextUtils.isEmpty(val4.trim()) || TextUtils.isEmpty(val5.trim())) {
            Toast.makeText(getActivity(), "Plz Fill All TextBoxes", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void viewInits() {
        mEditText_Name = mainView.findViewById(R.id.account_edttxt_name);
        mEditText_Lname = mainView.findViewById(R.id.account_edttxt_lname);
        mBtn_DOB = mainView.findViewById(R.id.account_btn_dob);
        mBtn_DOB.setKeyListener(null);
        mEditText_Phone = mainView.findViewById(R.id.account_edttxt_phone);
        mEditText_Address = mainView.findViewById(R.id.account_edttxt_address);
        mImg_Upload = mainView.findViewById(R.id.account_img_upload);
        mBtnSave = mainView.findViewById(R.id.account_btn_Save);
        createDialog();

        imgEncode = "";
    }

    private void viewListeners() {
        mImg_Upload.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtn_DOB.setOnClickListener(this);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_img_upload: {
                pickImage();
                break;
            }
            case R.id.account_btn_Save: {
                if (!emptyValidations()) {
                    saveInfo();
                }
                break;
            }
            case R.id.account_btn_dob: {
                setDateToEditText();
                break;
            }
        }
    }

    private void putDataInToVar() {
        fName = mEditText_Name.getText().toString();
        lName = mEditText_Lname.getText().toString();
        phone = mEditText_Phone.getText().toString();
        adress = mEditText_Address.getText().toString();
    }

    private void encodeImage() throws FileNotFoundException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
        bitmap.compress(Bitmap.CompressFormat.WEBP, 10, baos);
        byte[] imageBytes = baos.toByteArray();
        imgEncode = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void createDialog() {
        progressDialog = new AlertDialog.Builder(getActivity())
                .setView(R.layout.layout_loading_dialog)
                .setCancelable(false)
                .create();
    }

    private void saveInfo() {
        progressDialog.show();
        putDataInToVar();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("UsersData").child(mFirebaseAuth.getCurrentUser().getUid());
        UserModel userModel = new UserModel(fName, lName, dob, phone, adress, imgEncode);
        mDatabaseReference.setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void setDateToEditText() {
        DialogFragment datepicker;
        if (date == null) {
            datepicker = new DatePickerFragment();

        } else {
            datepicker = DatePickerFragment.newInstance(date);
        }

        datepicker.setTargetFragment(this, DOB_REQUEST_CODE);
        datepicker.show(getFragmentManager(), "datepicker");
    }

    private void clearFragmentStacks() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        clearFragmentStacks();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DOB_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    date = (Date) data.getSerializableExtra("date");
                    String shortDate = SimpleDateFormat.getDateInstance().format(date);
                    mBtn_DOB.setText(String.valueOf(shortDate));
                    dob = shortDate;
                }
                break;

            case PICK_IMAGE_REQUEST: {
                if (requestCode == PICK_IMAGE_REQUEST) {
                    if (resultCode == Activity.RESULT_OK) {
                        imageUri = data.getData();
                        Glide.with(getActivity()).load(imageUri).into(mImg_Upload);

                        try {
                            encodeImage();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
        }
    }

}
