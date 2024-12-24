package com.modtools.ak.utils;

import com.modtools.ak.Bungee;

import java.util.HashMap;

/**
 * Created by AnyKwey
 */
public enum TimeUnit {

  SECOND(Bungee.getInstance().getConfig("config").getString("TimeUnit.Second"), "s", 1L),
  MINUTE(Bungee.getInstance().getConfig("config").getString("TimeUnit.Minute"), "min", 60L),
  HOUR(Bungee.getInstance().getConfig("config").getString("TimeUnit.Hour"), "h", 3600L),
  DAY(Bungee.getInstance().getConfig("config").getString("TimeUnit.Day"), "d", 86400L),
  MOUTH(Bungee.getInstance().getConfig("config").getString("TimeUnit.Mouth"), "m", 2592000L);
  
  private String name;
  
  private String shortcut;
  
  private long toSecond;
  
  private static HashMap<String, TimeUnit> id_shortcuts;
  
  static {
    id_shortcuts = new HashMap<String, TimeUnit>();
    for (TimeUnit units : values())
      id_shortcuts.put(units.shortcut, units); 
  }
  
  TimeUnit(String name, String shortcut, long toSecond) {
    this.name = name;
    this.shortcut = shortcut;
    this.toSecond = toSecond;
  }
  
  public static TimeUnit getFromShortcut(String shortcut) {
    return id_shortcuts.get(shortcut);
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getShortcut() {
    return this.shortcut;
  }
  
  public long getToSecond() {
    return this.toSecond;
  }
  
  public static boolean existFromShortcut(String shortcut) {
    return id_shortcuts.containsKey(shortcut);
  }
}