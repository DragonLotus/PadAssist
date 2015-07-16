package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.anthony.damagecalculator.R;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * Created by Anthony on 7/16/2015.
 */
public class GravityListAdapter extends ArrayAdapter<Double>
{
   private Context mContext;
   private LayoutInflater inflater;
   private ArrayList<Double> gravityList;
   private int resourceId;

   public GravityListAdapter(Context context, int textViewResourceId, ArrayList<Double> gravityList)
   {
      super(context, textViewResourceId, gravityList);
      mContext = context;
      this.gravityList = gravityList;
      this.resourceId = textViewResourceId;
   }

   public View getView(int position, View convertView, ViewGroup parent)
   {

      ViewHolder viewHolder = null;
      if (convertView == null)
      {
         inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = inflater.inflate(resourceId, parent, false);
         viewHolder = new ViewHolder();
         viewHolder.gravityText = (TextView) convertView.findViewById(R.id.gravityText);
         convertView.setTag(R.string.viewHolder, viewHolder);
      } else
      {
         viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
      }
      DecimalFormat df = new DecimalFormat("#%");
      viewHolder.gravityText.setText(df.format(gravityList.get(position)));

      return convertView;
   }

   static class ViewHolder
   {
      TextView gravityText;
   }

}
