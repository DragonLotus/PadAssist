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
public class AwakeningGridRecycler extends RecyclerView.Adapter<AwakeningGridRecycler.ViewHolder> {

    private ArrayList<Integer> awakeningList;
    private ArrayList<Integer> awakeningListAll;
    private ArrayList<Integer> awakeningAmountList;
    private Context mContext;
    private LayoutInflater inflater;

    private View.OnClickListener onItemClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
        }
    };

    public AwakeningGridRecycler(Context context, ArrayList<Integer> awakenings){
        mContext = context;
        awakeningListAll = awakenings;
        Log.d("AwakeningGridRecycler", "awakeningList is: " + awakeningListAll);
        trimAwakenings();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        switch (awakeningList.get(position)) {
            case 1:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_1);
                break;
            case 2:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_2);
                break;
            case 3:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_3);
                break;
            case 4:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_4);
                break;
            case 5:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_5);
                break;
            case 6:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_6);
                break;
            case 7:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_7);
                break;
            case 8:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_8);
                break;
            case 9:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_9);
                break;
            case 10:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_10);
                break;
            case 11:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_11);
                break;
            case 12:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_12);
                break;
            case 13:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_13);
                break;
            case 14:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_14);
                break;
            case 15:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_15);
                break;
            case 16:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_16);
                break;
            case 17:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_17);
                break;
            case 18:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_18);
                break;
            case 19:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_19);
                break;
            case 20:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_20);
                break;
            case 21:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_21);
                break;
            case 22:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_22);
                break;
            case 23:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_23);
                break;
            case 24:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_24);
                break;
            case 25:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_25);
                break;
            case 26:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_26);
                break;
            case 27:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_27);
                break;
            case 28:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_28);
                break;
            case 29:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_29);
                break;
            case 30:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_30);
                break;
            case 31:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_31);
                break;
            case 32:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_32);
                break;
            case 33:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_33);
                break;
            case 34:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_34);
                break;
            case 35:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_35);
                break;
            case 36:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_36);
                break;
            case 37:
                viewHolder.awakeningPicture.setImageResource(R.drawable.awakening_37);
                break;
        }
        viewHolder.awakeningAmount.setText("x" + awakeningAmountList.get(position));

        viewHolder.itemView.setOnClickListener(onItemClickListener);
        viewHolder.itemView.setTag(viewHolder);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.awakening_grid, parent, false));
    }

    @Override
    public int getItemCount() {
        return awakeningList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView awakeningAmount;
        ImageView awakeningPicture;

        public ViewHolder(View convertView){
            super(convertView);
            awakeningAmount = (TextView) convertView.findViewById(R.id.awakeningAmount);
            awakeningPicture = (ImageView) convertView.findViewById(R.id.awakeningPicture);
        }
    }

    private void trimAwakenings(){
        if(awakeningList != null){
            awakeningList.clear();
        } else {
            awakeningList = new ArrayList<>();
        }
        if(awakeningAmountList != null){
            awakeningAmountList.clear();
        } else {
            awakeningAmountList = new ArrayList<>();
        }
        Log.d("Awakening Grid", "awakeningListAll is: " + awakeningListAll);
        int amount = 0;
        int awakening = 0;
        for (int i = 0; i < awakeningListAll.size(); i++){
            if(awakening == awakeningListAll.get(i)){
                amount++;
                if(!awakeningList.contains(awakening)){
                    awakeningList.add(awakening);
                } else {
                    awakeningListAll.remove(i);
                    i--;
                }
            } else {
                if(amount > 0){
                    awakeningAmountList.add(amount);
                }
                amount = 1;
                awakening = awakeningListAll.get(i);
                if(!awakeningList.contains(awakening)){
                    awakeningList.add(awakening);
                }
            }
        }
        awakeningAmountList.add(amount);
        Log.d("Awakening Grid", "awakeningAmountList is: " + awakeningAmountList);
    }
}
