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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.padassist.Adapters.BaseMonsterListRecycler;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Element;
import com.padassist.Fragments.SortElementDialogFragment;
import com.padassist.Fragments.SortStatsDialogFragment;
import com.padassist.Fragments.SortTypeDialogFragment;
import com.padassist.Graphics.FastScroller;
import com.padassist.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public abstract class BaseMonsterListUtil extends Fragment {
    public static final String TAG = BaseMonsterListUtil.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private boolean replaceAll;
    private long replaceMonsterId;
    protected RecyclerView monsterListView;
    protected ArrayList<BaseMonster> monsterList;
    protected ArrayList<BaseMonster> monsterListAll;
    protected BaseMonsterListRecycler baseMonsterListRecycler;
    private MenuItem searchMenuItem;
    protected SearchView searchView;
    private Toast toast;
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

    private FastScroller fastScroller;

    public BaseMonsterListUtil() {
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
        monsterListAll = (ArrayList) BaseMonster.getAllMonsters();
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
        Singleton.getInstance().setBaseSortMethod(sortMethod);
        switch (sortMethod) {
            case 0:
                Collections.sort(monsterList, monsterAlphabeticalComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 1:
                Collections.sort(monsterList, monsterNumberComparator);
                baseMonsterListRecycler.notifyDataSetChanged();
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
                baseMonsterListRecycler.notifyDataSetChanged();
                break;
            case 6:
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
        if (monsterList.contains(BaseMonster.getMonsterId(0))) {
            monsterList.remove(BaseMonster.getMonsterId(0));
            monsterList.add(0, BaseMonster.getMonsterId(0));
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
        if (monsterList.contains(BaseMonster.getMonsterId(0))) {
            monsterList.remove(BaseMonster.getMonsterId(0));
            monsterList.add(0, BaseMonster.getMonsterId(0));
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
        if (monsterList.contains(BaseMonster.getMonsterId(0))) {
            monsterList.remove(BaseMonster.getMonsterId(0));
            monsterList.add(0, BaseMonster.getMonsterId(0));
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
        if (monsterList.contains(BaseMonster.getMonsterId(0))) {
            monsterList.remove(BaseMonster.getMonsterId(0));
            monsterList.add(0, BaseMonster.getMonsterId(0));
        }
    }

    public void searchFilter(String query) {
        if (baseMonsterListRecycler != null) {
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
            sortArrayList(Singleton.getInstance().getBaseSortMethod());
            baseMonsterListRecycler.setExpandedPosition(-1);
//            if(fastScroller != null){
//                fastScroller.resizeScrollBar(baseMonsterListRecycler.expanded(), FastScroller.BASE_MONSTER_LIST);
//            }
        }
    }

    private void filterMonsterName(String query) {
        for (BaseMonster monster : monsterListAll) {
            if (monster.getName().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
    }

    private void filterMonsterNumber(String query) {
        for (BaseMonster monster : monsterListAll) {
            if (String.valueOf(monster.getMonsterId()).toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
    }

    private void filterMonsterType(String query) {
        for (BaseMonster monster : monsterListAll) {
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
        for (BaseMonster monster : monsterListAll) {
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
