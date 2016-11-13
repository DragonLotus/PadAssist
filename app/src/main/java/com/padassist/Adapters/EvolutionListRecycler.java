package com.padassist.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.Data.BaseMonster;
import com.padassist.Data.Monster;
import com.padassist.Fragments.MonsterPageFragment;
import com.padassist.Fragments.MonsterTabLayoutFragment;
import com.padassist.Graphics.TextStroke;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class EvolutionListRecycler extends RecyclerView.Adapter<EvolutionListRecycler.ViewHolder> {
    private ArrayList<BaseMonster> monsterList;
    private Realm realm;
    private ArrayList<BaseMonster> evolutions;
    private ArrayList<Long> evolutionsId;
    private Context mContext;
    private LayoutInflater inflater;
    private EvolutionListSimpleRecycler evolutionListSimpleRecycler;
    private long monsterId;
    private View.OnClickListener scrollToEvolutionOnClickListener;

    public EvolutionListRecycler(Context context, long monsterId, ArrayList<BaseMonster> monsterList,
                                 @Nullable View.OnClickListener scrollToEvolutionOnClickListener, Realm realm) {
        mContext = context;
        this.monsterList = monsterList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.realm = realm;
        evolutions = new ArrayList<>();
        evolutionsId = new ArrayList<>();
        this.monsterId = monsterId;
        this.scrollToEvolutionOnClickListener = scrollToEvolutionOnClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        evolutions.clear();
        evolutionsId.clear();
        getAllEvolutions(position);
        if (evolutions.size() != 0) {
            evolutionListSimpleRecycler = new EvolutionListSimpleRecycler(mContext, evolutions, scrollToEvolutionOnClickListener);
            if (evolutions.size() < 6) {
                viewHolder.evolutionRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(evolutions.size(), StaggeredGridLayoutManager.VERTICAL));
            } else {
                viewHolder.evolutionRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL));
            }
            viewHolder.evolutionRecyclerView.setAdapter(evolutionListSimpleRecycler);
        }

        viewHolder.monsterPicture.setTag(R.string.index, monsterList.get(position).getMonsterId());

        viewHolder.monsterPicture.setImageBitmap(monsterList.get(position).getMonsterPicture());
        viewHolder.monsterIdStroke.setText("" + monsterList.get(position).getMonsterId());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.evolution_monster_list_grid, parent, false));
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
        RecyclerView evolutionRecyclerView;

        public ViewHolder(View convertView) {
            super(convertView);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            monsterIdStroke = (TextStroke) convertView.findViewById(R.id.monsterIdStroke);
            evolutionRecyclerView = (RecyclerView) convertView.findViewById(R.id.evolutionRecyclerView);
        }
    }

    private void getAllEvolutions(int position) {
        BaseMonster monster;
        for (int i = 0; i < monsterList.get(position).getEvolutions().size(); i++) {
            if (monsterList.get(position).getEvolutions().get(i).getValue() != monsterId) {
                evolutionsId.add(monsterList.get(position).getEvolutions().get(i).getValue());
            }
        }
        for (int i = 0; i < evolutionsId.size(); i++) {
            monster = realm.where(BaseMonster.class).equalTo("monsterId", evolutionsId.get(i)).findFirst();
            for (int j = 0; j < monster.getEvolutions().size(); j++) {
                if (!evolutionsId.contains(monster.getEvolutions().get(j).getValue())
                        && monster.getEvolutions().get(j).getValue() != monsterList.get(position).getMonsterId()
                        && monster.getEvolutions().get(j).getValue() != monsterId) {
                    evolutionsId.add(monster.getEvolutions().get(j).getValue());
                }
            }
        }
        for (int i = 0; i < evolutionsId.size(); i++) {
            monster = realm.where(BaseMonster.class).equalTo("monsterId", evolutionsId.get(i)).findFirst();
            evolutions.add(monster);
        }
    }
}
