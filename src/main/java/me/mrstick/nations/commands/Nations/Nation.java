package me.mrstick.nations.commands.Nations;

import me.mrstick.nations.commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Nation implements CommandInterface {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (args.length > 1) return false;

        String help = """
                §8-----------------------------------
                §7• §6Nations §7- §f[§e/nation§f]
                §7• §e/nation §7- §fShows all the commands
                §7• §e/nation create §7- §fDeclare your own Nation
                §7• §e/nation abandon §7- §fDelete/Abandon your Nation
                §7• §e/nation change §7- §fEdit info about your Nation
                §7• §e/nation claim §7- §fClaim's the Chunk you standing on
                §8-----------------------------------
                """;
        sender.sendMessage(help);

        return false;
    }
}
