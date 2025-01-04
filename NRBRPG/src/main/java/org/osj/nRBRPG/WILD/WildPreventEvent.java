package org.osj.nRBRPG.WILD;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.osj.nRBRPG.MANAGER.WorldManager;

import java.util.ArrayList;
import java.util.List;

public class WildPreventEvent implements Listener
{
    public static List<Material> preventList = new ArrayList<>();
    public static List<Material> expPreventList = new ArrayList<>();
    public WildPreventEvent()
    {
        preventList.add(Material.LAVA_BUCKET);
        preventList.add(Material.TNT);
        preventList.add(Material.TNT_MINECART);

        expPreventList.add(Material.EMERALD_ORE);
        expPreventList.add(Material.LAPIS_ORE);
        expPreventList.add(Material.NETHER_QUARTZ_ORE);
    }

    @EventHandler
    public void onPreventInteraction(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(player.isOp())
        {
            return;
        }
        if(!WorldManager.wildWorlds.contains(player.getWorld().getName()))
        {
            return;
        }
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        if(-60 <= x && x <= 80 && 102 <= y && y <= 141 && -80 <= z && z <= 40)
        {
            event.setCancelled(true);
            return;
        }
        if(1108 <= x && x <= 1166 && 70 <= y && y <= 122 && 1512 <= z && z <= 1570)
        {
            event.setCancelled(true);
            return;
        }
        Material mainHand = player.getInventory().getItemInMainHand().getType();
        Material offhand = player.getInventory().getItemInOffHand().getType();
        if(preventList.contains(mainHand) || preventList.contains(offhand))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPreventExp(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        if(expPreventList.contains(block.getType()))
        {
            event.setExpToDrop(1);
        }
    }

    @EventHandler
    public void onWitherSkeletonDeath(EntityDeathEvent event)
    {
        if(!WorldManager.wildWorlds.contains(event.getEntity().getWorld().getName()))
        {
            return;
        }
        if(event.getEntityType().equals(EntityType.WITHER_SKELETON))
        {
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onPreventVillageBookInteraction(PlayerInteractEntityEvent event)
    {
        if(!WorldManager.wildWorlds.contains(event.getPlayer().getWorld().getName()))
        {
            return;
        }
        if(event.getRightClicked().getType().equals(EntityType.VILLAGER))
        {
            Villager villager = (Villager) event.getRightClicked();

            if(villager.getProfession().equals(Villager.Profession.LIBRARIAN))
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteractEnchant(PlayerInteractEvent event)
    {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            Block clicked = event.getClickedBlock();
            if(clicked.getType().equals(Material.ENCHANTING_TABLE))
            {
                event.setCancelled(true);
            }
            else if(clicked.getType().equals(Material.GRINDSTONE))
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFire(BlockIgniteEvent event)
    {
        if(event.getCause().equals(BlockIgniteEvent.IgniteCause.SPREAD))
        {
            event.setCancelled(true);
        }
    }
}
