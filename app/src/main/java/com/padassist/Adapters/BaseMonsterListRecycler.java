package com.padassist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.padassist.Data.LeaderSkill;
import com.padassist.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class BaseMonsterListRecycler extends RecyclerView.Adapter<BaseMonsterListRecycler.ViewHolder> {

    private ArrayList<BaseMonster> monsterList;
    private View.OnClickListener monsterListOnClickListener;
    private View.OnLongClickListener monsterListOnLongClickListener;
    private Context mContext;
    private LayoutInflater inflater;
    private Toast toast;
    private int expandedPosition = -1;

    private ExpandChange expandChange;

    public interface ExpandChange{
        public void onExpandChange(int expandedPosition);
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int previous;
            ViewHolder holder = (ViewHolder) v.getTag();
            if(holder.getAdapterPosition() != expandedPosition){
                if (expandedPosition >= 0){
                    previous = expandedPosition;
                    notifyItemChanged(previous);
                }
                expandedPosition = holder.getAdapterPosition();
                notifyItemChanged(expandedPosition);
            } else {
                previous = expandedPosition;
                expandedPosition = -1;
                notifyItemChanged(previous);
            }
            expandChange.onExpandChange(expandedPosition);
        }
    };

    public BaseMonsterListRecycler(Context context, ArrayList<BaseMonster> monsterList, ExpandChange expandChange, View.OnClickListener monsterListOnClickListener, View.OnLongClickListener monsterListOnLongClickListener){
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListOnClickListener = monsterListOnClickListener;
        this.monsterListOnLongClickListener = monsterListOnLongClickListener;
        this.expandChange = expandChange;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterId.setText("" + monsterList.get(position).getMonsterId());
        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolder.rarity.setText("" + monsterList.get(position).getRarity());
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);
        viewHolder.monsterHP.setText(Integer.toString(monsterList.get(position).getHpMax()) + " / ");
        viewHolder.monsterATK.setText(Integer.toString(monsterList.get(position).getAtkMax()) + " / ");
        viewHolder.monsterRCV.setText(Integer.toString(monsterList.get(position).getRcvMax()));

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
        }else {
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

        switch(monsterList.get(position).getType1()){
            case 0:
                viewHolder.type1.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type1.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type1.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type1.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type1.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type1.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type1.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type1.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type1.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type1.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type1.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type1.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type1.setVisibility(View.INVISIBLE);
                break;
        }
        switch(monsterList.get(position).getType2()){
            case 0:
                viewHolder.type2.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type2.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type2.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type2.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type2.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type2.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type2.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type2.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type2.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type2.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type2.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type2.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type2.setVisibility(View.INVISIBLE);
                break;
        }
        switch(monsterList.get(position).getType3()){
            case 0:
                viewHolder.type3.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                viewHolder.type3.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                viewHolder.type3.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                viewHolder.type3.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                viewHolder.type3.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                viewHolder.type3.setImageResource(R.drawable.type_god);
                break;
            case 6:
                viewHolder.type3.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                viewHolder.type3.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                viewHolder.type3.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                viewHolder.type3.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                viewHolder.type3.setVisibility(View.INVISIBLE);
                break;
            case 14:
                viewHolder.type3.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                viewHolder.type3.setVisibility(View.INVISIBLE);
                break;
        }

        viewHolder.monsterName.setSelected(true);
        viewHolder.itemView.setOnClickListener(onItemClickListener);
        viewHolder.itemView.setTag(R.string.index, position);
        viewHolder.itemView.setTag(viewHolder);
        viewHolder.choose.setOnClickListener(monsterListOnClickListener);
        viewHolder.choose.setTag(R.string.index, position);
        viewHolder.itemView.setOnLongClickListener(monsterListOnLongClickListener);
        viewHolder.leaderSkillName.setSelected(true);
        if(monsterList.get(position).getMonsterId() == 0){
            viewHolder.itemView.setOnClickListener(monsterListOnClickListener);
        } else {
            viewHolder.itemView.setOnClickListener(onItemClickListener);
        }
        if(position == expandedPosition && monsterList.get(position).getMonsterId() != 0){
            viewHolder.expandLayout.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.GONE);
            viewHolder.monsterATK.setVisibility(View.GONE);
            viewHolder.monsterRCV.setVisibility(View.GONE);
            for(int i = 0; i < 9; i++){
                if(i >= monsterList.get(position).getMaxAwakenings()){
                    viewHolder.awakeningHolder.getChildAt(i).setVisibility(View.GONE);
                }else {
                    viewHolder.awakeningHolder.getChildAt(i).setVisibility(View.VISIBLE);
                }
            }
            for (int j = 0; j < monsterList.get(position).getMaxAwakenings(); j++) {
                switch (monsterList.get(position).getAwokenSkills().get(j)) {
                    case 1:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_1);
                        break;
                    case 2:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_2);
                        break;
                    case 3:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_3);
                        break;
                    case 4:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_4);
                        break;
                    case 5:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_5);
                        break;
                    case 6:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_6);
                        break;
                    case 7:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_7);
                        break;
                    case 8:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_8);
                        break;
                    case 9:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_9);
                        break;
                    case 10:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_10);
                        break;
                    case 11:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_11);
                        break;
                    case 12:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_12);
                        break;
                    case 13:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_13);
                        break;
                    case 14:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_14);
                        break;
                    case 15:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_15);
                        break;
                    case 16:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_16);
                        break;
                    case 17:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_17);
                        break;
                    case 18:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_18);
                        break;
                    case 19:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_19);
                        break;
                    case 20:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_20);
                        break;
                    case 21:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_21);
                        break;
                    case 22:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_22);
                        break;
                    case 23:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_23);
                        break;
                    case 24:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_24);
                        break;
                    case 25:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_25);
                        break;
                    case 26:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_26);
                        break;
                    case 27:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_27);
                        break;
                    case 28:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_28);
                        break;
                    case 29:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_29);
                        break;
                    case 30:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_30);
                        break;
                    case 31:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_31);
                        break;
                    case 32:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_32);
                        break;
                    case 33:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_33);
                        break;
                    case 34:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_34);
                        break;
                    case 35:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_35);
                        break;
                    case 36:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_36);
                        break;
                    case 37:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_37);
                        break;
                    case 38:
                        viewHolder.awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_38);
                        break;
                }
            }

            viewHolder.rarity.setText("" + monsterList.get(position).getRarity());
            viewHolder.rarityStar.setColorFilter(0xFFD4D421);
            viewHolder.hpBase.setText("" + monsterList.get(position).getHpMin());
            viewHolder.atkBase.setText("" + monsterList.get(position).getAtkMin());
            viewHolder.rcvBase.setText("" + monsterList.get(position).getRcvMin());
            viewHolder.hpTotal.setText("" + monsterList.get(position).getHpMax());
            viewHolder.atkTotal.setText("" + monsterList.get(position).getAtkMax());
            viewHolder.rcvTotal.setText("" + monsterList.get(position).getRcvMax());
            viewHolder.leaderSkillName.setText("" + monsterList.get(position).getLeaderSkill() + "");
            viewHolder.leaderSkillDesc.setText(LeaderSkill.getLeaderSkill(monsterList.get(position).getLeaderSkill()).getDescription());
            viewHolder.levelMax.setText("Level " + monsterList.get(position).getMaxLevel());
        } else {
            viewHolder.expandLayout.setVisibility(View.GONE);
            if(monsterList.get(position).getType1() != -1){
                viewHolder.monsterHP.setVisibility(View.VISIBLE);
                viewHolder.monsterATK.setVisibility(View.VISIBLE);
                viewHolder.monsterRCV.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.base_monster_list_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView monsterName, monsterId, rarity, monsterHP, monsterATK, monsterRCV, hpBase, hpTotal, atkBase, atkTotal, rcvBase, rcvTotal, leaderSkill, leaderSkillName, leaderSkillDesc, levelMax;
        ImageView monsterPicture, type1, type2, type3, rarityStar, awakening1, awakening2, awakening3, awakening4, awakening5, awakening6, awakening7, awakening8, awakening9;
        RelativeLayout expandLayout;
        LinearLayout awakeningHolder, latentHolder;
        Button choose;

        public ViewHolder(View convertView){
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
            leaderSkill = (TextView) convertView.findViewById(R.id.leaderSkill);
            leaderSkillDesc = (TextView) convertView.findViewById(R.id.leaderSkillDesc);
            leaderSkillName = (TextView) convertView.findViewById(R.id.leaderSkillName);
            awakeningHolder = (LinearLayout) convertView.findViewById(R.id.awakeningHolder);
            latentHolder = (LinearLayout) convertView.findViewById(R.id.latentHolder);
            choose = (Button) convertView.findViewById(R.id.choose);
            expandLayout = (RelativeLayout) convertView.findViewById(R.id.expandLayout);
            levelMax = (TextView) convertView.findViewById(R.id.levelMax);
        }
    }

    public void notifyDataSetChanged(ArrayList<BaseMonster> monsterList) {
        this.monsterList = monsterList;
        notifyDataSetChanged();
    }

    public ArrayList<BaseMonster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(ArrayList<BaseMonster> monsterList) {
        this.monsterList = monsterList;
    }

    public BaseMonster getItem(int position){
        return monsterList.get(position);
    }

    public boolean expanded() {
        if(expandedPosition!=-1){
            return true;
        } else return false;
    }

    public void setExpandedPosition(int expandedPosition) {
        int previous = this.expandedPosition;
        this.expandedPosition = expandedPosition;
        notifyItemChanged(previous);
        if(expandedPosition >= 0){
            notifyItemChanged(expandedPosition);
        }
    }
}
