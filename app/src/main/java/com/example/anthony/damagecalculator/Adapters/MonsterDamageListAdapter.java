package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;
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
    private int resourceId, combos;
    private boolean hasEnemy, hasAbsorb, hasReduction, hasDamageThreshold;
    private ArrayList<OrbMatch> orbMatches;
    private Enemy enemy;

    public MonsterDamageListAdapter(Context context, int textViewResourceId, ArrayList<Monster> monsterList, boolean hasEnemy, ArrayList<OrbMatch> orbMatches, Enemy enemy, int combos, boolean hasAbsorb, boolean hasReduction, boolean hasDamageThreshold) {
        super(context, textViewResourceId, monsterList);
        mContext = context;
        this.monsterList = monsterList;
        this.resourceId = textViewResourceId;
        this.hasEnemy = hasEnemy;
        this.orbMatches = orbMatches;
        this.enemy = enemy;
        this.combos = combos;
        this.hasAbsorb = hasAbsorb;
        this.hasReduction = hasReduction;
        this.hasDamageThreshold = hasDamageThreshold;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
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
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        int totalPlus = monsterList.get(position).getAtkPlus() + monsterList.get(position).getHpPlus() + monsterList.get(position).getRcvPlus();
        viewHolder.monsterPlus.setText(" +" + Integer.toString(totalPlus) + " ");
        viewHolder.monsterAwakenings.setText(" " + Integer.toString(monsterList.get(position).getCurrentAwakenings()));
        if (monsterList.get(position).getCurrentAwakenings() == monsterList.get(position).getMaxAwakenings()) {
            viewHolder.monsterAwakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monsterAwakenings.setText("");
        }
        //Needs to get # of orb awakenings from team object maybe
        viewHolder.monsterElement1Damage.setText(monsterList.get(position).getElement1DamageString(orbMatches, 6, combos));
        viewHolder.monsterElement2Damage.setText(monsterList.get(position).getElement2DamageString(orbMatches, 6, combos));
        if (hasEnemy) {
            if (hasReduction) {
                viewHolder.monsterElement1DamageEnemy.setText("(" + monsterList.get(position).getElement1DamageReductionString(orbMatches, 6, enemy, combos) + ")");
                viewHolder.monsterElement2DamageEnemy.setText("(" + monsterList.get(position).getElement2DamageReductionString(orbMatches, 6, enemy, combos) + ")");
            } else {
                viewHolder.monsterElement1DamageEnemy.setText("(" + monsterList.get(position).getElement1DamageEnemyString(orbMatches, 6, enemy, combos) + ")");
                viewHolder.monsterElement2DamageEnemy.setText("(" + monsterList.get(position).getElement2DamageEnemyString(orbMatches, 6, enemy, combos) + ")");
            }

        } else {
            viewHolder.monsterElement1DamageEnemy.setVisibility(View.INVISIBLE);
            viewHolder.monsterElement2DamageEnemy.setVisibility(View.INVISIBLE);
        }
        setTextColors(position, viewHolder);
        return convertView;
    }

    static class ViewHolder {
        TextView monsterName, monsterPlus, monsterAwakenings, monsterElement1Damage, monsterElement1DamageEnemy, monsterElement2Damage, monsterElement2DamageEnemy;
        ImageView monsterPicture;
    }

    public void setTextColors(int position, ViewHolder viewHolder) {
        if (monsterList.get(position).getElement1().equals(com.example.anthony.damagecalculator.Data.Color.RED)) {
            viewHolder.monsterElement1Damage.setTextColor(Color.parseColor("#FF0000"));
            viewHolder.monsterElement1DamageEnemy.setTextColor(Color.parseColor("#FF0000"));
        } else if (monsterList.get(position).getElement1().equals(com.example.anthony.damagecalculator.Data.Color.BLUE)) {
            viewHolder.monsterElement1Damage.setTextColor(Color.parseColor("#4444FF"));
            viewHolder.monsterElement1DamageEnemy.setTextColor(Color.parseColor("#4444FF"));
        } else if (monsterList.get(position).getElement1().equals(com.example.anthony.damagecalculator.Data.Color.GREEN)) {
            viewHolder.monsterElement1Damage.setTextColor(Color.parseColor("#00FF00"));
            viewHolder.monsterElement1DamageEnemy.setTextColor(Color.parseColor("#00FF00"));
        } else if (monsterList.get(position).getElement1().equals(com.example.anthony.damagecalculator.Data.Color.LIGHT)) {
            viewHolder.monsterElement1Damage.setTextColor(Color.parseColor("#F0F000"));
            viewHolder.monsterElement1DamageEnemy.setTextColor(Color.parseColor("#F0F000"));
        } else if (monsterList.get(position).getElement1().equals(com.example.anthony.damagecalculator.Data.Color.DARK)) {
            viewHolder.monsterElement1Damage.setTextColor(Color.parseColor("#AA00FF"));
            viewHolder.monsterElement1DamageEnemy.setTextColor(Color.parseColor("#AA00FF"));
        }
        if (monsterList.get(position).getElement2().equals(com.example.anthony.damagecalculator.Data.Color.RED)) {
            viewHolder.monsterElement2Damage.setTextColor(Color.parseColor("#FF0000"));
            viewHolder.monsterElement2DamageEnemy.setTextColor(Color.parseColor("#FF0000"));
        } else if (monsterList.get(position).getElement2().equals(com.example.anthony.damagecalculator.Data.Color.BLUE)) {
            viewHolder.monsterElement2Damage.setTextColor(Color.parseColor("#4444FF"));
            viewHolder.monsterElement2DamageEnemy.setTextColor(Color.parseColor("#4444FF"));
        } else if (monsterList.get(position).getElement2().equals(com.example.anthony.damagecalculator.Data.Color.GREEN)) {
            viewHolder.monsterElement2Damage.setTextColor(Color.parseColor("#00CC00"));
            viewHolder.monsterElement2DamageEnemy.setTextColor(Color.parseColor("#00CC00"));
        } else if (monsterList.get(position).getElement2().equals(com.example.anthony.damagecalculator.Data.Color.LIGHT)) {
            viewHolder.monsterElement2Damage.setTextColor(Color.parseColor("#F0F000"));
            viewHolder.monsterElement2DamageEnemy.setTextColor(Color.parseColor("#F0F000"));
        } else if (monsterList.get(position).getElement2().equals(com.example.anthony.damagecalculator.Data.Color.DARK)) {
            viewHolder.monsterElement2Damage.setTextColor(Color.parseColor("#AA00FF"));
            viewHolder.monsterElement2DamageEnemy.setTextColor(Color.parseColor("#AA00FF"));
        }
    }
}
