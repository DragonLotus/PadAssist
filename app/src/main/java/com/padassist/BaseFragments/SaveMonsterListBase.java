package com.padassist.BaseFragments;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.SaveMonsterListRecycler;
import com.padassist.BroadcastReceivers.JustAnotherBroadcastReceiver;
import com.padassist.Comparators.MonsterAlphabeticalComparator;
import com.padassist.Comparators.MonsterAtkComparator;
import com.padassist.Comparators.MonsterAwakeningComparator;
import com.padassist.Comparators.MonsterElement1Comparator;
import com.padassist.Comparators.MonsterElement2Comparator;
import com.padassist.Comparators.MonsterFavoriteComparator;
import com.padassist.Comparators.MonsterHpComparator;
import com.padassist.Comparators.MonsterLevelComparator;
import com.padassist.Comparators.MonsterNumberComparator;
import com.padassist.Comparators.MonsterPlusAtkComparator;
import com.padassist.Comparators.MonsterPlusComparator;
import com.padassist.Comparators.MonsterPlusHpComparator;
import com.padassist.Comparators.MonsterPlusRcvComparator;
import com.padassist.Comparators.MonsterRarityComparator;
import com.padassist.Comparators.MonsterRcvComparator;
import com.padassist.Comparators.MonsterType1Comparator;
import com.padassist.Comparators.MonsterType2Comparator;
import com.padassist.Comparators.MonsterType3Comparator;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Fragments.FilterDialogFragment;
import com.padassist.Fragments.MonsterTabLayoutFragment;
import com.padassist.Fragments.SortElementDialogFragment;
import com.padassist.Fragments.SortPlusDialogFragment;
import com.padassist.Fragments.SortStatsDialogFragment;
import com.padassist.Fragments.SortTypeDialogFragment;
import com.padassist.Graphics.FastScroller;
import com.padassist.R;
import com.padassist.Util.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;


public abstract class SaveMonsterListBase extends Fragment {
    public static final String TAG = SaveMonsterListBase.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    protected RecyclerView monsterListView;
    protected ArrayList<Monster> filteredMonsters = new ArrayList<>();
    protected ArrayList<Monster> monsterList;
    protected ArrayList<Monster> monsterListAll;
    protected boolean helper;
    protected MenuItem searchMenuItem;
    protected SearchView searchView;
    protected SaveMonsterListRecycler saveMonsterListRecycler;
    private Toast toast;
    private TextView savedMonsters;
    private boolean firstRun = true;
    private SortElementDialogFragment sortElementDialogFragment;
    private SortTypeDialogFragment sortTypeDialogFragment;
    private SortStatsDialogFragment sortStatsDialogFragment;
    private SortPlusDialogFragment sortPlusDialogFragment;
    private Comparator<Monster> monsterNumberComparator = new MonsterNumberComparator();
    private Comparator<Monster> monsterElement1Comparator = new MonsterElement1Comparator();
    private Comparator<Monster> monsterElement2Comparator = new MonsterElement2Comparator();
    private Comparator<Monster> monsterType1Comparator = new MonsterType1Comparator();
    private Comparator<Monster> monsterType2Comparator = new MonsterType2Comparator();
    private Comparator<Monster> monsterType3Comparator = new MonsterType3Comparator();
    private Comparator<Monster> monsterAlphabeticalComparator = new MonsterAlphabeticalComparator();
    private Comparator<Monster> monsterHpComparator = new MonsterHpComparator();
    private Comparator<Monster> monsterAtkComparator = new MonsterAtkComparator();
    private Comparator<Monster> monsterRcvComparator = new MonsterRcvComparator();
    private Comparator<Monster> monsterRarityComparator = new MonsterRarityComparator();
    private Comparator<Monster> monsterAwakeningComparator = new MonsterAwakeningComparator();
    private Comparator<Monster> monsterPlusComparator = new MonsterPlusComparator();
    private Comparator<Monster> monsterPlusHpComparator = new MonsterPlusHpComparator();
    private Comparator<Monster> monsterPlusAtkComparator = new MonsterPlusAtkComparator();
    private Comparator<Monster> monsterPlusRcvComparator = new MonsterPlusRcvComparator();
    private Comparator<Monster> monsterLevelComparator = new MonsterLevelComparator();
    private Comparator<Monster> monsterFavoriteComparator = new MonsterFavoriteComparator();
    private FilterDialogFragment filterDialogFragment;
    protected Realm realm;
    private Monster monsterZero;
    protected int selection;
    protected Monster monster;
    protected Team team;
    protected JustAnotherBroadcastReceiver broadcastReceiver;

