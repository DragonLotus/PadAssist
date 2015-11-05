package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.Fragments.MonsterPageFragment;
import com.example.anthony.damagecalculator.Fragments.MonsterTabLayoutFragment;
import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.Singleton;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class MonsterListRecycler extends RecyclerView.Adapter<MonsterListRecycler.ViewHolder> {
    private ArrayList<Monster> monsterList;
    private Team team;
    private Context mContext;
    private LayoutInflater inflater;

    private View.OnClickListener onItemClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
//            team.setMonsterOverwrite(holder.getAdapterPosition());
//            team.save();
            Singleton.getInstance().setMonsterOverwrite(holder.getAdapterPosition());
            if (monsterList.get(holder.getAdapterPosition()).getMonsterId() == 0) {
                ((MainActivity) mContext).switchFragment(MonsterTabLayoutFragment.newInstance(false, 0), MonsterTabLayoutFragment.TAG);
            }else {
                ((MainActivity) mContext).switchFragment(MonsterPageFragment.newInstance(), MonsterPageFragment.TAG);
            }
        }
    };

    public MonsterListRecycler(Context context, ArrayList<Monster> monsterList, Team team){
        mContext = context;
        this.monsterList = monsterList;
        this.team = team;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("MonsterListRecycler", "monsterList is: " + monsterList);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolder.monsterATK.setText(Integer.toString(monsterList.get(position).getTotalAtk()) + " / ");
        viewHolder.monsterRCV.setText(Integer.toString(monsterList.get(position).getTotalRcv()));
        viewHolder.monsterHP.setText(Integer.toString(monsterList.get(position).getTotalHp()) + " / ");
        viewHolder.monsterAwakenings.setText(" " + Integer.toString(monsterList.get(position).getCurrentAwakenings()));
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterLevelValue.setText(Integer.toString(monsterList.get(position).getCurrentLevel()));
        viewHolder.monsterPlus.setText(" +" + Integer.toString(monsterList.get(position).getTotalPlus()) + " ");
        viewHolder.rarity.setText("" + monsterList.get(position).getRarity());
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);
        if (monsterList.get(position).getCurrentAwakenings() == monsterList.get(position).getMaxAwakenings()) {
            viewHolder.monsterAwakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monsterAwakenings.setText("");
        }

        if (monsterList.get(position).getTotalPlus() == 0) {
            viewHolder.monsterPlus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monsterPlus.setVisibility(View.VISIBLE);
        }
        if (monsterList.get(position).getCurrentAwakenings() == 0) {
            viewHolder.monsterAwakenings.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monsterAwakenings.setVisibility(View.VISIBLE);
        }
        if(monsterList.get(position).getMonsterId() == 0){
            viewHolder.monsterLevelValue.setVisibility(View.INVISIBLE);
            viewHolder.monsterHP.setVisibility(View.INVISIBLE);
            viewHolder.monsterATK.setVisibility(View.INVISIBLE);
            viewHolder.monsterRCV.setVisibility(View.INVISIBLE);
            viewHolder.monsterLevel.setVisibility(View.INVISIBLE);
            viewHolder.type1.setVisibility(View.INVISIBLE);
            viewHolder.type2.setVisibility(View.INVISIBLE);
            viewHolder.rarity.setVisibility(View.INVISIBLE);
            viewHolder.rarityStar.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.monsterLevelValue.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.VISIBLE);
            viewHolder.monsterATK.setVisibility(View.VISIBLE);
            viewHolder.monsterRCV.setVisibility(View.VISIBLE);
            viewHolder.monsterLevel.setVisibility(View.VISIBLE);
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
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
                viewHolder.type1.setVisibility(View.GONE);
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
                viewHolder.type2.setVisibility(View.GONE);
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
                viewHolder.type3.setVisibility(View.GONE);
                break;
        }

        if(monsterList.get(position).getType2() == -1){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewHolder.type1.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            viewHolder.type1.setLayoutParams(params);
        }

        if(monsterList.get(position).getType3() == -1){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)viewHolder.type2.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            viewHolder.type2.setLayoutParams(params);
        }

        viewHolder.itemView.setOnClickListener(onItemClickListener);
        viewHolder.itemView.setTag(viewHolder);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.monster_list_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView monsterName, monsterPlus, monsterAwakenings, monsterLevelValue, monsterHP, monsterATK, monsterRCV, monsterLevel, rarity;
        ImageView monsterPicture, type1, type2, type3, rarityStar;

        public ViewHolder(View convertView){
            super(convertView);
            monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            monsterPlus = (TextView) convertView.findViewById(R.id.monsterPlus);
            monsterAwakenings = (TextView) convertView.findViewById(R.id.monsterAwakenings);
            monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            monsterLevelValue = (TextView) convertView.findViewById(R.id.monsterLevelValue);
            monsterLevel = (TextView) convertView.findViewById(R.id.monsterLevel);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            type1 = (ImageView) convertView.findViewById(R.id.type1);
            type2 = (ImageView) convertView.findViewById(R.id.type2);
            type3 = (ImageView) convertView.findViewById(R.id.type3);
            rarity = (TextView) convertView.findViewById(R.id.rarity);
            rarityStar = (ImageView) convertView.findViewById(R.id.rarityStar);
        }
    }

    public void updateList(ArrayList<Monster> monsterList){
        this.monsterList = monsterList;
        notifyDataSetChanged();
    }
}
