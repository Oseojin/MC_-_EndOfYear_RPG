package org.osj.nRBRPG.LOBBY;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.osj.nRBRPG.CHUNK_OWNERSHIP.ChunkManager;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;

public class LobbyPreventEvent implements Listener
{
    @EventHandler
    public void onPreventInteraction(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(player.isOp())
        {
            return;
        }
        if(!player.getWorld().getName().equals(WorldManager.lobbyWorld))
        {
            return;
        }
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_AIR))
        {
            return;
        }
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock != null && NRBRPG.getChunkManager().canInteractChunk(player, clickedBlock.getChunk()))
        {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void onPreventVillageInteraction(PlayerInteractEntityEvent event)
    {
        if(!event.getPlayer().getWorld().getName().equals(WorldManager.lobbyWorld))
        {
            return;
        }
        if(event.getRightClicked().getType().equals(EntityType.VILLAGER))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event)
    {
        if(event.getBlock().getWorld().getName().equals(WorldManager.lobbyWorld))
        {
            event.setCancelled(true);
        }
    }
}
