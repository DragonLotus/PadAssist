package com.padassist.Fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.padassist.Constants;
import com.padassist.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by DragonLotus on 8/15/2016.
 */

public class DownloadFragment extends Fragment {
    public static final String TAG = DownloadFragment.class.getSimpleName();
    public static final int MONSTERS = 1, LEADER_SKILLS = 2, ACTIVE_SKILLS = 3;
    private ProgressDialog progressDialog;
    private DownloadAsyncTask asyncTask;
    private int dataType;
    private Uri uri;

    public DownloadFragment() {

    }

    public static Fragment newInstance(int downloadData, Uri uri){
        Fragment fragment = new DownloadFragment();
        Bundle args = new Bundle();
        args.putInt("dataType", downloadData);
        args.putParcelable("uri", uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loading, container, false);
        if(getArguments() != null){
            dataType = getArguments().getInt("dataType");
            uri = getArguments().getParcelable("uri");
        }
        asyncTask = new DownloadAsyncTask(dataType);
        asyncTask.execute(uri);
        return rootView;
    }

    private class DownloadAsyncTask extends AsyncTask<Uri, Integer, Integer> {

        private int dataType;
        final long ONE_MEGABYTE = 1024 * 1024;
        final long ONE_KILOBYTE = 1024;

        public DownloadAsyncTask (int dataType){
            this.dataType = dataType;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Downloading...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            switch (dataType){
                case DownloadFragment.MONSTERS:
                    progressDialog.setMessage("Downloading Monster file...");
                    break;
            }
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Uri... params) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(values[0] == Constants.numOfLeaderSkills){
                progressDialog.setMessage("Loading Active Skills...");
            } else if (values[0] == Constants.numOfLeaderSkills + Constants.numOfActiveSkills){
                progressDialog.setMessage("Loading Monsters...");
            } else if (values[0] == Constants.numOfLeaderSkills + Constants.numOfActiveSkills + Constants.numOfMonsters) {
                progressDialog.setMessage("Updating Saved Monsters...");
            }
        }
    }
}


