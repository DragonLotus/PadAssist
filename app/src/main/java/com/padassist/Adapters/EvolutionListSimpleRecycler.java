package com.padassist.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.padassist.Data.BaseMonster;
import com.padassist.Graphics.TextStroke;
import com.padassist.R;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class EvolutionListSimpleRecycler extends RecyclerView.Adapter<EvolutionListSimpleRecycler.ViewHolder> {
    private ArrayList<BaseMonster> monsterList;
    private Context mContext;
    private LayoutInflater inflater;
    private View.OnClickListener scrollToEvolutionOnClickListener;

    public EvolutionListSimpleRecycler(Context context, ArrayList<BaseMonster> monsterList,
                                       @Nullable View.OnClickListener scrollToEvolutionOnClickListener) {
        mContext = context;
        this.monsterList = monsterList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.scrollToEvolutionOnClickListener = scrollToEvolutionOnClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.monsterPicture.setTag(R.string.index, monsterList.get(position).getMonsterId());

        viewHolder.monsterPicture.setImageBitmap(monsterList.get(position).getMonsterPicture());
        viewHolder.monsterIdStroke.setText("" + monsterList.get(position).getMonsterId());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.base_monster_list_small_grid, parent, false));
        viewHolder.monsterPicture.setOnClickListener(scrollToEvolutionOnClickListener);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView monsterPicture;
        TextStroke monsterIdStroke;

        public ViewHolder(View convertView) {
            super(convertView);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            monsterIdStroke = (TextStroke) convertView.findViewById(R.id.monsterIdStroke);
        }
    }
}
