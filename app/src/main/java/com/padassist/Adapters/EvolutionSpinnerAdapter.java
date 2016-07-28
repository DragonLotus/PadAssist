package com.padassist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.Data.BaseMonster;
import com.padassist.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/11/2015.
 */
public class EvolutionSpinnerAdapter extends ArrayAdapter<Long> {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Long> evolutions;
    private int resourceId;

    public EvolutionSpinnerAdapter(Context context, int resource, int textViewResourceId, ArrayList<Long> evolutions) {
        super(context, resource, textViewResourceId, evolutions);
        mContext = context;
        this.evolutions = evolutions;
        this.resourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.monsterName = (TextView) convertView.findViewById(R.id.monsterName);
            viewHolder.rarity = (TextView) convertView.findViewById(R.id.rarity);
            viewHolder.type1 = (ImageView) convertView.findViewById(R.id.type1);
            viewHolder.type2 = (ImageView) convertView.findViewById(R.id.type2);
            viewHolder.type3 = (ImageView) convertView.findViewById(R.id.type3);
            viewHolder.monsterPicture = (ImageView) convertView.findViewById(R.id.monsterPicture);
            viewHolder.rarityStar = (ImageView) convertView.findViewById(R.id.rarityStar);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.monsterName.setText(BaseMonster.getMonsterId(evolutions.get(position)).getName());
        viewHolder.monsterPicture.setImageResource(BaseMonster.getMonsterId(evolutions.get(position)).getMonsterPicture());
        viewHolder.rarity.setText("" + BaseMonster.getMonsterId(evolutions.get(position)).getRarity());
        viewHolder.rarityStar.setColorFilter(0xFFD4D421);

        if(evolutions.get(position) == 0){
            if(evolutions.size() == 1){
                viewHolder.monsterName.setText("No evolutions available.");
            }else {
                viewHolder.monsterName.setText("No evolution selected.");
            }
            viewHolder.monsterName.setPadding(0, 16, 0, 16);
            viewHolder.monsterPicture.setVisibility(View.GONE);
            viewHolder.rarity.setVisibility(View.GONE);
            viewHolder.rarityStar.setVisibility(View.GONE);
            viewHolder.type1.setVisibility(View.GONE);
            viewHolder.type2.setVisibility(View.GONE);
            viewHolder.type3.setVisibility(View.GONE);
        } else {
            viewHolder.monsterName.setPadding(0, 0, 0, 0);
            viewHolder.monsterPicture.setVisibility(View.VISIBLE);
            viewHolder.rarity.setVisibility(View.VISIBLE);
            viewHolder.rarityStar.setVisibility(View.VISIBLE);
            viewHolder.type1.setVisibility(View.VISIBLE);
            viewHolder.type2.setVisibility(View.VISIBLE);
            viewHolder.type3.setVisibility(View.VISIBLE);
        }

        switch(BaseMonster.getMonsterId(evolutions.get(position)).getType1()){
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
        switch(BaseMonster.getMonsterId(evolutions.get(position)).getType2()){
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
        switch(BaseMonster.getMonsterId(evolutions.get(position)).getType3()){
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
        if(position%2 == 1){
            viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
        } else {
            viewHolder.relativeLayout.setBackgroundColor(Color.parseColor("#e8e8e8"));
        }
        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    static class ViewHolder {
        TextView monsterName, rarity;
        ImageView monsterPicture, type1, type2, type3, rarityStar;
        RelativeLayout relativeLayout;
    }
}
