package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class SaveMonsterListRecycler extends RecyclerView.Adapter<SaveMonsterListRecycler.ViewHolder> {

    private ArrayList<Monster> monsterList;
    private View.OnClickListener monsterListOnClickListener;
    private Context mContext;
    private LayoutInflater inflater;
    private Toast toast;

    private View.OnClickListener onItemClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
        }
    };

    public SaveMonsterListRecycler(Context context, ArrayList<Monster> monsterList, View.OnClickListener monsterListOnClickListener){
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListOnClickListener = monsterListOnClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.monsterName.setText(monsterList.get(position).getName());
        viewHolder.monsterLevel.setText("Lv. " + monsterList.get(position).getCurrentLevel() + " - ");
        viewHolder.monsterPlus.setText(" +"+ monsterList.get(position).getTotalPlus() + " ");
        viewHolder.monsterATK.setText(Integer.toString(monsterList.get(position).getTotalAtk()) + " / ");
        viewHolder.monsterRCV.setText(Integer.toString(monsterList.get(position).getTotalRcv()));
        viewHolder.monsterHP.setText(Integer.toString(monsterList.get(position).getTotalHp()) + " / ");
        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        viewHolder.monsterAwakenings.setText(" " + Integer.toString(monsterList.get(position).getCurrentAwakenings()));
        if (monsterList.get(position).getCurrentAwakenings() == monsterList.get(position).getMaxAwakenings()) {
            viewHolder.monsterAwakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monsterAwakenings.setText("");
        }
        if (monsterList.get(position).getMonsterId() == 0) {
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
            viewHolder.monsterPlus.setVisibility(View.GONE);
            viewHolder.monsterAwakenings.setVisibility(View.GONE);
            viewHolder.monsterHP.setVisibility(View.GONE);
            viewHolder.monsterATK.setVisibility(View.GONE);
            viewHolder.monsterRCV.setVisibility(View.GONE);
            viewHolder.monsterLevel.setVisibility(View.GONE);
            viewHolder.favorite.setVisibility(View.INVISIBLE);
            viewHolder.favoriteOutline.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
            viewHolder.monsterPlus.setVisibility(View.VISIBLE);
            viewHolder.monsterPicture.setVisibility(View.VISIBLE);
            viewHolder.monsterAwakenings.setVisibility(View.VISIBLE);
            viewHolder.monsterHP.setVisibility(View.VISIBLE);
            viewHolder.monsterATK.setVisibility(View.VISIBLE);
            viewHolder.monsterRCV.setVisibility(View.VISIBLE);
            viewHolder.monsterLevel.setVisibility(View.VISIBLE);
            viewHolder.favorite.setVisibility(View.VISIBLE);
            viewHolder.favoriteOutline.setVisibility(View.VISIBLE);
        }

        viewHolder.favorite.setColorFilter(0xFFFFAADD);
        if(monsterList.get(position).isFavorite()){
            viewHolder.favorite.setVisibility(View.VISIBLE);
        }else {
            viewHolder.favorite.setVisibility(View.INVISIBLE);
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
        viewHolder.favorite.setTag(R.string.index, position);
        viewHolder.favoriteOutline.setTag(R.string.index, position);
        viewHolder.favorite.setOnClickListener(favoriteOnClickListener);
        viewHolder.favoriteOutline.setOnClickListener(favoriteOnClickListener);

        viewHolder.monsterName.setSelected(true);
        viewHolder.itemView.setTag(R.string.index, position);
        viewHolder.itemView.setOnClickListener(monsterListOnClickListener);
    }

    private View.OnClickListener favoriteOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            if (!monsterList.get(position).isFavorite()){
                monsterList.get(position).setFavorite(true);
                monsterList.get(position).save();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(mContext, "Monster favorited", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                monsterList.get(position).setFavorite(false);
                monsterList.get(position).save();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(mContext, "Monster unfavorited", Toast.LENGTH_SHORT);
                toast.show();
            }
            notifyDataSetChanged();
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.save_monster_list_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView monsterName, monsterPlus, monsterAwakenings, monsterHP, monsterATK, monsterRCV, monsterLevel;
        ImageView monsterPicture, type1, type2, type3, favorite, favoriteOutline;

        public ViewHolder(View convertView){
            super(convertView);
            monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            monsterPlus = (TextView) convertView.findViewById(R.id.monsterPlus);
            monsterAwakenings = (TextView) convertView.findViewById(R.id.monsterAwakenings);
            monsterATK = (TextView) convertView.findViewById(R.id.monsterATK);
            monsterRCV = (TextView) convertView.findViewById(R.id.monsterRCV);
            monsterHP = (TextView) convertView.findViewById(R.id.monsterHP);
            monsterLevel = (TextView) convertView.findViewById(R.id.monsterLevel);
            type1 = (ImageView) convertView.findViewById(R.id.type1);
            type2 = (ImageView) convertView.findViewById(R.id.type2);
            type3 = (ImageView) convertView.findViewById(R.id.type3);
            favorite = (ImageView) convertView.findViewById(R.id.favorite);
            favoriteOutline = (ImageView) convertView.findViewById(R.id.favoriteOutline);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
        }
    }

    public void notifyDataSetChanged(ArrayList<Monster> monsterList) {
        this.monsterList = monsterList;
        notifyDataSetChanged();
    }

    public ArrayList<Monster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(ArrayList<Monster> monsterList) {
        this.monsterList = monsterList;
    }

    public Monster getItem(int position){
        return monsterList.get(position);
    }
}
