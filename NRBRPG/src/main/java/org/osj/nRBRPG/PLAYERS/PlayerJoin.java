package org.osj.nRBRPG.PLAYERS;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;
import org.osj.nRBRPG.MANAGER.WorldManager;

public class PlayerJoin implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore())
        {
            player.getInventory().addItem(CustomStack.getInstance("nrb.land_document").getItemStack());
            player.teleport(SpawnLocationManager.lobbySpawnLoc);
            return;
        }
        if(player.getWorld().getName().equals(WorldManager.lobbyWorld) || WorldManager.wildWorlds.contains(player.getWorld().getName()))
        {
            return;
        }
        player.teleport(SpawnLocationManager.lobbySpawnLoc);
    }
}
