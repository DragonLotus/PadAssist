package com.example.anthony.damagecalculator.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.Fragments.MonsterPageFragment;
import com.example.anthony.damagecalculator.Fragments.MonsterTabLayoutFragment;
import com.example.anthony.damagecalculator.Fragments.TeamLoadDialogFragment;
import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.Singleton;

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

//    private View.OnClickListener onItemClickListener = new View.OnClickListener(){
//        @Override
//        public void onClick(View v) {
//            int position = (int) v.getTag(R.string.index);
//            if (teamLoadDialogFragment == null) {
//                teamLoadDialogFragment = TeamLoadDialogFragment.newInstance(loadTeam, teamList.get(position));
//            }
//
//            teamLoadDialogFragment.show(, "Show Team Load dialog", teamList.get(position));
//        }
//    };
//
//    private TeamLoadDialogFragment.LoadTeam loadTeam = new TeamLoadDialogFragment.LoadTeam() {
//        @Override
//        public void loadTeam() {
//            Team loadTeam = new Team(teamListAdapter.getItem(selectedTeam));
//            Log.d("Team Log", "Team Name: " + loadTeam.getTeamName());
//            loadTeam.setTeamIdOverwrite(loadTeam.getTeamId());
//            loadTeam.setTeamId(0);
//            loadTeam.save();
//        }
//
//        @Override
//        public void editTeam(String teamName) {
//            Team loadTeam = Team.getTeamById(teamListAdapter.getItem(selectedTeam).getTeamId());
//            loadTeam.setTeamName(teamName);
//            loadTeam.save();
//            teamList = (ArrayList) Team.getAllTeams();
//            teamListAdapter.updateList(teamList);
//        }
//
//        @Override
//        public void deleteTeam() {
////            Team.deleteTeam(teamListAdapter.getItem(selectedTeam).getTeamId());
//            Team deleteTeam = Team.getTeamById(teamListAdapter.getItem(selectedTeam).getTeamId());
//            Log.d("Team List Log", "Delete Team Name is: " + deleteTeam.getTeamName() + "Team id is: " + deleteTeam.getTeamId());
//            deleteTeam.delete();
//            teamList.remove(selectedTeam);
//            Log.d("Team List Log", "Team size is: " + teamList.size());
//            teamListAdapter.notifyDataSetChanged();
//            if (teamList.size() == 0) {
//                savedTeams.setVisibility(View.VISIBLE);
//            } else {
//                savedTeams.setVisibility(View.GONE);
//            }
//        }
//
//        @Override
//        public void favoriteTeam(boolean favorite) {
//            teamListAdapter.getItem(selectedTeam).setFavorite(favorite);
//            teamListAdapter.getItem(selectedTeam).save();
//            Log.d("TeamListFragment", "Team 0 is favorite: " + teamList.get(0).isFavorite());
//            teamListAdapter.notifyDataSetChanged();
//        }
//    };

    public TeamListRecycler(Context context, ArrayList<Team> teamList, View.OnClickListener teamListOnClickListener){
        mContext = context;
        this.teamList = teamList;
        this.teamListOnClickListener = teamListOnClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.teamName.setText(teamList.get(position).getTeamName());
        Log.d("Team Name", "" + teamList.get(position).getTeamName());
        Log.d("Team List Size", "" + teamList.size() + " " + teamList.get(position).getMonsters());
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
        Log.d("Monster getview", "Monster getView: " + teamList.get(position).getMonsters(4) + " " + teamList.get(position));
        viewHolder.monster5Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(4).getTotalPlus()) + " ");
        viewHolder.monster5Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(4).getCurrentAwakenings()));
        viewHolder.monster5Picture.setImageResource(teamList.get(position).getMonsters(4).getMonsterPicture());
        viewHolder.monster6Plus.setText(" +" + Integer.toString(teamList.get(position).getMonsters(5).getTotalPlus()) + " ");
        viewHolder.monster6Awakenings.setText(" " + Integer.toString(teamList.get(position).getMonsters(5).getCurrentAwakenings()));
        viewHolder.monster6Picture.setImageResource(teamList.get(position).getMonsters(5).getMonsterPicture());

        viewHolder.favorite.setColorFilter(0xFFFFAADD);
        if(teamList.get(position).isFavorite()){
            viewHolder.favorite.setVisibility(View.VISIBLE);
        }else {
            viewHolder.favorite.setVisibility(View.INVISIBLE);
        }

        if (teamList.get(position).getMonsters(0).getCurrentAwakenings() == teamList.get(position).getMonsters(0).getMaxAwakenings()) {
            viewHolder.monster1Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster1Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(0).getTotalPlus() == 0) {
            viewHolder.monster1Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(0).getMonsterId() != 0){
            viewHolder.monster1Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(0).getCurrentAwakenings() == 0) {
            viewHolder.monster1Awakenings.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(0).getMonsterId() != 0){
            viewHolder.monster1Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(1).getCurrentAwakenings() == teamList.get(position).getMonsters(1).getMaxAwakenings()) {
            viewHolder.monster2Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster2Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(1).getTotalPlus() == 0) {
            viewHolder.monster2Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(1).getMonsterId() != 0){
            viewHolder.monster2Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(1).getCurrentAwakenings() == 0) {
            viewHolder.monster2Awakenings.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(1).getMonsterId() != 0){
            viewHolder.monster2Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(2).getCurrentAwakenings() == teamList.get(position).getMonsters(2).getMaxAwakenings()) {
            viewHolder.monster3Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster3Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(2).getTotalPlus() == 0) {
            viewHolder.monster3Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(2).getMonsterId() != 0){
            viewHolder.monster3Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(2).getCurrentAwakenings() == 0) {
            viewHolder.monster3Awakenings.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(2).getMonsterId() != 0){
            viewHolder.monster3Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(3).getCurrentAwakenings() == teamList.get(position).getMonsters(3).getMaxAwakenings()) {
            viewHolder.monster4Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster4Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(3).getTotalPlus() == 0) {
            viewHolder.monster4Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(3).getMonsterId() != 0){
            viewHolder.monster4Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(3).getCurrentAwakenings() == 0) {
            viewHolder.monster4Awakenings.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(3).getMonsterId() != 0){
            viewHolder.monster4Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(4).getCurrentAwakenings() == teamList.get(position).getMonsters(4).getMaxAwakenings()) {
            viewHolder.monster5Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster5Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(4).getTotalPlus() == 0) {
            viewHolder.monster5Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(4).getMonsterId() != 0){
            viewHolder.monster5Plus.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(4).getCurrentAwakenings() == 0) {
            viewHolder.monster5Awakenings.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(4).getMonsterId() != 0){
            viewHolder.monster5Awakenings.setVisibility(View.VISIBLE);
        }

        if (teamList.get(position).getMonsters(5).getCurrentAwakenings() == teamList.get(position).getMonsters(5).getMaxAwakenings()) {
            viewHolder.monster6Awakenings.setBackgroundResource(R.drawable.awakening_max);
            viewHolder.monster6Awakenings.setText("");
        }
        if (teamList.get(position).getMonsters(5).getTotalPlus() == 0) {
            viewHolder.monster6Plus.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(5).getMonsterId() != 0){
            viewHolder.monster6Plus.setVisibility(View.VISIBLE);
        }
        if (teamList.get(position).getMonsters(5).getCurrentAwakenings() == 0) {
            viewHolder.monster6Awakenings.setVisibility(View.INVISIBLE);
        } else if (teamList.get(position).getMonsters(5).getMonsterId() != 0){
            viewHolder.monster6Awakenings.setVisibility(View.VISIBLE);
        }

        viewHolder.itemView.setOnClickListener(teamListOnClickListener);
        viewHolder.itemView.setTag(R.string.index, position);
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
        TextView teamName, monster1Plus, monster1Awakenings, monster2Plus, monster2Awakenings, monster3Plus, monster3Awakenings, monster4Plus, monster4Awakenings, monster5Plus, monster5Awakenings, monster6Plus, monster6Awakenings;
        ImageView monster1Picture, monster2Picture, monster3Picture, monster4Picture, monster5Picture, monster6Picture, favorite, favoriteOutline;

        public ViewHolder(View convertView){
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
            favorite = (ImageView) convertView.findViewById(R.id.favorite);
            favoriteOutline = (ImageView) convertView.findViewById(R.id.favoriteOutline);
        }
    }

    public void updateList(ArrayList<Team> teamList){
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    public Team getItem(int position){
        return teamList.get(position);
    }
}
