package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Element;
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
            viewHolder.type1 = (TextView) convertView.findViewById(R.id.type1);
            viewHolder.type2 = (TextView) convertView.findViewById(R.id.type2);
            viewHolder.type3 = (TextView) convertView.findViewById(R.id.type3);
            viewHolder.element1 = (ImageView) convertView.findViewById(R.id.element1);
            viewHolder.element2 = (ImageView) convertView.findViewById(R.id.element2);

            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterId.setText("" + monsterList.get(position).getMonsterId());
        viewHolder.type1.setText(monsterList.get(position).getType1String());
        viewHolder.type2.setText(monsterList.get(position).getType2String());
        viewHolder.type3.setText(monsterList.get(position).getType3String());

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
        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
            viewHolder.monsterId.setVisibility(View.GONE);
        }else {
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.monsterId.setVisibility(View.VISIBLE);
            viewHolder.element1.setVisibility(View.VISIBLE);
            viewHolder.element2.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView monsterName, monsterId, type1, type2, type3;
        ImageView element1, element2;

    }
}
