package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Graphics.ExpandableHeightGridView;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

public class MonsterSpecificAwakeningsListAdapter extends ArrayAdapter<Monster> {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Monster> monsterList;
    private ArrayList<Integer> awakeningList, awakeningListAll, monsterSpecificFilter;
    private AwakeningGridAdapter monsterAwakeningsGridAdapter;
    private int resourceId;

    public MonsterSpecificAwakeningsListAdapter(Context context, int textViewResourceId, ArrayList<Monster> monsterList) {
        super(context, textViewResourceId, monsterList);
        mContext = context;
        this.monsterList = monsterList;
        this.resourceId = textViewResourceId;
        awakeningList = new ArrayList<>();
        awakeningListAll = new ArrayList<>();
        monsterSpecificFilter = new ArrayList<>();

        setupAwakeningFilters();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            viewHolder.monsterAwakenings = (ExpandableHeightGridView) convertView.findViewById(R.id.monsterAwakenings);
            viewHolder.monsterLatentAwakenings = (ExpandableHeightGridView) convertView.findViewById(R.id.monsterLatentAwakenings);
            trimAwakenings(position);
            monsterAwakeningsGridAdapter = new AwakeningGridAdapter(mContext, awakeningList);
            viewHolder.monsterAwakenings.setAdapter(monsterAwakeningsGridAdapter);
            viewHolder.monsterAwakenings.setExpanded(true);
            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
//        for(int i = 0; i < monsterList.get(position).getCurrentAwakenings(); i++){
//            awakeningListAll.add(monsterList.get(position).getAwokenSkills(i));
//        }
//        trimAwakenings(position);
//        monsterAwakeningsGridAdapter.notifyDataSetChanged();
        return convertView;
    }

    static class ViewHolder {
        ImageView monsterPicture;
        ExpandableHeightGridView monsterAwakenings, monsterLatentAwakenings;

    }

    private void setupAwakeningFilters() {
        monsterSpecificFilter.add(1);
        monsterSpecificFilter.add(2);
        monsterSpecificFilter.add(3);
        monsterSpecificFilter.add(10);
        monsterSpecificFilter.add(27);
        monsterSpecificFilter.add(30);
        monsterSpecificFilter.add(31);
        monsterSpecificFilter.add(32);
        monsterSpecificFilter.add(33);
        monsterSpecificFilter.add(34);
        monsterSpecificFilter.add(35);
        monsterSpecificFilter.add(36);
        monsterSpecificFilter.add(37);
    }

    private void trimAwakenings(int position) {
        if (awakeningList.size() != 0) {
            awakeningList.clear();
        }
        if (awakeningListAll.size() != 0) {
            awakeningListAll.clear();
        }
        for (int i = 0; i < monsterList.get(position).getCurrentAwakenings(); i++) {
            awakeningListAll.add(monsterList.get(position).getAwokenSkills(i));
        }
        Log.d("Monster Specific", "AwakeningListAll is: " + awakeningListAll);
        for (int i = 0; i < awakeningListAll.size(); i++) {
            if (monsterSpecificFilter.contains(awakeningListAll.get(i))) {
                awakeningList.add(awakeningListAll.get(i));
            }
        }
        Log.d("Monster Specific", "AwakeningList is: " + awakeningListAll);
    }
}
