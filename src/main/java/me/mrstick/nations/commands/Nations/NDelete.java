package me.mrstick.nations.commands.Nations;

import me.mrstick.nations.commands.CommandInterface;
import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NDelete implements CommandInterface {

    // nation delete confirm
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly Players can execute this command!");
            return false;
        }

        YamlReader reader = new YamlReader();
        if (!sender.hasPermission("nation.delete") || !sender.hasPermission("nation.*")) {
            sender.sendMessage(reader.ReadMsg("permission-error"));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(reader.ReadMsg("abandon-nation"));
            return false;
        }

        String confirm = args[1];

        if (!confirm.equalsIgnoreCase("confirm")) {
            sender.sendMessage(reader.ReadMsg("abandon-nation"));
            return false;
        }

        LocalDatabase db = new LocalDatabase("plugins/Nations/data/database.db");

        UUID pUUID = player.getUniqueId();

        if (!db.CHECK("SELECT * FROM nations WHERE owner='"+pUUID+"'", "owner")) {
            player.sendMessage(reader.ReadMsg("no-nation-own"));
            return false;
        }
        String nPlayers = db.GET("SELECT * FROM nations WHERE owner='"+pUUID+"'", "players");
        if (Integer.parseInt(nPlayers) > 1) {
            player.sendMessage(reader.ReadMsg("lot-players-nation"));
            return false;
        }

        String nName = db.GET("SELECT * FROM nations WHERE owner='"+pUUID+"'", "name");
        db.POST("DELETE FROM nations WHERE name='"+nName+"'");

        String successMsg = reader.ReadMsg("nation-abandoned");
        player.sendMessage(reader.convNation(successMsg, nName));

        return false;
    }
}
