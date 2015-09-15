package com.example.anthony.damagecalculator.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.TextWatcher.MyTextWatcher;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterPageFragment extends Fragment {
    public static final String TAG = MonsterPageFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private View rootView;
    private OnFragmentInteractionListener mListener;
    private TextView monsterName, monsterStatsHPBase, monsterStatsHPTotal, monsterStatsATKBase, monsterStatsATKTotal, monsterStatsRCVBase, monsterStatsRCVTotal, monsterStatsWeightedValue, monsterStatsTotalWeightedValue;
    private EditText monsterLevelValue, monsterStatsHPPlus, monsterStatsATKPlus, monsterStatsRCVPlus, monsterAwakeningsValue;
    private Button monsterLevelMax, monsterStatsMax, monsterStatsHPMax, monsterStatsATKMax, monsterStatsRCVMax, monsterAwakeningsMax, monsterRemove, monsterStatsMaxAll, awakeningPlus, awakeningMinus;
    private ImageView monsterPicture;
    private LinearLayout awakeningHolder;
    private Monster monster;
    private Toast toast;
    private MonsterRemoveDialogFragment monsterRemoveDialogFragment;
    private int position;

    private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats() {
        @Override
        public void changeMonsterAttribute(int statToChange, int statValue) {
            if (statToChange == MyTextWatcher.CURRENT_LEVEL) {
                if (statValue == 0) {
                    statValue = 1;
                }
                monster.setCurrentLevel(statValue);
                monster.setCurrentAtk(DamageCalculationUtil.monsterStatCalc(monster.getAtkMin(), monster.getAtkMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getAtkScale()));
                monster.setCurrentHp(DamageCalculationUtil.monsterStatCalc(monster.getHpMin(), monster.getHpMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getHpScale()));
                monster.setCurrentRcv(DamageCalculationUtil.monsterStatCalc(monster.getRcvMin(), monster.getRcvMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getRcvScale()));
                monsterStatsHPBase.setText(String.valueOf(monster.getCurrentHp()));
                monsterStatsATKBase.setText(String.valueOf(monster.getCurrentAtk()));
                monsterStatsRCVBase.setText(String.valueOf(monster.getCurrentRcv()));
                monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
                monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
                monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
                monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.ATK_STAT) {
                monster.setAtkPlus(statValue);
                monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.RCV_STAT) {
                monster.setRcvPlus(statValue);
                monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.HP_STAT) {
                monster.setHpPlus(statValue);
                monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.AWAKENINGS) {
                monster.setCurrentAwakenings(statValue);
                if (monster.getCurrentAwakenings() > monster.getMaxAwakenings()) {
                    monster.setCurrentAwakenings(monster.getMaxAwakenings());
                    monsterAwakeningsValue.setText(Integer.toString(monster.getCurrentAwakenings()));
                }
                grayAwakenings();
            }
        }
    };

    private MyTextWatcher currentLevelWatcher = new MyTextWatcher(MyTextWatcher.CURRENT_LEVEL, changeStats);
    private MyTextWatcher hpPlusWatcher = new MyTextWatcher(MyTextWatcher.HP_STAT, changeStats);
    private MyTextWatcher atkPlusWatcher = new MyTextWatcher(MyTextWatcher.ATK_STAT, changeStats);
    private MyTextWatcher rcvPlusWatcher = new MyTextWatcher(MyTextWatcher.RCV_STAT, changeStats);
    private MyTextWatcher awakeningsWatcher = new MyTextWatcher(MyTextWatcher.AWAKENINGS, changeStats);

    private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
                if (monsterAwakeningsValue.getText().toString().equals("")) {
                    monsterAwakeningsValue.setText("0");
                    monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
                    grayAwakenings();
                } else if (monsterLevelValue.getText().toString().equals("")) {
                    monsterLevelValue.setText("1");
                } else if (monsterStatsHPPlus.getText().toString().equals("")) {
                    monsterStatsHPPlus.setText("0");

                } else if (monsterStatsATKPlus.getText().toString().equals("")) {
                    monsterStatsATKPlus.setText("0");
                } else if (monsterStatsRCVPlus.getText().toString().equals("")) {
                    monsterStatsRCVPlus.setText("0");
                }
                monsterStats();
            }
        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonsterPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonsterPageFragment newInstance(Monster monster, int position) {
        MonsterPageFragment fragment = new MonsterPageFragment();
        Bundle args = new Bundle();
        Log.d("Monster1:", "Monster1: " + monster);
        args.putParcelable("monster", monster);
        args.putInt("position", position);
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
            monster = getArguments().getParcelable("monster");
            Log.d("Monster2:", "Monster2: " + monster);
            position = getArguments().getInt("position");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_monster_page, container, false);
        initTextView(rootView);
        initImageView(rootView);
        monsterPicture = (ImageView) rootView.findViewById(R.id.monsterPicture);
        monsterLevelMax = (Button) rootView.findViewById(R.id.monsterLevelMax);
        monsterStatsMax = (Button) rootView.findViewById(R.id.monsterStatsMax);
        monsterStatsHPMax = (Button) rootView.findViewById(R.id.monsterStatsHPMax);
        monsterStatsATKMax = (Button) rootView.findViewById(R.id.monsterStatsATKMax);
        monsterStatsRCVMax = (Button) rootView.findViewById(R.id.monsterStatsRCVMax);
        monsterAwakeningsMax = (Button) rootView.findViewById(R.id.monsterAwakeningsMax);
        monsterStatsMaxAll = (Button) rootView.findViewById(R.id.monsterStatsMaxAll);
        awakeningMinus = (Button) rootView.findViewById(R.id.awakeningMinus);
        awakeningPlus = (Button) rootView.findViewById(R.id.awakeningPlus);
        monsterRemove = (Button) rootView.findViewById(R.id.monsterRemove);
        monsterLevelValue = (EditText) rootView.findViewById(R.id.monsterLevelValue);
        monsterStatsHPPlus = (EditText) rootView.findViewById(R.id.monsterStatsHPPlus);
        monsterStatsATKPlus = (EditText) rootView.findViewById(R.id.monsterStatsATKPlus);
        monsterStatsRCVPlus = (EditText) rootView.findViewById(R.id.monsterStatsRCVPlus);
        monsterAwakeningsValue = (EditText) rootView.findViewById(R.id.monsterAwakeningsValue);
        awakeningHolder = (LinearLayout) rootView.findViewById(R.id.awakeningHolder);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        monsterPicture.setImageResource(monster.getMonsterPicture());
        monsterName.setText(monster.getName());
        initializeEditTexts();
        monsterStats();
        monsterLevelValue.addTextChangedListener(currentLevelWatcher);
        monsterStatsHPPlus.addTextChangedListener(hpPlusWatcher);
        monsterStatsATKPlus.addTextChangedListener(atkPlusWatcher);
        monsterStatsRCVPlus.addTextChangedListener(rcvPlusWatcher);
        monsterAwakeningsValue.addTextChangedListener(awakeningsWatcher);

        monsterLevelValue.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsHPPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsATKPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsRCVPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterAwakeningsValue.setOnFocusChangeListener(editTextOnFocusChange);

        monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
        showAwakenings();
        grayAwakenings();
        awakeningMinus.setOnClickListener(awakeningButtons);
        awakeningPlus.setOnClickListener(awakeningButtons);

        monsterLevelMax.setOnClickListener(maxButtons);
        monsterStatsMax.setOnClickListener(maxButtons);
        monsterStatsHPMax.setOnClickListener(maxButtons);
        monsterStatsATKMax.setOnClickListener(maxButtons);
        monsterStatsRCVMax.setOnClickListener(maxButtons);
        monsterAwakeningsMax.setOnClickListener(maxButtons);
        monsterStatsMaxAll.setOnClickListener(maxButtons);
        monsterRemove.setOnClickListener(maxButtons);

        //rootView.getViewTreeObserver().addOnGlobalLayoutListener(rootListener);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        monster.save();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d("What is awakening", "awakening detach: " + monster.getCurrentAwakenings() + " " + monster);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void initTextView(View rootView) {
        monsterName = (TextView) rootView.findViewById(R.id.monsterName);
        monsterStatsHPBase = (TextView) rootView.findViewById(R.id.monsterStatsHPBase);
        monsterStatsHPTotal = (TextView) rootView.findViewById(R.id.monsterStatsHPTotal);
        monsterStatsATKBase = (TextView) rootView.findViewById(R.id.monsterStatsATKBase);
        monsterStatsATKTotal = (TextView) rootView.findViewById(R.id.monsterStatsATKTotal);
        monsterStatsRCVBase = (TextView) rootView.findViewById(R.id.monsterStatsRCVBase);
        monsterStatsRCVTotal = (TextView) rootView.findViewById(R.id.monsterStatsRCVTotal);
        monsterStatsWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsWeightedValue);
        monsterStatsTotalWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsTotalWeightedValue);

    }

    private void initImageView(View rootView) {
        monsterPicture = (ImageView) rootView.findViewById(R.id.monsterPicture);
    }

    private View.OnClickListener awakeningButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            monsterAwakeningsValue.clearFocus();
            if (v.equals(awakeningMinus)) {
                if (Integer.parseInt(monsterAwakeningsValue.getText().toString()) > 0 && Integer.parseInt(monsterAwakeningsValue.getText().toString()) <= monster.getMaxAwakenings()) {
                    //monster.setCurrentAwakenings(monster.getCurrentAwakenings() - 1);
                    monsterAwakeningsValue.setText("" + (monster.getCurrentAwakenings() - 1));
                }
            } else if (v.equals(awakeningPlus)) {
                if (Integer.parseInt(monsterAwakeningsValue.getText().toString()) >= 0 && Integer.parseInt(monsterAwakeningsValue.getText().toString()) < monster.getMaxAwakenings()) {
                    //monster.setCurrentAwakenings(monster.getCurrentAwakenings() + 1);
                    monsterAwakeningsValue.setText("" + (monster.getCurrentAwakenings() + 1));

                }
            }
            grayAwakenings();

        }
    };

    private View.OnClickListener maxButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearTextFocus();
            if (v.equals(monsterLevelMax)) {
                monsterLevelValue.setText(Integer.toString(monster.getMaxLevel()));
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Level maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsMax)) {
                monsterStatsHPPlus.setText("99");
                monsterStatsATKPlus.setText("99");
                monsterStatsRCVPlus.setText("99");
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Maxed all +", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsHPMax)) {
                monsterStatsHPPlus.setText("99");
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "HP + maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsATKMax)) {
                monsterStatsATKPlus.setText("99");
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "ATK + maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsRCVMax)) {
                monsterStatsRCVPlus.setText("99");
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "RCV + maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterAwakeningsMax)) {
                monsterAwakeningsValue.setText(Integer.toString(monster.getMaxAwakenings()));
                monster.setCurrentAwakenings(monster.getMaxAwakenings());
                grayAwakenings();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Awakenings maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsMaxAll)) {
                monsterLevelValue.setText(Integer.toString(monster.getMaxLevel()));
                monsterStatsHPPlus.setText("99");
                monsterStatsATKPlus.setText("99");
                monsterStatsRCVPlus.setText("99");
                monsterAwakeningsValue.setText(Integer.toString(monster.getMaxAwakenings()));
                monster.setCurrentAwakenings(monster.getMaxAwakenings());
                grayAwakenings();
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "All stats maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterRemove)) {
                //Remove monster. Can choose monster from search bar.
                if (monsterRemoveDialogFragment == null) {
                    monsterRemoveDialogFragment = MonsterRemoveDialogFragment.newInstance(removeMonster);
                }
                monsterRemoveDialogFragment.show(getChildFragmentManager(), "Show Remove Monster");
            }
        }
    };

    public void showAwakenings() {
        // Show max # of awakenings
        int i = 0;
        View view = null;
        for (i = monster.getMaxAwakenings(); i < 9; i++) {
            view = awakeningHolder.getChildAt(i);
            view.setVisibility(View.GONE);
        }
    }

    //Gray out depending on monsterAwakeningsValue
    @TargetApi(11)
    public void grayAwakenings() {
        int i = 0;
        View view = null;
        for (i = 0; i < monster.getCurrentAwakenings(); i++) {
            view = awakeningHolder.getChildAt(i);

            view.setAlpha(1);
        }
        for (i = monster.getCurrentAwakenings(); i < monster.getMaxAwakenings(); i++) {
            view = awakeningHolder.getChildAt(i);

            view.setAlpha((float) .5);
        }
    }

    public void monsterStats() {
        //Update method because our TextWatcher no work
        setMonsterStats();
        setTextViewValues();

    }

    public void setMonsterStats() {
        monster.setCurrentLevel(Integer.parseInt(monsterLevelValue.getText().toString()));
        monster.setCurrentAtk(DamageCalculationUtil.monsterStatCalc(monster.getAtkMin(), monster.getAtkMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getAtkScale()));
        monster.setCurrentHp(DamageCalculationUtil.monsterStatCalc(monster.getHpMin(), monster.getHpMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getHpScale()));
        monster.setCurrentRcv(DamageCalculationUtil.monsterStatCalc(monster.getRcvMin(), monster.getRcvMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getRcvScale()));
        monster.setHpPlus(Integer.valueOf(monsterStatsHPPlus.getText().toString()));
        monster.setAtkPlus(Integer.valueOf(monsterStatsATKPlus.getText().toString()));
        monster.setRcvPlus(Integer.valueOf(monsterStatsRCVPlus.getText().toString()));
    }

    public void setTextViewValues() {
        Log.d("What are the values", "Values: " + monster.getCurrentHp() + " " + monster.getCurrentAtk() + " " + monster.getCurrentRcv());
        monsterStatsHPBase.setText(String.valueOf(monster.getCurrentHp()));
        monsterStatsATKBase.setText(String.valueOf(monster.getCurrentAtk()));
        monsterStatsRCVBase.setText(String.valueOf(monster.getCurrentRcv()));
        monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
        monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
        monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
        monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
        monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));

    }

    public void initializeEditTexts() {
        monsterLevelValue.setText(String.valueOf(monster.getCurrentLevel()));
        monsterStatsHPPlus.setText(String.valueOf(monster.getHpPlus()));
        monsterStatsATKPlus.setText(String.valueOf(monster.getAtkPlus()));
        monsterStatsRCVPlus.setText(String.valueOf(monster.getRcvPlus()));
        monsterAwakeningsValue.setText(String.valueOf(monster.getCurrentAwakenings()));
    }

    public void clearTextFocus() {
        monsterLevelValue.clearFocus();
        monsterStatsHPPlus.clearFocus();
        monsterStatsATKPlus.clearFocus();
        monsterStatsRCVPlus.clearFocus();
        monsterAwakeningsValue.clearFocus();
    }

    private MonsterRemoveDialogFragment.RemoveMonster removeMonster = new MonsterRemoveDialogFragment.RemoveMonster() {
        @Override
        public void removeMonsterDatabase() {
            monsterRemoveDialogFragment.dismiss();
        }

        @Override
        public void removeMonsterTeam() {
            Log.d("Monster Page Log","Position is: " + position + " " + Monster.getMonsterId(0) + " Monster name: " + Monster.getMonsterId(0).getName());
            Team newTeam = new Team(Team.getTeamById(0));
            switch (position){
                case 0:
                    newTeam.setLead(Monster.getMonsterId(0));
                    break;
                case 1:
                    newTeam.setSub1(Monster.getMonsterId(0));
                    break;
                case 2:
                    newTeam.setSub2(Monster.getMonsterId(0));
                    break;
                case 3:
                    newTeam.setSub3(Monster.getMonsterId(0));
                    break;
                case 4:
                    newTeam.setSub4(Monster.getMonsterId(0));
                    break;
                case 5:
                    newTeam.setHelper(Monster.getMonsterId(0));
                    break;
            }
            for(int i = 0; i < newTeam.getMonsters().size(); i++){
                Log.d("Monster Page Log", "Monster name: " + newTeam.getMonsters(i).getName() + " Monster id: " + newTeam.getMonsters(i).getMonsterId());
            }
            newTeam.save();
//            monster = Monster.getMonsterId(0);
//            monsterName.setText(monster.getName());
//            monsterPicture.setImageResource(monster.getMonsterPicture());
//            setTextViewValues();
//            initializeEditTexts();
//            showAwakenings();
            monsterRemoveDialogFragment.dismiss();
//            getChildFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

//   private ViewTreeObserver.OnGlobalLayoutListener rootListener = new ViewTreeObserver.OnGlobalLayoutListener()
//   {
//      @Override
//      public void onGlobalLayout()
//      {
//
//         if (getResources().getConfiguration().keyboardHidden == Configuration.KEYBOARDHIDDEN_YES)
//         {
//            if(((ViewGroup)rootView).getFocusedChild() == null)
//            {
//               Log.d("FKTHOMSA", "DAM U THOMAS");
//            }
//            ((ViewGroup)rootView).getFocusedChild().clearFocus();
//            Log.d("is this real?", "what da");
//
//         }
//      }
//   };


}
