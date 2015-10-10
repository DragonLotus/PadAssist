package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anthony.damagecalculator.Adapters.MonsterPagerAdapter;
import com.example.anthony.damagecalculator.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterTabLayoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterTabLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterTabLayoutFragment extends AbstractFragment {

    public static final String TAG = MonsterTabLayoutFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MonsterPagerAdapter monsterPagerAdapter;

    // TODO: Rename and change types and number of parameters
    public static MonsterTabLayoutFragment newInstance() {
        MonsterTabLayoutFragment fragment = new MonsterTabLayoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MonsterTabLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monster_tab_layout, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        monsterPagerAdapter = new MonsterPagerAdapter(getChildFragmentManager(), getActivity());
        Log.d("MonsterTab", "monsterPagerAdapter is: " + monsterPagerAdapter);
        viewPager.setAdapter(monsterPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void reverseArrayList() {
        if(tabLayout.getSelectedTabPosition() == 0){
            ((AbstractFragment)monsterPagerAdapter.getItem(0)).reverseArrayList();
        }else if(tabLayout.getSelectedTabPosition() == 1){
            ((AbstractFragment)monsterPagerAdapter.getItem(1)).reverseArrayList();
        }
    }

    @Override
    public void sortArrayList(int sortMethod) {
        if(tabLayout.getSelectedTabPosition() == 0){
            ((AbstractFragment)monsterPagerAdapter.getItem(0)).sortArrayList(sortMethod);
        }else if(tabLayout.getSelectedTabPosition() == 1){
            ((AbstractFragment)monsterPagerAdapter.getItem(1)).sortArrayList(sortMethod);
        }
    }

}
