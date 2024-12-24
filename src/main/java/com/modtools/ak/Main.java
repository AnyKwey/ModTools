package com.modtools.ak;

import com.modtools.ak.data.mysql.MySQL;
import com.modtools.ak.events.EventRegister;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;
    private Map<UUID, Location> frozenPlayers;

    @Override
    public void onEnable() {
        instance = this;
        frozenPlayers = new HashMap<>();
        saveDefaultConfig();
        MySQL.connect(getConfig().getString("mysql.host"), getConfig().getInt("mysql.port"), getConfig().getString("mysql.database"), getConfig().getString("mysql.user"), getConfig().getString("mysql.password"));
        getServer().getConsoleSender().sendMessage(getConfig().getString("prefix").replace("&", "§") + getConfig().getString("start-message").replace("&", "§"));
        new EventRegister(this).registerCommands();
        new EventRegister(this).registerListeners();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
        getServer().getConsoleSender().sendMessage(getConfig().getString("prefix").replace("&", "§") + getConfig().getString("close-message").replace("&", "§"));
    }

    public static Main getInstance() {
        return instance;
    }

    public Map<UUID, Location> getFrozenPlayers() {
        return frozenPlayers;
    }

    public boolean isFreeze(Player player){
        return getFrozenPlayers().containsKey(player.getUniqueId());
    }
}