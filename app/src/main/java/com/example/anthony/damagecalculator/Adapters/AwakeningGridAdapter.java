package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/18/2015.
 */
public class AwakeningGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> awakeningList;
    private ArrayList<Integer> awakeningListAll;
    private ArrayList<Integer> awakeningAmountList;
    private int resourceId;

    public AwakeningGridAdapter(Context context, ArrayList<Integer> awakenings){
        mContext = context;
        awakeningListAll = awakenings;
        trimAwakenings();
    }

    @Override
    public int getCount() {
        return awakeningList.size();
    }

    @Override
    public Object getItem(int position) {
        return awakeningList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.awakening_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.awakeningAmount = (TextView) convertView.findViewById(R.id.awakeningAmount);
            viewHolder.awakeningPicture = (ImageView) convertView.findViewById(R.id.awakeningPicture);

            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }

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
        }
        viewHolder.awakeningAmount.setText("x" + awakeningAmountList.get(position));
        return convertView;
    }


    static class ViewHolder {
        TextView awakeningAmount;
        ImageView awakeningPicture;

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
