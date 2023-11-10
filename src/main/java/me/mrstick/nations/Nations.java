package me.mrstick.nations;

import me.mrstick.nations.scripts.DataFileCreation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nations extends JavaPlugin {

    @Override
    public void onLoad() {

        // Creates necessary files/folders
        DataFileCreation DfCreation = new DataFileCreation();
        DfCreation.CreateDataFiles();
    }

    @Override
    public void onEnable() {

        Bukkit.getServer().getConsoleSender().sendMessage("§f[§6Nations§f] Nations are Ready to Fight!");
    }

    @Override
    public void onDisable() {

        Bukkit.getServer().getConsoleSender().sendMessage("§f[§6Nations§f] Peace Out!");
    }
}
