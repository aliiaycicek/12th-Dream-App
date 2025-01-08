package com.example.a12thdreamapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FavoriteTeamsActivity extends AppCompatActivity implements FavoriteTeamsAdapter.OnTeamDeleteListener {
    private RecyclerView teamsRecyclerView;
    private TextView emptyView;
    private FavoriteTeamsAdapter adapter;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_teams);

        teamsRecyclerView = findViewById(R.id.teamsRecyclerView);
        emptyView = findViewById(R.id.emptyView);

        teamsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }

        loadFavoriteTeams();
    }

    private void loadFavoriteTeams() {
        FirebaseManager.getFavoriteTeams(currentUserId, new FirebaseManager.OnFavoriteTeamsLoadedListener() {
            @Override
            public void onTeamsLoaded(List<FavoriteTeam> teams) {
                if (teams.isEmpty()) {
                    teamsRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("Henüz kaydedilmiş kadro bulunmamaktadır");
                } else {
                    emptyView.setVisibility(View.GONE);
                    teamsRecyclerView.setVisibility(View.VISIBLE);
                    adapter = new FavoriteTeamsAdapter(teams, FavoriteTeamsActivity.this);
                    teamsRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(FavoriteTeamsActivity.this,
                    "Takımlar yüklenirken hata oluştu: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTeamDelete(final FavoriteTeam team) {
        FirebaseManager.deleteFavoriteTeam(team, new FirebaseManager.OnTeamDeletedListener() {
            @Override
            public void onTeamDeleted() {
                adapter.removeTeam(team);
                if (adapter.getItemCount() == 0) {
                    teamsRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("Henüz kaydedilmiş kadro bulunmamaktadır");
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(FavoriteTeamsActivity.this,
                    "Takım silinirken hata oluştu: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
} 