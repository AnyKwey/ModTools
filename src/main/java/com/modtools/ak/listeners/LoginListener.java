package com.modtools.ak.listeners;

import com.modtools.ak.manager.PlayerInfos;
import com.modtools.ak.manager.moderation.BanManager;
import com.modtools.ak.manager.moderation.StaffManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by AnyKwey
 */
public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PostLoginEvent e){
        ProxiedPlayer player = e.getPlayer();
        PlayerInfos.update(player.getUniqueId());
        if(BanManager.isBanned(player.getUniqueId())) player.disconnect(BanManager.banMessage(player.getUniqueId())
                .replace("&", "§")
                .replace("%reason%", BanManager.getReason(player.getUniqueId()))
                .replace("%duration%", BanManager.getTimeLeft(player.getUniqueId()))
                .replace("%author%", BanManager.getWhoBanned(player.getUniqueId()))
                .replace("%when%", BanManager.getWhenBanned(player.getUniqueId()))
                .replace("%uuid%", player.getUniqueId().toString())
                .replace("\n", "\n"));
        if(player.hasPermission("modtools.stafflist")) StaffManager.addToStaffList(player);
    }
}
