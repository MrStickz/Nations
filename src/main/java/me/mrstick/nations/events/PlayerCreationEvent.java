package me.mrstick.nations.events;

import me.mrstick.nations.Api.Vault.Vault;
import me.mrstick.nations.scripts.Configs.Config;
import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
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

        Config conf = new Config();
        FileConfiguration config = conf.loadConfig("plugins/Nations/config.yml");

        int startBal = config.getInt("economy.start-bal");

        String command = "INSERT INTO players VALUES ('" + playerUUID + "', '" + playerName + "', '', '')";
        db.POST(command);

        Economy economy = Vault.getEconomy();
        economy.depositPlayer(player, startBal);

        // Sending First Join Message
        YamlReader reader = new YamlReader();
        String welcomeMsg = reader.ReadMsg("player-first-join");
        player.sendMessage(welcomeMsg);

    }

}
