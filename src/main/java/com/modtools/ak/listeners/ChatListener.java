package com.modtools.ak.listeners;

import com.modtools.ak.manager.moderation.MuteManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by AnyKwey
 */
public class ChatListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent e){
        ProxiedPlayer player = (ProxiedPlayer) e.getSender();
        if(MuteManager.isMute(player.getUniqueId())){
            if(e.getMessage().startsWith("/")) return;
            e.setCancelled(true);
            player.sendMessage(MuteManager.forbideChatMessage(player.getUniqueId()));
        }
    }
}
