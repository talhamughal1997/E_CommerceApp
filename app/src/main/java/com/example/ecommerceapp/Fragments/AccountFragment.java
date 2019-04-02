package com.example.ecommerceapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ecommerceapp.R;

public class AccountFragment extends Fragment {

    View mainView;
    EditText mEditText_Name, mEditText_Lname, mEditText_DOB, mEditText_Phone, mEditText_Address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_account, container, false);
        return mainView;
    }



}
