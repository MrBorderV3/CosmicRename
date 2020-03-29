package me.border.cosmicrename.listener;

import me.border.cosmicrename.CosmicRename;
import me.border.cosmicrename.command.Renamer;
import me.border.cosmicrename.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RenamerListener implements Listener {

    private CosmicRename plugin;

    public RenamerListener(CosmicRename plugin){
        this.plugin = plugin;
    }

    HashMap<Player, Integer> plr = new HashMap<>();
/*
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&5&lCosmic Rename"))){
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getItemMeta().getDisplayName().equals(Renamer.renamerMeta())){
                return;
            }
            plr.put(p, e.getSlot());
            p.closeInventory();
            p.sendMessage(Utils.ucs("Rename.pickName"));
        }
    }
*/
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (plr.containsKey(p) || p.hasMetadata("message")){
            if (e.isShiftClick()){
                e.setCancelled(true);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (plr.containsKey(p) || p.hasMetadata("message")) {
            e.setCancelled(true);
        }
    }

    HashMap<Player, String> messages = new HashMap<>();
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("cancel")){
            e.setCancelled(true);
            p.sendMessage(Utils.ucs("Rename.cancelled"));
            plr.remove(p);
            p.removeMetadata("message", plugin);
            return;
        }
        if (plr.containsKey(p) && !p.hasMetadata("message")) {
            e.setCancelled(true);
            if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR || p.getItemInHand().getItemMeta().getDisplayName().equals(Renamer.renamerMeta())) {
                return;
            }
            List<String> whitelist = Utils.csl("Whitelist");
            if (!whitelist.contains(p.getItemInHand().getType().toString())){
                plr.remove(p);
                p.sendMessage(Utils.ucs("Rename.notWhitelisted"));
                return;
            }
            plr.remove(p);
            plr.put(p, p.getInventory().getHeldItemSlot());
            messages.put(p, e.getMessage());
            p.setMetadata("message", new FixedMetadataValue(plugin, "message"));
            p.sendMessage(Utils.ucs("Rename.pleaseConfirm").replaceAll("%name%", Utils.chat(e.getMessage())));
            return;
        }
        if (p.hasMetadata("message")){
            if (e.getMessage().equalsIgnoreCase("cancel")){
                e.setCancelled(true);
                p.sendMessage(Utils.ucs("Rename.cancelled"));
                plr.remove(p);
                p.removeMetadata("message", plugin);
                return;
            }
            if (e.getMessage().equalsIgnoreCase("confirm")){
                e.setCancelled(true);
                p.sendMessage(Utils.ucs("Rename.confirmed"));
                p.removeMetadata("message", plugin);
                int itemSlot = plr.get(p);
                plr.remove(p);
                ItemStack item = p.getInventory().getItem(itemSlot);
                ItemMeta itemm = item.getItemMeta();
                itemm.setDisplayName(Utils.chat(messages.get(p)) );
                item.setItemMeta(itemm);
                p.getInventory().setItem(itemSlot, item);
                p.getInventory().removeItem(Renamer.renamer());
            } else {
                p.sendMessage(Utils.ucs("Rename.mustBeConfirmOrCancel"));
            }
        }
    }


    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.NAME_TAG && p.getItemInHand().getItemMeta().getDisplayName().equals(Renamer.renamerMeta()) && p.getItemInHand().getItemMeta().hasLore()) {
                p.sendMessage(Utils.ucs("Rename.startingProcess"));
                p.sendMessage(Utils.ucs("Rename.pickName"));
                plr.remove(p);
                plr.put(p, 0);  
                return;
            }
        }
    }


    public Inventory inventory = Bukkit.createInventory(null, 9*5, Utils.chat("&5&lCosmic Rename"));

    public void openInventory(Player p){
        ItemStack[] inv = p.getInventory().getContents();
        inventory.setContents(inv);

        p.openInventory(inventory);
    }

}
