package com.padassist.Util;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
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

import com.padassist.Adapters.BaseMonsterListRecycler;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Element;
import com.padassist.Fragments.FilterDialogFragment;
import com.padassist.Fragments.SortElementDialogFragment;
import com.padassist.Fragments.SortStatsDialogFragment;
import com.padassist.Fragments.SortTypeDialogFragment;
import com.padassist.Graphics.FastScroller;
import com.padassist.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;


public abstract class BaseMonsterListBase extends Fragment {
    public static final String TAG = BaseMonsterListBase.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private boolean replaceAll;
    private long replaceMonsterId;
    protected RecyclerView monsterListView;
    protected ArrayList<BaseMonster> filteredMonsters = new ArrayList<>();
    protected ArrayList<BaseMonster> monsterList;
    protected ArrayList<BaseMonster> monsterListAll;
    protected BaseMonsterListRecycler baseMonsterListRecycler;
    private MenuItem searchMenuItem;
    protected SearchView searchView;
    private Toast toast;
    private boolean firstRun = true;
    private SortElementDialogFragment sortElementDialogFragment;
    private SortTypeDialogFragment sortTypeDialogFragment;
    private SortStatsDialogFragment sortStatsDialogFragment;
    private Comparator<BaseMonster> monsterNumberComparator = new BaseMonsterNumberComparator();
    private Comparator<BaseMonster> monsterElement1Comparator = new BaseMonsterElement1Comparator();
    private Comparator<BaseMonster> monsterElement2Comparator = new BaseMonsterElement2Comparator();
    private Comparator<BaseMonster> monsterType1Comparator = new BaseMonsterType1Comparator();
    private Comparator<BaseMonster> monsterType2Comparator = new BaseMonsterType2Comparator();
    private Comparator<BaseMonster> monsterType3Comparator = new BaseMonsterType3Comparator();
    private Comparator<BaseMonster> monsterAlphabeticalComparator = new BaseMonsterAlphabeticalComparator();
    private Comparator<BaseMonster> monsterHpComparator = new BaseMonsterHpComparator();
    private Comparator<BaseMonster> monsterAtkComparator = new BaseMonsterAtkComparator();
    private Comparator<BaseMonster> monsterRcvComparator = new BaseMonsterRcvComparator();
    private Comparator<BaseMonster> monsterRarityComparator = new BaseMonsterRarityComparator();
    private Comparator<BaseMonster> monsterAwakeningComparator = new BaseMonsterAwakeningComparator();
    protected Realm realm;
    private FilterDialogFragment filterDialogFragment;
    private TextView noResults;

    protected SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());
    protected boolean isGrid = preferences.getBoolean("isGrid", true);

    private FastScroller fastScroller;

    public BaseMonsterListBase() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base_monster_list, container, false);
        monsterListView = (RecyclerView) rootView.findViewById(R.id.monsterListView);
        fastScroller = (FastScroller) rootView.findViewById(R.id.fastScroller);
        noResults = (TextView) rootView.findViewById(R.id.noResults);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.sortGroup, true);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggleCoop:
                baseMonsterListRecycler.notifyDataSetChanged();
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
                    filterDialogFragment = FilterDialogFragment.newInstance(saveTeam, false);
                }
                if (!filterDialogFragment.isAdded() && !firstRun) {
                    filterDialogFragment.show(getChildFragmentManager(), false, "Filter");
                }
                break;
            case R.id.toggleGrid:
                preferences.edit().putBoolean("isGrid", !isGrid).apply();
                isGrid = preferences.getBoolean("isGrid", true);
                if (isGrid) {
                    monsterListView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
                } else {
                    monsterListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                }
                baseMonsterListRecycler.notifyDataSetChanged(isGrid);
                if (baseMonsterListRecycler.getExpandedPosition() > -1) {
                    monsterListView.scrollToPosition(baseMonsterListRecycler.getExpandedPosition());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            replaceAll = getArguments().getBoolean("replaceAll");
            replaceMonsterId = getArguments().getLong("replaceMonsterId");
        }
        realm = Realm.getDefaultInstance();
        if (monsterListAll == null) {
            monsterListAll = new ArrayList<>();
        }

        monsterListAll.addAll(realm.where(BaseMonster.class).greaterThan("monsterId", 0).findAllSorted("monsterId"));

