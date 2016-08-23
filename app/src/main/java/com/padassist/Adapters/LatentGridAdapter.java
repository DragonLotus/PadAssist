package com.padassist.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.padassist.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/18/2015.
 */
public class LatentGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> latentsList;
    private int resourceId;
    private Boolean isEnable = false;
    private ArrayList<Integer> filterList;

    public LatentGridAdapter(Context context, ArrayList<Integer> latents, ArrayList<Integer> filterList) {
        mContext = context;
        latentsList = latents;
        this.filterList = filterList;
    }

    @Override
    public int getCount() {
        return latentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return latentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.type_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.typePicture = (ImageView) convertView.findViewById(R.id.typePicture);

            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        switch (latentsList.get(position)) {
            case 1:
                if (filterList.contains(1)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_1);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_1_gray);
                }
                break;
            case 2:
                if (filterList.contains(2)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_2);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_2_gray);
                }
                break;
            case 3:
                if (filterList.contains(3)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_3);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_3_gray);
                }
                break;
            case 4:
                if (filterList.contains(4)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_4);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_4_gray);
                }
                break;
            case 5:
                if (filterList.contains(5)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_5);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_5_gray);
                }
                break;
            case 6:
                if (filterList.contains(6)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_6);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_6_gray);
                }
                break;
            case 7:
                if (filterList.contains(7)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_7);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_7_gray);
                }
                break;
            case 8:
                if (filterList.contains(8)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_8);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_8_gray);
                }
                break;
            case 9:
                if (filterList.contains(9)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_9);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_9_gray);
                }
                break;
            case 10:
                if (filterList.contains(10)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_10);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_10_gray);
                }
                break;
            case 11:
                if (filterList.contains(11)) {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_11);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_11_gray);
                }
                break;
            default:
                viewHolder.typePicture.setImageResource(R.drawable.latent_awakening_blank);
                break;
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView typePicture;

    }
}
