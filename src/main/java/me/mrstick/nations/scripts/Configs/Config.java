package me.mrstick.nations.scripts.Configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Config {

    public FileConfiguration loadConfig(String Path) {
        File configFile = new File(Path);
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void copyDefaultConfig(File configFile, String name) {
        try {
            // Load the default config from resources and replace!
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name)) {
                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
