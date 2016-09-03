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
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by DragonLotus on 10/11/2015.
 */
public class EvolutionSpinnerAdapter extends ArrayAdapter<Long> {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Long> evolutions;
    private int resourceId;
    private Realm realm = Realm.getDefaultInstance();

    public EvolutionSpinnerAdapter(Context context, int resource, int textViewResourceId, ArrayList<Long> evolutions) {
        super(context, resource, textViewResourceId, evolutions);
        mContext = context;
        this.evolutions = evolutions;
        this.resourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
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
            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.monsterName.setText(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getName());
        viewHolder.monsterPicture.setImageResource(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getMonsterPicture());
        viewHolder.rarity.setText("" + realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getRarity());
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);

        if(evolutions.get(position) == 0){
            if(evolutions.size() == 1){
                viewHolder.monsterName.setText("No evolutions available.");
            }else {
                viewHolder.monsterName.setText("No evolution selected.");
            }
            viewHolder.monsterName.setPadding(0, 16, 0, 16);
            viewHolder.monsterPicture.setVisibility(View.GONE);
            viewHolder.rarity.setVisibility(View.GONE);
            viewHolder.rarityStar.setVisibility(View.GONE);
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
        } else {
            viewHolder.monsterName.setPadding(0, 0, 0, 0);
            viewHolder.monsterPicture.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
        }

        if(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getType1() >= 0){
            viewHolder.type1.setImageResource(ImageResourceUtil.monsterType(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getType1()));
        } else {
            viewHolder.type1.setVisibility(View.INVISIBLE);
        }
        if(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getType2() >= 0){
            viewHolder.type2.setImageResource(ImageResourceUtil.monsterType(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getType2()));
        } else {
            viewHolder.type2.setVisibility(View.INVISIBLE);
        }
        if(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getType3() >= 0){
            viewHolder.type3.setImageResource(ImageResourceUtil.monsterType(realm.where(BaseMonster.class).equalTo("monsterId",evolutions.get(position)).findFirst().getType3()));
        } else {
            viewHolder.type3.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
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
            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        if (position % 2 == 1) {
            viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#e8e8e8"));
        } else {
            viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        return getView(position, convertView, parent);
    }

    static class ViewHolder {
        TextView monsterName, rarity;
        ImageView monsterPicture, type1, type2, type3, rarityStar;
        RelativeLayout relativeLayout;
    }
}
