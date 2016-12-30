package com.padassist.BaseFragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.EvolutionListRecycler;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Monster;
import com.padassist.Graphics.TextStroke;
import com.padassist.Graphics.TooltipSameSkill;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public abstract class BaseMonsterListRecyclerBase extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int GRID = 0, LINEAR = 1;
    protected ArrayList<BaseMonster> monsterList;
    protected View.OnClickListener monsterListOnClickListener;
    protected View.OnLongClickListener monsterListOnLongClickListener;
    protected Context mContext;
    protected LayoutInflater inflater;
    private Toast toast;
    private int expandedPosition = -1;
    private boolean evolutionExpanded = false;
    protected RecyclerView monsterListView;
    protected Realm realm;
    protected boolean isGrid;
    protected int fortyEightDp;
    protected int eightDp;
    protected int fiftyFourDp;
    protected ClearTextFocus clearTextFocus;
    protected TooltipSameSkill tooltipSameSkill;
    protected ArrayList<BaseMonster> evolutions = new ArrayList<>();
    protected EvolutionListRecycler evolutionListRecycler;

    public interface ClearTextFocus {
        public void doThis();
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
            evolutionExpanded = false;
        }
    };

    private View.OnClickListener expandEvolutionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout holder = (RelativeLayout) v.getTag();

            if (evolutionExpanded) {
                ((TextView) holder.getChildAt(0)).setText("Show Evolutions");
                holder.getChildAt(1).setVisibility(View.GONE);
            } else {
                ((TextView) holder.getChildAt(0)).setText("Hide Evolutions");
                holder.getChildAt(1).setVisibility(View.VISIBLE);
            }

            evolutionExpanded = !evolutionExpanded;
        }
    };

    private View.OnClickListener scrollToEvolutionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            long monsterId = (long) v.getTag(R.string.index);
            int position = -1;
            Log.d("BaseMonsterRecyclerBase", "monsterId is: " + monsterId);
            for (int i = 0; i < monsterList.size(); i++) {
                if (monsterList.get(i).getMonsterId() == monsterId) {
                    position = i;
                    break;
                }
            }
            if (position > 0) {
                evolutionExpanded = false;
                expandedPosition = position;
                monsterListView.scrollToPosition(position);
            }
        }
    };


    private View.OnClickListener sameSkillToolTipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout layout = (RelativeLayout) v.getTag();
            BaseMonster monster = monsterList.get(expandedPosition);
            if (!monster.getActiveSkillString().equals("Blank")) {
                RealmResults<BaseMonster> results = realm.where(BaseMonster.class).equalTo("activeSkillString", monster.getActiveSkillString()).findAllSorted("monsterId");
                tooltipSameSkill = new TooltipSameSkill(mContext, "Monsters with the same skill:", results);
                tooltipSameSkill.show(layout.getChildAt(1));
            }

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
        ViewHolderLinear viewHolderLinear = (ViewHolderLinear) viewHolder;

        viewHolderLinear.element1.setImageResource(ImageResourceUtil.orbColor(monsterList.get(position).getElement1()));
        if (monsterList.get(position).getElement1Int() == -1) {
            viewHolderLinear.element2.setVisibility(View.GONE);
        } else {
            viewHolderLinear.element2.setImageResource(ImageResourceUtil.orbColor(monsterList.get(position).getElement2()));
            viewHolderLinear.element2.setVisibility(View.VISIBLE);
        }

        evolutions.clear();
        for (int i = 0; i < monsterList.get(position).getEvolutions().size(); i++) {
            evolutions.add(realm.where(BaseMonster.class).equalTo("monsterId", monsterList.get(position).getEvolutions().get(i).getValue()).findFirst());
        }
        if (evolutions.size() == 0) {
            viewHolderLinear.evolutionText.setVisibility(View.GONE);
        } else {
            viewHolderLinear.evolutionText.setVisibility(View.VISIBLE);
        }

        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        } else {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
        viewHolderLinear.monsterName.setText(monsterList.get(position).getName());
        viewHolderLinear.monsterId.setText("" + monsterList.get(position).getMonsterId());
//        viewHolderLinear.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolderLinear.monsterPicture.setImageBitmap(monsterList.get(position).getMonsterPicture());
        viewHolderLinear.rarity.setText("" + monsterList.get(position).getRarity());
        viewHolderLinear.monsterHP.setText(Integer.toString(monsterList.get(position).getHpMax()) + " / ");
        viewHolderLinear.monsterATK.setText(Integer.toString(monsterList.get(position).getAtkMax()) + " / ");
        viewHolderLinear.monsterRCV.setText(Integer.toString(monsterList.get(position).getRcvMax()));

        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolderLinear.type1.setVisibility(View.GONE);
            viewHolderLinear.type2.setVisibility(View.GONE);
            viewHolderLinear.type3.setVisibility(View.GONE);
            viewHolderLinear.monsterId.setVisibility(View.INVISIBLE);
            viewHolderLinear.rarity.setVisibility(View.GONE);
            viewHolderLinear.rarityStar.setVisibility(View.GONE);
            viewHolderLinear.monsterHP.setVisibility(View.GONE);
            viewHolderLinear.monsterATK.setVisibility(View.GONE);
            viewHolderLinear.monsterRCV.setVisibility(View.GONE);
        } else {
            viewHolderLinear.type1.setVisibility(View.VISIBLE);
            viewHolderLinear.type2.setVisibility(View.VISIBLE);
            viewHolderLinear.type3.setVisibility(View.VISIBLE);
            viewHolderLinear.monsterId.setVisibility(View.VISIBLE);
            viewHolderLinear.monsterPicture.setVisibility(View.VISIBLE);
            viewHolderLinear.rarity.setVisibility(View.VISIBLE);
            viewHolderLinear.rarityStar.setVisibility(View.VISIBLE);
            viewHolderLinear.monsterHP.setVisibility(View.VISIBLE);
            viewHolderLinear.monsterATK.setVisibility(View.VISIBLE);
            viewHolderLinear.monsterRCV.setVisibility(View.VISIBLE);
        }

        if (monsterList.get(position).getType1() >= 0) {
            viewHolderLinear.type1.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getType1()));
        } else {
            viewHolderLinear.type1.setVisibility(View.INVISIBLE);
        }

        if (monsterList.get(position).getType2() >= 0) {
            viewHolderLinear.type2.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getType2()));
        } else {
            viewHolderLinear.type2.setVisibility(View.INVISIBLE);
        }

        if (monsterList.get(position).getType3() >= 0) {
            viewHolderLinear.type3.setImageResource(ImageResourceUtil.monsterType(monsterList.get(position).getType3()));
        } else {
            viewHolderLinear.type3.setVisibility(View.INVISIBLE);
        }

        viewHolderLinear.itemView.setTag(R.string.index, position);
        viewHolderLinear.choose.setTag(R.string.index, position);

        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolderLinear.itemView.setOnClickListener(monsterListOnClickListener);
        } else {
            viewHolderLinear.itemView.setOnClickListener(expandOnItemClickListener);
        }


        if (position == expandedPosition && monsterList.get(position).getMonsterId() != 0) {
            viewHolderLinear.expandLayout.setVisibility(View.VISIBLE);
            viewHolderLinear.monsterHP.setVisibility(View.GONE);
            viewHolderLinear.monsterATK.setVisibility(View.GONE);
            viewHolderLinear.monsterRCV.setVisibility(View.GONE);
            viewHolderLinear.monsterName.setVisibility(View.VISIBLE);
            viewHolderLinear.rarity.setVisibility(View.VISIBLE);
            viewHolderLinear.rarityStar.setVisibility(View.VISIBLE);
            viewHolderLinear.monsterId.setVisibility(View.VISIBLE);

            if (monsterList.get(position).getType1() > -1) {
                viewHolderLinear.type1.setVisibility(View.VISIBLE);
                viewHolderLinear.type2.setVisibility(View.INVISIBLE);
                viewHolderLinear.type3.setVisibility(View.INVISIBLE);
            }

            if (monsterList.get(position).getType2() > -1) {
                viewHolderLinear.type2.setVisibility(View.VISIBLE);
                viewHolderLinear.type3.setVisibility(View.INVISIBLE);
            }

            if (monsterList.get(position).getType3() > -1) {
                viewHolderLinear.type3.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < 9; i++) {
                if (i >= monsterList.get(position).getMaxAwakenings()) {
                    viewHolderLinear.awakeningHolder.getChildAt(i).setVisibility(View.GONE);
                } else {
                    viewHolderLinear.awakeningHolder.getChildAt(i).setVisibility(View.VISIBLE);
                }
            }
            for (int j = 0; j < monsterList.get(position).getMaxAwakenings(); j++) {
                viewHolderLinear.awakeningHolder.getChildAt(j).setBackgroundResource(ImageResourceUtil.monsterAwakening(monsterList.get(position).getAwokenSkills().get(j).getValue()));
            }

            viewHolderLinear.rarity.setText("" + monsterList.get(position).getRarity());
            viewHolderLinear.hpBase.setText("" + monsterList.get(position).getHpMin());
            viewHolderLinear.atkBase.setText("" + monsterList.get(position).getAtkMin());
            viewHolderLinear.rcvBase.setText("" + monsterList.get(position).getRcvMin());
            viewHolderLinear.hpTotal.setText("" + monsterList.get(position).getHpMax());
            viewHolderLinear.atkTotal.setText("" + monsterList.get(position).getAtkMax());
            viewHolderLinear.rcvTotal.setText("" + monsterList.get(position).getRcvMax());
            viewHolderLinear.weightedBase.setText(monsterList.get(position).getWeightedBaseString());
            viewHolderLinear.weightedTotal.setText(monsterList.get(position).getWeightedTotalString());

            if (monsterList.get(position).getActiveSkillString().equals("Blank")) {
                viewHolderLinear.activeSkillName.setText("None");
                viewHolderLinear.activeSkillDesc.setVisibility(View.GONE);
                viewHolderLinear.activeSkillCooldown.setVisibility(View.GONE);
            } else {
                if (monsterList.get(position).getActiveSkill() == null) {
                    viewHolderLinear.activeSkillDesc.setText("This monster has an active skill but the author of this app  has not realized that it doesn't exist in his database yet.");
                    viewHolderLinear.activeSkillCooldown.setVisibility(View.GONE);
                } else {
                    viewHolderLinear.activeSkillDesc.setText(monsterList.get(position).getActiveSkill().getDescription());
                    viewHolderLinear.activeSkillCooldown.setVisibility(View.VISIBLE);
                    viewHolderLinear.activeSkillCooldown.setText("(CD " + monsterList.get(position).getActiveSkill().getMaximumCooldown() + " -> " + monsterList.get(position).getActiveSkill().getMinimumCooldown() + ")");
                }
                viewHolderLinear.activeSkillName.setVisibility(View.VISIBLE);
                viewHolderLinear.activeSkillDesc.setVisibility(View.VISIBLE);
                viewHolderLinear.activeSkillName.setText("" + monsterList.get(position).getActiveSkillString() + "");
            }

            if (monsterList.get(position).getLeaderSkillString().equals("Blank")) {
                viewHolderLinear.leaderSkillName.setText("None");
                viewHolderLinear.leaderSkillDesc.setVisibility(View.GONE);
            } else {
                if (monsterList.get(position).getLeaderSkill() == null) {
                    viewHolderLinear.leaderSkillDesc.setText("This monster has a leader skill but the author of this app  has not realized that it doesn't exist in his database yet.\n(Hint: This leader skill does nothing.)");
                } else {
                    viewHolderLinear.leaderSkillDesc.setText(monsterList.get(position).getLeaderSkill().getDescription());
                }
                viewHolderLinear.leaderSkillName.setVisibility(View.VISIBLE);
                viewHolderLinear.leaderSkillDesc.setVisibility(View.VISIBLE);
                viewHolderLinear.leaderSkillName.setText("" + monsterList.get(position).getLeaderSkillString() + "");
            }
            viewHolderLinear.levelMax.setText("Level " + monsterList.get(position).getMaxLevel());

            if (evolutionExpanded) {
                viewHolderLinear.evolutionText.setText("Hide Evolutions");
                viewHolderLinear.evolutionRecyclerView.setVisibility(View.VISIBLE);
            } else {
                viewHolderLinear.evolutionText.setText("Show Evolutions");
                viewHolderLinear.evolutionRecyclerView.setVisibility(View.GONE);
            }
            evolutionListRecycler = new EvolutionListRecycler(mContext, monsterList.get(position).getMonsterId(), evolutions, scrollToEvolutionOnClickListener, realm);
            viewHolderLinear.evolutionRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            viewHolderLinear.evolutionRecyclerView.setAdapter(evolutionListRecycler);
            if (isGrid) {
                viewHolderLinear.monsterIdStroke.setVisibility(View.GONE);
                ((StaggeredGridLayoutManager.LayoutParams) viewHolderLinear.itemView.getLayoutParams()).setFullSpan(true);
                viewHolderLinear.monsterPicture.getLayoutParams().height = fortyEightDp;
                viewHolderLinear.monsterPicture.getLayoutParams().width = fortyEightDp;
                viewHolderLinear.monsterPicture.requestLayout();
                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
            }
        } else {
            viewHolderLinear.expandLayout.setVisibility(View.GONE);
            if (monsterList.get(position).getType1() != -1 && !isGrid) {
                viewHolderLinear.monsterHP.setVisibility(View.VISIBLE);
                viewHolderLinear.monsterATK.setVisibility(View.VISIBLE);
                viewHolderLinear.monsterRCV.setVisibility(View.VISIBLE);
            }
            if (isGrid) {
                viewHolderLinear.monsterIdStroke.setVisibility(View.VISIBLE);
                ((StaggeredGridLayoutManager.LayoutParams) viewHolderLinear.itemView.getLayoutParams()).setFullSpan(false);
                viewHolderLinear.monsterPicture.getLayoutParams().height = fiftyFourDp;
                viewHolderLinear.monsterPicture.getLayoutParams().width = fiftyFourDp;
                viewHolderLinear.monsterPicture.requestLayout();
            }
        }
    }

    private void setGridLayout(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolderGrid viewHolderGrid = (ViewHolderGrid) viewHolder;
        viewHolderGrid.monsterIdStroke.setText("" + monsterList.get(position).getMonsterId());
        viewHolderGrid.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
//        viewHolderGrid.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolderGrid.monsterPicture.setImageBitmap(monsterList.get(position).getMonsterPicture());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case GRID:
                ViewHolderGrid viewHolderGrid = new ViewHolderGrid(inflater.inflate(R.layout.base_monster_list_grid, parent, false));
                viewHolderGrid.itemView.setOnClickListener(expandOnItemClickListener);
                viewHolderGrid.itemView.setTag(viewHolderGrid);
                viewHolderGrid.itemView.setOnLongClickListener(monsterListOnLongClickListener);
                return viewHolderGrid;
            default:
                ViewHolderLinear viewHolderLinear = new ViewHolderLinear(inflater.inflate(R.layout.base_monster_list_row, parent, false));
                viewHolderLinear.rarityStar.setColorFilter(0xFFD4D421);
                viewHolderLinear.monsterName.setHorizontallyScrolling(true);
                viewHolderLinear.monsterName.setSelected(true);

                viewHolderLinear.itemView.setOnClickListener(expandOnItemClickListener);
                viewHolderLinear.itemView.setTag(viewHolderLinear);

                viewHolderLinear.choose.setOnClickListener(monsterListOnClickListener);
                viewHolderLinear.itemView.setOnLongClickListener(monsterListOnLongClickListener);

                viewHolderLinear.leaderSkillName.setHorizontallyScrolling(true);
                viewHolderLinear.leaderSkillName.setSelected(true);

                viewHolderLinear.activeSkillName.setHorizontallyScrolling(true);
                viewHolderLinear.activeSkillName.setSelected(true);

                viewHolderLinear.skillHolder.setOnClickListener(sameSkillToolTipOnClickListener);
                viewHolderLinear.skillHolder.setTag(viewHolderLinear.skillHolder);

                viewHolderLinear.evolutionHolder.setOnClickListener(expandEvolutionClickListener);
                viewHolderLinear.evolutionHolder.setTag(viewHolderLinear.evolutionHolder);
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
        TextView monsterName, monsterId, rarity, monsterHP, monsterATK, monsterRCV, hpBase, hpTotal, atkBase, atkTotal, rcvBase, rcvTotal,
                leaderSkillName, leaderSkillDesc, levelMax, activeSkillDesc, activeSkillName, activeSkillCooldown,
                weightedBase, weightedTotal, evolutionText;
        ImageView monsterPicture, type1, type2, type3, rarityStar, awakening1, awakening2, awakening3, awakening4, awakening5,
                awakening6, awakening7, awakening8, awakening9, leaderSkill, activeSkill, element1, element2;
        RelativeLayout expandLayout;
        LinearLayout awakeningHolder, latentHolder;
        Button choose;
        RelativeLayout relativeLayout, skillHolder, leaderSkillHolder, evolutionHolder;
        TextStroke monsterIdStroke;
        RecyclerView evolutionRecyclerView;

        public ViewHolderLinear(View convertView) {
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
            weightedBase = (TextView) convertView.findViewById(R.id.weightedBase);
            weightedTotal = (TextView) convertView.findViewById(R.id.weightedTotal);
            skillHolder = (RelativeLayout) convertView.findViewById(R.id.skillHolder);
            leaderSkillHolder = (RelativeLayout) convertView.findViewById(R.id.leaderSkillHolder);
            evolutionRecyclerView = (RecyclerView) convertView.findViewById(R.id.evolutionRecyclerView);
            evolutionText = (TextView) convertView.findViewById(R.id.evolutionText);
            evolutionHolder = (RelativeLayout) convertView.findViewById(R.id.evolutionHolder);
            element1 = (ImageView) convertView.findViewById(R.id.element1);
            element2 = (ImageView) convertView.findViewById(R.id.element2);
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

    public int getExpandedPosition() {
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
