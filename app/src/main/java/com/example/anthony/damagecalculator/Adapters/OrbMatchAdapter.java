package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by Thomas on 7/12/2015.
 */
public class OrbMatchAdapter extends ArrayAdapter<OrbMatch> {
    private Context mContext;
    private LayoutInflater inflater;

    private ArrayList<OrbMatch> orbMatches;
    private int resourceId;
        public OrbMatchAdapter(Context context, int textViewResourceId, ArrayList<OrbMatch> orbMatches) {
            super(context, textViewResourceId, orbMatches);
            mContext = context;
            this.orbMatches = orbMatches;
            this.resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resourceId, parent, false);
                viewHolder = new ViewHolder();
//                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

//            viewHolder.name.setText(mContentList.get(position).getCountryName());

            return convertView;
        }

        static class ViewHolder {
            TextView name;
            //put things you have in your xml here
        }

}
