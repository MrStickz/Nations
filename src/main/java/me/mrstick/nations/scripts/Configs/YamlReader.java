package me.mrstick.nations.scripts.Configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class YamlReader {

    public String ReadSConf(String path) {

        Config conf = new Config();
        FileConfiguration configuration = conf.loadConfig("plugins/Nations/config.yml");

        return configuration.getString(path);

    }

    public Integer ReadDConf(String path) {

        Config conf = new Config();
        FileConfiguration configuration = conf.loadConfig("plugins/Nations/config.yml");

        return configuration.getInt(path);

    }

    public Boolean ReadBConf(String path) {

        Config conf = new Config();
        FileConfiguration configuration = conf.loadConfig("plugins/Nations/config.yml");

        return configuration.getBoolean(path);

    }

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

    public String convName(String msg, String name) {
        return msg.replace("{name}", name);
    }

    public String convCost(String msg, String cost) {
        return msg.replace("{cost}", cost);
    }

    public String convPlayer(String msg, Player player) {
        String name = player.getName();
        return msg.replace("{player}", name);
    }
    public String convPlayer(String msg, String name) {
        return msg.replace("{player}", name);
    }

    public String convNation(String msg, String nation) {
        return msg.replace("{nation}", nation);
    }

}
