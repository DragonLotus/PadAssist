package com.padassist.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.padassist.Data.Monster;
import com.padassist.Graphics.ExpandableHeightGridView;
import com.padassist.R;

import java.util.ArrayList;

public class MonsterSpecificAwakeningsListAdapter extends ArrayAdapter<Monster> {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Monster> monsterList;
    private ArrayList<Integer> awakeningList, awakeningListAll, monsterSpecificFilter, latentList, latentListAll, latentMonsterSpecificFilter;
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
        latentList = new ArrayList<>();
        latentListAll = new ArrayList<>();
        latentMonsterSpecificFilter = new ArrayList<>();

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
            trimAwakenings(position);
            monsterAwakeningsGridAdapter = new AwakeningGridAdapter(mContext, awakeningList, latentList);
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
        ExpandableHeightGridView monsterAwakenings;

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
        monsterSpecificFilter.add(38);
        latentMonsterSpecificFilter.add(1);
        latentMonsterSpecificFilter.add(2);
        latentMonsterSpecificFilter.add(3);
        latentMonsterSpecificFilter.add(5);
        latentMonsterSpecificFilter.add(11);
    }

    private void trimAwakenings(int position) {
        if (awakeningList.size() != 0) {
            awakeningList.clear();
        }
        if (awakeningListAll.size() != 0) {
            awakeningListAll.clear();
        }
        if (monsterList.get(position).getCurrentAwakenings() < monsterList.get(position).getMaxAwakenings()) {
            for (int i = 0; i < monsterList.get(position).getCurrentAwakenings(); i++) {
                awakeningListAll.add(monsterList.get(position).getAwokenSkills(i));
            }
        } else {
            for (int i = 0; i < monsterList.get(position).getMaxAwakenings(); i++) {
                awakeningListAll.add(monsterList.get(position).getAwokenSkills(i));
            }
        }

        Log.d("Monster Specific", "AwakeningListAll is: " + awakeningListAll);
        for (int i = 0; i < awakeningListAll.size(); i++) {
            if (monsterSpecificFilter.contains(awakeningListAll.get(i))) {
                awakeningList.add(awakeningListAll.get(i));
            }
        }
        Log.d("Monster Specific", "AwakeningList is: " + awakeningListAll);
        Log.d("latentListTag", "1. latentList is: " + latentList + " latentListAll is: " + latentListAll);
        if (latentList.size() != 0) {
            latentList.clear();
        }
        if (latentListAll.size() != 0) {
            latentListAll.clear();
        }

        Log.d("latentListTag", "2. latentList is: " + latentList + " latentListAll is: " + latentListAll);
        if (monsterList.get(position).getLatents().get(0) != 0 || monsterList.get(position).getLatents().get(1) != 0 || monsterList.get(position).getLatents().get(2) != 0 || monsterList.get(position).getLatents().get(3) != 0 || monsterList.get(position).getLatents().get(4) != 0) {

            for (int i = 0; i < monsterList.get(position).getLatents().size(); i++) {
                latentListAll.add(monsterList.get(position).getLatents().get(i));
            }
            for (int i = 0; i < latentListAll.size(); i++) {
                if (latentMonsterSpecificFilter.contains(latentListAll.get(i))) {
                    latentList.add(latentListAll.get(i));
                }
            }
        }
        Log.d("latentListTag", "3. latentList is: " + latentList + " latentListAll is: " + latentListAll + " monster is: " + monsterList.get(position).getName() + " monster latents are: " + monsterList.get(position).getLatents());

    }
}
