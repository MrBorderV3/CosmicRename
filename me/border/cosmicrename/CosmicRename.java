package me.border.cosmicrename;

import me.border.cosmicrename.command.Renamer;
import me.border.cosmicrename.listener.RenamerListener;
import me.border.cosmicrename.util.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicRename extends JavaPlugin {

    @Override
    public void onEnable(){
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        new Utils(this);
        new Renamer(this);
        getServer().getPluginManager().registerEvents(new RenamerListener(this), this);
    }
}
