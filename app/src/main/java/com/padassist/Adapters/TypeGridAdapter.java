package com.padassist.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.padassist.R;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/18/2015.
 */
public class TypeGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> typeList;
    private int resourceId;
    private Boolean isEnable = false;

    public TypeGridAdapter(Context context, ArrayList<Integer> types) {
        mContext = context;
        typeList = types;
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeList.get(position);
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
        switch (typeList.get(position)) {
            case 0:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(0)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_evo_material);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_evo_material_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_evo_material_disabled);
                }

                break;
            case 1:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(1)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_balanced);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_balanced_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_balanced_disabled);
                }
                break;
            case 2:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(2)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_physical);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_physical_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_physical_disabled);
                }
                break;
            case 3:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(3)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_healer);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_healer_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_healer_disabled);
                }
                break;
            case 4:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(4)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_dragon);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_dragon_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_dragon_disabled);
                }
                break;
            case 5:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(5)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_god);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_god_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_god_disabled);
                }
                break;
            case 6:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(6)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_attacker);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_attacker_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_attacker_disabled);
                }
                break;
            case 7:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(7)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_devil);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_devil_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_devil_disabled);
                }
                break;
            case 8:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(8)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_machine);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_machine_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_machine_disabled);
                }
                break;
            case 12:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(12)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_awoken);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_awoken_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_awoken_disabled);
                }
                break;
            case 14:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(14)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_enhance_material);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_enhance_material_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_enhance_material_disabled);
                }
                break;
            case 15:
                if (isEnable) {
                    if (Singleton.getInstance().getExtraTypeMultiplier().contains(15)) {
                        viewHolder.typePicture.setImageResource(R.drawable.type_vendor);
                    } else {
                        viewHolder.typePicture.setImageResource(R.drawable.type_vendor_gray);
                    }
                } else {
                    viewHolder.typePicture.setImageResource(R.drawable.type_vendor_disabled);
                }
                break;
            default:
                viewHolder.typePicture.setVisibility(View.GONE);
                break;
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView typePicture;

    }

    public void notifyEnable(Boolean isEnable) {
        this.isEnable = isEnable;
        notifyDataSetChanged();
    }
}
