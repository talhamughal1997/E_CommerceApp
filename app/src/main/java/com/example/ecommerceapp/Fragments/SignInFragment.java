package com.example.ecommerceapp.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.R;
import com.rey.material.widget.CheckBox;

public class SignInFragment extends Fragment implements View.OnClickListener {

    CheckBox chkbox;
    View mainView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        viewsInit();
        viewsEdition();

        return mainView;
    }

    void viewsInit() {
        chkbox = mainView.findViewById(R.id.chkbox_RememberMe);
    }

    public void viewsEdition() {
        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.playball);
        chkbox.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {

    }

}
