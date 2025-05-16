package com.modtools.ak.commands;

import com.modtools.ak.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by AnyKwey
 */
public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.PlayerOnly"));
            return false;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("gamemode")) {
            if (!(player.hasPermission("modtools.command.gamemode"))) {
                player.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.HasNotPermission").replace("&", "§"));
                return false;
            }

            if (args.length != 1) {
                player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.syntax").replace("&", "§"));
                return false;
            }


            if (args[0].equalsIgnoreCase("0")) {
                if (!(player.hasPermission("modtools.command.gamemode_zero"))) {
                    player.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.HasNotPermission").replace("&", "§"));
                    return false;
                }

                if (player.getGameMode() == GameMode.SURVIVAL) {
                    player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.AlreadyInTheGamemode-message").replace("&", "§"));
                    return false;
                }

                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.GamemodeUpdated-message").replace("&", "§"));
            } else if (args[0].equalsIgnoreCase("1")) {
                if (!(player.hasPermission("modtools.command.gamemode_one"))) {
                    player.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.HasNotPermission").replace("&", "§"));
                    return false;
                }

                if (player.getGameMode() == GameMode.CREATIVE) {
                    player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.AlreadyInTheGamemode-message").replace("&", "§"));
                    return false;
                }

                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.GamemodeUpdated-message").replace("&", "§"));
            } else if (args[0].equalsIgnoreCase("2")) {
                if (!(player.hasPermission("modtools.command.gamemode_two"))) {
                    player.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.HasNotPermission").replace("&", "§"));
                    return false;
                }

                if (player.getGameMode() == GameMode.ADVENTURE) {
                    player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.AlreadyInTheGamemode-message").replace("&", "§"));
                    return false;
                }

                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.GamemodeUpdated-message").replace("&", "§"));
            } else if (args[0].equalsIgnoreCase("3")) {
                if (!(player.hasPermission("modtools.command.gamemode_three"))) {
                    player.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.HasNotPermission").replace("&", "§"));
                    return false;
                }

                if (player.getGameMode() == GameMode.SPECTATOR) {
                    player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.AlreadyInTheGamemode-message").replace("&", "§"));
                    return false;
                }

                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Main.getInstance().getConfig().getString("Gamemode.GamemodeUpdated-message").replace("&", "§"));
            } else {
                player.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.FalseArgs").replace("%args%", args[0]).replace("&", "§"));
            }
        }
        return false;
    }
}
