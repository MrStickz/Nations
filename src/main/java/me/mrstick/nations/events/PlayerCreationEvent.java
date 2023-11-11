package me.mrstick.nations.events;

import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.UUID;

public class PlayerCreationEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        File database = new File("plugins/Nations/data/database.db");
        LocalDatabase db = new LocalDatabase(database.getAbsolutePath());

        Player player = e.getPlayer();

        if (db.CHECK("SELECT * FROM players WHERE uuid='" + player.getUniqueId() + "'", "uuid")) return;

        UUID playerUUID = player.getUniqueId();
        String playerName = player.getName();

        String command = "INSERT INTO players VALUES ('" + playerUUID + "', '" + playerName + "', '')";
        db.POST(command);

        YamlReader reader = new YamlReader();
        String welcomeMsg = reader.ReadMsg("player-first-join");
        player.sendMessage(welcomeMsg);

    }

}
