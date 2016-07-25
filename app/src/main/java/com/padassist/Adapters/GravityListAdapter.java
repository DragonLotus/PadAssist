package com.padassist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.padassist.Data.Enemy;
import com.padassist.R;

/**
 * Created by Anthony on 7/16/2015.
 */
public class GravityListAdapter extends ArrayAdapter<Integer> {
    private Context mContext;
    private LayoutInflater inflater;
    private Enemy enemy;
    private int resourceId;
    private UpdateGravityPercent updateGravityPercent;

    public GravityListAdapter(Context context, int textViewResourceId, Enemy enemy, UpdateGravityPercent updateGravityPercent) {
        super(context, textViewResourceId, enemy.getGravityList());
        mContext = context;
        this.enemy = enemy;
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
        //update the textview here by looping trhrough all objects and then setting the total percent
        updateGravityPercent.updatePercent();
    }


}
