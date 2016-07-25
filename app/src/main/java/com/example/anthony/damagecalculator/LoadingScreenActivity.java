package com.example.anthony.damagecalculator;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.anthony.damagecalculator.Fragments.DisclaimerDialogFragment;
import com.example.anthony.damagecalculator.Threads.ParseMonsterDatabaseThread;

public class LoadingScreenActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private DisclaimerDialogFragment disclaimerDialog;
    private Boolean complete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoadViewTask().execute();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoadingScreenActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Loading monsters...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(4806);
            progressDialog.setProgress(0);
            progressDialog.show();
            disclaimerDialog = DisclaimerDialogFragment.newInstance(dismissMe);
            disclaimerDialog.show(getSupportFragmentManager(), "yes");
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                ParseMonsterDatabaseThread parseMonsterDatabaseThread = new ParseMonsterDatabaseThread(new ParseMonsterDatabaseThread.UpdateProgress() {
                    @Override
                    public void updateValues(int counter) {
                        publishProgress(counter);
                    }
                });
                synchronized (this) {
                    parseMonsterDatabaseThread.start();
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            complete = true;
            progressDialog.dismiss();
            if(!disclaimerDialog.getShowing()){
                finish();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
            Log.d("LoadingScreenTag", "values is: " + values[0]);
            if (values[0] == 4806) {
                synchronized (this) {
                    this.notify();
                }
            } else {
                if (values[0] >= 3031) {
                    progressDialog.setMessage("Loading leader skills...");
                }
            }
        }
    }

    DisclaimerDialogFragment.Dismiss dismissMe = new DisclaimerDialogFragment.Dismiss() {
        @Override
        public void dismiss() {
            if(complete) {
                finish();
            }
        }
    };


}
