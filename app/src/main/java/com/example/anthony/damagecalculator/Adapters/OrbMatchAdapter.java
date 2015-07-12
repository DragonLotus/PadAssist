package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
public class OrbMatchAdapter extends ArrayAdapter<OrbMatch>
{
   private Context mContext;
   private LayoutInflater inflater;

   private ArrayList<OrbMatch> orbMatches;
   private int resourceId;

   public OrbMatchAdapter(Context context, int textViewResourceId, ArrayList<OrbMatch> orbMatches)
   {
      super(context, textViewResourceId, orbMatches);
      mContext = context;
      this.orbMatches = orbMatches;
      this.resourceId = textViewResourceId;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent)
   {

      ViewHolder viewHolder = null;
      if (convertView == null)
      {
         inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = inflater.inflate(resourceId, parent, false);
         viewHolder = new ViewHolder();
         viewHolder.orbMatchTotal = (TextView) convertView.findViewById(R.id.orbMatchTotal);
         viewHolder.orbImage = (ImageView) convertView.findViewById(R.id.orbImage);
         convertView.setTag(viewHolder);
      } else
      {
         viewHolder = (ViewHolder) convertView.getTag();
      }

      OrbMatch currentMatch = orbMatches.get(position);
      String rowResult = Integer.toString(currentMatch.getOrbsLinked()) + " Linked, " + Integer.toString(currentMatch.getNumOrbPlus()) + "+, Row: " + Boolean.toString(currentMatch.checkIfRow());
      viewHolder.orbMatchTotal.setText(rowResult);

      Drawable orbDrawable = getDrawable(R.drawable.darkorb);
      if (currentMatch.getColor() == Color.RED)
      {
         orbDrawable = getDrawable(R.drawable.redorb);
      }
      if (currentMatch.getColor() == Color.BLUE)
      {
         orbDrawable = getDrawable(R.drawable.blueorb);
      }
      if (currentMatch.getColor() == Color.GREEN)
      {
         orbDrawable = getDrawable(R.drawable.greenorb);
      }
      if (currentMatch.getColor() == Color.LIGHT)
      {
         orbDrawable = getDrawable(R.drawable.lightorb);
      }

      viewHolder.orbImage.setImageDrawable(orbDrawable);

      return convertView;
   }

   static class ViewHolder
   {
      TextView orbMatchTotal;
      ImageView orbImage;
   }

   private Drawable getDrawable(int drawable)
   {
      if (Build.VERSION.SDK_INT >= 22)
      {
         return mContext.getDrawable(drawable);
      }
      return mContext.getResources().getDrawable(drawable);
   }

}
