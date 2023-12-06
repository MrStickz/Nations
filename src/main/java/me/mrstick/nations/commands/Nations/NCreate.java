package me.mrstick.nations.commands.Nations;

import me.mrstick.nations.Api.Vault.VaultManager;
import me.mrstick.nations.commands.CommandInterface;
import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.JsonData.JsonData;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NCreate implements CommandInterface {

    // nation create <name> <displayName>
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly Players can execute this command!");
            return false;
        }

        YamlReader reader = new YamlReader();
        if (!sender.hasPermission("nation.create") || !sender.hasPermission("nation.*")) {
            sender.sendMessage(reader.ReadMsg("permission-error"));
            return false;
        }
        if (args.length < 3) {
            player.sendMessage(reader.ReadMsg("create-nation"));
            return false;
        }

        UUID pUUID = player.getUniqueId();

        String nName = args[1];
        String nDisplayName = args[2];

        JsonData conv = new JsonData();
        VaultManager vManager = new VaultManager();
        LocalDatabase db = new LocalDatabase("plugins/Nations/data/database.db");

        if(db.CHECK("SELECT * FROM nations WHERE owner='"+pUUID+"'", "owner")) {
            player.sendMessage(reader.ReadMsg("already-own-nation"));
            return false;
        }
        if(db.CHECK("SELECT * FROM nations WHERE name='"+nName.toLowerCase()+"'", "name")) {
            player.sendMessage(reader.ReadMsg("nation-exists"));
            return false;
        }

        Integer creationCost = reader.ReadDConf("nation.creation-cost");
        if (vManager.getBalance(player) < creationCost) {
            String errMsg = reader.ReadMsg("insufficient-creationCost");
            player.sendMessage(reader.convCost(errMsg, String.valueOf(creationCost)));
            return false;
        }


        Location loc = player.getLocation();
        World world = player.getWorld();
        String nSChunk = world.getName() + "," + loc.getChunk().getX() + "," + loc.getChunk().getZ();

        String claimCheck = db.GET("SELECT * FROM nations WHERE chunks LIKE '%"+nSChunk+"%'", "name");
        if (claimCheck != null) {
            String dName = db.GET("SELECT * FROM nations WHERE name='"+claimCheck+"'", "display-name");
            String errMsg = reader.ReadMsg("land-already-claimed");
            player.sendMessage(reader.convName(errMsg, dName));
            return false;
        }

        int nPlayers = 1;
        int nWealth = creationCost;
        int nLevel = 0;
        String chunkKey = conv.UpdateList("[]", nSChunk);
        String nRoles = "[]";

        vManager.removeBalance(player, creationCost);
        String values = "('"+nName.toLowerCase()+"', '"+ nDisplayName +"', '§7Nation of "+player.getName()+"§f', '"+pUUID+"', "+nPlayers+", "+nWealth+", "+nLevel+", '"+chunkKey+"', '"+nRoles+"')";
        db.POST("INSERT INTO nations VALUES "+values);

        String success = reader.ReadMsg("nation-created");
        player.sendMessage(reader.convName(success, nName));

        String bSuccess = reader.ReadMsg("broadcast-nation-creation");
        String sBSuccess = reader.convName(bSuccess, nDisplayName);
        Bukkit.broadcastMessage(reader.convPlayer(sBSuccess, player));

        return true;
    }
}
