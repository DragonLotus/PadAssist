package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 8/20/2015.
 */
public class TeamListAdapter extends ArrayAdapter<Team> {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Team> teamList;
    private int resourceId;

    public TeamListAdapter(Context context, int textViewResourceId, ArrayList<Team> teamList) {
        super(context, textViewResourceId, teamList);
        mContext = context;
        this.teamList = teamList;
        this.resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.teamName = (TextView) convertView.findViewById(R.id.teamName);
            viewHolder.monster1Plus = (TextView) convertView.findViewById(R.id.monster1Plus);
            viewHolder.monster1Awakenings = (TextView) convertView.findViewById(R.id.monster1Awakenings);
            viewHolder.monster2Plus = (TextView) convertView.findViewById(R.id.monster2Plus);
            viewHolder.monster2Awakenings = (TextView) convertView.findViewById(R.id.monster2Awakenings);
            viewHolder.monster3Plus = (TextView) convertView.findViewById(R.id.monster3Plus);
            viewHolder.monster3Awakenings = (TextView) convertView.findViewById(R.id.monster3Awakenings);
            viewHolder.monster4Plus = (TextView) convertView.findViewById(R.id.monster4Plus);
            viewHolder.monster4Awakenings = (TextView) convertView.findViewById(R.id.monster4Awakenings);
            viewHolder.monster5Plus = (TextView) convertView.findViewById(R.id.monster5Plus);
            viewHolder.monster5Awakenings = (TextView) convertView.findViewById(R.id.monster5Awakenings);
            viewHolder.monster6Plus = (TextView) convertView.findViewById(R.id.monster6Plus);
            viewHolder.monster6Awakenings = (TextView) convertView.findViewById(R.id.monster6Awakenings);
            viewHolder.monster1Picture = (ImageView) convertView.findViewById(R.id.monster1Picture);
            viewHolder.monster2Picture = (ImageView) convertView.findViewById(R.id.monster2Picture);
            viewHolder.monster3Picture = (ImageView) convertView.findViewById(R.id.monster3Picture);
            viewHolder.monster4Picture = (ImageView) convertView.findViewById(R.id.monster4Picture);
            viewHolder.monster5Picture = (ImageView) convertView.findViewById(R.id.monster5Picture);
            viewHolder.monster6Picture = (ImageView) convertView.findViewById(R.id.monster6Picture);

            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.teamName.setText(teamList.get(position).getTeamName());
        Log.d("Team Name", "" + teamList.get(position).getTeamName());
        Log.d("Team List Size", "" + teamList.size() + " " + teamList.get(position).getMonsters());
        viewHolder.monster1Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(0).getTotalPlus()) + " ");
        viewHolder.monster1Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(0).getCurrentAwakenings()));
        viewHolder.monster1Picture.setImageResource(teamList.get(position).getMonsters(0).getMonsterPicture());
        viewHolder.monster2Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(1).getTotalPlus()) + " ");
        viewHolder.monster2Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(1).getCurrentAwakenings()));
        viewHolder.monster2Picture.setImageResource(teamList.get(position).getMonsters(1).getMonsterPicture());
        viewHolder.monster3Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(2).getTotalPlus()) + " ");
        viewHolder.monster3Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(2).getCurrentAwakenings()));
        viewHolder.monster3Picture.setImageResource(teamList.get(position).getMonsters(2).getMonsterPicture());
        viewHolder.monster4Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(3).getTotalPlus()) + " ");
        viewHolder.monster4Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(3).getCurrentAwakenings()));
        viewHolder.monster4Picture.setImageResource(teamList.get(position).getMonsters(3).getMonsterPicture());
        Log.d("Monster getview", "Monster getView: " + teamList.get(position).getMonsters(4) + " " + teamList.get(position));
        viewHolder.monster5Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(4).getTotalPlus()) + " ");
        viewHolder.monster5Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(4).getCurrentAwakenings()));
        viewHolder.monster5Picture.setImageResource(teamList.get(position).getMonsters(4).getMonsterPicture());
        viewHolder.monster6Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(5).getTotalPlus()) + " ");
        viewHolder.monster6Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(5).getCurrentAwakenings()));
        viewHolder.monster6Picture.setImageResource(teamList.get(position).getMonsters(5).getMonsterPicture());

        if (teamList.get(position).getMonsters(0).getCurrentAwakenings() == teamList.get(position).getMonsters(0).getMaxAwakenings()) {
            viewHolder.monster1Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster1Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(0).getTotalPlus() == 0) {
            viewHolder.monster1Plus.setVisibility(View.INVISIBLE);
        }
        if (teamList.get(position).getMonsters(0).getCurrentAwakenings() == 0) {
            viewHolder.monster1Awakenings.setVisibility(View.INVISIBLE);
        }

        if (teamList.get(position).getMonsters(1).getCurrentAwakenings() == teamList.get(position).getMonsters(1).getMaxAwakenings()) {
            viewHolder.monster2Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster2Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(1).getTotalPlus() == 0) {
            viewHolder.monster2Plus.setVisibility(View.INVISIBLE);
        }
        if (teamList.get(position).getMonsters(1).getCurrentAwakenings() == 0) {
            viewHolder.monster2Awakenings.setVisibility(View.INVISIBLE);
        }

        if (teamList.get(position).getMonsters(2).getCurrentAwakenings() == teamList.get(position).getMonsters(2).getMaxAwakenings()) {
            viewHolder.monster3Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster3Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(2).getTotalPlus() == 0) {
            viewHolder.monster3Plus.setVisibility(View.INVISIBLE);
        }
        if (teamList.get(position).getMonsters(2).getCurrentAwakenings() == 0) {
            viewHolder.monster3Awakenings.setVisibility(View.INVISIBLE);
        }

        if (teamList.get(position).getMonsters(3).getCurrentAwakenings() == teamList.get(position).getMonsters(3).getMaxAwakenings()) {
            viewHolder.monster4Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster4Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(3).getTotalPlus() == 0) {
            viewHolder.monster4Plus.setVisibility(View.INVISIBLE);
        }
        if (teamList.get(position).getMonsters(3).getCurrentAwakenings() == 0) {
            viewHolder.monster4Awakenings.setVisibility(View.INVISIBLE);
        }

        if (teamList.get(position).getMonsters(4).getCurrentAwakenings() == teamList.get(position).getMonsters(4).getMaxAwakenings()) {
            viewHolder.monster5Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster5Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(4).getTotalPlus() == 0) {
            viewHolder.monster5Plus.setVisibility(View.INVISIBLE);
        }
        if (teamList.get(position).getMonsters(4).getCurrentAwakenings() == 0) {
            viewHolder.monster5Awakenings.setVisibility(View.INVISIBLE);
        }

        if (teamList.get(position).getMonsters(5).getCurrentAwakenings() == teamList.get(position).getMonsters(5).getMaxAwakenings()) {
            viewHolder.monster6Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster6Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(5).getTotalPlus() == 0) {
            viewHolder.monster6Plus.setVisibility(View.INVISIBLE);
        }
        if (teamList.get(position).getMonsters(5).getCurrentAwakenings() == 0) {
            viewHolder.monster6Awakenings.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }


    static class ViewHolder {
        TextView teamName, monster1Plus, monster1Awakenings, monster2Plus, monster2Awakenings, monster3Plus, monster3Awakenings, monster4Plus, monster4Awakenings, monster5Plus, monster5Awakenings, monster6Plus, monster6Awakenings;
        ImageView monster1Picture, monster2Picture, monster3Picture, monster4Picture, monster5Picture, monster6Picture;

    }
}
