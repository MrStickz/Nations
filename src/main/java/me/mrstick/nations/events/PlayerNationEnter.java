package me.mrstick.nations.events;

import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerNationEnter implements Listener {

    private Chunk lastChunk;
    private String nName;

    @EventHandler
    public void onNationEnter(PlayerMoveEvent e) {

        Chunk chunk = e.getPlayer().getChunk();
        if (chunk.equals(lastChunk)) {
            return;
        }

        lastChunk = chunk;
        LocalDatabase db = new LocalDatabase("plugins/Nations/data/database.db");
        YamlReader reader = new YamlReader();

        String chunkKey = e.getPlayer().getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ();
        String nClaim = db.GET("SELECT * FROM nations WHERE chunks LIKE '%"+chunkKey+"%'", "name");

        if (nClaim == null) {
            if (nName != null) {
                sendTitle(e.getPlayer(), reader.ReadMsg("entered-wild"), reader.ReadMsg("entered-wild-subtitle"));
            }
            nName = null;
            return;
        }
        if (nClaim.equals(nName)) {
            return;
        }

        String nDisplayName = db.GET("SELECT * FROM nations WHERE name='"+nClaim+"'", "display-name");
        String nDesc = db.GET("SELECT * FROM nations WHERE name='"+nClaim+"'", "description");

        Player player = e.getPlayer();

        String title = reader.ReadMsg("entered-nation");
        sendTitle(player, reader.convNation(title, nDisplayName), nDesc);
        nName = nClaim;

    }

    private void sendTitle(Player player, String title, String description) {

        int fadeIn = 10;
        int stay = 40;
        int fadeout = 10;
        player.sendTitle(title, description, fadeIn, stay, fadeout);

    }

}
