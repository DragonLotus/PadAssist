package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;

import java.util.ArrayList;

/**
 * Created by Anthony on 7/14/2015.
 */
public class MonsterListAdapter extends ArrayAdapter<Monster>
{
   private Context mContext;
   private LayoutInflater inflater;
   private ArrayList<Monster> monsterList;
   private int resourceId;
   private Toast toast;

   public MonsterListAdapter(Context context, int textViewResourceId, ArrayList<Monster> monsterList)
   {
      super(context, textViewResourceId, monsterList);
      mContext = context;
      this.monsterList = monsterList;
      this.resourceId = textViewResourceId;
   }

   static class ViewHolder
   {
      TextView name, monsterPlus, monsterAwakenings, monsterLevelValue, monsterHP, monsterATK, monsterRCV;
      ImageView monsterPicture;

   }


}
