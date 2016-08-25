package com.padassist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.padassist.Data.Monster;
import com.padassist.R;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class TeamBadgeGridRecycler extends RecyclerView.Adapter<TeamBadgeGridRecycler.ViewHolder> {
    private ArrayList<Integer> teamBadgeList;
    private Context mContext;
    private LayoutInflater inflater;
    private Realm realm = Realm.getDefaultInstance();
    private View.OnClickListener selectOnClickListener;
    private int selected;

    public TeamBadgeGridRecycler(Context context, ArrayList<Integer> teamBadgeList, int selected, View.OnClickListener selectOnClickListener) {
        mContext = context;
        this.teamBadgeList = teamBadgeList;
        this.selected = selected;
        this.selectOnClickListener = selectOnClickListener;
        Log.d("TeamBadgeGridRecycler", "selected is: " + selected);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        switch(position){
            case 0:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_nothing);
                break;
            case 1:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_cost);
                break;
            case 2:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_time_extend);
                break;
            case 3:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_mass_attack);
                break;
            case 4:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_rcv);
                break;
            case 5:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_hp);
                break;
            case 6:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_attack);
                break;
            case 7:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_skill_boost);
                break;
            case 8:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_bind_resist);
                break;
            case 9:
                viewHolder.teamBadge.setImageResource(R.drawable.team_badge_skill_bind_resist);
                break;
        }

        viewHolder.itemView.setTag(R.string.index, position);
        viewHolder.itemView.setOnClickListener(selectOnClickListener);
        if(selected == position){
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#a537dd"));
        } else {

            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
//        if (position % 2 == 1) {
//            viewHolder.itemView.setBackgroundColor(Color.parseColor("#e8e8e8"));
//        } else {
//            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
//        }
    }

    public void setSelected(int position){
        selected = position;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.team_badge_grid, parent, false));
    }

    @Override
    public int getItemCount() {
        return teamBadgeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView teamBadge;

        public ViewHolder(View convertView) {
            super(convertView);
            teamBadge = (ImageView) convertView.findViewById(R.id.teamBadge);
        }
    }
}
