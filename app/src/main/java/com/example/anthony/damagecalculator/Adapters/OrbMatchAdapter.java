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
import android.widget.Toast;

import com.example.anthony.damagecalculator.Data.Color;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.Fragments.MainFragment;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * Created by Thomas on 7/12/2015.
 */
public class OrbMatchAdapter extends ArrayAdapter<OrbMatch>
{
   private Context mContext;
   private LayoutInflater inflater;
   private Team team;
   private ArrayList<OrbMatch> orbMatches;
   private int resourceId;
   private Toast toast;

   private ImageView.OnClickListener removeOnClickListener = new ImageView.OnClickListener()
   {
      @Override
      public void onClick(View v)
      {
         if(team.sizeOrbMatches() > 0) {
            int position = (int) v.getTag(R.string.index);
            team.removeOrbMatches(position);
            notifyDataSetChanged();
            if (toast != null) {
               toast.cancel();
            }
            toast = Toast.makeText(mContext, "Match Removed", Toast.LENGTH_SHORT);
            toast.show();
         }
      }
   };

   public OrbMatchAdapter(Context context, int textViewResourceId, Team team)
   {
      super(context, textViewResourceId, team.getOrbMatches());
      mContext = context;
      this.team = team;
      //this.orbMatches = orbMatches;
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
         viewHolder.remove = (ImageView) convertView.findViewById(R.id.remove);
         convertView.setTag(R.string.viewHolder, viewHolder);
      } else
      {
         viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
      }

      OrbMatch currentMatch = team.getOrbMatches(position);
      String rowResult = Integer.toString(currentMatch.getOrbsLinked()) + " Linked, " + Integer.toString(currentMatch.getNumOrbPlus()) + "+, Row: " + Boolean.toString(currentMatch.checkIfRow());
      viewHolder.orbMatchTotal.setText(rowResult);

      Drawable orbDrawable = getDrawable(R.drawable.red_orb);
      if (currentMatch.getColor() == Color.DARK)
      {
         orbDrawable = getDrawable(R.drawable.dark_orb);
      }
      if (currentMatch.getColor() == Color.BLUE)
      {
         orbDrawable = getDrawable(R.drawable.blue_orb);
      }
      if (currentMatch.getColor() == Color.GREEN)
      {
         orbDrawable = getDrawable(R.drawable.green_orb);
      }
      if (currentMatch.getColor() == Color.LIGHT)
      {
         orbDrawable = getDrawable(R.drawable.light_orb);
      }
      if (currentMatch.getColor() == Color.HEART)
      {
         orbDrawable = getDrawable(R.drawable.heart_orb);
      }

      viewHolder.orbImage.setImageDrawable(orbDrawable);
      viewHolder.remove.setTag(R.string.index, position);
      viewHolder.remove.setOnClickListener(removeOnClickListener);
      return convertView;
   }

   static class ViewHolder
   {
      TextView orbMatchTotal;
      ImageView orbImage;
      ImageView remove;
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
