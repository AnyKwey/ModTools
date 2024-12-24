package com.modtools.ak.commands;

import com.modtools.ak.Bungee;
import com.modtools.ak.manager.PlayerInfos;
import com.modtools.ak.manager.moderation.StaffManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

/**
 * Created by AnyKwey
 */
public class KickCommand extends Command {

    public KickCommand() {
        super("kick", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.PlayerOnly").replace("&", "§"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!(player.hasPermission("modtools.command.kick"))) {
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.HasNotPermission").replace("&", "§"));
            return;
        }

        if(args.length == 0){
            helpMessage(player);
            return;
        }

        if(args.length == 1){
            String targetName = args[0];
            UUID targetUUID = PlayerInfos.getUUID(targetName);

            if (!(PlayerInfos.exist(targetName))) {
                player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.PlayerNeverConnected").replace("&", "§"));
                return;
            }

            if(!(ProxyServer.getInstance().getPlayer(targetUUID).isConnected())){
                player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.PlayerNotConnected").replace("&", "§"));
                return;
            }

            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.kick.kick").replace("&", "§").replace("%name%", targetName));
            ProxyServer.getInstance().getPlayer(targetUUID).disconnect(Bungee.getInstance().getConfig("config").getString("Messages.command.kick.kick-message").replace("&", "§").replace("%author%", player.getDisplayName()));
        }

        if(args.length >= 2){
            String targetName = args[0];
            UUID targetUUID = PlayerInfos.getUUID(targetName);

            if (!(PlayerInfos.exist(targetName))) {
                player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.PlayerNeverConnected").replace("&", "§"));
                return;
            }

            if(!(ProxyServer.getInstance().getPlayer(targetUUID).isConnected())){
                player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.PlayerNotConnected").replace("&", "§"));
                return;
            }

            String reason = "";
            for (int i = 1; i < args.length; i++) {
                reason = reason + args[i] + "";
            }

            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.kick.kick-reason").replace("&", "§").replace("%name%", targetName).replace("%reason%", reason));
            ProxyServer.getInstance().getPlayer(targetUUID).disconnect(Bungee.getInstance().getConfig("config").getString("Messages.command.kick.kick-reason-message").replace("&", "§").replace("%author%", player.getDisplayName()).replace("%reason%", reason));
        }
    }

    public void helpMessage(CommandSender sender) {
        sender.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.kick.syntax").replace("&", "§"));
    }
}
