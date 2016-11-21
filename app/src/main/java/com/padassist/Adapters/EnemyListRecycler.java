package com.padassist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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

import com.padassist.Data.Enemy;
import com.padassist.Graphics.TextStroke;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class EnemyListRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int GRID = 0, LINEAR = 1;
    private ArrayList<Enemy> monsterList;
    private Context mContext;
    private LayoutInflater inflater;
    private Toast toast;
    private int expandedPosition = -1;
    private RecyclerView monsterListView;
    private Realm realm;
    private boolean isGrid;
    private int fortyEightDp;
    private int eightDp;
    private int fiftyFourDp;
    private ClearTextFocus clearTextFocus;
    private Enemy enemy;

    public EnemyListRecycler(Context context, ArrayList<Enemy> monsterList, RecyclerView monsterListView,
                             boolean isGrid, Realm realm, ClearTextFocus clearTextFocus, Enemy enemy) {
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListView = monsterListView;
        this.isGrid = isGrid;
        this.clearTextFocus = clearTextFocus;
        this.realm = realm;
        this.enemy = enemy;

        fortyEightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, mContext.getResources().getDisplayMetrics());
        eightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mContext.getResources().getDisplayMetrics());
        fiftyFourDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 54, mContext.getResources().getDisplayMetrics());
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface ClearTextFocus {
        void doThis();

        void checkEmpty();
    }

    private View.OnClickListener expandOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearTextFocus.doThis();
            int previous;
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            if (holder.getAdapterPosition() != expandedPosition) {
                if (expandedPosition >= 0) {
                    previous = expandedPosition;
                    notifyItemChanged(previous);
                }
                expandedPosition = holder.getAdapterPosition();
                notifyItemChanged(expandedPosition);
                monsterListView.smoothScrollToPosition(expandedPosition);
            } else {
                previous = expandedPosition;
                expandedPosition = -1;
                notifyItemChanged(previous);
            }
        }
    };

    private View.OnClickListener enemyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag(R.string.index);
            enemy.setEnemy(realm.copyFromRealm(monsterList.get(position)));
            ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
        }
    };

    private View.OnClickListener enemyDeleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag(R.string.index);
            realm.beginTransaction();
            realm.where(Enemy.class).equalTo("enemyId", monsterList.get(position).getEnemyId()).findFirst().deleteFromRealm();
            realm.commitTransaction();
            monsterList.clear();
            monsterList.addAll(realm.where(Enemy.class).greaterThan("enemyId",0).findAll());
            notifyItemRemoved(position);
            notifyDataSetChanged(monsterList);
            expandedPosition = -1;
            clearTextFocus.checkEmpty();
        }
    };

    private View.OnLongClickListener enemyOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            int position = (int) view.getTag(R.string.index);
            enemy.setEnemy(realm.copyFromRealm(monsterList.get(position)));
            ((MainActivity) mContext).getSupportFragmentManager().popBackStack();
            return true;
        }
    };

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isGrid && position != expandedPosition) {
            setGridLayout(viewHolder, position);
        } else {
            setLinearLayout(viewHolder, position);
        }

    }

    private void setLinearLayout(RecyclerView.ViewHolder viewHolder, int position) {
        EnemyListRecycler.ViewHolderLinear viewHolderLinear = (EnemyListRecycler.ViewHolderLinear) viewHolder;

        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        } else {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }

        viewHolderLinear.enemyName.setText(monsterList.get(position).getEnemyName());
        viewHolderLinear.monsterPicture.setImageBitmap(ImageResourceUtil.getMonsterPicture(monsterList.get(position).getMonsterIdPicture()));
        viewHolderLinear.enemyHP.setText("HP: " + Long.toString(monsterList.get(position).getTargetHp()) + " ");
        viewHolderLinear.enemyDef.setText("DEF: " + Integer.toString(monsterList.get(position).getBeforeDefenseBreak()));

        viewHolderLinear.element1.setImageResource(ImageResourceUtil.orbColor(monsterList.get(position).getTargetElement().get(0).getElement()));

        if (monsterList.get(position).getTargetElement().get(0).getValue() == monsterList.get(position).getTargetElement().get(1).getValue()) {
            viewHolderLinear.element2.setVisibility(View.GONE);
        } else {
            viewHolderLinear.element2.setImageResource(ImageResourceUtil.orbColor(monsterList.get(position).getTargetElement().get(1).getElement()));
        }

        if (monsterList.get(position).isHasAbsorb()) {
            viewHolderLinear.spacer2.setVisibility(View.VISIBLE);
            viewHolderLinear.elementAbsorb.setVisibility(View.VISIBLE);
            viewHolderLinear.absorbHolderExpand.setVisibility(View.VISIBLE);
            for (int i = 0; i < monsterList.get(position).getAbsorb().size(); i++) {
                viewHolderLinear.absorbElementHolder.getChildAt(monsterList.get(position).getAbsorb().get(i).getValue()).setVisibility(View.VISIBLE);
            }
        } else {
            viewHolderLinear.spacer2.setVisibility(View.GONE);
            viewHolderLinear.elementAbsorb.setVisibility(View.GONE);
            viewHolderLinear.absorbHolderExpand.setVisibility(View.GONE);
            for (int i = 0; i < viewHolderLinear.absorbElementHolder.getChildCount(); i++) {
                viewHolderLinear.absorbElementHolder.getChildAt(i).setVisibility(View.GONE);
            }
        }

        if (monsterList.get(position).isHasReduction()) {
            viewHolderLinear.elementReduction.setVisibility(View.VISIBLE);
            viewHolderLinear.spacer3.setVisibility(View.VISIBLE);
            viewHolderLinear.reductionHolderExpand.setVisibility(View.VISIBLE);
            viewHolderLinear.reductionValue.setText(monsterList.get(position).getReductionValue() + "% ");
            for (int i = 0; i < monsterList.get(position).getReduction().size(); i++) {
                viewHolderLinear.reductionElementHolder.getChildAt(monsterList.get(position).getReduction().get(i).getValue()).setVisibility(View.VISIBLE);
            }
        } else {
            viewHolderLinear.spacer3.setVisibility(View.GONE);
            viewHolderLinear.elementReduction.setVisibility(View.GONE);
            viewHolderLinear.reductionHolderExpand.setVisibility(View.GONE);
            for (int i = 0; i < viewHolderLinear.reductionElementHolder.getChildCount(); i++) {
                viewHolderLinear.reductionElementHolder.getChildAt(i).setVisibility(View.GONE);
            }
        }

        if (monsterList.get(position).isHasDamageThreshold()) {
            viewHolderLinear.spacer4.setVisibility(View.VISIBLE);
            viewHolderLinear.damageThreshold.setVisibility(View.VISIBLE);
            viewHolderLinear.damageThresholdHolderExpand.setVisibility(View.VISIBLE);
            viewHolderLinear.damageImmunity.setVisibility(View.GONE);
            viewHolderLinear.damageImmunityHolderExpand.setVisibility(View.GONE);
            viewHolderLinear.damageThresholdValue.setText("" + monsterList.get(position).getDamageThreshold());
        } else if (monsterList.get(position).hasDamageImmunity()) {
            viewHolderLinear.spacer4.setVisibility(View.VISIBLE);
            viewHolderLinear.damageThreshold.setVisibility(View.GONE);
            viewHolderLinear.damageImmunity.setVisibility(View.VISIBLE);
            viewHolderLinear.damageThresholdHolderExpand.setVisibility(View.GONE);
            viewHolderLinear.damageImmunityHolderExpand.setVisibility(View.VISIBLE);
            viewHolderLinear.damageImmunityValue.setText("" + monsterList.get(position).getDamageImmunity());
        } else {
            viewHolderLinear.damageThreshold.setVisibility(View.GONE);
            viewHolderLinear.damageImmunity.setVisibility(View.GONE);
            viewHolderLinear.spacer4.setVisibility(View.GONE);
            viewHolderLinear.damageThresholdHolderExpand.setVisibility(View.GONE);
            viewHolderLinear.damageImmunityHolderExpand.setVisibility(View.GONE);
        }

        if (monsterList.get(position).getTypes().get(0).getValue() >= 0) {
            viewHolderLinear.type1.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getTypes().get(0).getValue()));
        } else {
            viewHolderLinear.type1.setVisibility(View.GONE);
        }

        if (monsterList.get(position).getTypes().get(1).getValue() >= 0) {
            viewHolderLinear.type2.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getTypes().get(1).getValue()));
        } else {
            viewHolderLinear.type2.setVisibility(View.GONE);
        }

        if (monsterList.get(position).getTypes().get(2).getValue() >= 0) {
            viewHolderLinear.type3.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getTypes().get(2).getValue()));
        } else {
            viewHolderLinear.type3.setVisibility(View.GONE);
        }

        viewHolderLinear.itemView.setTag(R.string.index, position);
        viewHolderLinear.choose.setTag(R.string.index, position);
        viewHolderLinear.delete.setTag(R.string.index, position);

        if (position == expandedPosition) {
            viewHolderLinear.expandLayout.setVisibility(View.VISIBLE);
            viewHolderLinear.enemyAttributeHolder.setVisibility(View.GONE);

            //Set expand layout stuff here

            if (isGrid) {
                ((StaggeredGridLayoutManager.LayoutParams) viewHolderLinear.itemView.getLayoutParams()).setFullSpan(true);
                viewHolderLinear.monsterPicture.getLayoutParams().height = fortyEightDp;
                viewHolderLinear.monsterPicture.getLayoutParams().width = fortyEightDp;
                viewHolderLinear.monsterPicture.requestLayout();
                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
            }
        } else {
            viewHolderLinear.expandLayout.setVisibility(View.GONE);
            if (!isGrid) {
                viewHolderLinear.enemyAttributeHolder.setVisibility(View.VISIBLE);
            } else {
                ((StaggeredGridLayoutManager.LayoutParams) viewHolderLinear.itemView.getLayoutParams()).setFullSpan(false);
                viewHolderLinear.monsterPicture.getLayoutParams().height = fiftyFourDp;
                viewHolderLinear.monsterPicture.getLayoutParams().width = fiftyFourDp;
                viewHolderLinear.monsterPicture.requestLayout();
            }
        }
    }

    private void setGridLayout(RecyclerView.ViewHolder viewHolder, int position) {
        EnemyListRecycler.ViewHolderGrid viewHolderGrid = (EnemyListRecycler.ViewHolderGrid) viewHolder;
        viewHolderGrid.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
//        viewHolderGrid.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolderGrid.monsterPicture.setImageBitmap(ImageResourceUtil.getMonsterPicture(monsterList.get(position).getMonsterIdPicture()));
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case GRID:
                EnemyListRecycler.ViewHolderGrid viewHolderGrid = new EnemyListRecycler.ViewHolderGrid(inflater.inflate(R.layout.base_monster_list_grid, parent, false));
                viewHolderGrid.itemView.setOnClickListener(expandOnItemClickListener);
                viewHolderGrid.itemView.setTag(viewHolderGrid);
                viewHolderGrid.itemView.setOnLongClickListener(enemyOnLongClickListener);
                viewHolderGrid.monsterIdStroke.setVisibility(View.GONE);
                return viewHolderGrid;
            default:
                EnemyListRecycler.ViewHolderLinear viewHolderLinear = new EnemyListRecycler.ViewHolderLinear(inflater.inflate(R.layout.enemy_list_row, parent, false));

                viewHolderLinear.enemyName.setHorizontallyScrolling(true);
                viewHolderLinear.enemyName.setSelected(true);

                viewHolderLinear.itemView.setOnClickListener(expandOnItemClickListener);
                viewHolderLinear.itemView.setTag(viewHolderLinear);

                viewHolderLinear.choose.setOnClickListener(enemyOnClickListener);

                viewHolderLinear.itemView.setOnLongClickListener(enemyOnLongClickListener);
                viewHolderLinear.delete.setOnClickListener(enemyDeleteOnClickListener);
                return viewHolderLinear;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isGrid && position != expandedPosition) {
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
        TextView enemyName, enemyHP, enemyDef, spacer1, spacer2, spacer3, spacer4, damageThresholdValue,
                damageImmunityValue, reductionValue;
        ImageView monsterPicture, type1, type2, type3, element1, element2, elementAbsorb, elementReduction,
                damageThreshold, damageImmunity;
        RelativeLayout expandLayout, enemyAttributeRelativeLayout, relativeLayout, absorbHolderExpand,
                reductionHolderExpand, damageThresholdHolderExpand, damageImmunityHolderExpand;
        LinearLayout typeHolder, enemyElementHolder, enemyAttributeHolder, buttonLinearLayout,
                absorbElementHolder, reductionElementHolder;
        Button choose, delete;

        public ViewHolderLinear(View convertView) {
            super(convertView);
            enemyName = (TextView) convertView.findViewById(R.id.enemyName);
            enemyHP = (TextView) convertView.findViewById(R.id.enemyHP);
            enemyDef = (TextView) convertView.findViewById(R.id.enemyDef);
            spacer1 = (TextView) convertView.findViewById(R.id.spacer1);
            spacer2 = (TextView) convertView.findViewById(R.id.spacer2);
            spacer3 = (TextView) convertView.findViewById(R.id.spacer3);
            spacer4 = (TextView) convertView.findViewById(R.id.spacer4);
            damageThresholdValue = (TextView) convertView.findViewById(R.id.damageThresholdValue);
            damageImmunityValue = (TextView) convertView.findViewById(R.id.damageImmunityValue);
            reductionValue = (TextView) convertView.findViewById(R.id.reductionValue);
            type1 = (ImageView) convertView.findViewById(R.id.type1);
            type2 = (ImageView) convertView.findViewById(R.id.type2);
            type3 = (ImageView) convertView.findViewById(R.id.type3);
            elementAbsorb = (ImageView) convertView.findViewById(R.id.elementAbsorb);
            elementReduction = (ImageView) convertView.findViewById(R.id.elementReduction);
            damageThreshold = (ImageView) convertView.findViewById(R.id.damageThreshold);
            damageImmunity = (ImageView) convertView.findViewById(R.id.damageImmunity);
            element1 = (ImageView) convertView.findViewById(R.id.element1);
            element2 = (ImageView) convertView.findViewById(R.id.element2);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            choose = (Button) convertView.findViewById(R.id.choose);
            delete = (Button) convertView.findViewById(R.id.delete);
            expandLayout = (RelativeLayout) convertView.findViewById(R.id.expandLayout);
            relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            enemyAttributeRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.enemyAttributeRelativeLayout);
            absorbHolderExpand = (RelativeLayout) convertView.findViewById(R.id.absorbHolderExpand);
            reductionHolderExpand = (RelativeLayout) convertView.findViewById(R.id.reductionHolderExpand);
            damageThresholdHolderExpand = (RelativeLayout) convertView.findViewById(R.id.damageThresholdHolderExpand);
            damageImmunityHolderExpand = (RelativeLayout) convertView.findViewById(R.id.damageImmunityHolderExpand);
            typeHolder = (LinearLayout) convertView.findViewById(R.id.typeHolder);
            enemyElementHolder = (LinearLayout) convertView.findViewById(R.id.enemyElementHolder);
            enemyAttributeHolder = (LinearLayout) convertView.findViewById(R.id.enemyAttributeHolder);
            absorbElementHolder = (LinearLayout) convertView.findViewById(R.id.absorbElementHolder);
            reductionElementHolder = (LinearLayout) convertView.findViewById(R.id.reductionElementHolder);
            buttonLinearLayout = (LinearLayout) convertView.findViewById(R.id.buttonLinearLayout);
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

    public void notifyDataSetChanged(ArrayList<Enemy> monsterList) {
        this.monsterList = monsterList;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(boolean isGrid) {
        this.isGrid = isGrid;
        notifyDataSetChanged();
    }

    public int getExpandedPosition() {
        return expandedPosition;
    }

    public ArrayList<Enemy> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(ArrayList<Enemy> monsterList) {
        this.monsterList = monsterList;
    }

    public Enemy getItem(int position) {
        return monsterList.get(position);
    }

    public boolean expanded() {
        if (expandedPosition != -1) {
            return true;
        } else return false;
    }

    public void setExpandedPosition(int expandedPosition) {
        int previous = this.expandedPosition;
        this.expandedPosition = expandedPosition;
        notifyItemChanged(previous);
        if (expandedPosition >= 0) {
            notifyItemChanged(expandedPosition);
        }
    }

}
