package com.modtools.ak.events;

import com.modtools.ak.Main;
import com.modtools.ak.commands.ModCommand;
import com.modtools.ak.listeners.ModCancels;
import com.modtools.ak.listeners.ModItemsInteract;
import com.modtools.ak.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Created by AnyKwey
 */
public class EventRegister {

    private Plugin plugin;

    public EventRegister(Plugin plugin){
        this.plugin = plugin;
    }

    public void registerListeners(){
        registerListener(new PlayerJoinListener());
        registerListener(new ModCancels());
        registerListener(new ModItemsInteract());
    }

    public void registerCommands(){
        registerCommand("mod", new ModCommand());
    }


    private void registerListener(Listener listener){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(listener, plugin);
    }

    private void registerCommand(String cmd, CommandExecutor command){
        Main.getInstance().getCommand(cmd).setExecutor(command);
    }
}
