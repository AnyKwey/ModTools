package com.modtools.ak.commands;

import com.modtools.ak.Bungee;
import com.modtools.ak.manager.PlayerInfos;
import com.modtools.ak.manager.moderation.BanManager;
import com.modtools.ak.utils.TimeUnit;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

/**
 * Created by AnyKwey
 */
public class BanCommand extends Command {

    public BanCommand() {
        super("ban", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.PlayerOnly").replace("&", "§"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!(player.hasPermission("modtools.command.ban"))) {
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.HasNotPermission").replace("&", "§"));
            return;
        }

        if (args.length < 3) {
            helpMessage(sender);
            return;
        }

        String targetName = args[0];
        if (!(PlayerInfos.exist(targetName))) {
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.PlayerNeverConnected").replace("&", "§"));
            return;
        }

        UUID targetUUID = PlayerInfos.getUUID(targetName);
        if (BanManager.isBanned(targetUUID)) {
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.ban.PlayerAlreadyBanned").replace("&", "§"));
            return;
        }

        String reason = "";
        for (int i = 2; i < args.length; i++) {
            reason = reason + args[i] + " ";
        }

        if(args[1].equalsIgnoreCase("perm")){
            if(!(player.hasPermission("modtools.command.ban.perm"))){
                player.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.HasNotPermission").replace("&", "§"));
            }
            BanManager.ban(targetUUID, -1, reason, player.getDisplayName());
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.ban.ban-perm").replace("%name%", targetName).replace("%reason%", reason).replace("&", "§"));
            return;
        }

        if(!args[1].contains(":")){
            helpMessage(sender);
            return;
        }

        int duration = 0;
        try {
            duration = Integer.parseInt(args[1].split(":")[0]);
        } catch(NumberFormatException e){
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("TimeUnit.messages.durationIsNumber").replace("&", "§"));
            return;
        }

        if(!TimeUnit.existFromShortcut(args[1].split(":")[1])){
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("TimeUnit.messages.unityNotExist").replace("&", "§"));
            for(TimeUnit units : TimeUnit.values()){
                player.sendMessage("§b" + units.getName() + " §f: §e" + units.getShortcut());
            }
            return;
        }

        TimeUnit unit = TimeUnit.getFromShortcut(args[1].split(":")[1]);
        long banTime = unit.getToSecond() * duration;

        BanManager.ban(targetUUID, banTime, reason, player.getDisplayName());
        player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.ban.ban").replace("%name%", targetName).replace("%duration%", "" + duration).replace("%unit%", unit.getName()).replace("%reason%", reason).replace("\n", "\n").replace("&", "§"));

        if(ProxyServer.getInstance().getPlayer(targetUUID).isConnected()) {
            ProxyServer.getInstance().getPlayer(targetUUID).disconnect(BanManager.banMessage(targetUUID));
        }
    }

    public void helpMessage(CommandSender sender) {
        sender.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.ban.syntax").replace("&", "§"));
    }
}
