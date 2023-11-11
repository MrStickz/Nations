package me.mrstick.nations;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.mrstick.nations.commands.Nation;
import me.mrstick.nations.events.PlayerCreationEvent;
import me.mrstick.nations.scripts.Configs.YamlReader;
import me.mrstick.nations.scripts.DataFileCreation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class Nations extends JavaPlugin {

    @Override
    public void onLoad() {

        // Registering CommandAPI
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));

        // Creates necessary files/folders
        DataFileCreation DfCreation = new DataFileCreation();
        DfCreation.CreateDataFiles();
    }

    @Override
    public void onEnable() {

        // Registering Commands
        CommandAPI.registerCommand(Nation.class);

        // Registering Events
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerCreationEvent(), this);

        // Getting info from message.yml
        YamlReader reader = new YamlReader();
        String startMsg = reader.ReadMsg("start-message");
        Bukkit.getServer().getConsoleSender().sendMessage(startMsg);
    }

    @Override
    public void onDisable() {

        YamlReader reader = new YamlReader();
        String stopMsg = reader.ReadMsg("stop-message");
        Bukkit.getServer().getConsoleSender().sendMessage(stopMsg);
    }
}
