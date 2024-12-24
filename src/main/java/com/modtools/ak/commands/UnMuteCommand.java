package com.modtools.ak.commands;


import com.modtools.ak.Bungee;
import com.modtools.ak.manager.PlayerInfos;
import com.modtools.ak.manager.moderation.MuteManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

/**
 * Created by AnyKwey
 */
public class UnMuteCommand extends Command {

    public UnMuteCommand() {
        super("unmute", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.PlayerOnly").replace("&", "§"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!(player.hasPermission("modtools.command.unmute"))) {
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.HasNotPermission").replace("&", "§"));
            return;
        }

        if (args.length != 1) {
            helpMessage(player);
            return;
        }

        String targetName = args[0];
        if (!(PlayerInfos.exist(targetName))) {
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.PlayerNeverConnected").replace("&", "§"));
            return;
        }

        UUID targetUUID = PlayerInfos.getUUID(targetName);
        if (!(MuteManager.isMute(targetUUID))) {
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.unmute.PlayerIsNotMuted").replace("&", "§"));
            return;
        }

        MuteManager.unmute(targetUUID);
        player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.unmute.unmute").replace("&", "§").replace("%name%", targetName));
    }

    public void helpMessage(CommandSender sender) {
        sender.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.unmute.syntax").replace("&", "§"));
    }
}
