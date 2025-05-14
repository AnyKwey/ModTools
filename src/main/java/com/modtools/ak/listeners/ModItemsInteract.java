package com.modtools.ak.listeners;

import com.modtools.ak.Main;
import com.modtools.ak.manager.moderation.PlayerManager;
import com.modtools.ak.utils.itemstack.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.BlockIterator;

import java.util.*;

/**
 * Created by AnyKwey
 */
public class ModItemsInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
        if(!(e.getRightClicked() instanceof  Player)) return;
        Player target = (Player) e.getRightClicked();
 
        e.setCancelled(true);

        /**
         * See players' inventory
         */
        Material invsee = new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Invsee.item"))).getType();
        if(player.getInventory().getItemInHand().getType() == invsee){
            Inventory inv = Bukkit.createInventory(null, Main.getInstance().getConfig().getInt("Invsee.inventory-size"), Main.getInstance().getConfig().getString("Invsee.inventory-name").replace("&", "§").replace("%name%", target.getName()));

            for(int i = 9; i < 53; i++){
                if(target.getInventory().getItem(i) != null){
                    inv.setItem(i, target.getInventory().getItem(i));
                }
            }

            inv.setItem(1, target.getInventory().getHelmet());
            inv.setItem(3, target.getInventory().getChestplate());
            inv.setItem(5, target.getInventory().getLeggings());
            inv.setItem(7, target.getInventory().getBoots());

            player.openInventory(inv);
        }

        /**
         * Follow a player
         */
        Material follow = new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Follow.item"))).getType();
        if(player.getInventory().getItemInHand().getType() == follow){
            player.setPassenger(target.getPlayer());
        }

        /**
         * Freeze players
         */
        Material freeze = new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Freeze.item"))).getType();
        if(player.getInventory().getItemInHand().getType() == freeze){
            if(Main.getInstance().getFrozenPlayers().containsKey(target.getUniqueId())){
                Main.getInstance().getFrozenPlayers().remove(target.getUniqueId());
                target.sendMessage(Main.getInstance().getConfig().getString("Freeze.unfreeze.target").replace("&", "§").replace("%player_name%", player.getName()));
                player.sendMessage(Main.getInstance().getConfig().getString("Freeze.unfreeze.player").replace("&", "§").replace("%name%", target.getName()));
            } else {
                Main.getInstance().getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
                target.sendMessage(Main.getInstance().getConfig().getString("Freeze.freeze.target").replace("&", "§").replace("%player_name%", player.getName()));
                player.sendMessage(Main.getInstance().getConfig().getString("Freeze.freeze.player").replace("&", "§").replace("%name%", target.getName()));
            }
        }

        /**
         * Kill players immediately
         */
        Material killer = new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Killer.item"))).getType();
        if(player.getInventory().getItemInHand().getType() == killer){
            target.damage(target.getHealth());
        }
    }
 
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;

        e.setCancelled(true);

        /**
         * Random teleportation
         */
        Material randomtp = new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Random-tp.item"))).getType();
        if(player.getInventory().getItemInHand().getType() == randomtp){
            List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
            list.remove(player);

            if(list.size() == 0){
                player.sendMessage(Main.getInstance().getConfig().getString("Random-tp.size-empty").replace("&", "§"));
                return;
            }

            Player target = list.get(new Random().nextInt(list.size()));
            player.teleport(target.getLocation());
            player.sendMessage(Main.getInstance().getConfig().getString("Random-tp.teleport-to").replace("&", "§").replace("%name%", target.getName()));
        }

        /**
         * Vanish
         */
        Material vanish = new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Vanish.item"))).getType();
        if(player.getInventory().getItemInHand().getType() == vanish){
            if(PlayerManager.getFromPlayer(player).isVanished()){
                PlayerManager.getFromPlayer(player).setVanished(false);
                player.sendMessage(Main.getInstance().getConfig().getString("Vanish.unvanish").replace("&", "§"));
            } else if(!PlayerManager.getFromPlayer(player).isVanished()){
                PlayerManager.getFromPlayer(player).setVanished(true);
                player.sendMessage(Main.getInstance().getConfig().getString("Vanish.vanish").replace("&", "§"));
            }
        }

        /**
         * Running
         */
        Material running = new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Running.item"))).getType();
        if(player.getInventory().getItemInHand().getType() == running){
            int maxDistance = (Main.getInstance().getConfig().getInt("Running.maxDistance"));

            BlockIterator iterator = new BlockIterator(player, maxDistance);

            while (iterator.hasNext()) {
                Block block = iterator.next();
                if (block.getType() != Material.AIR && block.getType().isSolid()) {
                    player.teleport(block.getLocation().add(0.5, 1, 0.5));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        for(Player players : Bukkit.getOnlinePlayers()){
            if(PlayerManager.isInModerationMod(players)){
                PlayerManager pm = PlayerManager.getFromPlayer(players);
                if(pm.isVanished()){
                    player.hidePlayer(players);
                }
            }
        }
    }
}