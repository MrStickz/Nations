package me.mrstick.nations.Api.Vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.util.Map;

public class VaultManager {

    public Double getBalance(Player player) {

        Economy eco = Vault.getEconomy();
        return eco.getBalance(player);

    }

    public void removeBalance(Player player, Integer amount) {
        Economy eco = Vault.getEconomy();
        eco.withdrawPlayer(player, amount);
    }

}
