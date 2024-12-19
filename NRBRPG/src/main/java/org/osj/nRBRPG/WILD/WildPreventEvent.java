package org.osj.nRBRPG.WILD;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.osj.nRBRPG.MANAGER.WorldManager;

import java.util.ArrayList;
import java.util.List;

public class WildPreventEvent implements Listener
{
    public static List<Material> preventList = new ArrayList<>();
    public WildPreventEvent()
    {
        preventList.add(Material.LAVA_BUCKET);
        preventList.add(Material.TNT);
        preventList.add(Material.TNT_MINECART);
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
        Material mainHand = player.getInventory().getItemInMainHand().getType();
        Material offhand = player.getInventory().getItemInOffHand().getType();
        if(preventList.contains(mainHand) || preventList.contains(offhand))
        {
            event.setCancelled(true);
        }
    }
}
