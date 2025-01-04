package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomFurniture;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobInteractEvent;
import io.lumine.mythic.bukkit.events.MythicMobSpawnEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import it.unimi.dsi.fastutil.Hash;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;
import org.osj.nRBRPG.PLAYERS.PlayerDeath;
import org.osj.nRBRPG.PLAYERS.PlayerJoin;
import org.osj.nRBRPG.PLAYERS.PlayerScaleCommand;

import java.util.*;

public class DungeonManager implements Listener
{
    public static HashMap<UUID, Dungeon> gateDungeonMap = new HashMap<>();
    public boolean generate = false;
    private int gateNum = 0;


    @EventHandler
    public void onInteractGate(MythicMobInteractEvent event)
    {
        Player player = event.getPlayer();
        ActiveMob customGate = event.getActiveMob();
        if(PlayerDeath.deathPlayerReviveMap.containsKey(player.getUniqueId()))
        {
            event.setCancelled();
            return;
        }
        if(customGate.getType().getDisplayName().get().contains("게이트 lv."))
        {
            if(player.getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.BEDROCK)))
            {
                customGate.despawn();
                int currGateNum = NRBRPG.getConfigManager().getConfig("gatenum").getInt("gatenum");
                NRBRPG.getConfigManager().getConfig("gatenum").set("gatenum", currGateNum - 1);
                NRBRPG.getConfigManager().saveConfig("gatenum");
                return;
            }
            if(PlayerScaleCommand.playerList.contains(player))
            {
                PlayerScaleCommand.playerList.remove(player);
                player.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.0);
            }
            if(!gateDungeonMap.containsKey(customGate.getEntity().getBukkitEntity().getUniqueId()))
            {
                int lv = ((int)customGate.getLevel());
                Dungeon newdungeon = DungeonGenerator.NewDungeon(gateDungeonMap.size(), customGate, lv);
                gateDungeonMap.put(customGate.getEntity().getBukkitEntity().getUniqueId(), newdungeon);
                gateDungeonMap.get(customGate.getEntity().getBukkitEntity().getUniqueId()).OpenRoom();
            }
            gateDungeonMap.get(customGate.getEntity().getBukkitEntity().getUniqueId()).EnterDungeon(player);
            //playerClickList.remove(player);
        }
        else if(customGate.getType().getDisplayName().get().contains("보스 게이트"))
        {
            if(player.getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.BEDROCK)))
            {
                customGate.despawn();
                return;
            }
            BossDungeon bossDungeon = NRBRPG.getBossDungeon();
            if(!bossDungeon.getActive()) // 최초 입장
            {
                // 보스 스폰
                bossDungeon.SpawnBoss();
            }
            Bukkit.getConsoleSender().sendMessage(bossDungeon.getTryChance() + "");
            if(bossDungeon.getTryChance() <= 0)
            {
                player.sendMessage(Component.text("입장 횟수를 모두 소진했습니다.").color(TextColor.color(255, 0 ,0)));
                return;
            }
            bossDungeon.EnterDungeon(player);
        }
    }

    @EventHandler
    public void onSpawnBossGate(MythicMobSpawnEvent event)
    {
        if(event.getMob().getType().getDisplayName().get().contains("보스 게이트"))
        {
            Bukkit.getConsoleSender().sendMessage("보스 게이트");
            NRBRPG.getBossDungeon().init(event.getMob());
        }
    }

    public void startGateGenerate()
    {
        BukkitScheduler gateScheduler = Bukkit.getScheduler();
        gateScheduler.runTaskTimer(NRBRPG.getServerInstance(), () ->
        {
            if(generate)
            {
                GateGenerator.generateGate();
            }
        }, 20L, 20L * 60L * 5L);
    }
}
