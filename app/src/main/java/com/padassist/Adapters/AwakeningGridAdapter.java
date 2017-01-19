package com.padassist.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.Data.Monster;
import com.padassist.Graphics.TooltipAwakening;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmList;

/**
 * Created by DragonLotus on 10/18/2015.
 */
public class AwakeningGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> awakeningList;
    private RealmList<Monster> monsterList;
    private ArrayList<Integer> latentList = new ArrayList<>();
    private ArrayList<Integer> awakeningListAll;
    private ArrayList<Integer> latentListAll;
    private ArrayList<Integer> awakeningAmountList;
    private ArrayList<Integer> latentAmountList;
    private boolean isMonsterSpecificAdapter;
    private int teamBadge;
    DecimalFormat format = new DecimalFormat("0");

    public AwakeningGridAdapter(Context context, RealmList<Monster> monsterList, ArrayList<Integer> awakenings, ArrayList<Integer> latents, boolean isMonsterSpecificAdapter, int teamBadge) {
        mContext = context;
        this.monsterList = monsterList;
        awakeningListAll = awakenings;
        latentListAll = latents;
        this.isMonsterSpecificAdapter = isMonsterSpecificAdapter;
        this.teamBadge = teamBadge;
        trimAwakenings();
    }

    @Override
    public int getCount() {
        if (latentListAll.size() != 0) {
            return (awakeningList.size() + latentList.size());
        } else {
            return awakeningList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (position >= awakeningList.size()) {
            return latentList.get(position - awakeningList.size());
        } else {
            return awakeningList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.awakening_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.awakeningAmount = (TextView) convertView.findViewById(R.id.awakeningAmount);
            viewHolder.awakeningPicture = (ImageView) convertView.findViewById(R.id.awakeningPicture);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);

            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }

        if (position >= awakeningList.size()) {
            viewHolder.awakeningPicture.setImageResource(ImageResourceUtil.monsterLatent(latentList.get(position - awakeningList.size())));
            viewHolder.awakeningAmount.setText("x" + latentAmountList.get(position - awakeningList.size()));
        } else {
            viewHolder.awakeningPicture.setImageResource(ImageResourceUtil.monsterAwakening(awakeningList.get(position)));
            viewHolder.awakeningAmount.setText("x" + awakeningAmountList.get(position));
        }
        viewHolder.relativeLayout.setTag(R.string.index, position);
        viewHolder.relativeLayout.setOnClickListener(awakeningToolTipOnClickListener);

        return convertView;
    }

    private View.OnClickListener awakeningToolTipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Log.d("AwakeningGridAdapter", "position is: " + position);
            ArrayList<Monster> filteredMonsters = new ArrayList<>();
            TooltipAwakening tooltipAwakening;
            String text = "Testerino";
            int awakening;
            double counter;
            if (position >= awakeningList.size()) {
                awakening = latentList.get(position - awakeningList.size());
                counter = latentAmountList.get(position - awakeningList.size());
                switch (awakening) {
                    case 1:
                        if ((counter * 1.5) == (long) (counter * 1.5)) {
                            text = "Bonus " + String.format("%d", (long) (counter * 1.5)) + "% of base HP";
                        } else {
                            text = "Bonus " + counter * 1.5 + "% of base HP";
                        }
                        break;
                    case 2:
                        text = "Bonus " + String.format("%d", (long) counter) + "% of base ATK";
                        break;
                    case 3:
                        text = "Bonus " + String.format("%d", (long) (counter * 5)) + "% of base RCV";
                        break;
                    case 4:
                        if (awakeningList.contains(19)) {
                            if (teamBadge == 2) {
                                text = "Extra " + (counter * .05 + 1 + awakeningAmountList.get(awakeningList.indexOf(19)) * .5) + " seconds to match (1s from Team Badge and " + awakeningAmountList.get(awakeningList.indexOf(19)) * .5 + "s from awakenings)";
                            } else if (teamBadge == 11) {
                                text = "Extra " + (counter * .05 + 2 + awakeningAmountList.get(awakeningList.indexOf(19)) * .5) + " seconds to match (2s from Team Badge and " + awakeningAmountList.get(awakeningList.indexOf(19)) * .5 + "s from awakenings)";
                            } else {
                                text = "Extra " + (counter * .05 + awakeningAmountList.get(awakeningList.indexOf(19)) * .5) + " seconds to match (" + awakeningAmountList.get(awakeningList.indexOf(19)) * .5 + "s from awakenings)";
                            }
                        } else {
                            if (teamBadge == 2) {
                                text = "Extra " + (counter * .05 + 1) + " seconds to match (1s from Team Badge)";
                            } else if (teamBadge == 11) {
                                text = "Extra " + (counter * .05 + 2) + " seconds to match (2s from Team Badge)";
                            } else {
                                if (counter == 20) {
                                    text = "Extra " + counter * .05 + " second to match";
                                } else {
                                    text = "Extra " + counter * .05 + " seconds to match";
                                }
                            }
                        }
                        break;
                    case 5:
                        text = "Auto-heal " + String.format("%d", (long) (counter * 15)) + "% of total RCV";
                        break;
                    case 6:
                        if (awakeningList.contains(4)) {
                            text = "Resist " + String.format("%d", (long) (counter + awakeningAmountList.get(awakeningList.indexOf(4)) * 5)) + "% of fire damage (" + awakeningAmountList.get(awakeningList.indexOf(4)) * 5 + "% from awakenings)";

                        } else {
                            text = "Resist " + String.format("%d", (long) counter) + "% of fire damage";
                        }
                        break;
                    case 7:
                        if (awakeningList.contains(5)) {
                            text = "Resist " + String.format("%d", (long) (counter + awakeningAmountList.get(awakeningList.indexOf(5)) * 5)) + "% of water damage (" + awakeningAmountList.get(awakeningList.indexOf(5)) * 5 + "% from awakenings)";

                        } else {
                            text = "Resist " + String.format("%d", (long) counter) + "% of water damage";
                        }
                        break;
                    case 8:
                        if (awakeningList.contains(6)) {
                            text = "Resist " + String.format("%d", (long) (counter + awakeningAmountList.get(awakeningList.indexOf(6)) * 5)) + "% of wood damage (" + awakeningAmountList.get(awakeningList.indexOf(6)) * 5 + "% from awakenings)";

                        } else {
                            text = "Resist " + String.format("%d", (long) counter) + "% of wood damage";
                        }
                        break;
                    case 9:
                        if (awakeningList.contains(7)) {
                            text = "Resist " + String.format("%d", (long) (counter + awakeningAmountList.get(awakeningList.indexOf(7)) * 5)) + "% of light damage (" + awakeningAmountList.get(awakeningList.indexOf(7)) * 5 + "% from awakenings)";

                        } else {
                            text = "Resist " + String.format("%d", (long) counter) + "% of light damage";
                        }
                        break;
                    case 10:
                        if (awakeningList.contains(8)) {
                            text = "Resist " + String.format("%d", (long) (counter + awakeningAmountList.get(awakeningList.indexOf(8)) * 5)) + "% of dark damage (" + awakeningAmountList.get(awakeningList.indexOf(8)) * 5 + "% from awakenings)";

                        } else {
                            text = "Resist " + String.format("%d", (long) counter) + "% of dark damage";
                        }
                        break;
                    case 11:
                        if (counter == 1) {
                            text = "Resist " + String.format("%d", (long) counter) + " turn of skill delay";
                        } else {
                            text = "Resist " + String.format("%d", (long) counter) + " turns of skill delay";
                        }
                        break;
                    case 12:
                        if ((counter * 1.5) == (long) (counter * 1.5)) {
                            text = "Bonus " + String.format("%d", (long) (counter * 1.5)) + "% of base HP, " + String.format("%d", (long) counter) + "% of base ATK, " + String.format("%d", (long) (counter * 5)) + "% of base RCV";
                        } else {
                            text = "Bonus " + counter * 1.5 + "% of base HP, " + String.format("%d", (long) counter) + "% of base ATK, " + String.format("%d", (long) (counter * 5)) + "% of base RCV";
                        }
                        break;
                    case 13:
                        if(awakeningList.contains(32)){
                            text = Math.pow(1.5, counter) * Math.pow(3, awakeningAmountList.get(awakeningList.indexOf(32))) + "x bonus damage versus God types";
                        } else {
                            text = Math.pow(1.5, counter) + "x bonus damage versus God types";
                        }
                        break;
                    case 14:
                        if(awakeningList.contains(31)){
                            text = Math.pow(1.5, counter) * Math.pow(3, awakeningAmountList.get(awakeningList.indexOf(31))) + "x bonus damage versus Dragon types";
                        } else {
                            text = Math.pow(1.5, counter) + "x bonus damage versus Dragon types";
                        }
                        break;
                    case 15:
                        if(awakeningList.contains(33)){
                            text = Math.pow(1.5, counter) * Math.pow(3, awakeningAmountList.get(awakeningList.indexOf(33))) + "x bonus damage versus Devil types";
                        } else {
                            text = Math.pow(1.5, counter) + "x bonus damage versus Devil types";
                        }
                        break;
                    case 16:
                        if(awakeningList.contains(34)){
                            text = Math.pow(1.5, counter) * Math.pow(3, awakeningAmountList.get(awakeningList.indexOf(34))) + "x bonus damage versus Machine types";
                        } else {
                            text = Math.pow(1.5, counter) + "x bonus damage versus Machine types";
                        }
                        break;
                    case 17:
                        if(awakeningList.contains(38)){
                            text = Math.pow(1.5, counter) * Math.pow(3, awakeningAmountList.get(awakeningList.indexOf(38))) + "x bonus damage versus Balanced types";
                        } else {
                            text = Math.pow(1.5, counter) + "x bonus damage versus Balanced types";
                        }
                        break;
                    case 18:
                        if(awakeningList.contains(35)){
                            text = Math.pow(1.5, counter) * Math.pow(3, awakeningAmountList.get(awakeningList.indexOf(35))) + "x bonus damage versus Attacker types";
                        } else {
                            text = Math.pow(1.5, counter) + "x bonus damage versus Attacker types";
                        }
                        break;
                    case 19:
                        if(awakeningList.contains(36)){
                            text = Math.pow(1.5, counter) * Math.pow(3, awakeningAmountList.get(awakeningList.indexOf(36))) + "x bonus damage versus Physical types";
                        } else {
                            text = Math.pow(1.5, counter) + "x bonus damage versus Physical types";
                        }
                        break;
                    case 20:
                        if(awakeningList.contains(37)){
                            text = Math.pow(1.5, counter) * Math.pow(3, awakeningAmountList.get(awakeningList.indexOf(37))) + "x bonus damage versus Healer types";
                        } else {
                            text = Math.pow(1.5, counter) + "x bonus damage versus Healer types";
                        }
                        break;
                }
                for (int i = 0; i < monsterList.size(); i++) {
                    for (int j = 0; j < monsterList.get(i).getLatents().size(); j++) {
                        if (monsterList.get(i).getLatents().get(j).getValue() == awakening) {
                            if (!filteredMonsters.contains(monsterList.get(i))) {
                                filteredMonsters.add(monsterList.get(i));
                            }
                        }
                    }
                }
                tooltipAwakening = new TooltipAwakening(mContext, text, awakening, true, filteredMonsters, isMonsterSpecificAdapter);
            } else {
                awakening = awakeningList.get(position);
                counter = awakeningAmountList.get(position);
                switch (awakening) {
                    case 1:
                        text = "Bonus " + String.format("%d", (long) (counter * 200)) + " HP";
                        break;
                    case 2:
                        text = "Bonus " + String.format("%d", (long) (counter * 100)) + " ATK";
                        break;
                    case 3:
                        text = "Bonus " + String.format("%d", (long) (counter * 50)) + " RCV";
                        break;
                    case 4:
                        if (latentList.contains(6)) {
                            text = "Resist " + (String.format("%d", (long) (counter * 5) + latentAmountList.get(latentList.indexOf(6)))) + "% of fire damage (" + latentAmountList.get(latentList.indexOf(6)) + "% from latents)";
                        } else {
                            text = "Resist " + String.format("%d", (long) (counter * 5)) + "% of fire damage";
                        }
                        break;
                    case 5:
                        if (latentList.contains(7)) {
                            text = "Resist " + (String.format("%d", (long) (counter * 5 + latentAmountList.get(latentList.indexOf(7))))) + "% of water damage (" + latentAmountList.get(latentList.indexOf(7)) + "% from latents)";
                        } else {
                            text = "Resist " + String.format("%d", (long) (counter * 5)) + "% of water damage";
                        }
                        break;
                    case 6:
                        if (latentList.contains(8)) {
                            text = "Resist " + String.format("%d", (long) (counter * 5 + latentAmountList.get(latentList.indexOf(8)))) + "% of wood damage (" + latentAmountList.get(latentList.indexOf(8)) + "% from latents)";
                        } else {
                            text = "Resist " + String.format("%d", (long) (counter * 5)) + "% of wood damage";
                        }
                        break;
                    case 7:
                        if (latentList.contains(9)) {

                            text = "Resist " + String.format("%d", (long) (counter * 5 + latentAmountList.get(latentList.indexOf(9)))) + "% of light damage (" + latentAmountList.get(latentList.indexOf(9)) + "% from latents)";
                        } else {
                            text = "Resist " + String.format("%d", (long) (counter * 5)) + "% of light damage";
                        }
                        break;
                    case 8:
                        if (latentList.contains(10)) {
                            text = "Resist " + String.format("%d", (long) (counter * 5 + latentAmountList.get(latentList.indexOf(10)))) + "% of dark damage (" + latentAmountList.get(latentList.indexOf(10)) + "% from latents)";
                        } else {
                            text = "Resist " + String.format("%d", (long) (counter * 5)) + "% of dark damage";
                        }
                        break;
                    case 9:
                        text = "Auto-Heal " + String.format("%d", (long) (counter * 500)) + " HP";
                        break;
                    case 10:
                        text = String.format("%d", (long) (counter * 50)) + "% chance to resist binds";
                        break;
                    case 11:
                        text = String.format("%d", (long) (counter * 20)) + "% chance to resist blinds";
                        break;
                    case 12:
                        text = String.format("%d", (long) (counter * 20)) + "% chance to resist jammer orbs";
                        break;
                    case 13:
                        text = String.format("%d", (long) (counter * 20)) + "% chance to resist poison orbs";
                        break;
                    case 14:
                        text = String.format("%d", (long) (counter * 20)) + "% drop chance and " + String.format("%d", (long) (counter * 5)) + "% bonus damage for enhanced fire orbs";
                        break;
                    case 15:
                        text = String.format("%d", (long) (counter * 20)) + "% drop chance and " + String.format("%d", (long) (counter * 5)) + "% bonus damage for enhanced water orbs";
                        break;
                    case 16:
                        text = String.format("%d", (long) (counter * 20)) + "% drop chance and " + String.format("%d", (long) (counter * 5)) + "% bonus damage for enhanced wood orbs";
                        break;
                    case 17:
                        text = String.format("%d", (long) (counter * 20)) + "% drop chance and " + String.format("%d", (long) (counter * 5)) + "% bonus damage for enhanced light orbs";
                        break;
                    case 18:
                        text = String.format("%d", (long) (counter * 20)) + "% drop chance and " + String.format("%d", (long) (counter * 5)) + "% bonus damage for enhanced dark orbs";
                        break;
                    case 19:
                        if (latentList.contains(4)) {
                            if (teamBadge == 2) {
                                text = "Extra " + (counter * .5 + 1 + latentAmountList.get(latentList.indexOf(4)) * .05) + " seconds to match (1s from Team Badge and " + latentAmountList.get(latentList.indexOf(4)) * .05 + "s from latents)";
                            } else if (teamBadge == 11) {
                                text = "Extra " + (counter * .5 + 2 + latentAmountList.get(latentList.indexOf(4)) * .05) + " seconds to match (2s from Team Badge and " + latentAmountList.get(latentList.indexOf(4)) * .05 + "s from latents)";
                            } else {
                                text = "Extra " + (counter * .5 + latentAmountList.get(latentList.indexOf(4)) * .05) + " seconds to match (" + latentAmountList.get(latentList.indexOf(4)) * .05 + "s from latents)";
                            }
                        } else {
                            if (teamBadge == 2) {
                                text = "Extra " + (counter * .5 + 1) + " seconds to match (1s from Team Badge)";
                            } else if (teamBadge == 11) {
                                text = "Extra " + (counter * .5 + 2) + " seconds to match (2s from Team Badge)";
                            } else {
                                if (counter == 2) {
                                    text = "Extra " + counter * .5 + " second to match";
                                } else {
                                    text = "Extra " + counter * .5 + " seconds to match";
                                }
                            }
                        }
                        break;
                    case 20:
                        text = String.format("%d", (long) (counter * 3)) + " turns of bind recovery when matching a row of heart orbs";
                        break;
                    case 21:
                        if (teamBadge == 7) {
                            text = String.format("%d", (long) (counter + 1)) + " turns charged for active skills (1 from Team Badge)";
                        } else {
                            text = String.format("%d", (long) counter) + " turns charged for active skills";
                        }
                        break;
                    case 22:
                        text = String.format("%d", (long) (counter * 10)) + "% bonus damage for matching a row of fire orbs";
                        break;
                    case 23:
                        text = String.format("%d", (long) (counter * 10)) + "% bonus damage for matching a row of water orbs";
                        break;
                    case 24:
                        text = String.format("%d", (long) (counter * 10)) + "% bonus damage for matching a row of wood orbs";
                        break;
                    case 25:
                        text = String.format("%d", (long) (counter * 10)) + "% bonus damage for matching a row of light orbs";
                        break;
                    case 26:
                        text = String.format("%d", (long) (counter * 10)) + "% bonus damage for matching a row of dark orbs";
                        break;
                    case 27:
                        text = "Attack two targets and deal " + Math.pow(1.5, counter) + "x bonus damage when matching 4 orbs";
                        break;
                    case 28:
                        if (teamBadge == 9) {
                            if (counter >= 5) {
                                text = String.format("%d", (long) (counter * 20)) + "% chance to resist skill bind (Team Badge has no effect)";
                            } else {
                                text = String.format("%d", (long) (((100 - (counter * 20)) * .5) + counter * 20)) + "% chance to resist skill bind (Stacks multiplicatively with Team Badge)";
                            }
                        } else {
                            text = String.format("%d", (long) (counter * 20)) + "% chance to resist skill bind";
                        }
                        break;
                    case 29:
                        text = String.format("%d", (long) (counter * 20)) + "% drop chance and " + String.format("%d", (long) (counter * 5)) + "% bonus healing for enhanced heart orbs";
                        break;
                    case 30:
                        text = "Boost stats by " + Math.pow(1.5, counter) + "x during cooperation mode";
                        break;
                    case 31:
                        if(latentList.contains(14)){
                            text = Math.pow(3, counter) * Math.pow(1.5, latentAmountList.get(latentList.indexOf(14))) + "x bonus damage versus Dragon types";
                        } else {
                            text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Dragon types";
                        }
                        break;
                    case 32:
                        if(latentList.contains(13)){
                            text = Math.pow(3, counter) * Math.pow(1.5, latentAmountList.get(latentList.indexOf(13))) + "x bonus damage versus God types";
                        } else {
                            text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus God types";
                        }
                        break;
                    case 33:
                        if(latentList.contains(15)){
                            text = Math.pow(3, counter) * Math.pow(1.5, latentAmountList.get(latentList.indexOf(15))) + "x bonus damage versus Devil types";
                        } else {
                            text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Devil types";
                        }
                        break;
                    case 34:
                        if(latentList.contains(16)){
                            text = Math.pow(3, counter) * Math.pow(1.5, latentAmountList.get(latentList.indexOf(16))) + "x bonus damage versus Machine types";
                        } else {
                            text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Machine types";
                        }
                        break;
                    case 35:
                        if(latentList.contains(18)){
                            text = Math.pow(3, counter) * Math.pow(1.5, latentAmountList.get(latentList.indexOf(18))) + "x bonus damage versus Attacker types";
                        } else {
                            text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Attacker types";
                        }
                        break;
                    case 36:
                        if(latentList.contains(19)){
                            text = Math.pow(3, counter) * Math.pow(1.5, latentAmountList.get(latentList.indexOf(19))) + "x bonus damage versus Physical types";
                        } else {
                            text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Physical types";
                        }
                        break;
                    case 37:
                        if(latentList.contains(20)){
                            text = Math.pow(3, counter) * Math.pow(1.5, latentAmountList.get(latentList.indexOf(20))) + "x bonus damage versus Healer types";
                        } else {
                            text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Healer types";
                        }
                        break;
                    case 38:
                        if(latentList.contains(17)){
                            text = Math.pow(3, counter) * Math.pow(1.5, latentAmountList.get(latentList.indexOf(17))) + "x bonus damage versus Dragon types";
                        } else {
                            text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Balanced types";
                        }
                        break;
                    case 39:
                        text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Awoken Material types";
                        break;
                    case 40:
                        text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Enhance Material types";
                        break;
                    case 41:
                        text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Vendor types";
                        break;
                    case 42:
                        text = String.format("%d", (long) Math.pow(3, counter)) + "x bonus damage versus Evo Material types";
                        break;
                    case 43:
                        text = String.format("%d", (long) Math.pow(2, counter)) + "x bonus damage when reaching 7 combos";
                        break;
                }
                for (int i = 0; i < monsterList.size(); i++) {
                    for (int j = 0; j < monsterList.get(i).getAwokenSkills().size(); j++) {
                        if (monsterList.get(i).getAwokenSkills(j) == awakening) {
                            if (!filteredMonsters.contains(monsterList.get(i))) {
                                filteredMonsters.add(monsterList.get(i));
                            }
                        }
                    }
                }
                tooltipAwakening = new TooltipAwakening(mContext, text, awakening, false, filteredMonsters, isMonsterSpecificAdapter);
            }
            tooltipAwakening.show(v);
        }
    };

    static class ViewHolder {
        TextView awakeningAmount;
        ImageView awakeningPicture;
        RelativeLayout relativeLayout;
    }

    private void trimAwakenings() {
        if (awakeningList != null) {
            awakeningList.clear();
        } else {
            awakeningList = new ArrayList<>();
        }
        if (awakeningAmountList != null) {
            awakeningAmountList.clear();
        } else {
            awakeningAmountList = new ArrayList<>();
        }
        int amount = 0;

        for (int i = 0; i < awakeningListAll.size(); i++) {
            if (!awakeningList.contains(awakeningListAll.get(i))) {
                awakeningList.add(awakeningListAll.get(i));
            }
        }
        Collections.sort(awakeningList);
        for (int i = 0; i < awakeningList.size(); i++) {
            for (int j = 0; j < awakeningListAll.size(); j++) {
                if (awakeningList.get(i).equals(awakeningListAll.get(j))) {
                    amount++;
                }
            }
            awakeningAmountList.add(amount);
            amount = 0;
        }

        if (latentListAll.size() != 0) {
            if (latentList != null) {
                latentList.clear();
            } else {
                latentList = new ArrayList<>();
            }
            if (latentAmountList != null) {
                latentAmountList.clear();
            } else {
                latentAmountList = new ArrayList<>();
            }

            for (int i = 0; i < latentListAll.size(); i++) {
                if (!latentList.contains(latentListAll.get(i))) {
                    latentList.add(latentListAll.get(i));
                }
            }
            Collections.sort(latentList);
            for (int i = 0; i < latentList.size(); i++) {
                for (int j = 0; j < latentListAll.size(); j++) {
                    if (latentList.get(i).equals(latentListAll.get(j))) {
                        amount++;
                    }
                }
                if(latentList.get(i) > 11){
                    amount = amount / 2;
                }
                latentAmountList.add(amount);
                amount = 0;

            }
        }
    }

    public void updateTeamBadge(int teamBadge) {
        this.teamBadge = teamBadge;
        notifyDataSetChanged();
    }
}
