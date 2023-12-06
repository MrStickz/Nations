package me.mrstick.nations.commands.Nations;

import me.mrstick.nations.commands.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NationsHandler extends CommandHandler {

    public NationsHandler() {
        register("", new Nation());
        register("create", new NCreate());
        register("delete", new NDelete());
        register("change", new NChange());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            getExecutor("").onCommand(sender, command, s, args);
            return true;
        }

        if (exists(args[0])) {

            getExecutor(args[0]).onCommand(sender, command, s, args);
        } else {

            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {

        if (cmd.getName().equalsIgnoreCase("nation")) {

            return super.onTabComplete(sender, cmd, alias, args);
        }

        return null;
    }
}
