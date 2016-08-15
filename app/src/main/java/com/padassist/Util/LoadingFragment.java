package com.padassist.Util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padassist.Fragments.DisclaimerDialogFragment;
import com.padassist.R;
import com.padassist.Threads.ParseMonsterDatabaseThread;

import io.realm.Realm;

/**
 * Created by DragonLotus on 8/15/2016.
 */

public class LoadingFragment extends Fragment {
    public static final String TAG = LoadingFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    private DisclaimerDialogFragment disclaimerDialog;
    private ImportAsyncTask asyncTask;

    public LoadingFragment() {

    }

    public static Fragment newInstance(){
        Fragment fragment = new LoadingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loading, container, false);
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
            progressDialog.setMessage("Loading monsters...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(4888);
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
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values[0] == 3075) {
                progressDialog.setMessage("Loading Leader Skills...");
            }
        }
    }
}


