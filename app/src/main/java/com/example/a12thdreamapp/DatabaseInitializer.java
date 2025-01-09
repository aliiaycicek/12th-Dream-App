package com.example.a12thdreamapp;

import android.content.Context;
import android.util.Log;


public class DatabaseInitializer {
    private final Context context;

    public DatabaseInitializer(Context context) {
        this.context = context;
    }

    public static void initializeDatabase() {
        FirebaseManager.initializeDatabase();
        FirebaseManager.clearDatabase(() -> {
            addLegendaryPlayers();
            addModernEraPlayers();
            addCoaches();
        });
    }

    private static void addLegendaryPlayers() {
        // Kaleciler
        addPlayer("Rüştü Reçber", "Kaleci", true, Player.PlayerCategory.LEGEND);
        addPlayer("Volkan Demirel", "Kaleci", true, Player.PlayerCategory.LEGEND);
        
        // Defans
        addPlayer("Roberto Carlos", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Önder Turacı", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Lugano", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Edu Dracena", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Gökhan Gönül", "Defans", true, Player.PlayerCategory.LEGEND);
        
        // Orta Saha
        addPlayer("Alex de Souza", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Emre Belözoğlu", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Mehmet Aurelio", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Selçuk Şahin", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Tuncay Şanlı", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Can Bartu", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Oğuz Çetin", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Jay-Jay Okocha", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        
        // Forvet
        addPlayer("Mateja Kezman", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Pierre van Hooijdonk", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Semih Şentürk", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Daniel Güiza", "Forvet", true, Player.PlayerCategory.LEGEND);
        
        // Ek efsane oyuncular
        addPlayer("Uğur Boral", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Zeki Rıza Sporel", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Lefter Küçükandonyadis", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Cemil Turan", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Müjdat Yetkiner", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Ogün Altıparmak", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Selçuk Yula", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Aykut Kocaman", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Rıdvan Dilmen", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Harald Schumacher", "Kaleci", true, Player.PlayerCategory.LEGEND);
        addPlayer("Engin Verel", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Jes Høgh", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Elvir Baljić", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Milan Rapaic", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Washington", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Nicolas Anelka", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Ariel Ortega", "Orta Saha", true, Player.PlayerCategory.LEGEND);

        // Ek efsane oyuncular - 2. Grup
        addPlayer("Zoran Mirković", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Stjepan Tomas", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Serhat Akın", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Volkan Yaman", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Haim Revivo", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Kennet Andersson", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Uche Okechukwu", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Saffet Sancaklı", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Aykut Erçetin", "Kaleci", true, Player.PlayerCategory.LEGEND);
        addPlayer("Bülent Uygun", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Abdullah Ercan", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Rüştü Namoğlu", "Kaleci", true, Player.PlayerCategory.LEGEND);
        addPlayer("Hakan Ünsal", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Mustafa Doğan", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Samuel Johnson", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Viorel Moldovan", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Jay-Jay Okocha", "Orta Saha", true, Player.PlayerCategory.LEGEND);

        // Ek efsane oyuncular - 3. Grup
        addPlayer("Recep Çetin", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Ümit Özat", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Yusuf Şimşek", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Mehmet Yozgatlı", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Tayfun Korkut", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Elvir Bolić", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Ceyhun Eriş", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Murat Öztürk", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Serdar Topraktepe", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Oktay Derelioğlu", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Hüseyin Kartal", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("İlhan Mansız", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Ergün Penbe", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Mert Nobre", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Mehmet Özdilek", "Forvet", true, Player.PlayerCategory.LEGEND);
        addPlayer("Aykut Yiğit", "Defans", true, Player.PlayerCategory.LEGEND);
        addPlayer("Rıza Çalımbay", "Orta Saha", true, Player.PlayerCategory.LEGEND);
        addPlayer("Tanju Çolak", "Forvet", true, Player.PlayerCategory.LEGEND);
    }

    private static void addModernEraPlayers() {
        // Kaleciler
        addPlayer("Altay Bayındır", "Kaleci", true, Player.PlayerCategory.MODERN);
        addPlayer("İrfan Can Eğribayat", "Kaleci", true, Player.PlayerCategory.MODERN);
        addPlayer("Dominik Livakovic", "Kaleci", true, Player.PlayerCategory.MODERN);
        
        // Defans
        addPlayer("Serdar Aziz", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Attila Szalai", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Ferdi Kadıoğlu", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Bright Osayi-Samuel", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Mert Müldür", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Jayden Oosterwolde", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Rodrigo Becão", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Alexander Djiku", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Çağlar Söyüncü", "Defans", true, Player.PlayerCategory.MODERN);
        addPlayer("Bonucci", "Defans", true, Player.PlayerCategory.MODERN);
        
        // Orta Saha
        addPlayer("Mesut Özil", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("İrfan Can Kahveci", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Mert Hakan Yandaş", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Arda Güler", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Miguel Crespo", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Fred", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("İsmail Yüksek", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Cengiz Ünder", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Ryan Kent", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Willian Arao", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Lincoln Henrique", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Bartuğ Elmaz", "Orta Saha", true, Player.PlayerCategory.MODERN);
        addPlayer("Krunic", "Orta Saha", true, Player.PlayerCategory.MODERN);
        
        // Forvet
        addPlayer("Edin Dzeko", "Forvet", true, Player.PlayerCategory.MODERN);
        addPlayer("Dusan Tadic", "Forvet", true, Player.PlayerCategory.MODERN);
        addPlayer("Sebastian Szymański", "Forvet", true, Player.PlayerCategory.MODERN);
        addPlayer("Joshua King", "Forvet", true, Player.PlayerCategory.MODERN);
        addPlayer("Serdar Dursun", "Forvet", true, Player.PlayerCategory.MODERN);
        addPlayer("Michy Batshuayi", "Forvet", true, Player.PlayerCategory.MODERN);
        addPlayer("João Pedro", "Forvet", true, Player.PlayerCategory.MODERN);
        addPlayer("Umut Nayir", "Forvet", true, Player.PlayerCategory.MODERN);
    }

    private static void addCoaches() {
        // Teknik Direktörler
        addCoach("Zico", true);
        addCoach("Christoph Daum", true);
        addCoach("Luis Aragonés", true);
        addCoach("Aykut Kocaman", true);
        addCoach("Ersun Yanal", true);
        addCoach("Vitor Pereira", true);
        addCoach("Dick Advocaat", true);
        addCoach("İsmail Kartal", true);
        addCoach("Phillip Cocu", true);
        addCoach("Jorge Jesus", true);
        addCoach("Jose Mourinho", true);
    }

    private static void addPlayer(String name, String position, boolean isFenerbahcePlayer, Player.PlayerCategory category) {
        Player player = new Player(name, position, isFenerbahcePlayer, category);
        FirebaseManager.addPlayer(player, new FirebaseManager.OnPlayerAddedListener() {
            @Override
            public void onPlayerAdded() {
                Log.d("DatabaseInitializer", "Oyuncu eklendi: " + name);
            }

            @Override
            public void onError(String error) {
                Log.e("DatabaseInitializer", "Oyuncu eklenirken hata: " + error);
            }
        });
    }

    private static void addCoach(String name, boolean isFenerbahceCoach) {
        Coach coach = new Coach(name, isFenerbahceCoach);
        FirebaseManager.addCoach(coach, new FirebaseManager.OnCoachAddedListener() {
            @Override
            public void onCoachAdded() {

            }

            @Override
            public void onError(String error) {

            }
        });
    }
} 