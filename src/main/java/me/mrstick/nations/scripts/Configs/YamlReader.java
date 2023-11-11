package me.mrstick.nations.scripts.Configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class YamlReader {

    public String ReadMsg(String path) {

        Config conf = new Config();
        FileConfiguration msg = conf.loadConfig("plugins/Nations/messages.yml");

        String prefix = msg.getString("prefix");
        String message = msg.getString(path);

        if (prefix == null || message == null) {
            Bukkit.getServer().getConsoleSender().sendMessage("§cAn Error occurred in Messages.yml, Please Check!");
            return null;
        }

        return message.replace("{prefix}", prefix);
    }

}
