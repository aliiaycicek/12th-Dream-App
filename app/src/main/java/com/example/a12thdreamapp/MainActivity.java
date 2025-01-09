package com.example.a12thdreamapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.ArrayList;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
    private List<Formation> formations;
    private Button goalkeeper;
    private Button[] defenders = new Button[5];
    private Button[] defensiveMids = new Button[2];
    private Button[] centralMids = new Button[2];
    private Button[] attackingMids = new Button[1];
    private Button[] forwards = new Button[3];
    private Button[] wingers = new Button[2];
    private Spinner formationSpinner;
    private Button coachButton;
    private Button saveTeamButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        DatabaseInitializer.initializeDatabase();
        
        initializeViews();
        initializeFormations();
        setupListeners();
    }

    private void initializeViews() {
        // Kaleci
        goalkeeper = findViewById(R.id.goalkeeper);
        
        // Defans
        defenders[0] = findViewById(R.id.defender1);
        defenders[1] = findViewById(R.id.defender2);
        defenders[2] = findViewById(R.id.defender3);
        defenders[3] = findViewById(R.id.defender4);
        defenders[4] = findViewById(R.id.defender5);
        
        // Orta saha
        defensiveMids[0] = findViewById(R.id.defensive_mid1);
        defensiveMids[1] = findViewById(R.id.defensive_mid2);
        
        centralMids[0] = findViewById(R.id.central_mid1);
        centralMids[1] = findViewById(R.id.central_mid2);
        
        attackingMids[0] = findViewById(R.id.attacking_mid1);
        
        // Kanatlar
        wingers[0] = findViewById(R.id.winger1);
        wingers[1] = findViewById(R.id.winger2);
        

        forwards[0] = findViewById(R.id.striker1);
        forwards[1] = findViewById(R.id.striker2);
        forwards[2] = findViewById(R.id.striker3);
        

        formationSpinner = findViewById(R.id.formationSpinner);
        coachButton = findViewById(R.id.coachButton);
        saveTeamButton = findViewById(R.id.saveTeamButton);


        int fbBlueColor = ContextCompat.getColor(this, R.color.fb_navy);
        ColorStateList blueColorStateList = ColorStateList.valueOf(fbBlueColor);


        goalkeeper.setBackgroundTintList(blueColorStateList);


        for (Button defender : defenders) {
            if (defender != null) {
                defender.setBackgroundTintList(blueColorStateList);
            }
        }


        for (Button mid : defensiveMids) {
            if (mid != null) {
                mid.setBackgroundTintList(blueColorStateList);
            }
        }
        for (Button mid : centralMids) {
            if (mid != null) {
                mid.setBackgroundTintList(blueColorStateList);
            }
        }
        for (Button mid : attackingMids) {
            if (mid != null) {
                mid.setBackgroundTintList(blueColorStateList);
            }
        }


        for (Button winger : wingers) {
            if (winger != null) {
                winger.setBackgroundTintList(blueColorStateList);
            }
        }


        for (Button forward : forwards) {
            if (forward != null) {
                forward.setBackgroundTintList(blueColorStateList);
            }
        }
    }

    private void initializeFormations() {
        formations = new ArrayList<>();
        formations.add(new Formation("4-4-2", 4, 4, 2));
        formations.add(new Formation("4-3-3", 4, 3, 3));
        formations.add(new Formation("4-2-3-1", 4, 5, 1));
        formations.add(new Formation("3-5-2", 3, 5, 2));
        formations.add(new Formation("3-4-3", 3, 4, 3));
        
        ArrayAdapter<Formation> adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, formations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formationSpinner.setAdapter(adapter);
    }

    private void showButtons(Button[] buttons, int count) {
        for (Button button : buttons) {
            if (button != null) {
                button.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
                count--;
            }
        }
    }

    private void updateForwardButtons(int count) {
        for (Button forward : forwards) {
            forward.setVisibility(View.GONE);
        }
        
        for (int i = 0; i < count && i < forwards.length; i++) {
            forwards[i].setVisibility(View.VISIBLE);
        }
    }

    private void showPlayerSelectionDialog(Button button, String position) {
        FirebaseManager.getAllPlayers(new FirebaseManager.OnPlayersLoadedListener() {
            @Override
            public void onPlayersLoaded(List<Player> allPlayers) {
                List<Player> filteredPlayers = new ArrayList<>();
                
                for (Player player : allPlayers) {

                    if (position.equals("Kaleci") && player.getPosition().equals("Kaleci")) {
                        filteredPlayers.add(player);
                    }

                    else if ((position.equals("Defans") || position.equals("Bek") || 
                             position.equals("Stoper")) && player.getPosition().equals("Defans")) {
                        filteredPlayers.add(player);
                    }

                    else if ((position.equals("Orta Saha") || 
                             position.equals("Defansif Orta Saha") || 
                             position.equals("Merkez Orta Saha") || 
                             position.equals("Ofansif Orta Saha") || 
                             position.equals("Kanat") ||
                             position.equals("Sol Kanat") || 
                             position.equals("Sağ Kanat") ||
                             position.equals("Sol OOS") ||
                             position.equals("Sağ OOS")) && 
                             player.getPosition().equals("Orta Saha")) {
                        filteredPlayers.add(player);
                    }

                    else if ((position.equals("Forvet") || 
                             position.equals("Santrafor") || 
                             position.equals("Sol Kanat Forvet") || 
                             position.equals("Sağ Kanat Forvet")) && 
                             player.getPosition().equals("Forvet")) {
                        filteredPlayers.add(player);
                    }
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(position + " Seç");

                String[] playerNames = new String[filteredPlayers.size()];
                for (int i = 0; i < filteredPlayers.size(); i++) {
                    playerNames[i] = filteredPlayers.get(i).getName();
                }

                builder.setItems(playerNames, (dialog, which) -> {
                    String selectedPlayer = playerNames[which];
                    button.setText(selectedPlayer);
                });

                builder.show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, 
                    "Oyuncular yüklenirken hata oluştu: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupListeners() {

        formationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Formation selectedFormation = formations.get(position);
                updateFormation(selectedFormation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        goalkeeper.setOnClickListener(v -> showPlayerSelectionDialog((Button)v, "Kaleci"));

        for (Button defender : defenders) {
            if (defender != null) {
                defender.setOnClickListener(v -> showPlayerSelectionDialog((Button)v, "Defans"));
            }
        }

        for (Button mid : defensiveMids) {
            if (mid != null) {
                mid.setOnClickListener(v -> showPlayerSelectionDialog((Button)v, "Defansif Orta Saha"));
            }
        }

        for (Button mid : centralMids) {
            if (mid != null) {
                mid.setOnClickListener(v -> showPlayerSelectionDialog((Button)v, "Merkez Orta Saha"));
            }
        }

        for (Button mid : attackingMids) {
            if (mid != null) {
                mid.setOnClickListener(v -> showPlayerSelectionDialog((Button)v, "Ofansif Orta Saha"));
            }
        }

        for (Button winger : wingers) {
            if (winger != null) {
                winger.setOnClickListener(v -> showPlayerSelectionDialog((Button)v, "Kanat"));
            }
        }

        for (Button forward : forwards) {
            if (forward != null) {
                forward.setOnClickListener(v -> showPlayerSelectionDialog((Button)v, "Forvet"));
            }
        }


        coachButton.setOnClickListener(v -> showCoachSelectionDialog());


        saveTeamButton.setOnClickListener(v -> saveTeam());
    }

    private void updateFormation(Formation formation) {

        updateDefenderButtons(formation.getDefenders());
        

        updateMidfieldFormation(formation, formation.getName());
        

        updateForwardButtons(formation.getForwards());
    }

    private void showCoachSelectionDialog() {
        FirebaseManager.getAllCoaches(new FirebaseManager.OnCoachesLoadedListener() {
            @Override
            public void onCoachesLoaded(List<Coach> allCoaches) {
                List<Coach> filteredCoaches = new ArrayList<>();
                

                for (Coach coach : allCoaches) {
                    if (coach.isFenerbahceCoach()) {
                        filteredCoaches.add(coach);
                    }
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Teknik Direktör Seç");

                String[] coachNames = new String[filteredCoaches.size()];
                for (int i = 0; i < filteredCoaches.size(); i++) {
                    coachNames[i] = filteredCoaches.get(i).getName();
                }

                builder.setItems(coachNames, (dialog, which) -> {
                    String selectedCoach = coachNames[which];
                    coachButton.setText(selectedCoach);
                });

                builder.show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, 
                    "Teknik direktörler yüklenirken hata oluştu: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTeam() {

        Formation selectedFormation = (Formation) formationSpinner.getSelectedItem();
        String formationName = selectedFormation.getName();
        

        List<String> players = new ArrayList<>();
        

        players.add(goalkeeper.getText().toString());
        

        for (Button defender : defenders) {
            if (defender.getVisibility() == View.VISIBLE) {
                players.add(defender.getText().toString());
            }
        }
        

        for (Button mid : defensiveMids) {
            if (mid.getVisibility() == View.VISIBLE) {
                players.add(mid.getText().toString());
            }
        }
        for (Button mid : centralMids) {
            if (mid.getVisibility() == View.VISIBLE) {
                players.add(mid.getText().toString());
            }
        }
        for (Button mid : attackingMids) {
            if (mid.getVisibility() == View.VISIBLE) {
                players.add(mid.getText().toString());
            }
        }
        for (Button winger : wingers) {
            if (winger.getVisibility() == View.VISIBLE) {
                players.add(winger.getText().toString());
            }
        }
        

        for (Button forward : forwards) {
            if (forward.getVisibility() == View.VISIBLE) {
                players.add(forward.getText().toString());
            }
        }


        String coach = coachButton.getText().toString();


        FavoriteTeam favoriteTeam = new FavoriteTeam();
        favoriteTeam.setFormation(formationName);
        favoriteTeam.setPlayers(players);
        favoriteTeam.setCoach(coach);
        favoriteTeam.setName("Dream Team " + System.currentTimeMillis());


        FirebaseManager.addFavoriteTeam(favoriteTeam, new FirebaseManager.OnFavoriteTeamAddedListener() {
            @Override
            public void onFavoriteTeamAdded() {
                Toast.makeText(MainActivity.this, "Kadro başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, "Kadro kaydedilirken hata: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDefenderButtons(int count) {
        for (Button defender : defenders) {
            if (defender != null) {
                defender.setVisibility(View.GONE);
                defender.setText(count == 3 ? "Stoper" : "Defans");
            }
        }
        
        for (int i = 0; i < count && i < defenders.length; i++) {
            defenders[i].setVisibility(View.VISIBLE);
        }
    }

    private void updateMidfieldFormation(Formation formation, String type) {
        switch(type) {
            case "4-4-2":

                showButtons(defensiveMids, 0);
                showButtons(centralMids, 2);
                showButtons(attackingMids, 0);
                showButtons(wingers, 2);
                

                if (wingers[0] != null) wingers[0].setText("Sol Kanat");
                if (wingers[1] != null) wingers[1].setText("Sağ Kanat");
                break;
                
            case "4-3-3":

                showButtons(defensiveMids, 1);
                showButtons(centralMids, 2);
                showButtons(attackingMids, 0);
                showButtons(wingers, 0);
                

                if (defensiveMids[0] != null) defensiveMids[0].setText("Defansif Orta Saha");
                if (centralMids[0] != null) centralMids[0].setText("Sol İç Orta Saha");
                if (centralMids[1] != null) centralMids[1].setText("Sağ İç Orta Saha");
                

                if (forwards[0] != null) forwards[0].setText("Sol Kanat Forvet");
                if (forwards[1] != null) forwards[1].setText("Santrafor");
                if (forwards[2] != null) forwards[2].setText("Sağ Kanat Forvet");
                break;
                
            case "4-2-3-1":

                showButtons(defensiveMids, 2);
                showButtons(centralMids, 0);
                showButtons(attackingMids, 1);
                showButtons(wingers, 2);
                

                if (wingers[0] != null) wingers[0].setText("Sol OOS");
                if (wingers[1] != null) wingers[1].setText("Sağ OOS");
                break;
                
            case "3-5-2":

                showButtons(defensiveMids, 1);
                showButtons(centralMids, 2);
                showButtons(attackingMids, 0);
                showButtons(wingers, 2);
                

                if (wingers[0] != null) wingers[0].setText("Sol Bek");
                if (wingers[1] != null) wingers[1].setText("Sağ Bek");
                break;
                
            case "3-4-3":

                showButtons(defensiveMids, 0);
                showButtons(centralMids, 2);
                showButtons(attackingMids, 0);
                showButtons(wingers, 2);
                

                if (wingers[0] != null) wingers[0].setText("Sol Bek");
                if (wingers[1] != null) wingers[1].setText("Sağ Bek");
                break;
        }
    }
}

