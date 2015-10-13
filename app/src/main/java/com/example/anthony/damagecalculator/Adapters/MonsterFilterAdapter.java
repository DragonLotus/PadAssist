package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/12/2015.
 */
public class MonsterFilterAdapter extends BaseAdapter implements Filterable {

    private MainActivity activity;
    private MonsterFilter monsterFilter;
    private Typeface typeface;
    private ArrayList<Monster> monsterList;
    private ArrayList<Monster> monsterFilterList;

    public MonsterFilterAdapter(MainActivity activity, ArrayList<Monster> monsterList){
        this.activity = activity;
        this.monsterList = monsterList;
        this.monsterFilterList = monsterList;

        getFilter();
    }

    @Override
    public int getCount() {
        return monsterFilterList.size();
    }

    @Override
    public Object getItem(int position) {
        return monsterFilterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (monsterFilter == null){
            monsterFilter = new MonsterFilter();
        }
        return monsterFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.save_monster_list_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            viewHolder.monsterPlus = (TextView) convertView.findViewById(R.id.monsterPlus);
            viewHolder.monsterAwakenings = (TextView) convertView.findViewById(R.id.monsterAwakenings);
            viewHolder.monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            viewHolder.monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            viewHolder.monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            viewHolder.monsterLevel = (TextView) convertView.findViewById(R.id.monsterLevel);
            viewHolder.type1 = (ImageView) convertView.findViewById(R.id.type1);
            viewHolder.type2 = (ImageView) convertView.findViewById(R.id.type2);
            viewHolder.type3 = (ImageView) convertView.findViewById(R.id.type3);
            viewHolder.favorite = (ImageView) convertView.findViewById(R.id.favorite);
            viewHolder.favoriteOutline = (ImageView) convertView.findViewById(R.id.favoriteOutline);
            viewHolder.monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);

            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }

        return convertView;
    }



    static class ViewHolder {
        TextView monsterName, monsterPlus, monsterAwakenings, monsterHP, monsterATK, monsterRCV, monsterLevel;
        ImageView monsterPicture, type1, type2, type3, favorite, favoriteOutline;
    }

    private class MonsterFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if(constraint != null && constraint.length()>0){
                ArrayList<Monster> tempList = new ArrayList<>();

                for(Monster monster : monsterList){
                    if (monster.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        tempList.add(monster);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            }else {
                filterResults.count = monsterList.size();
                filterResults.values = monsterList;
            }
            return filterResults;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            monsterFilterList = (ArrayList<Monster>) results.values;
            notifyDataSetChanged();
        }
    }
}
