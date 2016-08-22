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
public class AwakeningGridFilterAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> awakeningsList;
    private int resourceId;
    private Boolean isEnable = false;
    private ArrayList<Integer> filterList;

    public AwakeningGridFilterAdapter(Context context, ArrayList<Integer> awakenings, ArrayList<Integer> filterList) {
        mContext = context;
        awakeningsList = awakenings;
        this.filterList = filterList;
    }

    @Override
    public int getCount() {
        return awakeningsList.size();
    }

    @Override
    public Object getItem(int position) {
        return awakeningsList.get(position);
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
        switch (awakeningsList.get(position)) {
            case 1:
                if (filterList.contains(1)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_1);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_1_gray);
                }
                break;
            case 2:
                if (filterList.contains(2)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_2);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_2_gray);
                }
                break;
            case 3:
                if (filterList.contains(3)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_3);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_3_gray);
                }
                break;
            case 4:
                if (filterList.contains(4)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_4);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_4_gray);
                }
                break;
            case 5:
                if (filterList.contains(5)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_5);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_5_gray);
                }
                break;
            case 6:
                if (filterList.contains(6)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_6);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_6_gray);
                }
                break;
            case 7:
                if (filterList.contains(7)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_7);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_7_gray);
                }
                break;
            case 8:
                if (filterList.contains(8)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_8);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_8_gray);
                }
                break;
            case 9:
                if (filterList.contains(9)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_9);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_9_gray);
                }
                break;
            case 10:
                if (filterList.contains(10)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_10);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_10_gray);
                }
                break;
            case 11:
                if (filterList.contains(11)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_11);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_11_gray);
                }
                break;
            case 12:
                if (filterList.contains(12)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_12);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_12_gray);
                }
                break;
            case 13:
                if (filterList.contains(13)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_13);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_13_gray);
                }
                break;
            case 14:
                if (filterList.contains(14)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_14);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_14_gray);
                }
                break;
            case 15:
                if (filterList.contains(15)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_15);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_15_gray);
                }
                break;
            case 16:
                if (filterList.contains(16)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_16);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_16_gray);
                }
                break;
            case 17:
                if (filterList.contains(17)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_17);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_17_gray);
                }
                break;
            case 18:
                if (filterList.contains(18)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_18);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_18_gray);
                }
                break;
            case 19:
                if (filterList.contains(19)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_19);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_19_gray);
                }
                break;
            case 20:
                if (filterList.contains(20)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_20);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_20_gray);
                }
                break;
            case 21:
                if (filterList.contains(21)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_21);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_21_gray);
                }
                break;
            case 22:
                if (filterList.contains(22)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_22);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_22_gray);
                }
                break;
            case 23:
                if (filterList.contains(23)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_23);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_23_gray);
                }
                break;
            case 24:
                if (filterList.contains(24)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_24);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_24_gray);
                }
                break;
            case 25:
                if (filterList.contains(25)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_25);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_25_gray);
                }
                break;
            case 26:
                if (filterList.contains(26)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_26);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_26_gray);
                }
                break;
            case 27:
                if (filterList.contains(27)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_27);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_27_gray);
                }
                break;
            case 28:
                if (filterList.contains(28)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_28);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_28_gray);
                }
                break;
            case 29:
                if (filterList.contains(29)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_29);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_29_gray);
                }
                break;
            case 30:
                if (filterList.contains(30)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_30);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_30_gray);
                }
                break;
            case 31:
                if (filterList.contains(31)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_31);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_31_gray);
                }
                break;
            case 32:
                if (filterList.contains(32)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_32);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_32_gray);
                }
                break;
            case 33:
                if (filterList.contains(33)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_33);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_33_gray);
                }
                break;
            case 34:
                if (filterList.contains(34)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_34);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_34_gray);
                }
                break;
            case 35:
                if (filterList.contains(35)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_35);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_35_gray);
                }
                break;
            case 36:
                if (filterList.contains(36)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_36);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_36_gray);
                }
                break;
            case 37:
                if (filterList.contains(37)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_37);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_37_gray);
                }
                break;
            case 38:
                if (filterList.contains(38)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_38);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_38_gray);
                }
                break;
            case 39:
                if (filterList.contains(39)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_39);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_39_gray);
                }
                break;
            case 40:
                if (filterList.contains(40)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_40);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_40_gray);
                }
                break;
            case 41:
                if (filterList.contains(41)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_41);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_41_gray);
                }
                break;
            case 42:
                if (filterList.contains(42)) {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_42);
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.awakening_42_gray);
                }
                break;
            default:
                viewHolder.typePicture.setImageResource(R.drawable.awakening);
                break;
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView typePicture;

    }
}
