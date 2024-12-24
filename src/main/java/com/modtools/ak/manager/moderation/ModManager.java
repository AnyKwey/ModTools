package com.modtools.ak.manager.moderation;

import com.modtools.ak.data.mysql.MySQL;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ModManager {

  public static void add(UUID uuid) {
    if (isInMod(uuid))
      return;

    try {
      PreparedStatement sts = MySQL.getConnection().prepareStatement("INSERT INTO moderation (uuid, name) VALUES (?, ?)");
      sts.setString(1, uuid.toString());
      sts.setString(2, Bukkit.getPlayer(uuid).getName());
      sts.executeUpdate();
      sts.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public static void remove(UUID uuid) {
    if (!isInMod(uuid))
      return; 
    try {
      PreparedStatement sts = MySQL.getConnection().prepareStatement("DELETE FROM moderation WHERE uuid=?");
      sts.setString(1, uuid.toString());
      sts.executeUpdate();
      sts.close();

    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
  
  public static boolean isInMod(UUID uuid) {
    try {
      PreparedStatement sts = MySQL.getConnection().prepareStatement("SELECT * FROM moderation WHERE uuid=?");
      sts.setString(1, uuid.toString());
      ResultSet rs = sts.executeQuery();
      return rs.next();

    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    } 
  }
}