package com.padassist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.padassist.Data.Monster;
import com.padassist.BaseFragments.SaveMonsterListRecyclerBase;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class SaveMonsterListRecycler extends SaveMonsterListRecyclerBase {

    public SaveMonsterListRecycler(Context context, ArrayList<Monster> monsterList,
                                   RecyclerView monsterListView, View.OnClickListener monsterListOnClickListener,
                                   View.OnLongClickListener monsterListOnLongClickListener,
                                   View.OnClickListener deleteOnClickListener,
                                   View.OnClickListener expandOnClickListener, boolean isGrid,
                                   ClearTextFocus clearTextFocus){
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListOnClickListener = monsterListOnClickListener;
        this.monsterListOnLongClickListener = monsterListOnLongClickListener;
        this.monsterListView = monsterListView;
        this.deleteOnClickListener = deleteOnClickListener;
        this.expandOnClickListener = expandOnClickListener;
        this.isGrid = isGrid;
        this.clearTextFocus = clearTextFocus;

        sixteenDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, mContext.getResources().getDisplayMetrics());
        fortyEightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, mContext.getResources().getDisplayMetrics());
        fiftyFourDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 54, mContext.getResources().getDisplayMetrics());
        eightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mContext.getResources().getDisplayMetrics());

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
