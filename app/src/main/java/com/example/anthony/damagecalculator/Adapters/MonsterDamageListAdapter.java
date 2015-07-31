package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 7/18/2015.
 */
public class MonsterDamageListAdapter extends ArrayAdapter<Monster> {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Monster> monsterList;
    private int resourceId;
    private boolean hasEnemy;

    public MonsterDamageListAdapter(Context context, int textViewResourceId, ArrayList<Monster> monsterList, boolean hasEnemy)
    {
        super(context, textViewResourceId, monsterList);
        mContext = context;
        this.monsterList = monsterList;
        this.resourceId = textViewResourceId;
        this.hasEnemy = hasEnemy;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder= null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            viewHolder.monsterPlus = (TextView) convertView.findViewById(R.id.monsterPlus);
            viewHolder.monsterAwakenings = (TextView) convertView.findViewById(R.id.monsterAwakenings);
            viewHolder.monsterElement1Damage = (TextView) convertView.findViewById(R.id.monsterElement1Damage);
            viewHolder.monsterElement1DamageEnemy = (TextView) convertView.findViewById(R.id.monsterElement1DamageEnemy);
            viewHolder.monsterElement2Damage = (TextView) convertView.findViewById(R.id.monsterElement2Damage);
            viewHolder.monsterElement2DamageEnemy = (TextView) convertView.findViewById(R.id.monsterElement2DamageEnemy);

            convertView.setTag(R.string.viewHolder, viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        //Needs to take in monster stats. But yea, pretty much the gist of it.
            viewHolder.monsterElement1Damage.setText(String.valueOf(DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1)) + " ");
        viewHolder.monsterElement1DamageEnemy.setText("(" + String.valueOf(DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1)) + ")");
        viewHolder.monsterElement2Damage.setText(String.valueOf(DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1)) + " ");
        viewHolder.monsterElement2DamageEnemy.setText("(" + String.valueOf(DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1)) + ")");
        int totalPlus = monsterList.get(position).getAtkPlus() + monsterList.get(position).getHpPlus() + monsterList.get(position).getRcvPlus();
        viewHolder.monsterPlus.setText(" +" + Integer.toString(totalPlus) + " ");
        if (monsterList.get(position).getCurrentAwakenings() == monsterList.get(position).getMaxAwakenings()){
            viewHolder.monsterAwakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monsterAwakenings.setText("");
        }
        if(!hasEnemy){
            viewHolder.monsterElement1DamageEnemy.setVisibility(View.INVISIBLE);
            viewHolder.monsterElement2DamageEnemy.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    static class ViewHolder{
        TextView monsterName, monsterPlus, monsterAwakenings, monsterElement1Damage, monsterElement1DamageEnemy, monsterElement2Damage, monsterElement2DamageEnemy;
        ImageView monsterPicture;
    }
}
