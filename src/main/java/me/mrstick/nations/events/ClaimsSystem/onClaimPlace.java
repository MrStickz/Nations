package me.mrstick.nations.events.ClaimsSystem;

import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public class onClaimPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getChunk();
        String world = player.getWorld().getName();
        String chunkKey = world + "," + chunk.getX() + "," + chunk.getZ();

        LocalDatabase db = new LocalDatabase("plugins/Nations/data/database.db");
        String chunkExist = db.GET("SELECT * FROM nations WHERE chunks LIKE '%"+chunkKey+"%'", "chunks");

        if (chunkExist == null) {
            return;
        }
        String uuid = db.GET("SELECT * FROM nations WHERE chunks='"+chunkExist+"'", "owner");
        if (Objects.equals(player.getUniqueId().toString(), uuid)) {
            return;
        }

        YamlReader reader = new YamlReader();

        String nName = db.GET("SELECT * FROM nations WHERE chunks='"+chunkExist+"'", "display-name");
        e.setCancelled(true);

        String cMsg = reader.ReadMsg("land-claimed");
        player.sendMessage(reader.convName(cMsg, nName));

    }

}
