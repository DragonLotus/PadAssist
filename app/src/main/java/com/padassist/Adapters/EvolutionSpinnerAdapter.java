package com.padassist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.Data.BaseMonster;
import com.padassist.Data.Monster;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by DragonLotus on 10/11/2015.
 */
public class EvolutionSpinnerAdapter extends ArrayAdapter<BaseMonster> {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<BaseMonster> evolutions;
    private int resourceId;
    private Monster monster, tempMonster;

    public EvolutionSpinnerAdapter(Context context, int resource, Monster monster, ArrayList<BaseMonster> evolutions) {
        super(context, resource, evolutions);
        mContext = context;
        this.evolutions = evolutions;
        this.resourceId = resource;
        this.monster = monster;
        tempMonster = new Monster(monster);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            viewHolder.rarity = (TextView) convertView.findViewById(R.id.rarity);
            viewHolder.type1 = (ImageView) convertView.findViewById(R.id.type1);
            viewHolder.type2 = (ImageView) convertView.findViewById(R.id.type2);
            viewHolder.type3 = (ImageView) convertView.findViewById(R.id.type3);
            viewHolder.monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            viewHolder.rarityStar = (ImageView) convertView.findViewById(R.id.rarityStar);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            viewHolder.monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            viewHolder.monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            viewHolder.monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            viewHolder.monsterHPDiff = (TextView) convertView.findViewById(R.id.monsterHPDiff);
            viewHolder.monsterATKDiff = (TextView) convertView.findViewById(R.id.monsterATKDiff);
            viewHolder.monsterRCVDiff = (TextView) convertView.findViewById(R.id.monsterRCVDiff);
            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.monsterName.setText(evolutions.get(position).getName());
//        viewHolder.monsterPicture.setImageResource(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getMonsterPicture());
        viewHolder.monsterPicture.setImageBitmap(evolutions.get(position).getMonsterPicture());
        viewHolder.rarity.setText("" + evolutions.get(position).getRarity());
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);

        viewHolder.monsterHPDiff.setVisibility(View.GONE);
        viewHolder.monsterATKDiff.setVisibility(View.GONE);
        viewHolder.monsterRCVDiff.setVisibility(View.GONE);

