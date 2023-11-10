package me.mrstick.nations.scripts;

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
        File config = new File(folder, "config.yaml");
        if (!config.exists()) {
            try {
                config.createNewFile();
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
            db.POST("CREATE TABLE IF NOT EXISTS players (uuid TEXT PRIMARY KEY, username TEXT)");
        }

    }

}
