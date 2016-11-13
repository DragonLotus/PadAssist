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

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class OrbMatchRecycler extends RecyclerView.Adapter<OrbMatchRecycler.ViewHolder> {

    private ArrayList<OrbMatch> orbMatches;
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

    public OrbMatchRecycler(Context context, ArrayList<OrbMatch> orbMatches, View.OnClickListener orbMatchOnClickListener, View.OnClickListener removeOnClickListener){
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

        Drawable orbDrawable = getDrawable(R.drawable.red_orb);
        if (currentMatch.getElement() == Element.DARK)
        {
            orbDrawable = getDrawable(R.drawable.dark_orb);
        }
        if (currentMatch.getElement() == Element.BLUE)
        {
            orbDrawable = getDrawable(R.drawable.blue_orb);
        }
        if (currentMatch.getElement() == Element.GREEN)
        {
            orbDrawable = getDrawable(R.drawable.green_orb);
        }
        if (currentMatch.getElement() == Element.LIGHT)
        {
            orbDrawable = getDrawable(R.drawable.light_orb);
        }
        if (currentMatch.getElement() == Element.HEART)
        {
            orbDrawable = getDrawable(R.drawable.heart_orb);
        }
        if (currentMatch.getElement() == Element.JAMMER)
        {
            orbDrawable = getDrawable(R.drawable.jammer_orb);
        }
        if (currentMatch.getElement() == Element.POISON)
        {
            orbDrawable = getDrawable(R.drawable.poison_orb);
        }
        if (currentMatch.getElement() == Element.MORTAL_POISON)
        {
            orbDrawable = getDrawable(R.drawable.mortal_poison_orb);
        }

        viewHolder.orbMatchTotal.setSelected(true);
        viewHolder.orbImage.setImageDrawable(orbDrawable);
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

    public void add (OrbMatch orbMatch){
        orbMatches.add(orbMatch);
        notifyDataSetChanged();
    }

    public void clear (){
        orbMatches.clear();
        notifyDataSetChanged();
    }
}