        if (evolutions.get(position).getMonsterId() == 0) {
            if (evolutions.size() == 1) {
                viewHolder.monsterName.setText("No evolutions available.");
            } else {
                viewHolder.monsterName.setText("No evolution selected.");
            }
            viewHolder.monsterName.setPadding(0, 16, 0, 16);
            viewHolder.monsterPicture.setVisibility(View.GONE);
            viewHolder.rarity.setVisibility(View.GONE);
            viewHolder.rarityStar.setVisibility(View.GONE);
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
            viewHolder.monsterHP.setVisibility(View.GONE);
            viewHolder.monsterATK.setVisibility(View.GONE);
            viewHolder.monsterRCV.setVisibility(View.GONE);
        } else {
            viewHolder.monsterName.setPadding(0, 0, 0, 0);
            viewHolder.monsterPicture.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.VISIBLE);
            viewHolder.monsterATK.setVisibility(View.VISIBLE);
            viewHolder.monsterRCV.setVisibility(View.VISIBLE);
            tempMonster.setBaseMonster(evolutions.get(position));
            tempMonster.setCurrentLevel(monster.getCurrentLevel());
            viewHolder.monsterHP.setText(tempMonster.getTotalHp() + " / ");
            viewHolder.monsterATK.setText(tempMonster.getTotalAtk() + " / ");
            viewHolder.monsterRCV.setText("" + tempMonster.getTotalRcv());
        }

        if (evolutions.get(position).getType1() >= 0) {
            viewHolder.type1.setImageResource(ImageResourceUtil.monsterType(evolutions.get(position).getType1()));
            viewHolder.type1.setVisibility(View.VISIBLE);
        } else {
            viewHolder.type1.setVisibility(View.GONE);
        }
        if (evolutions.get(position).getType2() >= 0) {
            viewHolder.type2.setImageResource(ImageResourceUtil.monsterType(evolutions.get(position).getType2()));
            viewHolder.type2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.type2.setVisibility(View.GONE);
        }
        if (evolutions.get(position).getType3() >= 0) {
            viewHolder.type3.setImageResource(ImageResourceUtil.monsterType(evolutions.get(position).getType3()));
            viewHolder.type3.setVisibility(View.VISIBLE);
        } else {
            viewHolder.type3.setVisibility(View.GONE);
        }
        viewHolder.monsterName.setHorizontallyScrolling(true);
        viewHolder.monsterName.setSelected(true);
        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            viewHolder.rarity = (TextView) convertView.findViewById(R.id.rarity);
            viewHolder.type1 = (ImageView) convertView.findViewById(R.id.type1);
            viewHolder.type2 = (ImageView) convertView.findViewById(R.id.type2);
            viewHolder.type3 = (ImageView) convertView.findViewById(R.id.type3);
            viewHolder.monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            viewHolder.rarityStar = (ImageView) convertView.findViewById(R.id.rarityStar);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            viewHolder.monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            viewHolder.monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            viewHolder.monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            viewHolder.monsterHPDiff = (TextView) convertView.findViewById(R.id.monsterHPDiff);
            viewHolder.monsterATKDiff = (TextView) convertView.findViewById(R.id.monsterATKDiff);
            viewHolder.monsterRCVDiff = (TextView) convertView.findViewById(R.id.monsterRCVDiff);
            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        getView(position, convertView, parent);
        if (evolutions.get(position).getMonsterId() != 0) {
            viewHolder.monsterHPDiff.setVisibility(View.VISIBLE);
            viewHolder.monsterATKDiff.setVisibility(View.VISIBLE);
            viewHolder.monsterRCVDiff.setVisibility(View.VISIBLE);
            if ((tempMonster.getTotalHp() - monster.getTotalHp()) > 0) {
                viewHolder.monsterHPDiff.setText("+" + (tempMonster.getTotalHp() - monster.getTotalHp()) + " / ");
            } else if ((tempMonster.getTotalHp() - monster.getTotalHp()) == 0) {
                viewHolder.monsterHPDiff.setText("~ / ");
            } else {
                viewHolder.monsterHPDiff.setText("" + (tempMonster.getTotalHp() - monster.getTotalHp()) + " / ");
            }
            if ((tempMonster.getTotalAtk() - monster.getTotalAtk()) > 0) {
                viewHolder.monsterATKDiff.setText("+" + (tempMonster.getTotalAtk() - monster.getTotalAtk()) + " / ");
            } else if ((tempMonster.getTotalAtk() - monster.getTotalAtk()) == 0) {
                viewHolder.monsterATKDiff.setText("~ / ");
            } else {
                viewHolder.monsterATKDiff.setText("" + (tempMonster.getTotalAtk() - monster.getTotalAtk()) + " / ");
            }
            if ((tempMonster.getTotalRcv() - monster.getTotalRcv()) > 0) {
                viewHolder.monsterRCVDiff.setText("+" + (tempMonster.getTotalRcv() - monster.getTotalRcv()) + "");
            } else if ((tempMonster.getTotalRcv() - monster.getTotalRcv()) == 0) {
                viewHolder.monsterRCVDiff.setText("~");
            } else {
                viewHolder.monsterRCVDiff.setText("" + (tempMonster.getTotalRcv() - monster.getTotalRcv()) + "");
            }
        }

        if (position % 2 == 1) {
            viewHolder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        } else {
            viewHolder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView monsterName, rarity, monsterHP, monsterATK, monsterRCV, monsterHPDiff, monsterATKDiff, monsterRCVDiff;
        ImageView monsterPicture, type1, type2, type3, rarityStar;
        RelativeLayout relativeLayout;
    }
}
