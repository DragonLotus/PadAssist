package com.padassist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.Data.Monster;
import com.padassist.Fragments.MonsterPageFragment;
import com.padassist.Fragments.MonsterTabLayoutFragment;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.Singleton;

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
            switch (awakening) {
                case 1:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_1);
                    break;
                case 2:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_2);
                    break;
                case 3:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_3);
                    break;
                case 4:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_4);
                    break;
                case 5:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_5);
                    break;
                case 6:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_6);
                    break;
                case 7:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_7);
                    break;
                case 8:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_8);
                    break;
                case 9:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_9);
                    break;
                case 10:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_10);
                    break;
                case 11:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_11);
                    break;
                default:
                    viewHolder.awakening.setImageResource(R.drawable.latent_awakening_blank);
                    break;
            }
            for(int i = 0; i < monsterList.get(position).getLatents().size(); i++){
                if(monsterList.get(position).getLatents().get(i).getValue() == awakening){
                    counter++;
                }
            }
            viewHolder.awakeningAmount.setText("x" + counter);
        } else {
            switch (awakening) {
                case 1:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_1);
                    break;
                case 2:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_2);
                    break;
                case 3:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_3);
                    break;
                case 4:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_4);
                    break;
                case 5:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_5);
                    break;
                case 6:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_6);
                    break;
                case 7:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_7);
                    break;
                case 8:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_8);
                    break;
                case 9:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_9);
                    break;
                case 10:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_10);
                    break;
                case 11:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_11);
                    break;
                case 12:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_12);
                    break;
                case 13:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_13);
                    break;
                case 14:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_14);
                    break;
                case 15:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_15);
                    break;
                case 16:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_16);
                    break;
                case 17:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_17);
                    break;
                case 18:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_18);
                    break;
                case 19:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_19);
                    break;
                case 20:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_20);
                    break;
                case 21:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_21);
                    break;
                case 22:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_22);
                    break;
                case 23:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_23);
                    break;
                case 24:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_24);
                    break;
                case 25:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_25);
                    break;
                case 26:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_26);
                    break;
                case 27:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_27);
                    break;
                case 28:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_28);
                    break;
                case 29:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_29);
                    break;
                case 30:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_30);
                    break;
                case 31:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_31);
                    break;
                case 32:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_32);
                    break;
                case 33:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_33);
                    break;
                case 34:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_34);
                    break;
                case 35:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_35);
                    break;
                case 36:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_36);
                    break;
                case 37:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_37);
                    break;
                case 38:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_38);
                    break;
                case 39:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_39);
                    break;
                case 40:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_40);
                    break;
                case 41:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_41);
                    break;
                case 42:
                    viewHolder.awakening.setImageResource(R.drawable.awakening_42);
                    break;
                default:
                    viewHolder.awakening.setImageResource(R.drawable.awakening);
                    break;
            }
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
