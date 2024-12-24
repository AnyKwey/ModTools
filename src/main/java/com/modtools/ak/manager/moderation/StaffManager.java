package com.modtools.ak.manager.moderation;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnyKwey
 */
public class StaffManager {

    private static List<String> staffs = new ArrayList<>();

    public static List<String> getStaffs() {
        return staffs;
    }

    public static void addToStaffList(ProxiedPlayer player){
        getStaffs().add(player.getName());
    }

    public static void removeToStaffList(ProxiedPlayer player){
        getStaffs().remove(player.getName());
    }

    public static boolean IsInStaffList(ProxiedPlayer player){
        return getStaffs().contains(player.getName());
    }
}
