package com.padassist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Data.Element;
import com.padassist.Data.OrbMatch;
import com.padassist.R;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class OrbMatchRecycler extends RecyclerView.Adapter<OrbMatchRecycler.ViewHolder> {

    private RealmResults<OrbMatch> orbMatches;
    private Context mContext;
    private LayoutInflater inflater;
    private View.OnClickListener orbMatchOnClickListener;
    private Toast toast;
    private View.OnClickListener removeOnClickListener;

//    private ImageView.OnClickListener removeOnClickListener = new ImageView.OnClickListener()
//    {
//        @Override
//        public void onClick(View v)
//        {
//            if(orbMatches.size() > 0) {
//                int position = (int) v.getTag(R.string.index);
//                orbMatches.remove(position);
//                notifyDataSetChanged();
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(mContext, "Match Removed", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        }
//    };

    public OrbMatchRecycler(Context context, RealmResults<OrbMatch> orbMatches, View.OnClickListener orbMatchOnClickListener, View.OnClickListener removeOnClickListener){
        mContext = context;
        this.orbMatches = orbMatches;
        this.orbMatchOnClickListener = orbMatchOnClickListener;
        this.removeOnClickListener = removeOnClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        OrbMatch currentMatch = orbMatches.get(position);
        String rowResult = Integer.toString(currentMatch.getOrbsLinked()) + " Linked, " + Integer.toString(currentMatch.getNumOrbPlus()) + "+, Row: " + Boolean.toString(currentMatch.isRow()) + ", Cross: " + Boolean.toString(currentMatch.isCross());
        viewHolder.orbMatchTotal.setText(rowResult);

        int orbImageResource = R.drawable.red_orb;

        switch (currentMatch.getElement()){
            case RED:
                orbImageResource = R.drawable.red_orb;
                break;
            case BLUE:
                orbImageResource = R.drawable.blue_orb;
                break;
            case GREEN:
                orbImageResource = R.drawable.green_orb;
                break;
            case LIGHT:
                orbImageResource = R.drawable.light_orb;
                break;
            case HEART:
                orbImageResource = R.drawable.heart_orb;
                break;
            case JAMMER:
                orbImageResource = R.drawable.jammer_orb;
                break;
            case POISON:
                orbImageResource = R.drawable.poison_orb;
                break;
            case MORTAL_POISON:
                orbImageResource = R.drawable.mortal_poison_orb;
                break;
        }

        viewHolder.orbMatchTotal.setSelected(true);
        viewHolder.orbImage.setImageResource(orbImageResource);
        viewHolder.remove.setTag(R.string.index, position);
        viewHolder.remove.setOnClickListener(removeOnClickListener);

        viewHolder.itemView.setOnClickListener(orbMatchOnClickListener);
        viewHolder.itemView.setTag(R.string.index, position);

        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        } else {
            viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.orb_match_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return orbMatches.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orbMatchTotal;
        ImageView orbImage;
        ImageView remove;
        RelativeLayout relativeLayout;

        public ViewHolder(View convertView){
            super(convertView);
            orbMatchTotal = (TextView) convertView.findViewById(R.id.orbMatchTotal);
            orbImage = (ImageView) convertView.findViewById(R.id.orbImage);
            remove = (ImageView) convertView.findViewById(R.id.remove);
            relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
        }
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
