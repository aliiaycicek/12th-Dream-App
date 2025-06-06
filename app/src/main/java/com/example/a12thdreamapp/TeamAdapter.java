package com.example.a12thdreamapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;
import android.content.Context;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private List<FavoriteTeam> teams;
    private Context context;
    private OnTeamClickListener listener;

    public interface OnTeamClickListener {
        void onTeamClick(FavoriteTeam team);
    }

    public TeamAdapter() {
        this.teams = new ArrayList<>();
    }

    public void setOnTeamClickListener(OnTeamClickListener listener) {
        this.listener = listener;
    }

    public void setTeams(List<FavoriteTeam> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteTeam team = teams.get(position);
        holder.teamNameTextView.setText(team.getName());
        
        StringBuilder lineup = new StringBuilder();
        lineup.append("Formasyon: ").append(team.getFormation()).append("\n\n");
        
        List<String> players = team.getPlayers();
        if (players != null && !players.isEmpty()) {
            lineup.append("Oyuncular:\n");
            for (String player : players) {
                lineup.append("- ").append(player).append("\n");
            }
        }
        
        lineup.append("\nTeknik Direktör: ").append(team.getCoach());
        holder.lineupTextView.setText(lineup.toString());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTeamClick(team);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamNameTextView;
        TextView lineupTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamNameTextView = itemView.findViewById(R.id.teamNameTextView);
            lineupTextView = itemView.findViewById(R.id.lineupTextView);
        }
    }
}

