package com.padassist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Data.BaseMonster;
import com.padassist.Data.Enemy;
import com.padassist.Data.RealmElement;
import com.padassist.Data.RealmInt;
import com.padassist.Graphics.TextStroke;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class MonsterPortraitListRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int GRID = 0, LINEAR = 1;
    private ArrayList<BaseMonster> monsterList;
    private Context mContext;
    private LayoutInflater inflater;
    private Toast toast;
    private RecyclerView monsterListView;
    private Realm realm;
    private boolean isGrid;
    private Enemy enemy;

    public MonsterPortraitListRecycler(Context context, ArrayList<BaseMonster> monsterList, RecyclerView monsterListView,
                                       boolean isGrid, Realm realm, Enemy enemy) {
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListView = monsterListView;
        this.isGrid = isGrid;
        this.realm = realm;
        this.enemy = enemy;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface ClearTextFocus {
        public void doThis();
    }

    private View.OnClickListener enemyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag(R.string.index);
            enemy.setMonsterIdPicture(monsterList.get(position).getMonsterId());
            enemy.getTargetElement().set(0, new RealmElement(monsterList.get(position).getElement1Int()));
            if (monsterList.get(position).getElement2Int() == -1) {
                enemy.getTargetElement().set(1, new RealmElement(monsterList.get(position).getElement1Int()));
            } else {
                enemy.getTargetElement().set(1, new RealmElement(monsterList.get(position).getElement2Int()));
            }
            enemy.getTypes().set(0, new RealmInt(monsterList.get(position).getType1()));
            enemy.getTypes().set(1, new RealmInt(monsterList.get(position).getType2()));
            enemy.getTypes().set(2, new RealmInt(monsterList.get(position).getType3()));
            enemy.setEnemyName(monsterList.get(position).getName());
            ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
        }
    };

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isGrid) {
            setGridLayout(viewHolder, position);
        } else {
            setLinearLayout(viewHolder, position);
        }

    }

    private void setLinearLayout(RecyclerView.ViewHolder viewHolder, int position) {
        MonsterPortraitListRecycler.ViewHolderLinear viewHolderLinear = (MonsterPortraitListRecycler.ViewHolderLinear) viewHolder;

        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        } else {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }

        viewHolderLinear.itemView.setTag(R.string.index, position);

        viewHolderLinear.monsterName.setText(monsterList.get(position).getName());
        viewHolderLinear.monsterPicture.setImageBitmap(ImageResourceUtil.getMonsterPicture(monsterList.get(position).getMonsterId()));
        viewHolderLinear.monsterId.setText(monsterList.get(position).getMonsterIdString());
    }

    private void setGridLayout(RecyclerView.ViewHolder viewHolder, int position) {
        MonsterPortraitListRecycler.ViewHolderGrid viewHolderGrid = (MonsterPortraitListRecycler.ViewHolderGrid) viewHolder;
        viewHolderGrid.itemView.setTag(R.string.index, position);
        viewHolderGrid.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        viewHolderGrid.monsterIdStroke.setText(monsterList.get(position).getMonsterIdString());
        viewHolderGrid.monsterPicture.setImageBitmap(ImageResourceUtil.getMonsterPicture(monsterList.get(position).getMonsterId()));
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case GRID:
                MonsterPortraitListRecycler.ViewHolderGrid viewHolderGrid = new MonsterPortraitListRecycler.ViewHolderGrid(inflater.inflate(R.layout.base_monster_list_grid, parent, false));

                viewHolderGrid.itemView.setTag(viewHolderGrid);
                viewHolderGrid.itemView.setOnClickListener(enemyOnClickListener);
                return viewHolderGrid;
            default:
                MonsterPortraitListRecycler.ViewHolderLinear viewHolderLinear = new MonsterPortraitListRecycler.ViewHolderLinear(inflater.inflate(R.layout.monster_portrait_list_row, parent, false));

                viewHolderLinear.monsterName.setHorizontallyScrolling(true);
                viewHolderLinear.monsterName.setSelected(true);

                viewHolderLinear.itemView.setTag(viewHolderLinear);

                viewHolderLinear.itemView.setOnClickListener(enemyOnClickListener);
                return viewHolderLinear;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isGrid) {
            return GRID;
        } else {
            return LINEAR;
        }
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolderLinear extends RecyclerView.ViewHolder {
        TextView monsterName, monsterId;
        ImageView monsterPicture;

        public ViewHolderLinear(View convertView) {
            super(convertView);
            monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            monsterId = (TextView) convertView.findViewById(R.id.monsterId);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
        }
    }

    static class ViewHolderGrid extends RecyclerView.ViewHolder {
        ImageView monsterPicture;
        TextStroke monsterIdStroke;

        public ViewHolderGrid(View convertView) {
            super(convertView);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            monsterIdStroke = (TextStroke) convertView.findViewById(R.id.monsterIdStroke);
        }
    }

    public void notifyDataSetChanged(ArrayList<BaseMonster> monsterList) {
        this.monsterList = monsterList;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(boolean isGrid) {
        this.isGrid = isGrid;
        notifyDataSetChanged();
    }

    public ArrayList<BaseMonster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(ArrayList<BaseMonster> monsterList) {
        this.monsterList = monsterList;
    }

    public BaseMonster getItem(int position) {
        return monsterList.get(position);
    }

}
