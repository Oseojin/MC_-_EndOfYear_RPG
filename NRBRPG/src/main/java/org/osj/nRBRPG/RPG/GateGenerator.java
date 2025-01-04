package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomFurniture;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.entities.BukkitMagmaCube;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GateGenerator
{
    private static List<Material> leafList = new ArrayList<>(Arrays.asList(Material.ACACIA_LEAVES,
            Material.AZALEA_LEAVES,
            Material.BIRCH_LEAVES,
            Material.CHERRY_LEAVES,
            Material.JUNGLE_LEAVES,
            Material.DARK_OAK_LEAVES,
            Material.FLOWERING_AZALEA_LEAVES,
            Material.MANGROVE_LEAVES,
            Material.OAK_LEAVES,
            Material.SPRUCE_LEAVES));

    public static int maxGateNum = 160;

    public static void generateGate()
    {
        int currGateNum = NRBRPG.getConfigManager().getConfig("gatenum").getInt("gatenum");
        if(currGateNum >= maxGateNum)
        {
            Bukkit.getConsoleSender().sendMessage("게이트 포화 상태");
            return;
        }
        NRBRPG.getConfigManager().getConfig("gatenum").set("gatenum", currGateNum + 1);
        NRBRPG.getConfigManager().saveConfig("gatenum");

        Random random = new Random();
        int lv = random.nextInt(6, 11);


        int xOffset = -2000;
        int zOffset = -2000;

        Location gateLoc;

        while(true)
        {
            int x = random.nextInt(0, 80) * 50 + xOffset;
            int z = random.nextInt(0, 80) * 50 + zOffset;
            int y = 300;
            while(Bukkit.getWorld(WorldManager.wildWorlds.get(0)).getBlockAt(x, y, z).getType() == Material.AIR)
            {
                y--;
            }

            gateLoc = new Location(Bukkit.getWorld(WorldManager.wildWorlds.get(0)), x, y, z);
            Block block = gateLoc.getBlock();
            if(leafList.contains(block.getType()))
            {
                continue;
            }
            List<Entity> entityList = Bukkit.getWorld(WorldManager.wildWorlds.get(0)).getNearbyEntities(block.getLocation(), 1, 1, 1).stream().toList();
            if(entityList.isEmpty() || !MythicBukkit.inst().getMobManager().isMythicMob(entityList.getFirst()))
            {
                gateLoc.add(0,1,0);
                break;
            }
        }

        Bukkit.getConsoleSender().sendMessage("게이트 생성: " + gateLoc.getBlockX() + " " + gateLoc.getBlockY() + " " + gateLoc.getBlockZ());

        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob("GateNormal").orElse(null);
        ActiveMob gate = mob.spawn(BukkitAdapter.adapt(gateLoc), lv);
    }
}
