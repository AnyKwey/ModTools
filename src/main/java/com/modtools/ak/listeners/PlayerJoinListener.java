package com.modtools.ak.listeners;

import com.modtools.ak.manager.moderation.ModManager;
import com.modtools.ak.manager.moderation.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by AnyKwey
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if(ModManager.isInMod(player.getUniqueId())) {
            new PlayerManager(player).init();
        }
    }
}
