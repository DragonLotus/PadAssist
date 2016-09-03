package com.padassist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.padassist.Data.Monster;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class MonsterGridAwakeningRecycler extends RecyclerView.Adapter<MonsterGridAwakeningRecycler.ViewHolder> {
    private ArrayList<Monster> monsterList;
    private Context mContext;
    private LayoutInflater inflater;
    private int awakening;
    private boolean isLatent;
    private ArrayList<Integer> latentList;
    private Realm realm = Realm.getDefaultInstance();

    public MonsterGridAwakeningRecycler(Context context, int awakening, boolean isLatent, ArrayList<Monster> monsterList) {
        mContext = context;
        this.monsterList = monsterList;
        this.awakening = awakening;
        this.isLatent = isLatent;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.monsterPicture.setImageResource(monsterList.get(position).getMonsterPicture());
        int counter = 0;
        if(isLatent){
            viewHolder.awakening.setImageResource(ImageResourceUtil.monsterLatent(awakening));
            for(int i = 0; i < monsterList.get(position).getLatents().size(); i++){
                if(monsterList.get(position).getLatents().get(i).getValue() == awakening){
                    counter++;
                }
            }
            viewHolder.awakeningAmount.setText("x" + counter);
        } else {
            viewHolder.awakening.setImageResource(ImageResourceUtil.monsterAwakening(awakening));

            for(int i = 0; i < monsterList.get(position).getAwokenSkills().size(); i++){
                if(monsterList.get(position).getAwokenSkills().get(i).getValue() == awakening){
                    counter++;
                }
            }
            viewHolder.awakeningAmount.setText("x" + counter);
        }

//        viewHolder.itemView.setOnClickListener(onItemClickListener);
//        viewHolder.itemView.setOnLongClickListener(onItemLongClickListener);
        viewHolder.itemView.setTag(viewHolder);
//        if (position % 2 == 1) {
//            viewHolder.itemView.setBackgroundColor(Color.parseColor("#e8e8e8"));
//        } else {
//            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
//        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.monster_awakening_grid, parent, false));
    }

    @Override
    public int getItemCount() {
        return monsterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView awakeningAmount;
        ImageView monsterPicture, awakening;

        public ViewHolder(View convertView) {
            super(convertView);
            awakening = (ImageView) convertView.findViewById(R.id.awakening);
            monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            awakeningAmount = (TextView) convertView.findViewById(R.id.awakeningAmount);
        }
    }

    public void updateList(ArrayList<Monster> monsterList) {
        this.monsterList = monsterList;
        notifyDataSetChanged();
    }
}
