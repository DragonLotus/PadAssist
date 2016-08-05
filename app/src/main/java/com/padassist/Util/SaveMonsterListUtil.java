package com.padassist.Util;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import com.padassist.Adapters.SaveMonsterListRecycler;
import com.padassist.Data.Element;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Fragments.SortElementDialogFragment;
import com.padassist.Fragments.SortPlusDialogFragment;
import com.padassist.Fragments.SortStatsDialogFragment;
import com.padassist.Fragments.SortTypeDialogFragment;
import com.padassist.Graphics.FastScroller;
import com.padassist.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;


public abstract class SaveMonsterListUtil extends Fragment {
    public static final String TAG = SaveMonsterListUtil.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    protected RecyclerView monsterListView;
    protected ArrayList<Monster> monsterList;
    protected ArrayList<Monster> monsterListAll;
    protected MenuItem searchMenuItem;
    protected SearchView searchView;
    protected SaveMonsterListRecycler saveMonsterListRecycler;
    private Toast toast;
    private TextView savedMonsters;
    private boolean replaceAll;
    private long replaceMonsterId;
    private int monsterPosition;
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
    protected Realm realm = Realm.getDefaultInstance();
    private Monster monsterZero = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();

    private FastScroller fastScroller;

    public SaveMonsterListUtil() {
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                return true;
            }
        });
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
        onActivityCreatedSpecific();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            replaceAll = getArguments().getBoolean("replaceAll");
            replaceMonsterId = getArguments().getLong("replaceMonsterId");
        }
        if (monsterList == null) {
            monsterList = new ArrayList<>();
        }
//        filterMonsterName("");
        emptyCheck();

        fastScroller.setRecyclerView(monsterListView);


    }

    public abstract void onActivityCreatedSpecific();

    protected void emptyCheck(){
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
        Singleton.getInstance().setSaveSortMethod(sortMethod);
        switch (sortMethod) {
            case 0:
                Collections.sort(monsterList, monsterAlphabeticalComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 1:
                Collections.sort(monsterList, monsterNumberComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 2:
                if (sortElementDialogFragment == null) {
                    sortElementDialogFragment = SortElementDialogFragment.newInstance(sortByElement);
                }
                sortElementDialogFragment.show(getChildFragmentManager(), "Sort by Element");
                break;
            case 3:
                if (sortTypeDialogFragment == null) {
                    sortTypeDialogFragment = SortTypeDialogFragment.newInstance(sortByType);
                }
                sortTypeDialogFragment.show(getChildFragmentManager(), "Sort by Type");
                break;
            case 4:
                if (sortStatsDialogFragment == null) {
                    sortStatsDialogFragment = SortStatsDialogFragment.newInstance(sortByStats);
                }
                sortStatsDialogFragment.show(getChildFragmentManager(), "Sort by Stats");
                break;
            case 5:
                Collections.sort(monsterList, monsterRarityComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 6:
                Collections.sort(monsterList, monsterAwakeningComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 7:
                if (sortPlusDialogFragment == null) {
                    sortPlusDialogFragment = SortPlusDialogFragment.newInstance(sortByPlus);
                }
                sortPlusDialogFragment.show(getChildFragmentManager(), "Sort by Plus");
                break;
            case 8:
                Collections.sort(monsterList, monsterFavoriteComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 9:
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
                filterMonsterName(query);
                filterMonsterType(query);
                filterMonsterNumber(query);
//                filterMonsterElement(query);
            } else {
                monsterList.clear();
                monsterList.addAll(monsterListAll);
            }
            sortArrayList(Singleton.getInstance().getSaveSortMethod());
//            saveMonsterListRecycler.notifyDataSetChanged(monsterList);

            saveMonsterListRecycler.setExpandedPosition(-1);

            Log.d("SaveMonsterListUtil", "MonsterList is: " + monsterList);
        }


    }

    private void filterMonsterName(String query) {
        for (Monster monster : monsterListAll) {
            if (monster.getName().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
    }

    private void filterMonsterNumber(String query) {
        for (Monster monster : monsterListAll) {
            if (String.valueOf(monster.getBaseMonsterId()).toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
    }

    private void filterMonsterType(String query) {
        for (Monster monster : monsterListAll) {
            if (monster.getType1String().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            } else if (monster.getType2String().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            } else if (monster.getType3String().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
    }

    private void filterMonsterElement(String query) {
        for (Monster monster : monsterListAll) {
            if (query.toLowerCase().equals("fire") || query.toLowerCase().equals("red")) {
                if (monster.getElement1().equals(Element.RED) || monster.getElement2().equals(Element.RED) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            } else if (query.toLowerCase().equals("water") || query.toLowerCase().equals("blue")) {
                if (monster.getElement1().equals(Element.BLUE) || monster.getElement2().equals(Element.BLUE) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            } else if (query.toLowerCase().equals("wood") || query.toLowerCase().equals("green")) {
                if (monster.getElement1().equals(Element.GREEN) || monster.getElement2().equals(Element.GREEN) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            } else if (query.toLowerCase().equals("light") || query.toLowerCase().equals("yellow")) {
                if (monster.getElement1().equals(Element.LIGHT) || monster.getElement2().equals(Element.LIGHT) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            } else if (query.toLowerCase().equals("dark") || query.toLowerCase().equals("purple")) {
                if (monster.getElement1().equals(Element.DARK) || monster.getElement2().equals(Element.DARK) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            }
        }
    }
}
