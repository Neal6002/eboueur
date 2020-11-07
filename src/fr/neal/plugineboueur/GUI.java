package fr.neal.plugineboueur;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GUI implements Listener {

    private final CooldownManager cooldownManager = new CooldownManager();

    private final Plugin plugin;

    public GUI (Plugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Inventory inventory = Bukkit.createInventory(null, 9, "§3                Poubelle");
        ItemStack stone = new ItemStack(Material.STONE);
        ItemMeta stonemeta = stone.getItemMeta();
        stonemeta.setDisplayName(ChatColor.AQUA + "Ramasser les ordures.");
        ItemStack plume = new ItemStack(Material.FEATHER);
        ItemMeta plumemeta = stone.getItemMeta();
        plumemeta.setDisplayName(ChatColor.DARK_BLUE + "Jeter vos items. ");
        plume.setItemMeta(plumemeta);
        stone.setItemMeta(stonemeta);

        inventory.setItem(1, stone);
        inventory.setItem(7, plume);


        int timeleft = cooldownManager.getCooldown(player.getUniqueId());
        if (player.hasPermission("op")) {
            player.openInventory(inventory);
            return;
        }

        if (timeleft == 0) {

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                if (block.getType() == Material.STONE) {

                    event.setCancelled(true);

                    player.openInventory(inventory);

                    cooldownManager.setCooldowns(player.getUniqueId(), CooldownManager.DEFAULTCOOLDOWN);

                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            int timeleft = cooldownManager.getCooldown(player.getUniqueId());

                            cooldownManager.setCooldowns(player.getUniqueId(), --timeleft);

                            if (timeleft == 0) {
                                this.cancel();
                            }

                        }
                    }.runTaskTimer(this.plugin, 20, 20);
                }
            }
        } else if (block.getType() == Material.STONE) {
            player.sendMessage("§c Veuilez attendre " + timeleft + "§c s avant de réouvrire une poubelle ");
        }

    }



    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

       if (current == null) return;


        if(inv.getName().equalsIgnoreCase("§3                Poubelle"))
        {
            if(current.getType() == Material.STONE)
            {   ItemStack ordure1 = new ItemStack(Material.STONE);
                ItemStack ordure2 = new ItemStack(Material.VINE);
                ItemStack ordure3 = new ItemStack(Material.THIN_GLASS);
                ItemStack ordure4 = new ItemStack(Material.PAINTING);
                ItemStack ordure5 = new ItemStack(Material.SIGN);

                event.setCancelled(true);
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Vous avez récupéré les ordures.");
                player.getInventory().addItem(ordure1);
                player.getInventory().addItem(ordure2);
                player.getInventory().addItem(ordure3);
                player.getInventory().addItem(ordure4);
                player.getInventory().addItem(ordure5);


            }
            Inventory déchets = Bukkit.createInventory(null, 27, "§3Videz vos items içi");

            if(current.getType() == Material.FEATHER)

            {
                player.openInventory(déchets);
            }
        }





    }
}