    protected SharedPreferences preferences;
    protected boolean isGrid;

    private FastScroller fastScroller;

    public SaveMonsterListBase() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.sortGroup, true);
        menu.setGroupVisible(R.id.sortMoreGroup, true);
        menu.findItem(R.id.search).setVisible(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
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
            case R.id.toggleCoop:
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case R.id.reverseList:
                reverseArrayList();
                break;
            case R.id.sortAlphabetical:
                sortArrayList(0);
                break;
            case R.id.sortId:
                sortArrayList(1);
                break;
            case R.id.sortElement:
                sortArrayList(2);
                break;
            case R.id.sortType:
                sortArrayList(3);
                break;
            case R.id.sortStat:
                sortArrayList(4);
                break;
            case R.id.sortRarity:
                sortArrayList(5);
                break;
            case R.id.sortAwakenings:
                sortArrayList(6);
                break;
            case R.id.sortPlus:
                sortArrayList(7);
                break;
            case R.id.sortFavorite:
                sortArrayList(8);
                break;
            case R.id.sortLevel:
                sortArrayList(9);
                break;
            case R.id.sortLead:
                sortArrayList(10);
                break;
            case R.id.sortHelper:
                sortArrayList(11);
                break;
            case R.id.filterList:
                if (filterDialogFragment == null) {
                    filterDialogFragment = FilterDialogFragment.newInstance(saveTeam, true);
                }
                if (!filterDialogFragment.isAdded() && !firstRun) {
                    filterDialogFragment.show(getChildFragmentManager(), true, "Filter");
                }
                break;
            case R.id.toggleGrid:
                gridToggle();
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
//        if (getArguments() != null) {
//            replaceAll = getArguments().getBoolean("replaceAll");
//            replaceMonsterId = getArguments().getLong("replaceMonsterId");
//        }
        preferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
        isGrid = preferences.getBoolean("isGrid", true);
        realm = Realm.getDefaultInstance();
        monsterZero = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();
        onActivityCreatedSpecific();
        if (monsterList == null) {
            monsterList = new ArrayList<>();
        }
//        filterMonsterName("");
        emptyCheck();
        fastScroller.setRecyclerView(monsterListView);
    }

    protected SaveMonsterListRecyclerBase.ClearTextFocus clearTextFocus = new SaveMonsterListRecyclerBase.ClearTextFocus() {
        @Override
        public void doThis() {
            searchView.clearFocus();
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

    public abstract void onActivityCreatedSpecific();

    protected void emptyCheck() {
        if (monsterListAll.size() == 1) {
            if (monsterListAll.get(0).getMonsterId() != 0) {
                monsterListAll.add(0, monsterZero);
                savedMonsters.setVisibility(View.GONE);
                monsterListView.setVisibility(View.VISIBLE);
            } else {
                savedMonsters.setVisibility(View.VISIBLE);
                monsterListView.setVisibility(View.GONE);
            }
        } else {
            savedMonsters.setVisibility(View.GONE);
            monsterListView.setVisibility(View.VISIBLE);
        }
    }

    private void disableStuff() {
//        for (int i = 0; i < monsterList.size(); i++) {
//            if (monsterList.get(i).getMonsterId() == Team.getTeamById(0).getLead().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub1().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub2().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub3().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub4().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getHelper().getMonsterId()) {
//                monsterList.remove(i);
//            }
//        }
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

    private SortElementDialogFragment.SortBy sortByElement = new SortElementDialogFragment.SortBy() {
        @Override
        public void sortElement1() {
            Singleton.getInstance().setSaveSortMethod(201);
            Collections.sort(monsterList, monsterElement1Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortElement2() {
            Singleton.getInstance().setSaveSortMethod(202);
            Collections.sort(monsterList, monsterElement2Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }
    };

    private SortTypeDialogFragment.SortBy sortByType = new SortTypeDialogFragment.SortBy() {
        @Override
        public void sortType1() {
            Singleton.getInstance().setSaveSortMethod(301);
            Collections.sort(monsterList, monsterType1Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortType2() {
            Singleton.getInstance().setSaveSortMethod(302);
            Collections.sort(monsterList, monsterType2Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortType3() {
            Singleton.getInstance().setSaveSortMethod(303);
            Collections.sort(monsterList, monsterType3Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }
    };

    private SortStatsDialogFragment.SortBy sortByStats = new SortStatsDialogFragment.SortBy() {
        @Override
        public void sortHp() {
            Singleton.getInstance().setSaveSortMethod(401);
            Collections.sort(monsterList, monsterHpComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            Singleton.getInstance().setSaveSortMethod(402);
            Collections.sort(monsterList, monsterAtkComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            Singleton.getInstance().setSaveSortMethod(403);
            Collections.sort(monsterList, monsterRcvComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }
    };

    private SortPlusDialogFragment.SortBy sortByPlus = new SortPlusDialogFragment.SortBy() {
        @Override
        public void sortTotal() {
            Singleton.getInstance().setSaveSortMethod(701);
            Collections.sort(monsterList, monsterPlusComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortHp() {
            Singleton.getInstance().setSaveSortMethod(702);
            Collections.sort(monsterList, monsterPlusHpComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            Singleton.getInstance().setSaveSortMethod(703);
            Collections.sort(monsterList, monsterPlusAtkComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            Singleton.getInstance().setSaveSortMethod(704);
            Collections.sort(monsterList, monsterPlusRcvComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }
    };

    public void sortArrayList(int sortMethod) {
        switch (sortMethod) {
            case 0:
                Singleton.getInstance().setSaveSortMethod(sortMethod);
                Collections.sort(monsterList, monsterAlphabeticalComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 1:
                Singleton.getInstance().setSaveSortMethod(sortMethod);
                Collections.sort(monsterList, monsterNumberComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 2:
                if (sortElementDialogFragment == null) {
                    sortElementDialogFragment = SortElementDialogFragment.newInstance(sortByElement);
                }
                if (!sortElementDialogFragment.isAdded() && !firstRun) {
                    sortElementDialogFragment.show(getChildFragmentManager(), "Sort by Element");
                }
                break;
            case 3:
                if (sortTypeDialogFragment == null) {
                    sortTypeDialogFragment = SortTypeDialogFragment.newInstance(sortByType);
                }
                if (!sortTypeDialogFragment.isAdded() && !firstRun) {
                    sortTypeDialogFragment.show(getChildFragmentManager(), "Sort by Type");
                }
                break;
            case 4:
                if (sortStatsDialogFragment == null) {
                    sortStatsDialogFragment = SortStatsDialogFragment.newInstance(sortByStats);
                }
                if (!sortStatsDialogFragment.isAdded() && !firstRun) {
                    sortStatsDialogFragment.show(getChildFragmentManager(), "Sort by Stats");
                }
                break;
            case 5:
                Singleton.getInstance().setSaveSortMethod(sortMethod);
                Collections.sort(monsterList, monsterRarityComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 6:
                Singleton.getInstance().setSaveSortMethod(sortMethod);
                Collections.sort(monsterList, monsterAwakeningComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 7:
                Singleton.getInstance().setSaveSortMethod(sortMethod);
                if (sortPlusDialogFragment == null) {
                    sortPlusDialogFragment = SortPlusDialogFragment.newInstance(sortByPlus);
                }
                if (!sortPlusDialogFragment.isAdded() && !firstRun) {
                    sortPlusDialogFragment.show(getChildFragmentManager(), "Sort by Plus");
                }
                break;
            case 8:
                Singleton.getInstance().setSaveSortMethod(sortMethod);
                Collections.sort(monsterList, monsterFavoriteComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 9:
                Singleton.getInstance().setSaveSortMethod(sortMethod);
                Collections.sort(monsterList, monsterLevelComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 201:
                Collections.sort(monsterList, monsterElement1Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 202:
                Collections.sort(monsterList, monsterElement2Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 301:
                Collections.sort(monsterList, monsterType1Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 302:
                Collections.sort(monsterList, monsterType2Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 303:
                Collections.sort(monsterList, monsterType3Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 401:
                Collections.sort(monsterList, monsterHpComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 402:
                Collections.sort(monsterList, monsterAtkComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 403:
                Collections.sort(monsterList, monsterRcvComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 701:
                Collections.sort(monsterList, monsterPlusComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 702:
                Collections.sort(monsterList, monsterPlusHpComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 703:
                Collections.sort(monsterList, monsterPlusAtkComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 704:
                Collections.sort(monsterList, monsterPlusRcvComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;

        }
    }

    public void reverseArrayList() {
        switch (Singleton.getInstance().getSaveSortMethod()) {
            case 202:
                element2Reverse();
                saveMonsterListRecycler.setMonsterList(monsterList);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 302:
                type2Reverse();
                saveMonsterListRecycler.setMonsterList(monsterList);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 303:
                type3Reverse();
                saveMonsterListRecycler.setMonsterList(monsterList);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            default:
                defaultReverse();
                saveMonsterListRecycler.setMonsterList(monsterList);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;

        }
    }

    private void defaultReverse() {
        Collections.reverse(monsterList);
        if (monsterList.contains(monsterZero)) {
            monsterList.remove(monsterZero);
            monsterList.add(0, monsterZero);
        }
    }

    private void element2Reverse() {
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            if (monsterList.get(i).getElement2Int() >= 0) {
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for (int i = 0; i < sorting.size(); i++) {
            monsterList.add(i, sorting.get(i));
        }
        if (monsterList.contains(monsterZero)) {
            monsterList.remove(monsterZero);
            monsterList.add(0, monsterZero);
        }
    }

    private void type2Reverse() {
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            if (monsterList.get(i).getType2() >= 0) {
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for (int i = 0; i < sorting.size(); i++) {
            monsterList.add(i, sorting.get(i));
        }
        if (monsterList.contains(monsterZero)) {
            monsterList.remove(monsterZero);
            monsterList.add(0, monsterZero);
        }
    }

    private void type3Reverse() {
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            if (monsterList.get(i).getType3() >= 0) {
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for (int i = 0; i < sorting.size(); i++) {
            monsterList.add(i, sorting.get(i));
        }
        if (monsterList.contains(monsterZero)) {
            monsterList.remove(monsterZero);
            monsterList.add(0, monsterZero);
        }
    }

    public void searchFilter(String query) {
        if (saveMonsterListRecycler != null) {
            if (query != null && query.length() > 0) {
                if (!monsterList.isEmpty()) {
                    monsterList.clear();
                }
                RealmResults<Monster> results;
                results = realm.where(Monster.class)
                        .beginGroup()
                        .contains("baseMonster.name", query, Case.INSENSITIVE)
                        .or()
                        .contains("baseMonster.type1String", query, Case.INSENSITIVE)
                        .or()
                        .contains("baseMonster.type2String", query, Case.INSENSITIVE)
                        .or()
                        .contains("baseMonster.type3String", query, Case.INSENSITIVE)
                        .or()
                        .contains("baseMonster.monsterIdString", query, Case.INSENSITIVE)
                        .or()
                        .contains("baseMonster.activeSkillString", query, Case.INSENSITIVE)
                        .or()
                        .contains("baseMonster.leaderSkillString", query, Case.INSENSITIVE)
                        .or()
                        .contains("monsterInherit.baseMonster.activeSkillString", query, Case.INSENSITIVE)
                        .endGroup()
                        .greaterThan("monsterId", 0)
                        .equalTo("helper", helper)
                        .findAll();
                if (selection == MonsterTabLayoutFragment.INHERIT) {
                    results = results.where().equalTo("baseMonster.inheritable", true).findAll();
                }

                monsterList.addAll(results);

//                filterMonsters(query);

//                filterMonsterName(query);
//                filterMonsterType(query);
//                filterMonsterNumber(query);
//                filterMonsterElement(query);
            } else {
                monsterList.clear();
                monsterList.addAll(monsterListAll);
            }
            sortArrayList(Singleton.getInstance().getSaveSortMethod());
//            saveMonsterListRecycler.notifyDataSetChanged(monsterList);
            firstRun = false;
            saveMonsterListRecycler.setExpandedPosition(-1);
            if (monsterList.size() == 0) {
                savedMonsters.setText("No results");
                savedMonsters.setVisibility(View.VISIBLE);
            } else {
                savedMonsters.setText("No Saved Monsters");
                savedMonsters.setVisibility(View.INVISIBLE);
            }
        }
    }

    private FilterDialogFragment.SaveTeam saveTeam = new FilterDialogFragment.SaveTeam() {
        @Override
        public void filter() {
            boolean remove = true;
            if (filteredMonsters.size() > 0) {
                for (int i = 0; i < filteredMonsters.size(); i++) {
                    monsterList.add(filteredMonsters.get(i));
                }
                filteredMonsters.clear();
            }
            Iterator<Monster> iter = monsterList.iterator();
            if (Singleton.getInstance().getFilterElement1().size() != 0 || Singleton.getInstance().getFilterElement2().size() != 0 || Singleton.getInstance().getFilterTypes().size() != 0 || Singleton.getInstance().getFilterAwakenings().size() != 0 || Singleton.getInstance().getFilterLatents().size() != 0) {
                while (iter.hasNext()) {
                    Monster monster = iter.next();
                    if (Singleton.getInstance().getFilterElement1().size() != 0) {
                        if (Singleton.getInstance().getFilterElement1().contains(monster.getElement1())) {
                            remove = false;
                        }
                    }
                    if (Singleton.getInstance().getFilterElement2().size() != 0 && remove) {
                        if (Singleton.getInstance().getFilterElement2().contains(monster.getElement2())) {
                            remove = false;
                        }
                    }
                    if (Singleton.getInstance().getFilterTypes().size() != 0 && remove) {
                        for (int i = 0; i < monster.getTypes().size(); i++) {
                            if (Singleton.getInstance().getFilterTypes().contains(monster.getTypes().get(i)) && remove) {
                                remove = false;
                            }
                        }
                    }
                    if (Singleton.getInstance().getFilterAwakenings().size() != 0 && remove) {
                        for (int i = 0; i < monster.getAwokenSkills().size(); i++) {
                            if (Singleton.getInstance().getFilterAwakenings().contains(monster.getAwokenSkills(i)) && remove) {
                                remove = false;
                            }
                        }
                    }
                    if (Singleton.getInstance().getFilterLatents().size() != 0 && remove) {
                        for (int i = 0; i < monster.getLatents().size(); i++) {
                            if (Singleton.getInstance().getFilterLatents().contains(monster.getLatents().get(i).getValue()) && remove) {
                                remove = false;
                            }
                        }
                    }
                    if (remove) {
                        filteredMonsters.add(monster);
                        iter.remove();
                    }
                    remove = true;
                }
            }
            sortArrayList(Singleton.getInstance().getSaveSortMethod());
            saveMonsterListRecycler.setExpandedPosition(-1);
            saveMonsterListRecycler.notifyDataSetChanged();
            if (monsterList.size() == 0) {
                savedMonsters.setText("No results");
                savedMonsters.setVisibility(View.VISIBLE);
            } else {
                savedMonsters.setText("No Saved Monsters");
                savedMonsters.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void filterRequirements() {
            boolean match = true;
            int counter = 0;
            if (filteredMonsters.size() > 0) {
                for (int i = 0; i < filteredMonsters.size(); i++) {
                    monsterList.add(filteredMonsters.get(i));
                }
                filteredMonsters.clear();
            }
            Iterator<Monster> iter = monsterList.iterator();
            if (Singleton.getInstance().getFilterElement1().size() != 0 || Singleton.getInstance().getFilterElement2().size() != 0 || Singleton.getInstance().getFilterTypes().size() != 0 || Singleton.getInstance().getFilterAwakenings().size() != 0 || Singleton.getInstance().getFilterLatents().size() != 0) {
                while (iter.hasNext()) {
                    Monster monster = iter.next();
                    if (Singleton.getInstance().getFilterElement1().size() != 0) {
                        if (!Singleton.getInstance().getFilterElement1().contains(monster.getElement1())) {
                            match = false;
                        }
                    }
                    if (Singleton.getInstance().getFilterElement2().size() != 0 && match) {
                        if (!Singleton.getInstance().getFilterElement2().contains(monster.getElement2())) {
                            match = false;
                        }
                    }
                    if (Singleton.getInstance().getFilterTypes().size() != 0 && match) {
                        for (int i = 0; i < monster.getTypes().size(); i++) {
                            if (Singleton.getInstance().getFilterTypes().contains(monster.getTypes().get(i))) {
                                counter++;
                            }
                        }
                        if (counter != Singleton.getInstance().getFilterTypes().size()) {
                            match = false;
                        }
                        counter = 0;
                    }
                    if (Singleton.getInstance().getFilterAwakenings().size() != 0 && match) {
                        ArrayList<Integer> trimmedAwakenings = new ArrayList<>();
                        for (int i = 0; i < monster.getAwokenSkills().size(); i++) {
                            if (!trimmedAwakenings.contains(monster.getAwokenSkills(i))) {
                                trimmedAwakenings.add(monster.getAwokenSkills(i));
                            }
                        }
                        for (int i = 0; i < trimmedAwakenings.size(); i++) {
                            if (Singleton.getInstance().getFilterAwakenings().contains(trimmedAwakenings.get(i))) {
                                counter++;
                            }
                        }
                        if (counter != Singleton.getInstance().getFilterAwakenings().size()) {
                            match = false;
                        }
                        counter = 0;
                    }
                    if (Singleton.getInstance().getFilterLatents().size() != 0 && match) {
                        ArrayList<Integer> trimmedAwakenings = new ArrayList<>();
                        for (int i = 0; i < monster.getLatents().size(); i++) {
                            if (!trimmedAwakenings.contains(monster.getLatents().get(i).getValue())) {
                                trimmedAwakenings.add(monster.getLatents().get(i).getValue());
                            }
                        }
                        for (int i = 0; i < trimmedAwakenings.size(); i++) {
                            if (Singleton.getInstance().getFilterLatents().contains(trimmedAwakenings.get(i))) {
                                counter++;
                            }
                        }
                        if (counter != Singleton.getInstance().getFilterLatents().size()) {
                            match = false;
                        }
                        counter = 0;
                    }
                    if (!match) {
                        filteredMonsters.add(monster);
                        iter.remove();
                    }
                    match = true;
                }
            }
            sortArrayList(Singleton.getInstance().getSaveSortMethod());
            saveMonsterListRecycler.setExpandedPosition(-1);
            saveMonsterListRecycler.notifyDataSetChanged();
            if (monsterList.size() == 0) {
                savedMonsters.setText("No results");
                savedMonsters.setVisibility(View.VISIBLE);
            } else {
                savedMonsters.setText("No Saved Monsters");
                savedMonsters.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new JustAnotherBroadcastReceiver(receiverMethods);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("REFRESH"));

    }

    private JustAnotherBroadcastReceiver.receiverMethods receiverMethods = new JustAnotherBroadcastReceiver.receiverMethods() {
        @Override
        public void onReceiveMethod(Intent intent) {
            switch(intent.getAction()){
                case "REFRESH":
                    onSelect();
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (searchMenuItem != null) {
            if (MenuItemCompat.isActionViewExpanded(searchMenuItem)) {
                MenuItemCompat.collapseActionView(searchMenuItem);
            }
        }
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private void gridToggle(){
        preferences.edit().putBoolean("isGrid", !isGrid).apply();
        isGrid = preferences.getBoolean("isGrid", true);
        if (isGrid) {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        } else {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        }
        saveMonsterListRecycler.notifyDataSetChanged(isGrid);
        if (saveMonsterListRecycler.getExpandedPosition() > -1) {
            monsterListView.scrollToPosition(saveMonsterListRecycler.getExpandedPosition());
        }
    }

    public void onSelect(){
        if(preferences.getBoolean("isGrid", true) != isGrid){
            gridToggle();
        }
    }
}


