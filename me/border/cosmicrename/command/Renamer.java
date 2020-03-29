package me.border.cosmicrename.command;

import me.border.cosmicrename.CosmicRename;
import me.border.cosmicrename.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Renamer implements CommandExecutor {

    static CosmicRename plugin;

    public Renamer(CosmicRename plugin){
        this.plugin = plugin;

        plugin.getCommand("renamer").setExecutor(this);
    }

    public boolean onCommand(final CommandSender sender,final Command cmd,final String label,final String[] args) {

        if (sender.hasPermission("cosmicrename.renamer")) {
            if (args.length == 3){
                if (args[0].equalsIgnoreCase("give")){
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target == null){
                        sender.sendMessage(Utils.ucs("targetOffline").replaceAll("%target%", args[1]));
                        return true;
                    }
                    ItemStack renamer = renamer();
                    if (target.getInventory().firstEmpty() == -1){
                        for (int i = 0; i < target.getInventory().getSize(); i++) {
                            try {
                                if (target.getInventory().getItem(i).getItemMeta() != null) {
                                    if (target.getInventory().getItem(i).getItemMeta().getDisplayName().equals(renamerMeta())) {
                                        if (target.getInventory().getItem(i).getAmount() == 64) {
                                            break;
                                        }
                                        int amount;
                                        try {
                                            amount = Integer.parseInt(args[2]);
                                        } catch (NumberFormatException e){
                                            sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
                                            return true;
                                        }
                                        for (int ix = 0; ix < amount; ix++) {
                                            target.getInventory().addItem(renamer);
                                        }
                                        break;
                                    }
                                }
                            } catch (NullPointerException ignored){

                            }
                        }
                        Location loc = target.getLocation();
                        int amount;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e){
                            sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
                            return true;
                        }
                        for (int i = 0; i < amount; i++) {
                            loc.getWorld().dropItem(loc, renamer);
                        }
                    } else {
                        int amount;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e){
                            sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
                            return true;
                        }
                        for (int i = 0; i < amount; i++) {
                            target.getInventory().addItem(renamer);
                        }
                    }
                    target.sendMessage(Utils.ucs("Renamer.received"));
                    sender.sendMessage(Utils.ucs("Renamer.gave").replaceAll("%target%", args[1]));
                    return true;
                } else {
                    sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
                }
            }

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("giveall")) {
                    ItemStack renamer = renamer();
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.getInventory().firstEmpty() == -1) {
                            for (int i = 0; i < all.getInventory().getSize(); i++) {
                                try {
                                    if (all.getInventory().getItem(i).getItemMeta() != null) {
                                        if (all.getInventory().getItem(i).getItemMeta().getDisplayName().equals(renamerMeta())) {
                                            if (all.getInventory().getItem(i).getAmount() == 64) {
                                                break;
                                            }
                                            int amount;
                                            try {
                                                amount = Integer.parseInt(args[1]);
                                            } catch (NumberFormatException e) {
                                                sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
                                                return true;
                                            }
                                            for (int ix = 0; ix < amount; ix++) {
                                                all.getInventory().addItem(renamer);
                                            }
                                            break;
                                        }
                                    }
                                } catch (NullPointerException ignored) {

                                }
                            }
                            Location loc = all.getLocation();
                            int amount;
                            try {
                                amount = Integer.parseInt(args[1]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
                                return true;
                            }
                            for (int i = 0; i < amount; i++) {
                                loc.getWorld().dropItem(loc, renamer);
                            }
                        } else {
                            int amount;
                            try {
                                amount = Integer.parseInt(args[1]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
                                return true;
                            }
                            for (int i = 0; i < amount; i++) {
                                all.getInventory().addItem(renamer);
                            }
                        }
                        all.sendMessage(Utils.ucs("Renamer.received"));
                    }
                    sender.sendMessage(Utils.ucs("Renamer.gave").replaceAll("%target%", "ALL"));
                    return true;
                }

                if (args[0].equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.ucs("targetOffline").replaceAll("%target%", args[1]));
                        return true;
                    }
                    ItemStack renamer = renamer();
                    if (target.getInventory().firstEmpty() == -1) {
                        for (int i = 0; i < target.getInventory().getSize(); i++) {
                            try {
                                if (target.getInventory().getItem(i).getItemMeta() != null) {
                                    if (target.getInventory().getItem(i).getItemMeta().getDisplayName().equals(renamerMeta())) {
                                        if (target.getInventory().getItem(i).getAmount() == 64) {
                                            break;
                                        }
                                        target.getInventory().addItem(renamer);
                                        break;
                                    }
                                }
                            } catch (NullPointerException ignored) {

                            }
                        }
                        Location loc = target.getLocation();
                        loc.getWorld().dropItem(loc, renamer);
                    } else {
                        target.getInventory().addItem(renamer);
                    }
                    target.sendMessage(Utils.ucs("Renamer.received"));
                    sender.sendMessage(Utils.ucs("Renamer.gave").replaceAll("%target%", args[1]));
                    return true;
                } else {
                    sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
                }
            }

            if (args.length == 1){
                if (args[0].equalsIgnoreCase("giveall")){
                    ItemStack renamer = renamer();
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.getInventory().firstEmpty() == -1) {
                            for (int i = 0; i < all.getInventory().getSize(); i++) {
                                try {
                                    if (all.getInventory().getItem(i).getItemMeta() != null) {
                                        if (all.getInventory().getItem(i).getItemMeta().getDisplayName().equals(renamerMeta())) {
                                            if (all.getInventory().getItem(i).getAmount() == 64) {
                                                break;
                                            }
                                            all.getInventory().addItem(renamer);
                                            break;
                                        }
                                    }
                                } catch (NullPointerException ignored) {

                                }
                            }
                            Location loc = all.getLocation();
                            loc.getWorld().dropItem(loc, renamer);
                        } else {
                            all.getInventory().addItem(renamer);
                        }
                        all.sendMessage(Utils.ucs("Renamer.received"));
                    }
                    sender.sendMessage(Utils.ucs("Renamer.gave").replaceAll("%target%", "ALL"));
                    return true;
                }

            }else {
                sender.sendMessage(Utils.ucs("Renamer.incorrect-usage"));
            }
        }else {
            sender.sendMessage(Utils.ucs("noPermission"));
        }

        return false;
    }

    public static ItemStack renamer(){
        ItemStack renamer = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta renamerm = renamer.getItemMeta();
        renamerm.setDisplayName(Utils.chat(plugin.getConfig().getString("Renamer.Item.displayName", "&b&l*&5&lCosmicRenamer&b&l*" )));
        List<String> var1 = Utils.csl("Renamer.Item.lore");
        ArrayList<String> lore = new ArrayList<>();
        for (String output : var1) {
            lore.add(Utils.chat(output));
        }
        renamerm.setLore(lore);
        renamer.setItemMeta(renamerm);

        return renamer;
    }

    public static String renamerMeta(){
        return Utils.chat(plugin.getConfig().getString("Renamer.Item.displayName", "&b&l*&5&lCosmicRenamer&b&l*"));
    }
}
