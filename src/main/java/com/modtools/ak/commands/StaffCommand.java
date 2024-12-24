package com.modtools.ak.commands;

import com.modtools.ak.Bungee;
import com.modtools.ak.manager.moderation.StaffManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by AnyKwey
 */
public class StaffCommand extends Command {

    public StaffCommand() {
        super("staff", null);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.PlayerOnly").replace("&", "§"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!(player.hasPermission("modtools.command.staff"))) {
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("ErrorMessages.HasNotPermission").replace("&", "§"));
            return;
        }

        if(args.length != 0){
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.FalseArgs").replace("%args%", args[0]).replace("&", "§"));
            return;
        }

        if(args.length == 0){
            player.sendMessage(Bungee.getInstance().getConfig("config").getString("Messages.command.staff").replace("%list%", StaffManager.getStaffs().toString()).replace("&", "§"));
        }
    }
}
