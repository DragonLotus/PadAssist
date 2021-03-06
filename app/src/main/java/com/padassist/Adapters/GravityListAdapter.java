package com.padassist.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.padassist.Data.Enemy;
import com.padassist.R;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Anthony on 7/16/2015.
 */
public class GravityListAdapter extends ArrayAdapter<Integer> {
    private Context mContext;
    private LayoutInflater inflater;
    private Enemy enemy;
    private int resourceId;
    private UpdateGravityPercent updateGravityPercent;

    public GravityListAdapter(Context context, int textViewResourceId, Parcelable enemy, UpdateGravityPercent updateGravityPercent, ArrayList<Integer> gravityArrayList) {
        super(context, textViewResourceId, gravityArrayList);
        mContext = context;
        this.enemy = Parcels.unwrap(enemy);
        this.resourceId = textViewResourceId;
        this.updateGravityPercent = updateGravityPercent;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.gravityText = (TextView) convertView.findViewById(R.id.gravityText);
            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        viewHolder.gravityText.setText(String.valueOf(enemy.getGravityList(position)) + "%");

        return convertView;
    }

    public interface UpdateGravityPercent {
        public void updatePercent();
    }

    static class ViewHolder {
        TextView gravityText;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        //updateAwakenings the textview here by looping trhrough all objects and then setting the total percent
        updateGravityPercent.updatePercent();
    }


}
