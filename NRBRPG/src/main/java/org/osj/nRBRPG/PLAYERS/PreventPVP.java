package org.osj.nRBRPG.PLAYERS;

import com.destroystokyo.paper.event.entity.CreeperIgniteEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;
import org.osj.nRBRPG.RPG.MonsterSpawnManager;

public class PreventPVP implements Listener
{
    @EventHandler
    public void onPlayerAttackPlayer(PrePlayerAttackEntityEvent event)
    {
        if(event.getAttacked().getType().equals(EntityType.PLAYER))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMonsterDeath(EntityDamageByEntityEvent event)
    {
        if(event.getDamager().getType().equals(EntityType.BEE))
        {
            Bee bee = (Bee) event.getDamager();
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    bee.setHasStung(false);
                }
            }.runTaskLater(NRBRPG.getServerInstance(), 20L * 2L);
        }
    }
}
