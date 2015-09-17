package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
            viewHolder.type1 = (TextView) convertView.findViewById(R.id.type1);
            viewHolder.type2 = (TextView) convertView.findViewById(R.id.type2);
            viewHolder.element1 = (ImageView) convertView.findViewById(R.id.element1);
            viewHolder.element2 = (ImageView) convertView.findViewById(R.id.element2);

            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterPlus.setText("(+"+ monsterList.get(position).getTotalPlus() + ")");
        viewHolder.type1.setText(monsterList.get(position).getType1String());
        viewHolder.type2.setText(monsterList.get(position).getType2String());

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
            viewHolder.monsterPlus.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView monsterName, monsterPlus, type1, type2;
        ImageView element1, element2;

    }
}
