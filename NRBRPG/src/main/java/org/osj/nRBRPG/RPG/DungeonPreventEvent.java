package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.osj.nRBRPG.MANAGER.WorldManager;

import java.util.ArrayList;
import java.util.List;

public class DungeonPreventEvent implements Listener
{
    @EventHandler
    public void onPreventInteraction(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(player.isOp())
        {
            return;
        }
        if(!player.getWorld().getName().equals(WorldManager.dungeonWorld))
        {
            return;
        }
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR))
        {
            return;
        }
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock != null)
        {
            CustomBlock customBlock = CustomBlock.byAlreadyPlaced(clickedBlock);
            if(customBlock != null)
            {
                if(customBlock.getPermission().equals("nrb.monster_active_block") || customBlock.getPermission().equals("nrb.boss_active_block") || customBlock.getPermission().equals("nrb.chest_block"))
                {
                    return;
                }
            }
        }
        event.setCancelled(true);
    }
}
