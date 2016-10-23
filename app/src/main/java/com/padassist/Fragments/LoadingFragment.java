package com.padassist.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padassist.Constants;
import com.padassist.R;
import com.padassist.Threads.ParseMonsterDatabaseThread;
import com.padassist.Util.Singleton;

import io.realm.Realm;

/**
 * Created by DragonLotus on 8/15/2016.
 */

public class LoadingFragment extends Fragment {
    public static final String TAG = LoadingFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    private DisclaimerDialogFragment disclaimerDialog;
    private ImportAsyncTask asyncTask;
    private SharedPreferences preferences;

    public LoadingFragment() {

    }

    public static Fragment newInstance(){
        Fragment fragment = new LoadingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loading, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
        asyncTask = new ImportAsyncTask();
        asyncTask.execute();
        return rootView;
    }

    private class ImportAsyncTask extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Loading Leader Skills...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(preferences.getInt("numOfActiveSkills", 1) + preferences.getInt("numOfLeaderSkills", 1) + preferences.getInt("numOfMonsters", 1) + Constants.numOfSavedMonsters);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {

            Realm realm = Realm.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    ParseMonsterDatabaseThread parseMonsterDatabaseThread = new ParseMonsterDatabaseThread(new ParseMonsterDatabaseThread.UpdateProgress() {
                        @Override
                        public void updateValues(int counter) {
                            publishProgress(counter);
                            progressDialog.setProgress(counter);
                        }
                    });

                    parseMonsterDatabaseThread.run();

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressDialog.dismiss();
            getActivity().getSupportFragmentManager().popBackStack(LoadingFragment.TAG, 1);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(values[0] == preferences.getInt("numOfLeaderSkills", 1)){
                progressDialog.setMessage("Loading Active Skills...");
            } else if (values[0] == preferences.getInt("numOfLeaderSkills", 1) + preferences.getInt("numOfActiveSkills", 1)){
                progressDialog.setMessage("Loading Monsters...");
            } else if (values[0] == preferences.getInt("numOfLeaderSkills", 1) + preferences.getInt("numOfActiveSkills", 1) + preferences.getInt("numOfMonsters", 1)) {
                progressDialog.setMessage("Updating Saved Monsters...");
            }
        }
    }
}


