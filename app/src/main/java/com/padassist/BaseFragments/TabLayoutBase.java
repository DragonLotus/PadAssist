package com.padassist.BaseFragments;

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

import com.padassist.R;

import io.realm.Realm;

public abstract class TabLayoutBase extends Fragment {

    public static final String TAG = TabLayoutBase.class.getSimpleName();
    public static final int MAIN = 1;
    public static final int MONSTERS = 2;
    protected int selection;
    protected OnFragmentInteractionListener mListener;
    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    protected Realm realm = Realm.getDefaultInstance();

    public TabLayoutBase() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if(getSelection() == MAIN) {
            rootView = inflater.inflate(R.layout.fragment_monster_tab_layout_bottom, container, false);
        }else {
            rootView = inflater.inflate(R.layout.fragment_monster_tab_layout, container, false);
        }

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        // Inflate the layout for this fragment
        return rootView;
    }

    public abstract int getSelection();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

}
