package com.padassist.Util;

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

import com.padassist.Data.ActiveSkill;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.LeaderSkill;
import com.padassist.Graphics.TextStroke;
import com.padassist.R;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public abstract class BaseMonsterListRecyclerUtil extends RecyclerView.Adapter<BaseMonsterListRecyclerUtil.ViewHolder> {

    protected ArrayList<BaseMonster> monsterList;
    protected View.OnClickListener monsterListOnClickListener;
    protected View.OnLongClickListener monsterListOnLongClickListener;
    protected Context mContext;
    protected LayoutInflater inflater;
    private Toast toast;
    private int expandedPosition = -1;
    protected RecyclerView monsterListView;
    protected Realm realm = Realm.getDefaultInstance();
    protected boolean isGrid;
    protected int fortyEightDp;
    protected int eightDp;
    protected int fiftyFourDp;

    public interface ExpandChange {
        public void onExpandChange(int expandedPosition);
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int previous;
            ViewHolder holder = (ViewHolder) v.getTag();
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

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterId.setText("" + monsterList.get(position).getMonsterId());
        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolder.rarity.setText("" + monsterList.get(position).getRarity());
        viewHolder.monsterHP.setText(Integer.toString(monsterList.get(position).getHpMax()) + " / ");
        viewHolder.monsterATK.setText(Integer.toString(monsterList.get(position).getAtkMax()) + " / ");
        viewHolder.monsterRCV.setText(Integer.toString(monsterList.get(position).getRcvMax()));

        if (isGrid) {
            setGridLayout(viewHolder, position);
        } else {
            setLinearLayout(viewHolder, position);
        }

        if(monsterList.get(position).getType1() > 0){
            viewHolder.type1.setImageResource(ImageResourceUtil.monsterTypeImageResource(monsterList.get(position).getType1()));
        } else {
            viewHolder.type1.setVisibility(View.INVISIBLE);
        }

        if(monsterList.get(position).getType2() > 0){
            viewHolder.type2.setImageResource(ImageResourceUtil.monsterTypeImageResource(monsterList.get(position).getType2()));
        } else {
            viewHolder.type2.setVisibility(View.INVISIBLE);
        }

        if(monsterList.get(position).getType3() > 0){
            viewHolder.type3.setImageResource(ImageResourceUtil.monsterTypeImageResource(monsterList.get(position).getType3()));
        } else {
            viewHolder.type3.setVisibility(View.INVISIBLE);
        }
        viewHolder.itemView.setTag(R.string.index, position);
        viewHolder.choose.setTag(R.string.index, position);
        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolder.itemView.setOnClickListener(monsterListOnClickListener);
        } else {
            viewHolder.itemView.setOnClickListener(onItemClickListener);
        }

        if (position == expandedPosition && monsterList.get(position).getMonsterId() != 0) {
            viewHolder.expandLayout.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.GONE);
            viewHolder.monsterATK.setVisibility(View.GONE);
            viewHolder.monsterRCV.setVisibility(View.GONE);
            viewHolder.monsterName.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
            viewHolder.monsterId.setVisibility(View.VISIBLE);
            if (monsterList.get(position).getType1() > -1) {
                viewHolder.type1.setVisibility(View.VISIBLE);
                viewHolder.type2.setVisibility(View.INVISIBLE);
                viewHolder.type3.setVisibility(View.INVISIBLE);
            }
            if (monsterList.get(position).getType2() > -1) {
                viewHolder.type2.setVisibility(View.VISIBLE);
                viewHolder.type3.setVisibility(View.INVISIBLE);
            }
            if (monsterList.get(position).getType3() > -1) {
                viewHolder.type3.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < 9; i++) {
                if (i >= monsterList.get(position).getMaxAwakenings()) {
                    viewHolder.awakeningHolder.getChildAt(i).setVisibility(View.GONE);
                } else {
                    viewHolder.awakeningHolder.getChildAt(i).setVisibility(View.VISIBLE);
                }
            }
            for (int j = 0; j < monsterList.get(position).getMaxAwakenings(); j++) {
                viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(ImageResourceUtil.monsterAwakeningImageResource(monsterList.get(position).getAwokenSkills().get(j).getValue()));
            }

            viewHolder.rarity.setText("" + monsterList.get(position).getRarity());
            viewHolder.hpBase.setText("" + monsterList.get(position).getHpMin());
            viewHolder.atkBase.setText("" + monsterList.get(position).getAtkMin());
            viewHolder.rcvBase.setText("" + monsterList.get(position).getRcvMin());
            viewHolder.hpTotal.setText("" + monsterList.get(position).getHpMax());
            viewHolder.atkTotal.setText("" + monsterList.get(position).getAtkMax());
            viewHolder.rcvTotal.setText("" + monsterList.get(position).getRcvMax());
            if (monsterList.get(position).getActiveSkillString().equals("Blank")) {
                viewHolder.activeSkillName.setText("None");
                viewHolder.activeSkillDesc.setVisibility(View.GONE);
                viewHolder.activeSkillCooldown.setVisibility(View.GONE);
            } else {
                if(monsterList.get(position).getActiveSkill() == null){
                    viewHolder.activeSkillDesc.setText("This monster has an active skill but the author of this app  has not realized that it doesn't exist in his database yet.");
                    viewHolder.activeSkillCooldown.setVisibility(View.GONE);
                } else {
                    viewHolder.activeSkillDesc.setText(monsterList.get(position).getActiveSkill().getDescription());
                    viewHolder.activeSkillCooldown.setText("CD " + monsterList.get(position).getActiveSkill().getMaximumCooldown()+" -> "+monsterList.get(position).getActiveSkill().getMinimumCooldown());
                }
                viewHolder.activeSkillName.setVisibility(View.VISIBLE);
                viewHolder.activeSkillDesc.setVisibility(View.VISIBLE);
                viewHolder.activeSkillName.setText("" + monsterList.get(position).getActiveSkillString() + "");
            }
            if(monsterList.get(position).getLeaderSkillString().equals("Blank")){
                viewHolder.leaderSkillName.setText("None");
                viewHolder.leaderSkillDesc.setVisibility(View.GONE);
            } else{
                if(monsterList.get(position).getLeaderSkill() == null){
                    viewHolder.leaderSkillDesc.setText("This monster has a leader skill but the author of this app  has not realized that it doesn't exist in his database yet.\n(Hint: This leader skill does nothing.)");
                } else {
                    viewHolder.leaderSkillDesc.setText(monsterList.get(position).getLeaderSkill().getDescription());
                }
                viewHolder.leaderSkillName.setVisibility(View.VISIBLE);
                viewHolder.leaderSkillDesc.setVisibility(View.VISIBLE);
                viewHolder.leaderSkillName.setText("" + monsterList.get(position).getLeaderSkillString() + "");
            }
            viewHolder.levelMax.setText("Level " + monsterList.get(position).getMaxLevel());
            if(isGrid){
                viewHolder.monsterIdStroke.setVisibility(View.GONE);
                ((StaggeredGridLayoutManager.LayoutParams)viewHolder.itemView.getLayoutParams()).setFullSpan(true);
                viewHolder.monsterPicture.getLayoutParams().height = fortyEightDp;
                viewHolder.monsterPicture.getLayoutParams().width = fortyEightDp;
                viewHolder.monsterPicture.requestLayout();
            }
        } else {
            viewHolder.expandLayout.setVisibility(View.GONE);
            if (monsterList.get(position).getType1() != -1 && !isGrid) {
                viewHolder.monsterHP.setVisibility(View.VISIBLE);
                viewHolder.monsterATK.setVisibility(View.VISIBLE);
                viewHolder.monsterRCV.setVisibility(View.VISIBLE);
            }
            if(isGrid){
                viewHolder.monsterIdStroke.setVisibility(View.VISIBLE);
                ((StaggeredGridLayoutManager.LayoutParams)viewHolder.itemView.getLayoutParams()).setFullSpan(false);
                viewHolder.monsterPicture.getLayoutParams().height = fiftyFourDp;
                viewHolder.monsterPicture.getLayoutParams().width = fiftyFourDp;
                viewHolder.monsterPicture.requestLayout();
            }
        }
    }

    private void setLinearLayout(ViewHolder viewHolder, int position){
        viewHolder.monsterIdStroke.setVisibility(View.GONE);
        viewHolder.monsterPicture.getLayoutParams().height = fortyEightDp;
        viewHolder.monsterPicture.getLayoutParams().width = fortyEightDp;
        viewHolder.monsterPicture.requestLayout();
        viewHolder.relativeLayout.setPadding(eightDp, 0, eightDp, 0);
        viewHolder.monsterName.setVisibility(View.VISIBLE);
        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
            viewHolder.monsterId.setVisibility(View.INVISIBLE);
            viewHolder.rarity.setVisibility(View.GONE);
            viewHolder.rarityStar.setVisibility(View.GONE);
            viewHolder.monsterHP.setVisibility(View.GONE);
            viewHolder.monsterATK.setVisibility(View.GONE);
            viewHolder.monsterRCV.setVisibility(View.GONE);
        } else {
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
            viewHolder.monsterId.setVisibility(View.VISIBLE);
            viewHolder.monsterPicture.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.VISIBLE);
            viewHolder.monsterATK.setVisibility(View.VISIBLE);
            viewHolder.monsterRCV.setVisibility(View.VISIBLE);
        }
        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        } else {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
    }

    private void setGridLayout(ViewHolder viewHolder, int position){
        viewHolder.monsterIdStroke.setVisibility(View.VISIBLE);
        viewHolder.monsterIdStroke.setText("" + monsterList.get(position).getMonsterId());
        viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        viewHolder.monsterPicture.getLayoutParams().height = fiftyFourDp;
        viewHolder.monsterPicture.getLayoutParams().width = fiftyFourDp;
        viewHolder.monsterPicture.requestLayout();
        viewHolder.relativeLayout.setPadding(eightDp, eightDp, eightDp, eightDp);
        viewHolder.monsterName.setVisibility(View.GONE);
        viewHolder.monsterId.setVisibility(View.GONE);
        viewHolder.rarity.setVisibility(View.GONE);
        viewHolder.rarityStar.setVisibility(View.GONE);
        viewHolder.monsterHP.setVisibility(View.GONE);
        viewHolder.monsterATK.setVisibility(View.GONE);
        viewHolder.monsterRCV.setVisibility(View.GONE);
        viewHolder.type1.setVisibility(View.GONE);
        viewHolder.type2.setVisibility(View.GONE);
        viewHolder.type3.setVisibility(View.GONE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.base_monster_list_row, parent, false));
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);
        viewHolder.monsterName.setHorizontallyScrolling(true);
        viewHolder.monsterName.setSelected(true);
        viewHolder.itemView.setOnClickListener(onItemClickListener);

        viewHolder.itemView.setTag(viewHolder);
        viewHolder.choose.setOnClickListener(monsterListOnClickListener);

        viewHolder.itemView.setOnLongClickListener(monsterListOnLongClickListener);
        viewHolder.leaderSkillName.setHorizontallyScrolling(true);
        viewHolder.leaderSkillName.setSelected(true);
        return viewHolder;
    }



    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView monsterName, monsterId, rarity, monsterHP, monsterATK, monsterRCV, hpBase, hpTotal, atkBase, atkTotal, rcvBase, rcvTotal, leaderSkillName, leaderSkillDesc, levelMax, activeSkillDesc, activeSkillName, activeSkillCooldown;
        ImageView monsterPicture, type1, type2, type3, rarityStar, awakening1, awakening2, awakening3, awakening4, awakening5, awakening6, awakening7, awakening8, awakening9, leaderSkill, activeSkill;
        RelativeLayout expandLayout;
        LinearLayout awakeningHolder, latentHolder;
        Button choose;
        RelativeLayout relativeLayout;
        TextStroke monsterIdStroke;

        public ViewHolder(View convertView) {
            super(convertView);
            monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            monsterId = (TextView) convertView.findViewById(R.id.monsterId);
            rarity = (TextView) convertView.findViewById(R.id.rarity);
            monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            type1 = (ImageView) convertView.findViewById(R.id.type1);
            type2 = (ImageView) convertView.findViewById(R.id.type2);
            type3 = (ImageView) convertView.findViewById(R.id.type3);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            rarityStar = (ImageView) convertView.findViewById(R.id.rarityStar);
            hpBase = (TextView) convertView.findViewById(R.id.hpBase);
            atkBase = (TextView) convertView.findViewById(R.id.atkBase);
            rcvBase = (TextView) convertView.findViewById(R.id.rcvBase);
            hpTotal = (TextView) convertView.findViewById(R.id.hpTotal);
            atkTotal = (TextView) convertView.findViewById(R.id.atkTotal);
            rcvTotal = (TextView) convertView.findViewById(R.id.rcvTotal);
            awakening1 = (ImageView) convertView.findViewById(R.id.awakening1);
            awakening2 = (ImageView) convertView.findViewById(R.id.awakening2);
            awakening3 = (ImageView) convertView.findViewById(R.id.awakening3);
            awakening4 = (ImageView) convertView.findViewById(R.id.awakening4);
            awakening5 = (ImageView) convertView.findViewById(R.id.awakening5);
            awakening6 = (ImageView) convertView.findViewById(R.id.awakening6);
            awakening7 = (ImageView) convertView.findViewById(R.id.awakening7);
            awakening8 = (ImageView) convertView.findViewById(R.id.awakening8);
            awakening9 = (ImageView) convertView.findViewById(R.id.awakening9);
            leaderSkill = (ImageView) convertView.findViewById(R.id.leaderSkill);
            leaderSkillDesc = (TextView) convertView.findViewById(R.id.leaderSkillDesc);
            leaderSkillName = (TextView) convertView.findViewById(R.id.leaderSkillName);
            awakeningHolder = (LinearLayout) convertView.findViewById(R.id.awakeningHolder);
            latentHolder = (LinearLayout) convertView.findViewById(R.id.latentHolder);
            choose = (Button) convertView.findViewById(R.id.choose);
            expandLayout = (RelativeLayout) convertView.findViewById(R.id.expandLayout);
            levelMax = (TextView) convertView.findViewById(R.id.levelMax);
            relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            monsterIdStroke = (TextStroke) convertView.findViewById(R.id.monsterIdStroke);
            activeSkill = (ImageView) convertView.findViewById(R.id.activeSkill);
            activeSkillDesc = (TextView) convertView.findViewById(R.id.activeSkillDesc);
            activeSkillName = (TextView) convertView.findViewById(R.id.activeSkillName);
            activeSkillCooldown = (TextView) convertView.findViewById(R.id.activeSkillCooldown);
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

    public int getExpandedPosition(){
        return expandedPosition;
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
