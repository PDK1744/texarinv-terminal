package com.texar.inventory.db;


import java.io.FileInputStream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static DatabaseManager instance;
    private String url;
    private String user;
    private String password;

    public DatabaseManager() {
        loadConfig();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void loadConfig() {
        Properties prop = new Properties();
        Path configPath = Paths.get("config.properties");
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
            System.out.println("Loaded config.properties from external file.");
        } catch (IOException e) {
            System.out.println("External config.properties not found, trying classpath resource...");
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new RuntimeException("Unable to find config.properties externally or in resources");
                }
                prop.load(input);
                System.out.println("Loaded config.properties from resources.");
            } catch (IOException ex) {
                throw new RuntimeException("Failed to load DB config", ex);
            }
        }
        this.url = prop.getProperty("db.url");
        this.user = prop.getProperty("db.user");
        this.password = prop.getProperty("db.password");
    }



    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


}
