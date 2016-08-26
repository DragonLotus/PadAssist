package com.padassist.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.Data.Monster;
import com.padassist.Fragments.DisclaimerDialogFragment;
import com.padassist.Graphics.Tooltip;
import com.padassist.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/18/2015.
 */
public class AwakeningGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> awakeningList;
    private ArrayList<Monster> monsterList;
    private ArrayList<Integer> latentList = new ArrayList<>();
    private ArrayList<Integer> awakeningListAll;
    private ArrayList<Integer> latentListAll;
    private ArrayList<Integer> awakeningAmountList;
    private ArrayList<Integer> latentAmountList;
    private boolean monsterSpecificAdapter;
    private int teamBadge;

    public AwakeningGridAdapter(Context context, ArrayList<Monster> monsterList, ArrayList<Integer> awakenings, ArrayList<Integer> latents, boolean monsterSpecificAdapter, int teamBadge) {
        mContext = context;
        this.monsterList = monsterList;
        awakeningListAll = awakenings;
        latentListAll = latents;
        this.monsterSpecificAdapter = monsterSpecificAdapter;
        this.teamBadge = teamBadge;
        trimAwakenings();
    }

    @Override
    public int getCount() {
        if(latentListAll.size() != 0){
            return (awakeningList.size() + latentList.size());
        }
        else {
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
            switch (latentList.get(position - awakeningList.size())) {
                case 1:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_1);
                    break;
                case 2:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_2);
                    break;
                case 3:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_3);
                    break;
                case 4:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_4);
                    break;
                case 5:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_5);
                    break;
                case 6:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_6);
                    break;
                case 7:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_7);
                    break;
                case 8:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_8);
                    break;
                case 9:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_9);
                    break;
                case 10:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_10);
                    break;
                case 11:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_11);
                    break;
                default:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.latent_awakening_blank);
                    break;
            }
            viewHolder.awakeningAmount.setText("x" + latentAmountList.get(position - awakeningList.size()));
        } else {
            switch (awakeningList.get(position)) {
                case 1:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_1);
                    break;
                case 2:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_2);
                    break;
                case 3:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_3);
                    break;
                case 4:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_4);
                    break;
                case 5:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_5);
                    break;
                case 6:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_6);
                    break;
                case 7:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_7);
                    break;
                case 8:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_8);
                    break;
                case 9:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_9);
                    break;
                case 10:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_10);
                    break;
                case 11:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_11);
                    break;
                case 12:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_12);
                    break;
                case 13:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_13);
                    break;
                case 14:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_14);
                    break;
                case 15:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_15);
                    break;
                case 16:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_16);
                    break;
                case 17:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_17);
                    break;
                case 18:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_18);
                    break;
                case 19:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_19);
                    break;
                case 20:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_20);
                    break;
                case 21:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_21);
                    break;
                case 22:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_22);
                    break;
                case 23:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_23);
                    break;
                case 24:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_24);
                    break;
                case 25:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_25);
                    break;
                case 26:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_26);
                    break;
                case 27:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_27);
                    break;
                case 28:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_28);
                    break;
                case 29:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_29);
                    break;
                case 30:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_30);
                    break;
                case 31:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_31);
                    break;
                case 32:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_32);
                    break;
                case 33:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_33);
                    break;
                case 34:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_34);
                    break;
                case 35:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_35);
                    break;
                case 36:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_36);
                    break;
                case 37:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_37);
                    break;
                case 38:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_38);
                    break;
                case 39:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_39);
                    break;
                case 40:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_40);
                    break;
                case 41:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_41);
                    break;
                case 42:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_42);
                    break;
                default:
                    viewHolder.awakeningPicture.setImageResource(R.drawable.awakening);
                    break;
            }
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
            Tooltip tooltip;
            String text = "Testerino";
            int awakening;
            double counter;
            if(position >= awakeningList.size()){
                awakening = latentList.get(position - awakeningList.size());
                counter = latentAmountList.get(position - awakeningList.size());
                switch (awakening) {
                    case 1:
                        text = "Bonus " + counter * 1.5 + "% of base HP";
                        break;
                    case 2:
                        text = "Bonus " + counter + "% of base ATK";
                        break;
                    case 3:
                        text = "Bonus " + counter * 5 + "% of base RCV";
                        break;
                    case 4:
                        text = "Extra " + counter * .05 + " seconds to match";
                        break;
                    case 5:
                        text = "Auto-heal " + counter * 15 + "% of total RCV";
                        break;
                    case 6:
                        text = "Resist " + counter + "% of fire damage";
                        break;
                    case 7:
                        text = "Resist " + counter + "% of water damage";
                        break;
                    case 8:
                        text = "Resist " + counter + "% of grass damage";
                        break;
                    case 9:
                        text = "Resist " + counter + "% of light damage";
                        break;
                    case 10:
                        text = "Resist " + counter + "% of dark damage";
                        break;
                    case 11:
                        text = "Resist " + counter + " turns of skill delay";
                        break;
                }
                for (int i = 0; i < monsterList.size(); i++){
                    for(int j = 0; j < monsterList.get(i).getLatents().size(); j++){
                        if(monsterList.get(i).getLatents().get(j).getValue() == awakening){
                            if(!filteredMonsters.contains(monsterList.get(i))){
                                filteredMonsters.add(monsterList.get(i));
                            }
                        }
                    }
                }
                tooltip = new Tooltip(mContext, text, awakening, true, filteredMonsters, monsterSpecificAdapter);
            } else {
                awakening = awakeningList.get(position);
                counter = awakeningAmountList.get(position);
                switch (awakening) {
                    case 1:
                        text = "Bonus " + counter * 200 + "% HP";
                        break;
                    case 2:
                        text = "Bonus " + counter * 100 + "% ATK";
                        break;
                    case 3:
                        text = "Bonus " + counter * 50 + "% RCV";
                        break;
                    case 4:
                        text = "Resist " + counter * 5 + "% of fire damage";
                        break;
                    case 5:
                        text = "Resist " + counter * 5 + "% of water damage";
                        break;
                    case 6:
                        text = "Resist " + counter * 5 + "% of grass damage";
                        break;
                    case 7:
                        text = "Resist " + counter * 5 + "% of light damage";
                        break;
                    case 8:
                        text = "Resist " + counter * 5 + "% of dark damage";
                        break;
                    case 9:
                        text = "Auto-Heal " + counter * 500 + " HP";
                        break;
                    case 10:
                        text = counter * 50 + "% chance to resist bind";
                        break;
                    case 11:
                        text = counter * 20 + "% chance to resist blind";
                        break;
                    case 12:
                        text = counter * 20 + "% chance to resist jammer orbs";
                        break;
                    case 13:
                        text = counter * 20 + "% chance to resist poison orbs";
                        break;
                    case 14:
                        text = counter * 20 + "% drop chance and " + counter * 5 + "% bonus damage for enhanced fire orbs.";
                        break;
                    case 15:
                        text = counter * 20 + "% drop chance and " + counter * 5 + "% bonus damage for enhanced water orbs.";
                        break;
                    case 16:
                        text = counter * 20 + "% drop chance and " + counter * 5 + "% bonus damage for enhanced grass orbs.";
                        break;
                    case 17:
                        text = counter * 20 + "% drop chance and " + counter * 5 + "% bonus damage for enhanced light orbs.";
                        break;
                    case 18:
                        text = counter * 20 + "% drop chance and " + counter * 5 + "% bonus damage for enhanced dark orbs.";
                        break;
                    case 19:
                        if(teamBadge == 2){
                            text = "Extra " + (counter * .5 + 1) + " seconds to match (1s from Team Badge)";
                        } else {
                            text = "Extra " + counter * .5 + " seconds to match";
                        }
                        break;
                    case 20:
                        text = counter * 3 + " turns of bind recovery when matching a row of heart orbs";
                        break;
                    case 21:
                        if(teamBadge == 7){
                            text = (counter + 1) + " turns charged for active skills (1 from Team Badge)";
                        } else {
                            text = counter  + " turns charged for active skills";
                        }
                        break;
                    case 22:
                        text = counter * 10 + "% bonus damage for matching a row of fire orbs";
                        break;
                    case 23:
                        text = counter * 10 + "% bonus damage for matching a row of water orbs";
                        break;
                    case 24:
                        text = counter * 10 + "% bonus damage for matching a row of grass orbs";
                        break;
                    case 25:
                        text = counter * 10 + "% bonus damage for matching a row of light orbs";
                        break;
                    case 26:
                        text = counter * 10 + "% bonus damage for matching a row of dark orbs";
                        break;
                    case 27:
                        text = "Attack two targets and " + Math.pow(1.5, counter) + "x bonus damage when matching 4 orbs";
                        break;
                    case 28:
                        if(teamBadge == 9){
                            if(counter >= 5){
                                text = counter * 20 + "% chance to resist skill bind (Team Badge has no effect)";
                            } else {
                                text = (((100 - (counter * 20)) * .5) + counter * 20) + "% chance to resist skill bind (Stacks multiplicatively with Team Badge)";
                            }
                        } else {
                            text = counter * 20 + "% chance to resist skill bind";
                        }
                        break;
                    case 29:
                        text = counter * 20 + "% drop chance and " + counter * 5 + "% bonus healing for enhanced heart orbs.";
                        break;
                    case 30:
                        text = "Boost stats by " + Math.pow(1.5, counter) + "x during cooperation mode";
                        break;
                    case 31:
                        text = counter * 3 + "x bonus damage versus Dragon types";
                        break;
                    case 32:
                        text = counter * 3 + "x bonus damage versus God types";
                        break;
                    case 33:
                        text = counter * 3 + "x bonus damage versus Devil types";
                        break;
                    case 34:
                        text = counter * 3 + "x bonus damage versus Machine types";
                        break;
                    case 35:
                        text = counter * 3 + "x bonus damage versus Attacker types";
                        break;
                    case 36:
                        text = counter * 3 + "x bonus damage versus Physical types";
                        break;
                    case 37:
                        text = counter * 3 + "x bonus damage versus Healer types";
                        break;
                    case 38:
                        text = counter * 3 + "x bonus damage versus Balanced types";
                        break;
                    case 39:
                        text = counter * 3 + "x bonus damage versus Awoken Material types";
                        break;
                    case 40:
                        text = counter * 3 + "x bonus damage versus Enhance Material types";
                        break;
                    case 41:
                        text = counter * 3 + "x bonus damage versus Vendor types";
                        break;
                    case 42:
                        text = counter * 3 + "x bonus damage versus Evo Material types";
                        break;
                }
                for (int i = 0; i < monsterList.size(); i++){
                    for(int j = 0; j < monsterList.get(i).getAwokenSkills().size(); j++){
                        if(monsterList.get(i).getAwokenSkills(j) == awakening){
                            if(!filteredMonsters.contains(monsterList.get(i))){
                                filteredMonsters.add(monsterList.get(i));
                            }
                        }
                    }
                }
                tooltip = new Tooltip(mContext, text, awakening, false, filteredMonsters, monsterSpecificAdapter);
            }
            tooltip.show(v);
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
        int awakening = 0;
        for (int i = 0; i < awakeningListAll.size(); i++) {
            if (awakening == awakeningListAll.get(i)) {
                amount++;
                if (!awakeningList.contains(awakening)) {
                    awakeningList.add(awakening);
                } else {
                    awakeningListAll.remove(i);
                    i--;
                }
            } else {
                if (amount > 0) {
                    awakeningAmountList.add(amount);
                }
                amount = 1;
                awakening = awakeningListAll.get(i);
                if (!awakeningList.contains(awakening)) {
                    awakeningList.add(awakening);
                }
            }
        }
        awakeningAmountList.add(amount);

        if(latentListAll.size() != 0){
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
            int amount2 = 0;
            int latent = 0;
            for (int i = 0; i < latentListAll.size(); i++) {
                if (latent == latentListAll.get(i)) {
                    amount2++;
                    if (!latentList.contains(latent)) {
                        latentList.add(latent);
                    } else {
                        latentListAll.remove(i);
                        i--;
                    }
                } else {
                    if (amount2 > 0) {
                        latentAmountList.add(amount2);
                    }
                    amount2 = 1;
                    latent = latentListAll.get(i);
                    if (!latentList.contains(latent)) {
                        latentList.add(latent);
                    }
                }
            }
            latentAmountList.add(amount2);
        }
    }
}
