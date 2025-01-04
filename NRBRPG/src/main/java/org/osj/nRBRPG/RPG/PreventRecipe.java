package org.osj.nRBRPG.RPG;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Recipe;

public class PreventRecipe implements Listener
{
    @EventHandler
    public void onCraftNetheriteTemplate(CraftItemEvent event)
    {
        if(event.getRecipe().getResult().getType().equals(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
        {
            event.setCancelled(true);
        }
    }
}
