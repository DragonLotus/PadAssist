package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anthony.damagecalculator.R;




public class LoginDialogFragment extends DialogFragment {

    private View rootView;
    private Button loginButton, cancelButton;
    private EditText usernameEditText, passwordEditText;

    static LoginDialogFragment newInstance(){
        LoginDialogFragment f = new LoginDialogFragment();
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginButton = (Button) rootView.findViewById(R.id.loginButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        usernameEditText = (EditText) rootView.findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login_dialog, container, false);
        return rootView;
    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.equals(loginButton)){
                //Do login things
            }
            if(v.equals(cancelButton)){
                getDialog().dismiss();
            }
        }
    };



}
