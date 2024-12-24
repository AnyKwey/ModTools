package com.modtools.ak;

import com.modtools.ak.data.mysql.MySQL;
import com.modtools.ak.events.EventsRegister;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Bungee extends Plugin {

    private static Bungee instance;

    @Override
    public void onEnable() {
        instance = this;
        createFile("mysql");
        createFile("config");
        MySQL.connect(getConfig("mysql").getString("mysql.host"), getConfig("mysql").getInt("mysql.port"), getConfig("mysql").getString("mysql.database"), getConfig("mysql").getString("mysql.user"), getConfig("mysql").getString("mysql.password"));
        MySQL.createTables();
        new EventsRegister(this).registerCommands();
        new EventsRegister(this).registerListeners();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Bungee getInstance() {
        return instance;
    }

    private void createFile(String fileName){
        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();

                if(fileName.equals("mysql")){
                    Configuration config = getConfig(fileName);
                    config.set("mysql.host", "localhost");
                    config.set("mysql.port", 3306);
                    config.set("mysql.database", "database");
                    config.set("mysql.user", "root");
                    config.set("mysql.password", "");

                    saveConfig(config, fileName);
                }

                if(fileName.equals("config")){
                    Configuration config = getConfig(fileName);
                    config.set("Messages.start-message", "&aThe ModTools Plugin part bungeecord has been enabled.");
                    config.set("Messages.close-message", "&aThe ModTools Plugin part bungeecord has been disabled.");
                    config.set("Messages.PlayerNeverConnected", "&cError: This player has never connected.");
                    config.set("Messages.PlayerNotConnected", "&cError: This player is not connected.");

                    config.set("Messages.command.staff", "&c[Staff] &fStaff online: %list%");

                    config.set("Messages.command.kick.syntax", "&cSyntax: /kick <username> (reason)");
                    config.set("Messages.command.kick.kick", "&cYou have kicked %name%.");
                    config.set("Messages.command.kick.kick-reason", "&cYou have kicked %name% for %reason%.");
                    config.set("Messages.command.kick.kick-message", "&cYou had been kicked by %author%");
                    config.set("Messages.command.kick.kick-reason-message", "&cYou had been kicked for %reason% by %author%");

                    config.set("Messages.command.ban.syntax", "&cSyntax: /ban <username> <duration>:<unity>/perm <reason>");
                    config.set("Messages.command.ban.PlayerAlreadyBanned", "&cError: This player has been already banned.");
                    config.set("Messages.command.ban.ban-perm", "&aYou have banned %name% for a duration of &cpermanent &afor &c%reason%");
                    config.set("Messages.command.ban.ban", "&aYou have banned %name% for a duration of &c%duration% %unit% &afor &c%reason%");
                    config.set("Messages.command.ban.kick", "&cYou're banned for %reason% \nduring %duration% \nby %author% \n\n&8%ServerName% - %when% - %uuid%");

                    config.set("Messages.command.mute.syntax", "&cSyntax: /mute <username> <duration>:<unity>/perm <reason>");
                    config.set("Messages.command.mute.PlayerAlreadyMuted", "&cError: This player has been already muted.");
                    config.set("Messages.command.mute.mute", "&aYou have muted %name% for a duration of &c%duration% %unit% &afor &c%reason%");
                    config.set("Messages.command.mute.target.mute", "&cYou had been muted \n&cfor &c%reason%");
                    config.set("Messages.command.mute.target.bypass.message", "&cYou are muted.");
                    config.set("Messages.command.mute.target.bypass.hover", "&cReason: &f%reason% \n&cBy: &f%author% \n&cAt: &f%when% \n&cDuration: &f%duration%");

                    config.set("Messages.command.unban.syntax", "&cSyntax: /unban <username>");
                    config.set("Messages.command.unban.PlayerIsNotBanned", "&cSyntax: This player is not banned.");
                    config.set("Messages.command.unban.unban", "&8[&a&l✔&8] &aYou have unbanned %name%.");

                    config.set("Messages.command.unmute.syntax", "&cSyntax: /unmute <username>");
                    config.set("Messages.command.unmute.PlayerIsNotMuted", "&cSyntax: This player is not muted.");
                    config.set("Messages.command.unmute.unmute", "&8[&a&l✔&8] &aYou have unmuted %name%.");

                    config.set("ErrorMessages.PlayerOnly", "&cYou cannot execute this command here.");
                    config.set("ErrorMessages.FalseArgs", "&cError: The argument ''&f%args% &c''is not recognized!");
                    config.set("ErrorMessages.HasNotPermission", "&cYou have not the permission to execute this command.");

                    config.set("TimeUnit.Second", "Second(s)");
                    config.set("TimeUnit.Minute", "Minute(s)");
                    config.set("TimeUnit.Hour", "Hour(s)");
                    config.set("TimeUnit.Day", "Day(s)");
                    config.set("TimeUnit.Mouth", "Mouth");
                    config.set("TimeUnit.messages.durationIsNumber", "&cError: The value 'duration' must to be a number !");
                    config.set("TimeUnit.messages.unityNotExist", "&cError: This unity of time doesn't exist !");

                    saveConfig(config, fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Configuration getConfig(String fileName){
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), fileName + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getConfig(fileName);
    }

    public void saveConfig(Configuration config, String fileName){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), fileName + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
