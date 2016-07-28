package com.padassist.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padassist.Adapters.MonsterPagerAdapter;
import com.padassist.Data.Monster;
import com.padassist.R;

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
    private boolean replaceAll;
    private long replaceMonsterId;
    private int monsterPosition;
    private MonsterPagerAdapter monsterPagerAdapter;

    // TODO: Rename and change types and number of parameters
    public static MonsterTabLayoutFragment newInstance(boolean replaceAll, long replaceMonsterId, int monsterPosition) {
        MonsterTabLayoutFragment fragment = new MonsterTabLayoutFragment();
        Bundle args = new Bundle();
        args.putBoolean("replaceAll", replaceAll);
        args.putLong("replaceMonsterId", replaceMonsterId);
        args.putInt("monsterPosition", monsterPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public MonsterTabLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (getArguments() != null) {
            replaceAll = getArguments().getBoolean("replaceAll");
            replaceMonsterId = getArguments().getLong("replaceMonsterId");
            monsterPosition = getArguments().getInt("monsterPosition");
        }
        monsterPagerAdapter = new MonsterPagerAdapter(getChildFragmentManager(), getActivity(), replaceAll, replaceMonsterId);
        viewPager.setAdapter(monsterPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if(Monster.getAllHelperMonsters().size() < 1 && monsterPosition == 5 && replaceMonsterId == 0){
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }else if(Monster.getAllMonsters().size() <= 1){
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }
        if(tabLayout.getSelectedTabPosition() == 0){
            getActivity().setTitle("Saved Monsters");
        } else if(tabLayout.getSelectedTabPosition() == 1) {
            getActivity().setTitle("Create Monster");
        }
        viewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener);
    }

    private TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            if(position == 0){
                getActivity().setTitle("Saved Monsters");
            } else if(position == 1) {
                getActivity().setTitle("Create Monster");
            }
        }
    };

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

    @Override
    public void searchFilter(String query) {
        if(tabLayout.getSelectedTabPosition() == 0){
            ((AbstractFragment)monsterPagerAdapter.getItem(0)).searchFilter(query);
        }else if(tabLayout.getSelectedTabPosition() == 1){
            ((AbstractFragment)monsterPagerAdapter.getItem(1)).searchFilter(query);
        }
    }

}
