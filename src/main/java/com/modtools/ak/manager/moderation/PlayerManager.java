package com.modtools.ak.manager.moderation;

import com.modtools.ak.Main;
import com.modtools.ak.utils.itemstack.ItemBuilder;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by AnyKwey
 */
public class PlayerManager {

    private static List<UUID> moderateurs = new ArrayList<>();
    private static Map<UUID, PlayerManager> players = new HashMap<>();
    private Player player;
    private ItemStack[] items = new ItemStack[40];
    private boolean vanished;

    public PlayerManager(Player player){
        this.player = player;
        vanished = false;
    }

    public void init(){
        getPlayers().put(player.getUniqueId(), this);
        getModerateurs().add(player.getUniqueId());
        player.setGameMode(GameMode.getByValue(Main.getInstance().getConfig().getInt("gamemode")));
        if(Main.getInstance().getConfig().getBoolean("fly")) player.setFlying(true);
        saveInventory();

        if(Main.getInstance().getConfig().getBoolean("Knockback.use")) {
            player.getInventory().setItem(Main.getInstance().getConfig().getInt("Knockback.slot") - 1,
                    new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Knockback.item")))
                    .setDisplayName(Main.getInstance().getConfig().getString("Knockback.name").replace("&", "§"))
                    .setLore(Main.getInstance().getConfig().getString("Knockback.lore").replace("&", "§"))
                    .addEnchantment(Enchantment.KNOCKBACK, Main.getInstance().getConfig().getInt("Knockback.knockback")).toItemStack());
        }

        if(Main.getInstance().getConfig().getBoolean("Follow.use")) {
            player.getInventory().setItem(Main.getInstance().getConfig().getInt("Follow.slot") - 1,
                    new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Follow.item")))
                    .setDisplayName(Main.getInstance().getConfig().getString("Follow.name").replace("&", "§"))
                    .setLore(Main.getInstance().getConfig().getString("Follow.lore").replace("&", "§")).toItemStack());
        }

        if(Main.getInstance().getConfig().getBoolean("Invsee.use")) {
            player.getInventory().setItem(Main.getInstance().getConfig().getInt("Invsee.slot") - 1,
                    new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Invsee.item")))
                    .setDisplayName(Main.getInstance().getConfig().getString("Invsee.name").replace("&", "§"))
                    .setLore(Main.getInstance().getConfig().getString("Invsee.lore").replace("&", "§")).toItemStack());
        }

        if(Main.getInstance().getConfig().getBoolean("Vanish.use")) {
            player.getInventory().setItem(Main.getInstance().getConfig().getInt("Vanish.slot") - 1,
                    new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Vanish.item")))
                    .setDisplayName(Main.getInstance().getConfig().getString("Vanish.name").replace("&", "§"))
                    .setLore(Main.getInstance().getConfig().getString("Vanish.lore").replace("&", "§")).toItemStack());
        }

        if(Main.getInstance().getConfig().getBoolean("Random-tp.use")) {
            player.getInventory().setItem(Main.getInstance().getConfig().getInt("Random-tp.slot") - 1,
                    new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Random-tp.item")))
                    .setDisplayName(Main.getInstance().getConfig().getString("Random-tp.name").replace("&", "§"))
                    .setLore(Main.getInstance().getConfig().getString("Random-tp.lore").replace("&", "§")).toItemStack());
        }

        if(Main.getInstance().getConfig().getBoolean("Freeze.use")) {
            player.getInventory().setItem(Main.getInstance().getConfig().getInt("Freeze.slot") - 1,
                    new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Freeze.item")))
                    .setDisplayName(Main.getInstance().getConfig().getString("Freeze.name").replace("&", "§"))
                    .setLore(Main.getInstance().getConfig().getString("Freeze.lore").replace("&", "§")).toItemStack());
        }

        if(Main.getInstance().getConfig().getBoolean("Runner.use")) {
            player.getInventory().setItem(Main.getInstance().getConfig().getInt("Runner.slot") - 1,
                    new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Runner.item")))
                    .setDisplayName(Main.getInstance().getConfig().getString("Runner.name").replace("&", "§"))
                    .setLore(Main.getInstance().getConfig().getString("Runner.lore").replace("&", "§")).toItemStack());
        }

        if(Main.getInstance().getConfig().getBoolean("Killer.use")) {
            player.getInventory().setItem(Main.getInstance().getConfig().getInt("Killer.slot") - 1,
                    new ItemBuilder(Material.valueOf(Main.getInstance().getConfig().getString("Killer.item")))
                    .setDisplayName(Main.getInstance().getConfig().getString("Killer.name").replace("&", "§"))
                    .setLore(Main.getInstance().getConfig().getString("Killer.lore").replace("&", "§")).toItemStack());
        }
    }

    public void destroy(){
        getPlayers().remove(player.getUniqueId());
        getModerateurs().remove(player.getUniqueId());
        player.setGameMode(Bukkit.getServer().getDefaultGameMode());
        if(Main.getInstance().getConfig().getBoolean("fly")) player.setFlying(false);
        giveInventory();
    }

    public static PlayerManager getFromPlayer(Player player){
        return getPlayers().get(player.getUniqueId());
    }

    public static boolean isInModerationMod(Player player){
        return getModerateurs().contains(player.getUniqueId());
    }

    public ItemStack[] getItems() {
        return items;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (vanished) {
                if(ModManager.isInMod(players.getUniqueId())) players.showPlayer(player);
                if(!ModManager.isInMod(players.getUniqueId())) players.hidePlayer(player);
            } else {
                players.showPlayer(player);
            }
        }
    }

    public void saveInventory(){
        for(int slot = 0; slot < 36; slot++){
            ItemStack item = player.getInventory().getItem(slot);
            if(item != null){
                items[slot] = item;
            }
        }

        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void giveInventory(){
        player.getInventory().clear();

        for(int slot = 0; slot < 36; slot++){
            ItemStack item = items[slot];
            if(item != null){
                player.getInventory().setItem(slot, item);
            }
        }

        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
    }

    public static List<UUID> getModerateurs() {
        return moderateurs;
    }

    public static Map<UUID, PlayerManager> getPlayers() {
        return players;
    }
}