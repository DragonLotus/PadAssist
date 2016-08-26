package com.padassist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Data.LeaderSkill;
import com.padassist.Data.Monster;
import com.padassist.R;
import com.padassist.Util.SaveMonsterListRecyclerUtil;
import com.padassist.Util.SaveMonsterListUtil;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class SaveMonsterListRecycler extends SaveMonsterListRecyclerUtil {

    public SaveMonsterListRecycler(Context context, ArrayList<Monster> monsterList, RecyclerView monsterListView, View.OnClickListener monsterListOnClickListener, View.OnLongClickListener monsterListOnLongClickListener, View.OnClickListener deleteOnClickListener, boolean isGrid){
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListOnClickListener = monsterListOnClickListener;
        this.monsterListOnLongClickListener = monsterListOnLongClickListener;
        this.monsterListView = monsterListView;
        this.deleteOnClickListener = deleteOnClickListener;
        this.isGrid = isGrid;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
