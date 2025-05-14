package com.modtools.ak.listeners;

import com.modtools.ak.manager.moderation.ModManager;
import com.modtools.ak.manager.moderation.StaffManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by AnyKwey
 */
public class DisconnectListener implements Listener {

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e){
        ProxiedPlayer player = e.getPlayer();
        if(StaffManager.IsInStaffList(player)) StaffManager.removeToStaffList(player);
        ModManager.remove(player.getUniqueId());
        if(ModManager.isInMod(player.getUniqueId())){
            ModManager.remove(player.getUniqueId());
        }
    }
}
