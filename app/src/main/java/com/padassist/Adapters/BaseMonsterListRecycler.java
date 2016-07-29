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

import com.padassist.Data.BaseMonster;
import com.padassist.Data.LeaderSkill;
import com.padassist.R;
import com.padassist.Util.BaseMonsterListRecyclerUtil;
import com.padassist.Util.BaseMonsterListUtil;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class BaseMonsterListRecycler extends BaseMonsterListRecyclerUtil {

    public BaseMonsterListRecycler(Context context, ArrayList<BaseMonster> monsterList, RecyclerView monsterListView, View.OnClickListener monsterListOnClickListener, View.OnLongClickListener monsterListOnLongClickListener) {
        mContext = context;
        this.monsterList = monsterList;
        this.monsterListOnClickListener = monsterListOnClickListener;
        this.monsterListOnLongClickListener = monsterListOnLongClickListener;
        this.monsterListView = monsterListView;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
