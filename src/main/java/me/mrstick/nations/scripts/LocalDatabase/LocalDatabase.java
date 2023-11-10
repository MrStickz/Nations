package me.mrstick.nations.scripts.LocalDatabase;
import java.sql.*;

public class LocalDatabase {

    Connection connection = null;
    private final String dbPath;

    public LocalDatabase(String dbPath) {
        this.dbPath = "jdbc:sqlite:" + dbPath;
    }

    public void POST(String command) {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbPath);
            Statement statement = connection.createStatement();

            statement.executeUpdate(command);
        } catch (SQLException | ClassNotFoundException c) {
            throw new RuntimeException(c);
        }
    }

    public String GET(String command, String value) {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbPath);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(command);
            return resultSet.getString(value);

        } catch (SQLException | ClassNotFoundException c) {
            throw  new RuntimeException(c);
        }
    }
}