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

/**
 * Created by Anthony on 7/16/2015.
 */
public class GravityListAdapter extends ArrayAdapter<Integer>
{
   private Context mContext;
   private LayoutInflater inflater;
   private ArrayList<Integer> gravityList;
   private int resourceId;
   private UpdateGravityPercent updateGravityPercent;

   public GravityListAdapter(Context context, int textViewResourceId, ArrayList<Integer> gravityList, UpdateGravityPercent updateGravityPercent)
   {
      super(context, textViewResourceId, gravityList);
      mContext = context;
      this.gravityList = gravityList;
      this.resourceId = textViewResourceId;
      this.updateGravityPercent = updateGravityPercent;
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
      viewHolder.gravityText.setText(gravityList.get(position).toString() + "%");

      return convertView;
   }

   public interface UpdateGravityPercent
   {
      public void updatePercent();
   }

   static class ViewHolder
   {
      TextView gravityText;
   }

   @Override
   public void notifyDataSetChanged()
   {
      super.notifyDataSetChanged();
      //update the textview here by looping trhrough all objects and then setting the total percent
      updateGravityPercent.updatePercent();
   }


}