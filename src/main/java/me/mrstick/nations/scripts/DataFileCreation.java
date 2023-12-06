package me.mrstick.nations.scripts;

import me.mrstick.nations.scripts.Configs.Config;
import me.mrstick.nations.scripts.LocalDatabase.LocalDatabase;

import java.io.File;
import java.io.IOException;

public class DataFileCreation {

    public void CreateDataFiles() {

        // Creates Nations Folder
        File folder = new File("plugins/Nations");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Creates Config.yaml file
        File config = new File(folder, "config.yml");
        if (!config.exists()) {
            try {

                config.createNewFile();
                Config configFile = new Config();
                configFile.copyDefaultConfig(config, "config.yml");

            } catch (IOException c) {
                c.printStackTrace();
            }
        }

        // Creates Messages.yml file
        File messages = new File(folder, "messages.yml");
        if (!messages.exists()) {
            try {
                messages.createNewFile();
                Config msgFile = new Config();
                msgFile.copyDefaultConfig(messages, "messages.yml");
            } catch (IOException c) {
                c.printStackTrace();
            }
        }

        // Creates Data Folder
        File dataFd = new File(folder, "data");
        if (!dataFd.exists()) {
            dataFd.mkdir();
        }

        // Creates Database
        File database = new File(dataFd, "database.db");
        if (!database.exists()) {
            try {
                database.createNewFile();
            } catch (IOException c) {
                c.printStackTrace();
            }

            LocalDatabase db = new LocalDatabase(database.getAbsolutePath());
            db.POST("CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, username TEXT, nation TEXT, `nation-role` TEXT)");
            db.POST("CREATE TABLE IF NOT EXISTS nations (name TEXT PRIMARY KEY, `display-name` TEXT, description TEXT, owner INT, players INT, wealth INT, level INT, chunks TEXT, roles TEXT)");
            db.POST("CREATE TABLE IF NOT EXISTS subclaims (`area-name` TEXT PRIMARY KEY, nation TEXT, chunkkey TEXT, tax INT, `access-player` TEXT, `access-role` TEXT)");
        }
    }

}
