package com.padassist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/18/2015.
 */
public class AwakeningGridFilterAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> awakeningsList;
    private int resourceId;
    private Boolean isEnable = false;
    private ArrayList<Integer> filterList;

    public AwakeningGridFilterAdapter(Context context, ArrayList<Integer> awakenings, ArrayList<Integer> filterList) {
        mContext = context;
        awakeningsList = awakenings;
        this.filterList = filterList;
    }

    @Override
    public int getCount() {
        return awakeningsList.size();
    }

    @Override
    public Object getItem(int position) {
        return awakeningsList.get(position);
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
            convertView = inflater.inflate(R.layout.type_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.typePicture = (ImageView) convertView.findViewById(R.id.typePicture);

            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }

        if(filterList.contains(awakeningsList.get(position))){
            viewHolder.typePicture.setImageResource(ImageResourceUtil.monsterAwakening(awakeningsList.get(position)));
        } else {
            viewHolder.typePicture.setImageResource(ImageResourceUtil.monsterAwakeningGray(awakeningsList.get(position)));
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView typePicture;

    }
}
