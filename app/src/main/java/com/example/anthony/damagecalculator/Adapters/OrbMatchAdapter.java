package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.anthony.damagecalculator.Data.Color;
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
                viewHolder.orbMatchTotal = (TextView) convertView.findViewById(R.id.orbMatchTotal);
                viewHolder.orbImage = (ImageView) convertView.findViewById(R.id.orbImage);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

//            viewHolder.name.setText(mContentList.get(position).getCountryName());
            OrbMatch currentMatches = orbMatches.get(position);
           String rowResult = Integer.toString(currentMatches.getOrbsLinked()) + " Linked, " + Integer.toString(currentMatches.getNumOrbPlus()) + "+, Row: " + Boolean.toString(currentMatches.checkIfRow());
            viewHolder.orbMatchTotal.setText(rowResult);
            if(currentMatches.getColor() == Color.RED) {
                viewHolder.orbImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.redorb));

            }
            if(currentMatches.getColor() == Color.BLUE) {
                viewHolder.orbImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.blueorb));

            }
            if(currentMatches.getColor() == Color.GREEN) {
                viewHolder.orbImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.greenorb));

            }
            if(currentMatches.getColor() == Color.LIGHT) {
                viewHolder.orbImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.lightorb));

            }
            if(currentMatches.getColor() == Color.DARK) {
                viewHolder.orbImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.darkorb));

            }


            return convertView;
        }

        static class ViewHolder {
            TextView orbMatchTotal;
            ImageView orbImage;
        }

}
