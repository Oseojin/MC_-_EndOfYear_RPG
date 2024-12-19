package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomFurniture;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;
import org.osj.nRBRPG.PLAYERS.PlayerDeath;

import java.util.HashMap;
import java.util.Random;

public class DungeonManager implements Listener
{
    private static HashMap<Entity, Dungeon> gateDungeonMap = new HashMap<>();

    @EventHandler
    public void onInteractPortal(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if(!WorldManager.wildWorlds.contains(player.getWorld().getName()))
        {
            return;
        }
        if(!event.hasBlock())
        {
            return;
        }
        if(block == null)
        {
            return;
        }
        if(PlayerDeath.deathPlayerReviveMap.containsKey(player.getUniqueId()))
        {
            return;
        }
        CustomFurniture customGate = CustomFurniture.byAlreadySpawned(block);
        if(customGate == null)
        {
            return;
        }
        if(player.getInventory().getItemInMainHand().getType().equals(Material.BEDROCK))
        {
            return;
        }
        if(customGate.getPermission().equals("nrb.gate"))
        {
            if(!gateDungeonMap.containsKey(customGate.getEntity()))
            {
                int lv = Integer.parseInt(customGate.getNamespacedID().replace("nrb:gate_", ""));
                gateDungeonMap.put(customGate.getEntity(), DungeonGenerator.NewDungeon(gateDungeonMap.size(), block.getLocation(), lv));
                BukkitScheduler gateRemoveScheduler = Bukkit.getScheduler();
                gateRemoveScheduler.runTaskLater(NRBRPG.getServerInstance(), () ->
                {
                    customGate.getEntity().getLocation().getBlock().setType(Material.AIR);
                    customGate.getEntity().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
                    customGate.remove(false);
                }, 20L * 60L);
                gateDungeonMap.get(customGate.getEntity()).OpenRoom();
            }
            gateDungeonMap.get(customGate.getEntity()).EnterDungeon(player);
        }
        else if(customGate.getPermission().equals("nrb.boss_gate"))
        {
            BossDungeon bossDungeon = NRBRPG.getBossDungeon();
            if(!bossDungeon.getActive()) // 최초 입장
            {
                Random random = new Random();
                BossDungeon.TYPE type = BossDungeon.TYPE.values()[random.nextInt(0, BossDungeon.TYPE.values().length)];
                bossDungeon.init(type, 5, customGate);
                // 보스 스폰
                bossDungeon.SpawnBoss();
            }
            bossDungeon.EnterDungeon(player);
            if(bossDungeon.getTryChance() <= 0)
            {
                customGate.getEntity().getLocation().getBlock().setType(Material.AIR);
                customGate.getEntity().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
                customGate.getEntity().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
                customGate.remove(false);
            }
        }
    }

    public void startGateGenerate()
    {
        BukkitScheduler gateScheduler = Bukkit.getScheduler();
        gateScheduler.runTaskTimer(NRBRPG.getServerInstance(), GateGenerator::generateGate, 20L, 20L * 60L * 5);
    }
}
