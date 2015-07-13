package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anthony.damagecalculator.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView monsterName, monsterStatsHPBase, monsterStatsHPTotal, monsterStatsATKBase, monsterStatsATKTotal, monsterStatsRCVBase, monsterStatsRCVTotal, monsterStatsWeightedValue;
    private EditText monsterLevelValue, monsterStatsHPPlus, monsterStatsATKPlus, monsterStatsRCVPlus, monsterAwakeningsValue;
    private Button monsterLevelMax, monsterStatsMax, monsterStatsHPMax, monsterStatsATKMax, monsterStatsRCVMax, monsterAwakeningsMax, monsterChoose, monsterStatsMaxAll;
    private ImageView monsterPicture, monsterAwakening1, monsterAwakening2, monsterAwakening3, monsterAwakening4, monsterAwakening5, monsterAwakening6, monsterAwakening7, monsterAwakening8, monsterAwakening9;
    private LinearLayout awakeningHolder;
    private int monsterMinLevel = 1, monsterMaxLevel, monsterMinHP, monsterMaxHP, monsterMinATK, monsterMaxATK, monsterMinRCV, monsterMaxRCV, monsterMinAwakening, monsterMaxAwakening;
    private double monsterStatScale;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonsterPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonsterPageFragment newInstance(String param1, String param2) {
        MonsterPageFragment fragment = new MonsterPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MonsterPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        initTextView(rootView);
        initImageView(rootView);
        monsterLevelMax = (Button) rootView.findViewById(R.id.monsterLevelMax);
        monsterStatsMax = (Button) rootView.findViewById(R.id.monsterStatsMax);
        monsterStatsHPMax = (Button) rootView.findViewById(R.id.monsterStatsHPMax);
        monsterStatsATKMax = (Button) rootView.findViewById(R.id.monsterStatsATKMax);
        monsterStatsRCVMax = (Button) rootView.findViewById(R.id.monsterStatsRCVMax);
        monsterAwakeningsMax = (Button) rootView.findViewById(R.id.monsterAwakeningsMax);
        monsterStatsMaxAll = (Button) rootView.findViewById(R.id.monsterStatsMaxAll);
        monsterChoose = (Button) rootView.findViewById(R.id.monsterChoose);
        monsterLevelValue = (EditText) rootView.findViewById(R.id.monsterLevelValue);
        monsterStatsHPPlus = (EditText) rootView.findViewById(R.id.monsterStatsHPPlus);
        monsterStatsATKPlus = (EditText) rootView.findViewById(R.id.monsterStatsATKPlus);
        monsterStatsRCVPlus = (EditText) rootView.findViewById(R.id.monsterStatsRCVPlus);
        monsterAwakeningsValue = (EditText) rootView.findViewById(R.id.monsterAwakeningsValue);
        awakeningHolder = (LinearLayout) rootView.findViewById(R.id.awakeningHolder);

        return inflater.inflate(R.layout.fragment_monster_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    private void initTextView(View rootView)
    {
        monsterName = (TextView) rootView.findViewById(R.id.monsterName);
        monsterStatsHPBase = (TextView) rootView.findViewById(R.id.monsterStatsHPBase);
        monsterStatsHPTotal = (TextView) rootView.findViewById(R.id.monsterStatsHPTotal);
        monsterStatsATKBase = (TextView) rootView.findViewById(R.id.monsterStatsATKBase);
        monsterStatsATKTotal = (TextView) rootView.findViewById(R.id.monsterStatsATKTotal);
        monsterStatsRCVBase = (TextView) rootView.findViewById(R.id.monsterStatsRCVBase);
        monsterStatsRCVTotal = (TextView) rootView.findViewById(R.id.monsterStatsRCVTotal);
        monsterStatsWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsWeightedValue);

    }
    private void initImageView(View rootView)
    {
        monsterPicture = (ImageView) rootView.findViewById(R.id.monsterPicture);
        monsterAwakening1 = (ImageView) rootView.findViewById(R.id.monsterAwakening1);
        monsterAwakening2 = (ImageView) rootView.findViewById(R.id.monsterAwakening2);
        monsterAwakening3 = (ImageView) rootView.findViewById(R.id.monsterAwakening3);
        monsterAwakening4 = (ImageView) rootView.findViewById(R.id.monsterAwakening4);
        monsterAwakening5 = (ImageView) rootView.findViewById(R.id.monsterAwakening5);
        monsterAwakening6 = (ImageView) rootView.findViewById(R.id.monsterAwakening6);
        monsterAwakening7 = (ImageView) rootView.findViewById(R.id.monsterAwakening7);
        monsterAwakening8 = (ImageView) rootView.findViewById(R.id.monsterAwakening8);
        monsterAwakening9 = (ImageView) rootView.findViewById(R.id.monsterAwakening9);

    }
    public int monsterStatCalc(int minimumStat, int maximumStat, int currentLevel, int maxLevel, double statScale)
    {
        return (int)Math.floor(minimumStat + (maximumStat-minimumStat)*(Math.pow((double)((currentLevel - 1)/(maxLevel)), statScale)));
    }
}
