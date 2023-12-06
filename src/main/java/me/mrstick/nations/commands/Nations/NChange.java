package me.mrstick.nations.commands.Nations;

import me.mrstick.nations.Api.Vault.VaultManager;
import me.mrstick.nations.commands.CommandInterface;
import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NChange implements CommandInterface, TabCompleter {

    // nation change <display/name> <new name>
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly Players can execute this command!");
            return false;
        }

        YamlReader reader = new YamlReader();
        if (!sender.hasPermission("nation.change") || !sender.hasPermission("nation.*")) {
            sender.sendMessage(reader.ReadMsg("permission-error"));
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage(reader.ReadMsg("change-nation"));
            return false;
        }

        LocalDatabase db = new LocalDatabase("plugins/Nations/data/database.db");
        UUID pUUID = player.getUniqueId();

        if (!db.CHECK("SELECT * FROM nations WHERE owner='"+pUUID+"'", "owner")) {
            player.sendMessage(reader.ReadMsg("no-nation-own"));
            return false;
        }

        String subcmd = args[1];
        if (subcmd.equalsIgnoreCase("displayname")) {

            String nODisplayName = db.GET("SELECT * FROM nations WHERE owner='"+pUUID+"'", "display-name");

            String displayName = args[2];
            db.POST("UPDATE nations SET `display-name`='"+displayName+"' WHERE owner='"+pUUID+"'");

            String success = reader.ReadMsg("nation-displayname-changed");
            String sSuccess = reader.convNation(success, nODisplayName);
            String sSSuccess = reader.convName(sSuccess, displayName);
            player.sendMessage(sSSuccess);
            return true;

        } else if (subcmd.equalsIgnoreCase("name")) {

            int cost = reader.ReadDConf("nation.rename-cost");
            VaultManager manager = new VaultManager();
            if (manager.getBalance(player) < cost) {
                String err = reader.ReadMsg("insufficient-renameCost");
                player.sendMessage(reader.convCost(err, String.valueOf(cost)));
                return false;
            }

            String nOName = db.GET("SELECT * FROM nations WHERE owner='"+pUUID+"'", "name");

            String name = args[2];
            db.POST("UPDATE nations SET name='"+name+"' WHERE owner='"+pUUID+"'");
            manager.removeBalance(player, cost);

            String success = reader.ReadMsg("nation-renamed");
            String sSuccess = reader.convNation(success, nOName);
            String sSSuccess = reader.convName(sSuccess, name);
            Bukkit.broadcastMessage(sSSuccess);
            return true;

        } else {
            sender.sendMessage(reader.ReadMsg("change-nation"));
            return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("nation")) {
            if (strings.length == 2 && strings[0].equalsIgnoreCase("change")) {
                completions.add("displayname");
                completions.add("name");
                return completions;
            }
        }

        return completions;
    }
}
