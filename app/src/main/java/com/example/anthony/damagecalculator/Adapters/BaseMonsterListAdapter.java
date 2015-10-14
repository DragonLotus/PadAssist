package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 9/16/2015.
 */
public class BaseMonsterListAdapter extends ArrayAdapter<BaseMonster> {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<BaseMonster> monsterList;
    private int resourceId;

    public BaseMonsterListAdapter(Context context, int textViewResourceId, ArrayList<BaseMonster> monsterList){
        super(context, textViewResourceId, monsterList);
        mContext = context;
        this.monsterList = monsterList;
        this.resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;
        if (convertView == null){
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            viewHolder.monsterId = (TextView) convertView.findViewById(R.id.monsterId);
            viewHolder.rarity = (TextView) convertView.findViewById(R.id.rarity);
            viewHolder.monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            viewHolder.monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            viewHolder.monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            viewHolder.type1 = (ImageView) convertView.findViewById(R.id.type1);
            viewHolder.type2 = (ImageView) convertView.findViewById(R.id.type2);
            viewHolder.type3 = (ImageView) convertView.findViewById(R.id.type3);
            viewHolder.monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            viewHolder.rarityStar = (ImageView) convertView.findViewById(R.id.rarityStar);

            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterId.setText("" + monsterList.get(position).getMonsterId());
        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolder.rarity.setText("" + monsterList.get(position).getRarity());
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);
        viewHolder.monsterHP.setText(Integer.toString(monsterList.get(position).getHpMax()) + " / ");
        viewHolder.monsterATK.setText(Integer.toString(monsterList.get(position).getAtkMax()) + " / ");
        viewHolder.monsterRCV.setText(Integer.toString(monsterList.get(position).getRcvMax()));

        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
            viewHolder.monsterId.setVisibility(View.INVISIBLE);
            viewHolder.rarity.setVisibility(View.GONE);
            viewHolder.rarityStar.setVisibility(View.GONE);
            viewHolder.monsterHP.setVisibility(View.GONE);
            viewHolder.monsterATK.setVisibility(View.GONE);
            viewHolder.monsterRCV.setVisibility(View.GONE);
        }else {
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
            viewHolder.monsterId.setVisibility(View.VISIBLE);
            viewHolder.monsterPicture.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.VISIBLE);
            viewHolder.monsterATK.setVisibility(View.VISIBLE);
            viewHolder.monsterRCV.setVisibility(View.VISIBLE);
        }

        switch(monsterList.get(position).getType1()){
            case 0:
                viewHolder.type1.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type1.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type1.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type1.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type1.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type1.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type1.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type1.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type1.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type1.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type1.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type1.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type1.setVisibility(View.INVISIBLE);
                break;
        }
        switch(monsterList.get(position).getType2()){
            case 0:
                viewHolder.type2.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type2.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type2.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type2.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type2.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type2.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type2.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type2.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type2.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type2.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type2.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type2.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type2.setVisibility(View.INVISIBLE);
                break;
        }
        switch(monsterList.get(position).getType3()){
            case 0:
                viewHolder.type3.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type3.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type3.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type3.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type3.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type3.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type3.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type3.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type3.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type3.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type3.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type3.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type3.setVisibility(View.INVISIBLE);
                break;
        }

//        if(monsterList.get(position).getType2() == -1){
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewHolder.type1.getLayoutParams();
//            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            viewHolder.type1.setLayoutParams(params);
//        }
//
//        if(monsterList.get(position).getType3() == -1){
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewHolder.type2.getLayoutParams();
//            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            viewHolder.type2.setLayoutParams(params);
//        }

        return convertView;
    }

    static class ViewHolder {
        TextView monsterName, monsterId, rarity, monsterHP, monsterATK, monsterRCV;
        ImageView monsterPicture, type1, type2, type3, rarityStar;

    }
    public void notifyDataSetChanged(ArrayList<BaseMonster> monsterList) {
        this.monsterList = monsterList;
        notifyDataSetChanged();
        Log.d("Save Monster Adapter", "monsterList notify is: " + monsterList);
    }

    public ArrayList<BaseMonster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(ArrayList<BaseMonster> monsterList) {
        this.monsterList = monsterList;
    }
}
