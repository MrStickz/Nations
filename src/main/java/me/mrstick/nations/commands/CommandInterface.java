package me.mrstick.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

//IMPORTANT: This is an interface, not a class.
public interface CommandInterface {

    //Every time I make a command, I will use this same method.
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args);

}