//        Gson gson = new Gson();
//        String baseMonsterList = preferences.getString("BaseMonsterList", "");
//        if(baseMonsterList.isEmpty()){
//            RealmResults<BaseMonster> results = realm.where(BaseMonster.class).greaterThan("monsterId", 0).findAllSorted("monsterId");
//            for(BaseMonster monster : results){
//                monsterListAll.add(realm.copyFromRealm(monster));
//            }
//            String jsonBaseMonsters = gson.toJson(monsterListAll);
//            preferences.edit().putString("BaseMonsterList", jsonBaseMonsters).apply();
//        } else {
//            Type baseMonsterType = new TypeToken<ArrayList<BaseMonster>>(){}.getType();
//            monsterListAll = gson.fromJson(baseMonsterList, baseMonsterType);
//        }

        if (monsterList == null) {
            monsterList = new ArrayList<>();
            monsterList.addAll(monsterListAll);
        }

        fastScroller.setRecyclerView(monsterListView);
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
            Singleton.getInstance().setBaseSortMethod(201);
            Collections.sort(monsterList, monsterElement1Comparator);
            baseMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortElement2() {
            Singleton.getInstance().setBaseSortMethod(202);
            Collections.sort(monsterList, monsterElement2Comparator);
            baseMonsterListRecycler.notifyDataSetChanged();
        }
    };

    private SortTypeDialogFragment.SortBy sortByType = new SortTypeDialogFragment.SortBy() {
        @Override
        public void sortType1() {
            Singleton.getInstance().setBaseSortMethod(301);
            Collections.sort(monsterList, monsterType1Comparator);
            baseMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortType2() {
            Singleton.getInstance().setBaseSortMethod(302);
            Collections.sort(monsterList, monsterType2Comparator);
            baseMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortType3() {
            Singleton.getInstance().setBaseSortMethod(303);
            Collections.sort(monsterList, monsterType3Comparator);
            baseMonsterListRecycler.notifyDataSetChanged();
        }
    };

    private SortStatsDialogFragment.SortBy sortByStats = new SortStatsDialogFragment.SortBy() {
        @Override
        public void sortHp() {
            Singleton.getInstance().setBaseSortMethod(401);
            Collections.sort(monsterList, monsterHpComparator);
            baseMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            Singleton.getInstance().setBaseSortMethod(402);
            Collections.sort(monsterList, monsterAtkComparator);
            baseMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            Singleton.getInstance().setBaseSortMethod(403);
            Collections.sort(monsterList, monsterRcvComparator);
            baseMonsterListRecycler.notifyDataSetChanged();
        }
    };

    public void sortArrayList(int sortMethod) {
        switch (sortMethod) {
            case 0:
                Singleton.getInstance().setBaseSortMethod(sortMethod);
                Collections.sort(monsterList, monsterAlphabeticalComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 1:
                Singleton.getInstance().setBaseSortMethod(sortMethod);
                Collections.sort(monsterList, monsterNumberComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
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
                Singleton.getInstance().setBaseSortMethod(sortMethod);
                Collections.sort(monsterList, monsterRarityComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 6:
                Singleton.getInstance().setBaseSortMethod(sortMethod);
                Collections.sort(monsterList, monsterAwakeningComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 201:
                Collections.sort(monsterList, monsterElement1Comparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 202:
                Collections.sort(monsterList, monsterElement2Comparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 301:
                Collections.sort(monsterList, monsterType1Comparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 302:
                Collections.sort(monsterList, monsterType2Comparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 303:
                Collections.sort(monsterList, monsterType3Comparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 401:
                Collections.sort(monsterList, monsterHpComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 402:
                Collections.sort(monsterList, monsterAtkComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 403:
                Collections.sort(monsterList, monsterRcvComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
        }
    }

    public void reverseArrayList() {
        switch (Singleton.getInstance().getBaseSortMethod()) {
            case 202:
                element2Reverse();
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 302:
                type2Reverse();
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 303:
                type3Reverse();
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            default:
                defaultReverse();
                baseMonsterListRecycler.notifyDataSetChanged();
                break;

        }
    }

    private void defaultReverse() {
        Collections.reverse(monsterList);
        if (monsterList.contains(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst())) {
            monsterList.remove(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
            monsterList.add(0, realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
        }
    }

    private void element2Reverse() {
        ArrayList<BaseMonster> sorting = new ArrayList<>();
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
        if (monsterList.contains(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst())) {
            monsterList.remove(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
            monsterList.add(0, realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
        }
    }

    private void type2Reverse() {
        ArrayList<BaseMonster> sorting = new ArrayList<>();
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
        if (monsterList.contains(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst())) {
            monsterList.remove(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
            monsterList.add(0, realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
        }
    }

    private void type3Reverse() {
        ArrayList<BaseMonster> sorting = new ArrayList<>();
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
        if (monsterList.contains(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst())) {
            monsterList.remove(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
            monsterList.add(0, realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
        }
    }

    public void searchFilter(String query) {
        if (baseMonsterListRecycler != null) {
            if (query != null && query.length() > 0) {
                if (!monsterList.isEmpty()) {
                    monsterList.clear();
                }
                if(query.length() == 1){
                    filterMonsters(query);
                } else {
                    RealmResults<BaseMonster> results = realm.where(BaseMonster.class)
                            .beginGroup()
                            .contains("name", query, Case.INSENSITIVE)
                            .or()
                            .contains("type1String", query, Case.INSENSITIVE)
                            .or()
                            .contains("type2String", query, Case.INSENSITIVE)
                            .or()
                            .contains("type3String", query, Case.INSENSITIVE)
                            .or()
                            .contains("monsterIdString", query, Case.INSENSITIVE)
                            .endGroup()
                            .greaterThan("monsterId", 0).findAll();
                    monsterList.addAll(results);
                }

            } else {
                monsterList.clear();
                monsterList.addAll(monsterListAll);
            }
            sortArrayList(Singleton.getInstance().getBaseSortMethod());
            firstRun = false;
            baseMonsterListRecycler.setExpandedPosition(-1);
            if (monsterList.size() == 0) {
                noResults.setVisibility(View.VISIBLE);
            } else {
                noResults.setVisibility(View.INVISIBLE);
            }
//            if(fastScroller != null){
//                fastScroller.resizeScrollBar(baseMonsterListRecycler.expanded(), FastScroller.BASE_MONSTER_LIST);
//            }
        }
    }



    private void filterMonsters(String query) {
        for (BaseMonster monster : monsterListAll) {
            if (monster.getName().toLowerCase().contains(query.toLowerCase())) {
                monsterList.add(monster);
            } else if (String.valueOf(monster.getMonsterId()).toLowerCase().contains(query.toLowerCase())) {
                monsterList.add(monster);
            } else if (monster.getType1String().toLowerCase().contains(query.toLowerCase())) {
                monsterList.add(monster);
            } else if (monster.getType2String().toLowerCase().contains(query.toLowerCase())) {
                monsterList.add(monster);
            } else if (monster.getType3String().toLowerCase().contains(query.toLowerCase())) {
                monsterList.add(monster);
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
            Iterator<BaseMonster> iter = monsterList.iterator();
            if (Singleton.getInstance().getFilterElement1().size() != 0 || Singleton.getInstance().getFilterElement2().size() != 0 || Singleton.getInstance().getFilterTypes().size() != 0 || Singleton.getInstance().getFilterAwakenings().size() != 0 || Singleton.getInstance().getFilterLatents().size() != 0) {
                while (iter.hasNext()) {
                    BaseMonster monster = iter.next();
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
                            if (Singleton.getInstance().getFilterAwakenings().contains(monster.getAwokenSkills(i).getValue()) && remove) {
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
            sortArrayList(Singleton.getInstance().getBaseSortMethod());
            baseMonsterListRecycler.setExpandedPosition(-1);
            baseMonsterListRecycler.notifyDataSetChanged();
            if (monsterList.size() == 0) {
                noResults.setVisibility(View.VISIBLE);
            } else {
                noResults.setVisibility(View.INVISIBLE);
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
            Iterator<BaseMonster> iter = monsterList.iterator();
            if (Singleton.getInstance().getFilterElement1().size() != 0 || Singleton.getInstance().getFilterElement2().size() != 0 || Singleton.getInstance().getFilterTypes().size() != 0 || Singleton.getInstance().getFilterAwakenings().size() != 0 || Singleton.getInstance().getFilterLatents().size() != 0) {
                while (iter.hasNext()) {
                    BaseMonster monster = iter.next();
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
                            if (!trimmedAwakenings.contains(monster.getAwokenSkills(i).getValue())) {
                                trimmedAwakenings.add(monster.getAwokenSkills(i).getValue());
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
                    if (!match) {
                        filteredMonsters.add(monster);
                        iter.remove();
                    }
                    match = true;
                }
            }
            sortArrayList(Singleton.getInstance().getBaseSortMethod());
            baseMonsterListRecycler.setExpandedPosition(-1);
            baseMonsterListRecycler.notifyDataSetChanged();
            if (monsterList.size() == 0) {
                noResults.setVisibility(View.VISIBLE);
            } else {
                noResults.setVisibility(View.INVISIBLE);
            }
        }
    };
}
