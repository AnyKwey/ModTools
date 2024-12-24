package com.modtools.ak.manager;

import com.modtools.ak.data.mysql.MySQL;
import net.md_5.bungee.api.ProxyServer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerInfos {

    public static void update(UUID uuid) {
        try {
            PreparedStatement sts = MySQL.getConnection().prepareStatement("SELECT name FROM playerinfos WHERE uuid=?");
            sts.setString(1, uuid.toString());
            ResultSet rs = sts.executeQuery();
            if (rs.next()) {
                PreparedStatement update = MySQL.getConnection().prepareStatement("UPDATE playerinfos SET name=? WHERE uuid=?");
                update.setString(1, ProxyServer.getInstance().getPlayer(uuid).getName());
                update.setString(2, uuid.toString());
                update.executeUpdate();
                update.close();
            } else {
                PreparedStatement insert = MySQL.getConnection().prepareStatement("INSERT INTO playerinfos (uuid, name) VALUES (?, ?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, ProxyServer.getInstance().getPlayer(uuid).getName());
                insert.executeUpdate();
                insert.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean exist(String playerName) {
        try {
            PreparedStatement sts = MySQL.getConnection().prepareStatement("SELECT * FROM playerinfos WHERE name=?");
            sts.setString(1, playerName);
            ResultSet rs = sts.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static UUID getUUID(String playerName) {
        try {
            PreparedStatement sts = MySQL.getConnection().prepareStatement("SELECT uuid FROM playerinfos WHERE name=?");
            sts.setString(1, playerName);
            ResultSet rs = sts.executeQuery();
            if (rs.next())
                return UUID.fromString(rs.getString("uuid"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
