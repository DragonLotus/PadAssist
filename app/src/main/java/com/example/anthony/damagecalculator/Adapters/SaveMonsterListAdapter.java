package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 9/16/2015.
 */
public class SaveMonsterListAdapter extends ArrayAdapter<Monster> {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Monster> monsterList;
    private int resourceId;

    public SaveMonsterListAdapter(Context context, int textViewResourceId, ArrayList<Monster> monsterList){
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
            viewHolder.monsterPlus = (TextView) convertView.findViewById(R.id.monsterPlus);
            viewHolder.type1 = (ImageView) convertView.findViewById(R.id.type1);
            viewHolder.type2 = (ImageView) convertView.findViewById(R.id.type2);
            viewHolder.type3 = (ImageView) convertView.findViewById(R.id.type3);
            viewHolder.element1 = (ImageView) convertView.findViewById(R.id.element1);
            viewHolder.element2 = (ImageView) convertView.findViewById(R.id.element2);

            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterPlus.setText("(+"+ monsterList.get(position).getTotalPlus() + ")");


        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
            viewHolder.monsterPlus.setVisibility(View.GONE);
        }else {
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
            viewHolder.monsterPlus.setVisibility(View.VISIBLE);
            viewHolder.element1.setVisibility(View.VISIBLE);
            viewHolder.element2.setVisibility(View.VISIBLE);
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
//
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

        if(monsterList.get(position).getElement1().equals(Element.RED)){
            viewHolder.element1.setImageResource(R.drawable.red_orb);
        }else if (monsterList.get(position).getElement1().equals(Element.BLUE)){
            viewHolder.element1.setImageResource(R.drawable.blue_orb);
        }else if (monsterList.get(position).getElement1().equals(Element.GREEN)){
            viewHolder.element1.setImageResource(R.drawable.green_orb);
        }else if (monsterList.get(position).getElement1().equals(Element.LIGHT)){
            viewHolder.element1.setImageResource(R.drawable.light_orb);
        }else if (monsterList.get(position).getElement1().equals(Element.DARK)){
            viewHolder.element1.setImageResource(R.drawable.dark_orb);
        }else if (monsterList.get(position).getElement1().equals(Element.BLANK)){
            viewHolder.element1.setVisibility(View.INVISIBLE);
        }

        if(monsterList.get(position).getElement2().equals(Element.RED)){
            viewHolder.element2.setImageResource(R.drawable.red_orb);
        }else if (monsterList.get(position).getElement2().equals(Element.BLUE)){
            viewHolder.element2.setImageResource(R.drawable.blue_orb);
        }else if (monsterList.get(position).getElement2().equals(Element.GREEN)){
            viewHolder.element2.setImageResource(R.drawable.green_orb);
        }else if (monsterList.get(position).getElement2().equals(Element.LIGHT)){
            viewHolder.element2.setImageResource(R.drawable.light_orb);
        }else if (monsterList.get(position).getElement2().equals(Element.DARK)){
            viewHolder.element2.setImageResource(R.drawable.dark_orb);
        }else if (monsterList.get(position).getElement2().equals(Element.BLANK)){
            viewHolder.element2.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView monsterName, monsterPlus;
        ImageView element1, element2, type1, type2, type3;

    }
}
