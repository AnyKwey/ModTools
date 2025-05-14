package com.modtools.ak.commands;

import com.modtools.ak.Main;
import com.modtools.ak.manager.moderation.ModManager;
import com.modtools.ak.manager.moderation.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.PlayerOnly"));
            return false;
        }

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("mod")){
            if(!(player.hasPermission("modtools.command.mod"))){
                player.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.HasNotPermission").replace("&", "§"));
                return false;
            }

            if(args.length != 0){
                player.sendMessage(Main.getInstance().getConfig().getString("ErrorMessages.FalseArgs").replace("%args%", args[0]).replace("&", "§"));
                return false;
            }

            if(args.length == 0){
                if(Main.getInstance().getConfig().getBoolean("ri2")){
                    if(ModManager.isInMod(player.getUniqueId())){
                        ModManager.remove(player.getUniqueId());
                        PlayerManager.getFromPlayer(player).destroy();
                    } else {
                        ModManager.add(player.getUniqueId());
                        new PlayerManager(player).init();
                    }
                } else {
                    if(ModManager.isInMod(player.getUniqueId())){
                        ModManager.remove(player.getUniqueId());
                        player.sendMessage(Main.getInstance().getConfig().getString("ModerationTools.Destroy").replace("&", "§").replace("\n", "\n"));
                    } else {
                        ModManager.add(player.getUniqueId());
                        player.sendMessage(Main.getInstance().getConfig().getString("ModerationTools.Init").replace("&", "§").replace("\n", "\n"));
                    }
                }
            }
        }
        return false;
    }
}
