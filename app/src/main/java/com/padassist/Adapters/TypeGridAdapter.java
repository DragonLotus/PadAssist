package com.padassist.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/18/2015.
 */
public class TypeGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> typeList;
    private int resourceId;
    private Boolean isEnable = false;
    private ArrayList<Integer> filterList;

    public TypeGridAdapter(Context context, ArrayList<Integer> types, ArrayList<Integer> filterList) {
        mContext = context;
        typeList = types;
        this.filterList = filterList;
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeList.get(position);
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

        if(typeList.get(position) >= 0){
            if (isEnable) {
                if (filterList.contains(typeList.get(position))) {
                    viewHolder.typePicture.setImageResource(ImageResourceUtil.monsterType(typeList.get(position)));
                } else {
                    viewHolder.typePicture.setImageResource(ImageResourceUtil.monsterTypeGray(typeList.get(position)));
                }
            } else {
                viewHolder.typePicture.setImageResource(ImageResourceUtil.monsterTypeDisabled(typeList.get(position)));
            }
        } else{
            viewHolder.typePicture.setVisibility(View.GONE);
        }


        return convertView;
    }


    static class ViewHolder {
        ImageView typePicture;

    }

    public void notifyEnable(Boolean isEnable) {
        this.isEnable = isEnable;
        notifyDataSetChanged();
    }
}
