package com.example.a12thdreamapp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class FavoriteTeamsAdapter extends RecyclerView.Adapter<FavoriteTeamsAdapter.ViewHolder> {
    private List<FavoriteTeam> teams;
    private Context context;
    private OnTeamDeleteListener deleteListener;

    public interface OnTeamDeleteListener {
        void onTeamDelete(FavoriteTeam team);
    }

    public FavoriteTeamsAdapter(List<FavoriteTeam> teams, OnTeamDeleteListener listener) {
        this.teams = teams;
        this.deleteListener = listener;
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
        
        // Dizilişi göster
        StringBuilder lineup = new StringBuilder();
        lineup.append("Formasyon: ").append(team.getFormation()).append("\n\n");
        
        // Oyuncuları listele
        List<String> players = team.getPlayers();
        if (players != null && !players.isEmpty()) {
            lineup.append("Oyuncular:\n");
            for (String player : players) {
                lineup.append("- ").append(player).append("\n");
            }
        }
        
        // Teknik direktörü göster
        lineup.append("\nTeknik Direktör: ").append(team.getCoach());
        
        holder.lineupTextView.setText(lineup.toString());

        // Silme butonu işlevselliği
        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onTeamDelete(team);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void removeTeam(FavoriteTeam team) {
        int position = teams.indexOf(team);
        if (position != -1) {
            teams.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamNameTextView;
        TextView lineupTextView;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamNameTextView = itemView.findViewById(R.id.teamNameTextView);
            lineupTextView = itemView.findViewById(R.id.lineupTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
} 