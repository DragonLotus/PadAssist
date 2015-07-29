package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        //return super.onCreateDialog(savedInstanceState);
//        return new AlertDialog.Builder(getActivity())
//                .setTitle("Login to PadHerder")
//                .setPositiveButton("Login",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //Do Login things.
//                                getDialog().dismiss();
//                            }
//                        }
//                )
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                getDialog().dismiss();
//                            }
//                        }
//                )
//                .create();
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_login_dialog, container, false);

        loginButton = (Button) rootView.findViewById(R.id.loginButton);
        cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        usernameEditText = (EditText) rootView.findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) rootView.findViewById(R.id.passwordEditText);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginButton.setOnClickListener(buttonOnClickListener);
        cancelButton.setOnClickListener(buttonOnClickListener);
        getDialog().setTitle("Login to PADherder");
    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.equals(loginButton)){
                //Do login things
                getDialog().dismiss();
            }
            if(v.equals(cancelButton)){
                getDialog().dismiss();
            }
        }
    };



}
