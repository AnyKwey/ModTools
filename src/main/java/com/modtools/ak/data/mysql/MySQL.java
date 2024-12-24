package com.modtools.ak.data.mysql;

import com.modtools.ak.Bungee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by AnyKwey
 */
public class MySQL {

    private static Connection conn;

    public static void connect(String host, int port, String database, String user, String password) {
        if (!isConnected()) {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=latin1&autoReconnect=true", user, password);
                System.out.println("[MySQL] Connection accepted!");
            } catch (SQLException e) {
                System.out.println("[MySQL] Connection Refused!");
                e.printStackTrace();
            }
        }
    }

    public static void disconnect(){
        if (isConnected()){
            try {
                conn.close();
                System.out.println("[MySQL] MySQL disconnected!");
            } catch (SQLException e) {
                System.out.println("[MySQL] MySQL could not disconnect!");
                e.printStackTrace();
            }
        }
    }

    public static void createTables() {
        try {
            Statement state = conn.createStatement();
            state.executeUpdate("CREATE TABLE IF NOT EXISTS `playerinfos` (`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " `uuid` VARCHAR(255)," +
                    " `name` VARCHAR(255))");
            state.executeUpdate("CREATE TABLE IF NOT EXISTS `bans` (`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " `uuid` VARCHAR(255)," +
                    " `end` BIGINT," +
                    " `reason` VARCHAR(255)," +
                    " `author` VARCHAR(255)," +
                    " `banned` TIMESTAMP)");
            state.executeUpdate("CREATE TABLE IF NOT EXISTS `mutes` (`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " `uuid` VARCHAR(255)," +
                    " `end` BIGINT," +
                    " `reason` VARCHAR(255)," +
                    " `author` VARCHAR(255)," +
                    " `muted` TIMESTAMP)");
            state.executeUpdate("CREATE TABLE IF NOT EXISTS `moderation` (`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " `uuid` VARCHAR(255)," +
                    " `name` VARCHAR(255))");
        } catch (SQLException e){
            System.out.println("[MySQL] tables could not be created");
            e.printStackTrace();
        }
    }

    public static boolean isConnected(){
        try {
            if ((conn == null) || (conn.isClosed()) || conn.isValid(5)){
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("[MySQL] MySQL could not stay connected!");
            e.printStackTrace();
        }
        return false;
    }

    public static Connection getConnection() {
        return conn;
    }
}