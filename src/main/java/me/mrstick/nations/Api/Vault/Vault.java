package me.mrstick.nations.Api.Vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private static Economy economy;

    public void setupVault(Plugin plugin) {

        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage("§f[§6Nations§f]§c Vault not found. Please Install Vault in your Server");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getConsoleSender().sendMessage("§f[§6Nations§f]§c Economy Manager not found! Install Economy Manager like Essentials");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        economy = rsp.getProvider();

    }

    public static Economy getEconomy() {

        return economy;
    }

}
