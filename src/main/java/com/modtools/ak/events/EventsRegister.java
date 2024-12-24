package com.modtools.ak.events;


import com.modtools.ak.Bungee;
import com.modtools.ak.commands.*;
import com.modtools.ak.listeners.ChatListener;
import com.modtools.ak.listeners.DisconnectListener;
import com.modtools.ak.listeners.LoginListener;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

/**
 * Created by AnyKwey
 */
public class EventsRegister {

    private Plugin plugin;

    public EventsRegister(Plugin plugin){
        this.plugin = plugin;
    }

    public void registerListeners(){
        registerListener(new LoginListener());
        registerListener(new DisconnectListener());
        registerListener(new ChatListener());
    }

    public void registerCommands(){
        registerCommand(new BanCommand());
        registerCommand(new KickCommand());
        registerCommand(new MuteCommand());
        registerCommand(new StaffCommand());
        registerCommand(new UnBanCommand());
        registerCommand(new UnMuteCommand());
    }


    private void registerListener(Listener listener){
        PluginManager pm = Bungee.getInstance().getProxy().getPluginManager();
        pm.registerListener(plugin, listener);
    }

    private void registerCommand(Command command){
        plugin.getProxy().getPluginManager().registerCommand(plugin, command);
    }
}
