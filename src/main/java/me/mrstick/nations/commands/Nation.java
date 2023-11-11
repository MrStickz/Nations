package me.mrstick.nations.commands;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import org.bukkit.command.CommandSender;

@Command("nation")
public class Nation {

    @Default
    public static void nation(CommandSender sender) {
        String help = """
                §8-----------------------------------
                §7• §6Nations §7- §f[§e/nation§f]
                §7• §e/nation §7- §fShows all the commands
                §8-----------------------------------
                """;
        sender.sendMessage(help);
    }


}
