package com.padassist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/11/2015.
 */
public class TypeSpinnerAdapter extends ArrayAdapter<Integer> {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Integer> types;
    private int resourceId;

    public TypeSpinnerAdapter(Context context, int resource, ArrayList<Integer> types) {
        super(context, resource, types);
        mContext = context;
        this.types = types;
        this.resourceId = resource;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.typeName = (TextView) convertView.findViewById(R.id.typeName);
            viewHolder.typeIcon = (ImageView) convertView.findViewById(R.id.typeIcon);
            viewHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        switch (types.get(position)) {
            case 0:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_evo_material);
                viewHolder.typeName.setText("Evo Material");
                break;
            case 1:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_balanced);
                viewHolder.typeName.setText("Balanced");
                break;
            case 2:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_physical);
                viewHolder.typeName.setText("Physical");
                break;
            case 3:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_healer);
                viewHolder.typeName.setText("Healer");
                break;
            case 4:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_dragon);
                viewHolder.typeName.setText("Dragon");
                break;
            case 5:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_god);
                viewHolder.typeName.setText("God");
                break;
            case 6:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_attacker);
                viewHolder.typeName.setText("Attacker");
                break;
            case 7:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_devil);
                viewHolder.typeName.setText("Devil");
                break;
            case 8:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_machine);
                viewHolder.typeName.setText("Machine");
                break;
            case 12:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_awoken);
                viewHolder.typeName.setText("Awoken Skill Material");
                break;
            case 14:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_enhance_material);
                viewHolder.typeName.setText("Enhance Material");
                break;
            case 15:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_vendor);
                viewHolder.typeName.setText("Vendor");
                break;
            default:
                viewHolder.typeIcon.setVisibility(View.INVISIBLE);
                viewHolder.typeName.setText("None");
                break;
        }
        if (position % 2 == 1) {
            viewHolder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.background));
        } else {
            viewHolder.relativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.background_alternate));
        }
        return convertView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.typeName = (TextView) convertView.findViewById(R.id.typeName);
            viewHolder.typeIcon = (ImageView) convertView.findViewById(R.id.typeIcon);
            convertView.setTag(R.string.viewHolder, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.string.viewHolder);
        }
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getContext().getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams z = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        z.height = height;
        z.width = height;
        z.addRule(RelativeLayout.CENTER_HORIZONTAL);
        z.addRule(RelativeLayout.CENTER_VERTICAL);
        viewHolder.typeIcon.setLayoutParams(z);
        switch (types.get(position)) {
            case 0:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_evo_material);
                viewHolder.typeName.setText("");
                break;
            case 1:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_balanced);
                viewHolder.typeName.setText("");
                break;
            case 2:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_physical);
                viewHolder.typeName.setText("");
                break;
            case 3:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_healer);
                viewHolder.typeName.setText("");
                break;
            case 4:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_dragon);
                viewHolder.typeName.setText("");
                break;
            case 5:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_god);
                viewHolder.typeName.setText("");
                break;
            case 6:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_attacker);
                viewHolder.typeName.setText("");
                break;
            case 7:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_devil);
                viewHolder.typeName.setText("");
                break;
            case 8:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_machine);
                viewHolder.typeName.setText("");
                break;
            case 12:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_awoken);
                viewHolder.typeName.setText("");
                break;
            case 14:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_enhance_material);
                viewHolder.typeName.setText("");
                break;
            case 15:
                viewHolder.typeIcon.setVisibility(View.VISIBLE);
                viewHolder.typeIcon.setImageResource(R.drawable.type_vendor);
                viewHolder.typeName.setText("");
                break;
            default:
                viewHolder.typeIcon.setVisibility(View.GONE);
                viewHolder.typeName.setText("None");
                break;
        }
        return convertView;
    }

    static class ViewHolder {
        TextView typeName;
        ImageView typeIcon;
        RelativeLayout relativeLayout;
    }
}
