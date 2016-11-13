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
public class LatentGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> latentsList;
    private int resourceId;
    private Boolean isEnable = false;
    private ArrayList<Integer> filterList;

    public LatentGridAdapter(Context context, ArrayList<Integer> latents, ArrayList<Integer> filterList) {
        mContext = context;
        latentsList = latents;
        this.filterList = filterList;
    }

    @Override
    public int getCount() {
        return latentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return latentsList.get(position);
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

        if(filterList.contains(latentsList.get(position))){
            viewHolder.typePicture.setImageResource(ImageResourceUtil.monsterLatent(latentsList.get(position)));
        } else {
            viewHolder.typePicture.setImageResource(ImageResourceUtil.monsterLatentGray(latentsList.get(position)));
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView typePicture;

    }
}
