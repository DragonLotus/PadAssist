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
import com.padassist.Util.ImageResourceUtil;

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
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.teamBadge.setImageResource(ImageResourceUtil.teamBadge(position));

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
