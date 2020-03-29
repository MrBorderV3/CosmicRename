package me.border.cosmicrename.util;

import me.border.cosmicrename.CosmicRename;
import org.bukkit.ChatColor;

import java.util.List;

public class Utils{
    static CosmicRename plugin;
    @SuppressWarnings("static-access")
    public Utils(CosmicRename plugin){
        this.plugin = plugin;
    }

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String cs(String s) {
        return plugin.getConfig().getString(s);
    }

    public static String ucs(String s) {
        return Utils.chat(plugin.getConfig().getString(s));
    }

    public static List<String> csl(String s) {
        return plugin.getConfig().getStringList(s);
    }

    public static Integer ci(String s) {
        return plugin.getConfig().getInt(s);
    }

    public static Boolean cb(String s) {
        return plugin.getConfig().getBoolean(s);
    }


}

