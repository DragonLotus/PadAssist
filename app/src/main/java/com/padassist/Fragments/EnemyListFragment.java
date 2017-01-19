package com.padassist.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.EnemyListRecycler;
import com.padassist.BaseFragments.SaveMonsterListRecyclerBase;
import com.padassist.Data.Enemy;
import com.padassist.Data.Monster;
import com.padassist.Graphics.FastScroller;
import com.padassist.R;
import com.padassist.Util.Singleton;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;


public class EnemyListFragment extends Fragment {
    public static final String TAG = EnemyListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private RecyclerView monsterListView;
    private ArrayList<Enemy> enemyList;
    protected ArrayList<Enemy> enemyListAll;
    private MenuItem searchMenuItem;
    protected SearchView searchView;
    protected EnemyListRecycler enemyListRecycler;
    private Toast toast;
    private TextView savedMonsters;
    private boolean firstRun = true;
    private Realm realm;
    private Enemy enemy;

    protected SharedPreferences preferences;
    protected boolean isGrid;

    private FastScroller fastScroller;

    public EnemyListFragment() {
    }

    public static EnemyListFragment newInstance(Parcelable enemy) {
        EnemyListFragment fragment = new EnemyListFragment();
        Bundle args = new Bundle();
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (searchMenuItem != null) {
            if (MenuItemCompat.isActionViewExpanded(searchMenuItem)) {
                MenuItemCompat.collapseActionView(searchMenuItem);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.toggleGrid).setVisible(true);
        menu.findItem(R.id.search).setVisible(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        searchView.setQueryHint(getResources().getString(R.string.search_hint_enemy));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                return true;
            }
        });
        searchView.setOnFocusChangeListener(searchViewOnFocusChangeListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggleGrid:
                preferences.edit().putBoolean("isGrid", !isGrid).apply();
                isGrid = preferences.getBoolean("isGrid", true);
                if (isGrid) {
                    monsterListView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
                } else {
                    monsterListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                }
                enemyListRecycler.notifyDataSetChanged(isGrid);
                if (enemyListRecycler.getExpandedPosition() > -1) {
                    monsterListView.scrollToPosition(enemyListRecycler.getExpandedPosition());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_save_monster_list, container, false);
        monsterListView = (RecyclerView) rootView.findViewById(R.id.monsterListView);
        savedMonsters = (TextView) rootView.findViewById(R.id.savedMonsters);
        fastScroller = (FastScroller) rootView.findViewById(R.id.fastScroller);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            enemy = Parcels.unwrap(getArguments().getParcelable("enemy"));
        }
        savedMonsters.setText("No Saved Enemies");
        preferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
        isGrid = preferences.getBoolean("isGrid", true);
        realm = Realm.getDefaultInstance();

        if (enemyListAll == null) {
            enemyListAll = new ArrayList<>();
        }

        enemyListAll.clear();
        enemyListAll.addAll(realm.where(Enemy.class).greaterThan("enemyId", 0).findAll());

        if (enemyList == null) {
            enemyList = new ArrayList<>();
        }
        emptyCheck();
        fastScroller.setRecyclerView(monsterListView);

        if (isGrid) {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        } else {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        }

        enemyListRecycler = new EnemyListRecycler(getContext(), enemyList, monsterListView, isGrid, realm, clearTextFocus);
        monsterListView.setAdapter(enemyListRecycler);
        getActivity().setTitle("Replace Enemy");
    }



    private EnemyListRecycler.ClearTextFocus clearTextFocus = new EnemyListRecycler.ClearTextFocus() {
        @Override
        public void doThis() {
            searchView.clearFocus();
        }

        @Override
        public void checkEmpty() {
            emptyCheck();
        }
    };

    private View.OnFocusChangeListener searchViewOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            hideKeyboard(v);
        }
    };

    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void emptyCheck() {
        if (enemyListAll.size() == 0) {
            savedMonsters.setVisibility(View.VISIBLE);
            monsterListView.setVisibility(View.GONE);
        } else {
            savedMonsters.setVisibility(View.GONE);
            monsterListView.setVisibility(View.VISIBLE);
        }
    }

    // TODO: Rename method, updateAwakenings argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void defaultReverse() {
        Collections.reverse(enemyList);
    }

    public void searchFilter(String query) {
        if (enemyListRecycler != null) {
            if (query != null && query.length() > 0) {
                if (!enemyList.isEmpty()) {
                    enemyList.clear();
                }
                RealmResults<Enemy> results = realm.where(Enemy.class)
                        .beginGroup()
                        .contains("enemyName", query, Case.INSENSITIVE)
//                        .or()
//                        .contains("type1String", query, Case.INSENSITIVE)
//                        .or()
//                        .contains("type2String", query, Case.INSENSITIVE)
//                        .or()
//                        .contains("type3String", query, Case.INSENSITIVE)
//                        .or()
//                        .contains("baseMonsterIdString", query, Case.INSENSITIVE)
//                        .or()
//                        .contains("activeSkillString", query, Case.INSENSITIVE)
//                        .or()
//                        .contains("leaderSkillString", query, Case.INSENSITIVE)
                        .endGroup()
                        .greaterThan("enemyId", 0)
                        .findAll();

                enemyList.addAll(results);

            } else {
                enemyList.clear();
                enemyList.addAll(enemyListAll);
            }
            firstRun = false;
            enemyListRecycler.setExpandedPosition(-1);
            enemyListRecycler.notifyDataSetChanged();
            if (enemyList.size() == 0) {
                savedMonsters.setText("No results");
                savedMonsters.setVisibility(View.VISIBLE);
            } else {
                savedMonsters.setText("No Saved Enemies");
                savedMonsters.setVisibility(View.INVISIBLE);
            }
        }
    }
}
