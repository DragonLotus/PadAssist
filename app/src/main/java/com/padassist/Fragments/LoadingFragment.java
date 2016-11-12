package com.padassist.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.padassist.Constants;
import com.padassist.Data.BaseMonster;
import com.padassist.R;
import com.padassist.Threads.ParseMonsterDatabaseThread;
import com.padassist.Util.Singleton;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by DragonLotus on 8/15/2016.
 */

public class LoadingFragment extends Fragment {
    public static final String TAG = LoadingFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    private DisclaimerDialogFragment disclaimerDialog;
    private ImportAsyncTask asyncTask;
    private SharedPreferences preferences;
    private ArrayList<Long> missingImages;

    public LoadingFragment() {

    }

    public static Fragment newInstance(){
        Fragment fragment = new LoadingFragment();
        Bundle args = new Bundle();
//        args.putSerializable("missingImages", missingImages);
        fragment.setArguments(args);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
//            missingImages = (ArrayList<Long>) getArguments().getSerializable("missingImages");
        }
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
            Log.d("LoadingFragment", "image results doInBackground size  size is: " + realm.where(BaseMonster.class).findAll().size());
            missingImageCheck(realm);
            realm.close();

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

    private void missingImageCheck(Realm realm){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference monsterImageReference;
        File folder = new File(getActivity().getFilesDir(), "monster_images");
        if (!folder.exists()) {
            folder.mkdir();
        }

        missingImages = new ArrayList<>();
        File imageCheck;
        RealmResults<BaseMonster> results = realm.where(BaseMonster.class).findAll();
        Log.d("LoadingFragment", "image results size is: " + results.size());
        for (BaseMonster monster : results) {
            imageCheck = new File(getActivity().getFilesDir(), "monster_images/monster_" + monster.getMonsterId() + ".png");
            if (!imageCheck.exists() && monster.getMonsterId() != 0) {
                missingImages.add(monster.getMonsterId());
            }
        }
        Log.d("LoadingFragment", "missingImages is: " + missingImages + " missingImages size: " + missingImages.size());
        for (int i = 0; i < missingImages.size(); i++) {
            Log.d("LoadingFragment", "Supposedly downloading image.");
            monsterImageReference = storage.getReferenceFromUrl("gs://padassist-7b3cf.appspot.com/monster_images/all/monster_" + missingImages.get(i) + ".png");
            imageCheck = new File(getActivity().getFilesDir(), "monster_images/monster_" + missingImages.get(i) + ".png");

            monsterImageReference.getFile(imageCheck)
                    .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Log.d("LoadingFragment", "On download success image.");
                        }
                    });
        }
    }
}


