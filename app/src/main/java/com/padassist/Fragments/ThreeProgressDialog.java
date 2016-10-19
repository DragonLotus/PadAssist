package com.padassist.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.padassist.R;

import org.w3c.dom.Text;

/**
 * Created by DragonLotus on 10/19/2016.
 */

public class ThreeProgressDialog extends DialogFragment {

    private ProgressBar progressBar1, progressBar2, progressBar3;
    private TextView minMax1, minMax2, minMax3;

    public static ThreeProgressDialog newInstance() {
        ThreeProgressDialog dialogFragment = new ThreeProgressDialog();
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_three_progress_dialog, null);
        progressBar1 = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) rootView.findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) rootView.findViewById(R.id.progressBar3);
        minMax1 = (TextView) rootView.findViewById(R.id.progressMinMax1);
        minMax2 = (TextView) rootView.findViewById(R.id.progressMinMax2);
        minMax3 = (TextView) rootView.findViewById(R.id.progressMinMax3);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setTitle("Downloading files...");

        return builder.create();
    }

    public void setProgressBar1(int progress) {
        progressBar1.setProgress(progress);
        if (progress < 100) {
            minMax1.setText(progress + "%");
        } else {
            minMax1.setText("Up to date");
        }
    }

    public void setProgressBar2(int progress) {
        progressBar2.setProgress(progress);
        if (progress < 100) {
            minMax2.setText(progress + "%");
        } else {
            minMax2.setText("Up to date");
        }
    }

    public void setProgressBar3(int progress) {
        progressBar3.setProgress(progress);
        if (progress < 100) {
            minMax3.setText(progress + "%");
        } else {
            minMax3.setText("Up to date");
        }
    }

    public int getProgress1(){
        return progressBar1.getProgress();
    }

    public int getProgress2(){
        return progressBar2.getProgress();
    }

    public int getProgress3(){
        return progressBar3.getProgress();
    }

    public void setMinMax1(String text){
        minMax1.setText("text");
    }

    public void setMinMax2(String text){
        minMax2.setText("text");
    }

    public void setMinMax3(String text){
        minMax3.setText("text");
    }
}
