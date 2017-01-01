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

import com.padassist.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/11/2015.
 */
public class LatentSpinnerAdapter extends ArrayAdapter<Integer> {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> latents;
    private int resourceId;

    public LatentSpinnerAdapter(Context context, int resource,  ArrayList<Integer> latents) {
        super(context, resource, latents);
        mContext = context;
        this.latents = latents;
        this.resourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.latentName = (TextView) convertView.findViewById(R.id.latentName);
            viewHolder.latentDesc = (TextView) convertView.findViewById(R.id.latentDesc);
            viewHolder.latentIcon = (ImageView) convertView.findViewById(R.id.latentIcon);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }

        switch(latents.get(position)){
            case 0:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_blank);
                viewHolder.latentName.setText("None");
                viewHolder.latentDesc.setText("");
                break;
            case 1:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_1);
                viewHolder.latentName.setText("Enhanced HP");
                viewHolder.latentDesc.setText("");
                break;
            case 2:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_2);
                viewHolder.latentName.setText("Enhanced ATK");
                viewHolder.latentDesc.setText("");
                break;
            case 3:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_3);
                viewHolder.latentName.setText("Enhanced RCV");
                viewHolder.latentDesc.setText("");
                break;
            case 4:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_4);
                viewHolder.latentName.setText("Extend Time");
                viewHolder.latentDesc.setText("");
                break;
            case 5:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_5);
                viewHolder.latentName.setText("Auto-Recover");
                viewHolder.latentDesc.setText("");
                break;
            case 6:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_6);
                viewHolder.latentName.setText("Reduce Fire Damage");
                viewHolder.latentDesc.setText("");
                break;
            case 7:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_7);
                viewHolder.latentName.setText("Reduce Water Damage");
                viewHolder.latentDesc.setText("");
                break;
            case 8:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_8);
                viewHolder.latentName.setText("Reduce Wood Damage");
                viewHolder.latentDesc.setText("");
                break;
            case 9:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_9);
                viewHolder.latentName.setText("Reduce Light Damage");
                viewHolder.latentDesc.setText("");
                break;
            case 10:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_10);
                viewHolder.latentName.setText("Reduce Dark Damage");
                viewHolder.latentDesc.setText("");
                break;
            case 11:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_11);
                viewHolder.latentName.setText("Skill Delay Resist");
                viewHolder.latentDesc.setText("");
                break;
            case 12:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_12);
                viewHolder.latentName.setText("Improve All Stats");
                viewHolder.latentDesc.setText("");
                break;
            case 13:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_13);
                viewHolder.latentName.setText("God Killer");
                viewHolder.latentDesc.setText("");
                break;
            case 14:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_14);
                viewHolder.latentName.setText("Dragon Killer");
                viewHolder.latentDesc.setText("");
                break;
            case 15:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_15);
                viewHolder.latentName.setText("Devil Killer");
                viewHolder.latentDesc.setText("");
                break;
            case 16:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_16);
                viewHolder.latentName.setText("Machine Killer");
                viewHolder.latentDesc.setText("");
                break;
            case 17:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_17);
                viewHolder.latentName.setText("Balanced Killer");
                viewHolder.latentDesc.setText("");
                break;
            case 18:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_18);
                viewHolder.latentName.setText("Attacker Killer");
                viewHolder.latentDesc.setText("");
                break;
            case 19:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_19);
                viewHolder.latentName.setText("Physical Killer");
                viewHolder.latentDesc.setText("");
                break;
            case 20:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_20);
                viewHolder.latentName.setText("Healer Killer");
                viewHolder.latentDesc.setText("");
                break;
            default:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_blank);
                viewHolder.latentName.setText("Name goes here");
                viewHolder.latentDesc.setText("");
                break;
        }
        viewHolder.latentDesc.setHorizontallyScrolling(true);
        viewHolder.latentDesc.setSelected(true);

        viewHolder.latentName.setHorizontallyScrolling(true);
        viewHolder.latentName.setSelected(true);
        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.latentName = (TextView) convertView.findViewById(R.id.latentName);
            viewHolder.latentDesc = (TextView) convertView.findViewById(R.id.latentDesc);
            viewHolder.latentIcon = (ImageView) convertView.findViewById(R.id.latentIcon);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            convertView.setTag(R.string.viewHolder, viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }

        switch(latents.get(position)){
            case 0:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_blank);
                viewHolder.latentName.setText("None");
                viewHolder.latentDesc.setText("");
                break;
            case 1:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_1);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("Base HP increased by 1.5%.");
                break;
            case 2:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_2);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("Base ATK increased by 1%.");
                break;
            case 3:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_3);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("Base RCV increased by 5%.");
                break;
            case 4:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_4);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("Additional 0.05 seconds for matching.");
                break;
            case 5:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_5);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("Heal 15% of RCV every turn a match is made.");
                break;
            case 6:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_6);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1% less damage taken from Fire type enemies.");
                break;
            case 7:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_7);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1% less damage taken from Water type enemies.");
                break;
            case 8:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_8);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1% less damage taken from Wood type enemies.");
                break;
            case 9:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_9);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1% less damage taken from Light type enemies.");
                break;
            case 10:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_10);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1% less damage taken from Dark type enemies.");
                break;
            case 11:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_11);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("Reduce skill delay by 1 turn.");
                break;
            case 12:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_12);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("Base HP increased by 1.5%, base ATK increased by 1%, base RCV increased by 5%. Takes 2 Latent slots.");
                break;
            case 13:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_13);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1.5x bonus damage versus God types");
                break;
            case 14:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_14);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1.5x bonus damage versus Dragon types");
                break;
            case 15:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_15);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1.5x bonus damage versus Devil types");
                break;
            case 16:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_16);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1.5x bonus damage versus Machine types");
                break;
            case 17:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_17);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1.5x bonus damage versus Balanced types");
                break;
            case 18:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_18);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1.5x bonus damage versus Attacker types");
                break;
            case 19:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_19);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1.5x bonus damage versus Physical types");
                break;
            case 20:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_20);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("1.5x bonus damage versus Healer types");
                break;
            default:
                viewHolder.latentIcon.setImageResource(R.drawable.latent_awakening_blank);
                viewHolder.latentName.setText("");
                viewHolder.latentDesc.setText("Description goes here.");
                break;
        }
        viewHolder.latentDesc.setHorizontallyScrolling(true);
        viewHolder.latentDesc.setSelected(true);

        viewHolder.latentName.setHorizontallyScrolling(true);
        viewHolder.latentName.setSelected(true);

        if (position % 2 == 1) {
            viewHolder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        } else {
            viewHolder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView latentName, latentDesc;
        ImageView latentIcon;
        RelativeLayout relativeLayout;
    }
}
