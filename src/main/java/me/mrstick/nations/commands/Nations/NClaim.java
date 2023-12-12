package me.mrstick.nations.commands.Nations;

import me.mrstick.nations.Api.Vault.VaultManager;
import me.mrstick.nations.commands.CommandInterface;
import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.JsonData.JsonData;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.UUID;

public class NClaim implements CommandInterface {

    // nation claim
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly Players can execute this command!");
            return false;
        }
        YamlReader reader = new YamlReader();
        if (!sender.hasPermission("nation.claim") || !sender.hasPermission("nation.*")) {
            sender.sendMessage(reader.ReadMsg("permission-error"));
            return false;
        }
        if (args.length < 1) {
            player.sendMessage(reader.ReadMsg("claim-nation"));
            return false;
        }

        UUID pUUID = player.getUniqueId();

        LocalDatabase db = new LocalDatabase("plugins/Nations/data/database.db");
        if(!db.CHECK("SELECT * FROM nations WHERE owner='"+pUUID+"'", "owner")) {
            player.sendMessage(reader.ReadMsg("no-nation-own"));
            return false;
        }

        Chunk chunk = player.getLocation().getChunk();
        String chunkKey = player.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ();

        String claimChunk = db.GET("SELECT * FROM nations WHERE chunks LIKE '%"+chunkKey+"%'", "name");
        if (!(claimChunk == null)) {
            String dName = db.GET("SELECT * FROM nations WHERE name='"+claimChunk+"'", "display-name");
            String errMsg = reader.ReadMsg("land-already-claimed");
            player.sendMessage(reader.convNation(errMsg, dName));
            return false;
        }

        VaultManager manager = new VaultManager();
        int cost = reader.ReadDConf("nation.chunk-claim-cost");
        if (manager.getBalance(player) < cost) {
            System.out.println(manager.getBalance(player));
            String errMsg = reader.ReadMsg("insufficient-claimCost");
            String sErrMsg = reader.convCost(errMsg, String.valueOf(cost));
            player.sendMessage(sErrMsg);
            return false;
        }

        JsonData data = new JsonData();

        String nChunks = db.GET("SELECT * FROM nations WHERE owner='"+pUUID+"'", "chunks");
        String newNChunks = data.UpdateList(nChunks, chunkKey);

        db.POST("UPDATE nations SET chunks='"+newNChunks+"' WHERE owner='"+pUUID+"'");
        manager.removeBalance(player, cost);

        String success = reader.ReadMsg("chunk-claimed");
        player.sendMessage(success);

        return true;
    }

}
