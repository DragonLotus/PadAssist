package com.padassist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.padassist.Data.BaseMonster;
import com.padassist.Data.Monster;
import com.padassist.Graphics.TextStroke;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class MonsterGridPortraitRecycler extends RecyclerView.Adapter<MonsterGridPortraitRecycler.ViewHolder> {
    private RealmResults<BaseMonster> monsterList;
    private Context mContext;
    private LayoutInflater inflater;
    private Realm realm = Realm.getDefaultInstance();

    public MonsterGridPortraitRecycler(Context context, RealmResults<BaseMonster> monsterList) {
        mContext = context;
        this.monsterList = monsterList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolder.monsterPicture.setImageBitmap(monsterList.get(position).getMonsterPicture());
        viewHolder.monsterId.setText(monsterList.get(position).getMonsterIdString());
        viewHolder.itemView.setTag(viewHolder);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.base_monster_list_grid, parent, false));
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextStroke monsterId;
        ImageView monsterPicture;

        public ViewHolder(View convertView) {
            super(convertView);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            monsterId = (TextStroke) convertView.findViewById(R.id.monsterIdStroke);
        }
    }

    public void updateList(RealmResults<BaseMonster> monsterList) {
        this.monsterList = monsterList;
        notifyDataSetChanged();
    }
}
