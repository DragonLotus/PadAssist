package com.padassist.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padassist.Data.Team;
import com.padassist.Fragments.TeamLoadDialogFragment;
import com.padassist.R;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 11/4/2015.
 */
public class TeamListRecycler extends RecyclerView.Adapter<TeamListRecycler.ViewHolder> {

    private ArrayList<Team> teamList;
    private Context mContext;
    private LayoutInflater inflater;
    private View.OnClickListener teamListOnClickListener;
    private TeamLoadDialogFragment teamLoadDialogFragment;
    private ArrayList<Integer> latentList1;
    private ArrayList<Integer> latentList2;
    private ArrayList<Integer> latentList3;
    private ArrayList<Integer> latentList4;
    private ArrayList<Integer> latentList5;
    private ArrayList<Integer> latentList6;
    int expandedPosition = -1;

    public TeamListRecycler(Context context, ArrayList<Team> teamList, View.OnClickListener teamListOnClickListener) {
        mContext = context;
        this.teamList = teamList;
        this.teamListOnClickListener = teamListOnClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.teamName.setText(teamList.get(position).getTeamName());
        viewHolder.monster1Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(0).getTotalPlus()) + " ");
        viewHolder.monster1Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(0).getCurrentAwakenings()));
        viewHolder.monster1Picture.setImageResource(teamList.get(position).getMonsters(0).getMonsterPicture());
        viewHolder.monster2Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(1).getTotalPlus()) + " ");
        viewHolder.monster2Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(1).getCurrentAwakenings()));
        viewHolder.monster2Picture.setImageResource(teamList.get(position).getMonsters(1).getMonsterPicture());
        viewHolder.monster3Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(2).getTotalPlus()) + " ");
        viewHolder.monster3Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(2).getCurrentAwakenings()));
        viewHolder.monster3Picture.setImageResource(teamList.get(position).getMonsters(2).getMonsterPicture());
        viewHolder.monster4Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(3).getTotalPlus()) + " ");
        viewHolder.monster4Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(3).getCurrentAwakenings()));
        viewHolder.monster4Picture.setImageResource(teamList.get(position).getMonsters(3).getMonsterPicture());
        viewHolder.monster5Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(4).getTotalPlus()) + " ");
        viewHolder.monster5Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(4).getCurrentAwakenings()));
        viewHolder.monster5Picture.setImageResource(teamList.get(position).getMonsters(4).getMonsterPicture());
        viewHolder.monster6Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(5).getTotalPlus()) + " ");
        viewHolder.monster6Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(5).getCurrentAwakenings()));
        viewHolder.monster6Picture.setImageResource(teamList.get(position).getMonsters(5).getMonsterPicture());

        if (latentList1 == null) {
            latentList1 = new ArrayList<>();
        } else {
            latentList1.clear();
        }

        for (int i = 0; i < teamList.get(position).getMonsters(0).getLatents().size(); i++) {
            if (teamList.get(position).getMonsters(0).getLatents().get(i).getValue() != 0) {
                latentList1.add(1);
            }
        }
        if (latentList1.size() == 5) {
            viewHolder.monster1Latents.setBackgroundResource(R.drawable.latent_max);
            viewHolder.monster1Latents.setText("");
            viewHolder.monster1Latents.setVisibility(View.VISIBLE);
        } else if (latentList1.size() == 0) {
            viewHolder.monster1Latents.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monster1Latents.setText(" " + latentList1.size());
            viewHolder.monster1Latents.setVisibility(View.VISIBLE);
        }

        if (latentList2 == null) {
            latentList2 = new ArrayList<>();
        } else {
            latentList2.clear();
        }

        for (int i = 0; i < teamList.get(position).getMonsters(1).getLatents().size(); i++) {
            if (teamList.get(position).getMonsters(1).getLatents().get(i).getValue() != 0) {
                latentList2.add(1);
            }
        }
        if (latentList2.size() == 5) {
            viewHolder.monster2Latents.setBackgroundResource(R.drawable.latent_max);
            viewHolder.monster2Latents.setText("");
            viewHolder.monster2Latents.setVisibility(View.VISIBLE);
        } else if (latentList2.size() == 0) {
            viewHolder.monster2Latents.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monster2Latents.setText(" " + latentList2.size());
            viewHolder.monster2Latents.setVisibility(View.VISIBLE);
        }

        if (latentList3 == null) {
            latentList3 = new ArrayList<>();
        } else {
            latentList3.clear();
        }

        for (int i = 0; i < teamList.get(position).getMonsters(2).getLatents().size(); i++) {
            if (teamList.get(position).getMonsters(2).getLatents().get(i).getValue() != 0) {
                latentList3.add(1);
            }
        }
        if (latentList3.size() == 5) {
            viewHolder.monster3Latents.setBackgroundResource(R.drawable.latent_max);
            viewHolder.monster3Latents.setText("");
            viewHolder.monster3Latents.setVisibility(View.VISIBLE);
        } else if (latentList3.size() == 0) {
            viewHolder.monster3Latents.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monster3Latents.setText(" " + latentList3.size());
            viewHolder.monster3Latents.setVisibility(View.VISIBLE);
        }

        if (latentList4 == null) {
            latentList4 = new ArrayList<>();
        } else {
            latentList4.clear();
        }

        for (int i = 0; i < teamList.get(position).getMonsters(3).getLatents().size(); i++) {
            if (teamList.get(position).getMonsters(3).getLatents().get(i).getValue() != 0) {
                latentList4.add(1);
            }
        }
        if (latentList4.size() == 5) {
            viewHolder.monster4Latents.setBackgroundResource(R.drawable.latent_max);
            viewHolder.monster4Latents.setText("");
            viewHolder.monster4Latents.setVisibility(View.VISIBLE);
        } else if (latentList4.size() == 0) {
            viewHolder.monster4Latents.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monster4Latents.setText(" " + latentList4.size());
            viewHolder.monster4Latents.setVisibility(View.VISIBLE);
        }

        if (latentList5 == null) {
            latentList5 = new ArrayList<>();
        } else {
            latentList5.clear();
        }

        for (int i = 0; i < teamList.get(position).getMonsters(4).getLatents().size(); i++) {
            if (teamList.get(position).getMonsters(4).getLatents().get(i).getValue() != 0) {
                latentList5.add(1);
            }
        }
        if (latentList5.size() == 5) {
            viewHolder.monster5Latents.setBackgroundResource(R.drawable.latent_max);
            viewHolder.monster5Latents.setText("");
            viewHolder.monster5Latents.setVisibility(View.VISIBLE);
        } else if (latentList5.size() == 0) {
            viewHolder.monster5Latents.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monster5Latents.setText(" " + latentList5.size());
            viewHolder.monster5Latents.setVisibility(View.VISIBLE);
        }

        if (latentList6 == null) {
            latentList6 = new ArrayList<>();
        } else {
            latentList6.clear();
        }

        for (int i = 0; i < teamList.get(position).getMonsters(5).getLatents().size(); i++) {
            if (teamList.get(position).getMonsters(5).getLatents().get(i).getValue() != 0) {
                latentList6.add(1);
            }
        }
        if (latentList6.size() == 5) {
            viewHolder.monster6Latents.setBackgroundResource(R.drawable.latent_max);
            viewHolder.monster6Latents.setText("");
            viewHolder.monster6Latents.setVisibility(View.VISIBLE);
        } else if (latentList6.size() == 0) {
            viewHolder.monster6Latents.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.monster6Latents.setText(" " + latentList6.size());
            viewHolder.monster6Latents.setVisibility(View.VISIBLE);
        }

        viewHolder.favorite.setColorFilter(0xFFFFAADD);
        if (teamList.get(position).isFavorite()) {
            viewHolder.favorite.setVisibility(View.VISIBLE);
        } else {
            viewHolder.favorite.setVisibility(View.INVISIBLE);
        }

        if (teamList.get(position).getMonsters(0).getCurrentAwakenings() >= teamList.get(position).getMonsters(0).getMaxAwakenings()) {
            viewHolder.monster1Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster1Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(0).getTotalPlus() == 0) {
            viewHolder.monster1Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(0).getMonsterId() != 0) {
            viewHolder.monster1Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(0).getCurrentAwakenings() == 0) {
            viewHolder.monster1Awakenings.setVisibility(View.INVISIBLE);
            if (latentList1.size() != 0) {
                ViewGroup.LayoutParams z = viewHolder.monster1Awakenings.getLayoutParams();
                viewHolder.monster1Latents.setLayoutParams(z);
            }
        } else if (teamList.get(position).getMonsters(0).getMonsterId() != 0) {
            viewHolder.monster1Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(1).getCurrentAwakenings() >= teamList.get(position).getMonsters(1).getMaxAwakenings()) {
            viewHolder.monster2Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster2Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(1).getTotalPlus() == 0) {
            viewHolder.monster2Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(1).getMonsterId() != 0) {
            viewHolder.monster2Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(1).getCurrentAwakenings() == 0) {
            viewHolder.monster2Awakenings.setVisibility(View.INVISIBLE);
            if (latentList2.size() != 0) {
                ViewGroup.LayoutParams z = viewHolder.monster2Awakenings.getLayoutParams();
                viewHolder.monster2Latents.setLayoutParams(z);
            }
        } else if (teamList.get(position).getMonsters(1).getMonsterId() != 0) {
            viewHolder.monster2Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(2).getCurrentAwakenings() >= teamList.get(position).getMonsters(2).getMaxAwakenings()) {
            viewHolder.monster3Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster3Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(2).getTotalPlus() == 0) {
            viewHolder.monster3Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(2).getMonsterId() != 0) {
            viewHolder.monster3Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(2).getCurrentAwakenings() == 0) {
            viewHolder.monster3Awakenings.setVisibility(View.INVISIBLE);
            if (latentList3.size() != 0) {
                ViewGroup.LayoutParams z = viewHolder.monster3Awakenings.getLayoutParams();
                viewHolder.monster3Latents.setLayoutParams(z);
            }
        } else if (teamList.get(position).getMonsters(2).getMonsterId() != 0) {
            viewHolder.monster3Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(3).getCurrentAwakenings() >= teamList.get(position).getMonsters(3).getMaxAwakenings()) {
            viewHolder.monster4Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster4Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(3).getTotalPlus() == 0) {
            viewHolder.monster4Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(3).getMonsterId() != 0) {
            viewHolder.monster4Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(3).getCurrentAwakenings() == 0) {
            viewHolder.monster4Awakenings.setVisibility(View.INVISIBLE);
            if (latentList4.size() != 0) {
                ViewGroup.LayoutParams z = viewHolder.monster4Awakenings.getLayoutParams();
                viewHolder.monster4Latents.setLayoutParams(z);
            }
        } else if (teamList.get(position).getMonsters(3).getMonsterId() != 0) {
            viewHolder.monster4Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(4).getCurrentAwakenings() >= teamList.get(position).getMonsters(4).getMaxAwakenings()) {
            viewHolder.monster5Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster5Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(4).getTotalPlus() == 0) {
            viewHolder.monster5Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(4).getMonsterId() != 0) {
            viewHolder.monster5Plus.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(4).getCurrentAwakenings() == 0) {
            viewHolder.monster5Awakenings.setVisibility(View.INVISIBLE);
            if (latentList5.size() != 0) {
                ViewGroup.LayoutParams z = viewHolder.monster5Awakenings.getLayoutParams();
                viewHolder.monster5Latents.setLayoutParams(z);
            }
        } else if (teamList.get(position).getMonsters(4).getMonsterId() != 0) {
            viewHolder.monster5Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(5).getCurrentAwakenings() >= teamList.get(position).getMonsters(5).getMaxAwakenings()) {
            viewHolder.monster6Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster6Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(5).getTotalPlus() == 0) {
            viewHolder.monster6Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(5).getMonsterId() != 0) {
            viewHolder.monster6Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(5).getCurrentAwakenings() == 0) {
            viewHolder.monster6Awakenings.setVisibility(View.INVISIBLE);
            if (latentList6.size() != 0) {
                ViewGroup.LayoutParams z = viewHolder.monster6Awakenings.getLayoutParams();
                viewHolder.monster6Latents.setLayoutParams(z);
            }
        } else if (teamList.get(position).getMonsters(5).getMonsterId() != 0) {
            viewHolder.monster6Awakenings.setVisibility(View.VISIBLE);
        }

        viewHolder.teamName.setHorizontallyScrolling(true);
        viewHolder.teamName.setSelected(true);

        viewHolder.itemView.setOnClickListener(teamListOnClickListener);
        viewHolder.itemView.setTag(R.string.index, position);
        if (position % 2 == 1) {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#e8e8e8"));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.team_list_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamName, monster1Plus, monster1Awakenings, monster2Plus, monster2Awakenings, monster3Plus, monster3Awakenings, monster4Plus, monster4Awakenings, monster5Plus, monster5Awakenings, monster6Plus, monster6Awakenings, monster1Latents, monster2Latents, monster3Latents, monster4Latents, monster5Latents, monster6Latents;
        ImageView monster1Picture, monster2Picture, monster3Picture, monster4Picture, monster5Picture, monster6Picture, favorite, favoriteOutline;
        RelativeLayout relativeLayout;

        public ViewHolder(View convertView) {
            super(convertView);
            teamName = (TextView) convertView.findViewById(R.id.teamName);
            monster1Plus = (TextView) convertView.findViewById(R.id.monster1Plus);
            monster1Awakenings = (TextView) convertView.findViewById(R.id.monster1Awakenings);
            monster2Plus = (TextView) convertView.findViewById(R.id.monster2Plus);
            monster2Awakenings = (TextView) convertView.findViewById(R.id.monster2Awakenings);
            monster3Plus = (TextView) convertView.findViewById(R.id.monster3Plus);
            monster3Awakenings = (TextView) convertView.findViewById(R.id.monster3Awakenings);
            monster4Plus = (TextView) convertView.findViewById(R.id.monster4Plus);
            monster4Awakenings = (TextView) convertView.findViewById(R.id.monster4Awakenings);
            monster5Plus = (TextView) convertView.findViewById(R.id.monster5Plus);
            monster5Awakenings = (TextView) convertView.findViewById(R.id.monster5Awakenings);
            monster6Plus = (TextView) convertView.findViewById(R.id.monster6Plus);
            monster6Awakenings = (TextView) convertView.findViewById(R.id.monster6Awakenings);
            monster1Picture = (ImageView) convertView.findViewById(R.id.monster1Picture);
            monster2Picture = (ImageView) convertView.findViewById(R.id.monster2Picture);
            monster3Picture = (ImageView) convertView.findViewById(R.id.monster3Picture);
            monster4Picture = (ImageView) convertView.findViewById(R.id.monster4Picture);
            monster5Picture = (ImageView) convertView.findViewById(R.id.monster5Picture);
            monster6Picture = (ImageView) convertView.findViewById(R.id.monster6Picture);
            monster1Latents = (TextView) convertView.findViewById(R.id.monster1Latents);
            monster2Latents = (TextView) convertView.findViewById(R.id.monster2Latents);
            monster3Latents = (TextView) convertView.findViewById(R.id.monster3Latents);
            monster4Latents = (TextView) convertView.findViewById(R.id.monster4Latents);
            monster5Latents = (TextView) convertView.findViewById(R.id.monster5Latents);
            monster6Latents = (TextView) convertView.findViewById(R.id.monster6Latents);
            favorite = (ImageView) convertView.findViewById(R.id.favorite);
            favoriteOutline = (ImageView) convertView.findViewById(R.id.favoriteOutline);
            relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
        }
    }

    public void updateList(ArrayList<Team> teamList) {
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    public Team getItem(int position) {
        return teamList.get(position);
    }

    public boolean expanded() {
        if (expandedPosition != -1) {
            return true;
        } else return false;
    }
}
