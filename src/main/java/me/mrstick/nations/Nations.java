package me.mrstick.nations;

import me.mrstick.nations.Api.Vault.Vault;
import me.mrstick.nations.commands.Nations.NationsHandler;
import me.mrstick.nations.events.ClaimsSystem.onClaimBreak;
import me.mrstick.nations.events.ClaimsSystem.onClaimInteract;
import me.mrstick.nations.events.ClaimsSystem.onClaimPlace;
import me.mrstick.nations.events.PlayerCreationEvent;
import me.mrstick.nations.events.PlayerNationEnter;
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

        // Registering Apis
        Vault vault = new Vault();
        vault.setupVault(this);

        // Registering Commands
        getCommand("nation").setExecutor(new NationsHandler());

        // Registering Events
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerCreationEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onClaimBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onClaimPlace(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new onClaimInteract(), this);

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerNationEnter(), this);


        Bukkit.getServer().getConsoleSender().sendMessage("§f[§6Nations§f] Nations are Ready to Fight!");
    }

    @Override
    public void onDisable() {

        Bukkit.getServer().getConsoleSender().sendMessage("§f[§6Nations§f] Peace Out!");
    }
}
