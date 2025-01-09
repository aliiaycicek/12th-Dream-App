package com.example.a12thdreamapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> players;
    private OnPlayerClickListener listener;

    public interface OnPlayerClickListener {
        void onPlayerClick(Player player);
    }

    public PlayerAdapter(List<Player> players, OnPlayerClickListener listener) {
        this.players = players;
        this.listener = listener;
    }

    public void updatePlayers(List<Player> newPlayers) {
        this.players = newPlayers;
        notifyDataSetChanged();
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.playerNameTextView.setText(player.getName());
        holder.playerPositionTextView.setText(player.getPosition());


        if (player.getCategory() == Player.PlayerCategory.LEGEND) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFEB3B"));
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView playerNameTextView;
        TextView playerPositionTextView;
        CardView cardView;

        PlayerViewHolder(View itemView) {
            super(itemView);
            playerNameTextView = itemView.findViewById(R.id.playerNameTextView);
            playerPositionTextView = itemView.findViewById(R.id.playerPositionTextView);
            cardView = itemView.findViewById(R.id.playerCardView);
        }
    }
}
