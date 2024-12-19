package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomFurniture;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.osj.nRBRPG.ITEMSADDER.CustomItemManager;
import org.osj.nRBRPG.MANAGER.PointManager;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.MESSAGE.MessageManager;
import org.osj.nRBRPG.NRBRPG;

import java.util.LinkedList;
import java.util.List;

public class BossDungeon implements Listener
{
    public enum TYPE
    {
        ENDER_DRAGON,
        WITHER,
        WARDEN
    }
    private List<Player> inPlayerList = new LinkedList<>();
    private Location bossSpawnLoc;
    private CustomFurniture gate;
    private Location spawnLoc;
    private Location originLoc;
    private TYPE type;
    private int tryChance = 0;
    private ActiveMob boss;
    private boolean active = false;
    public void init(TYPE type, int tryChance, CustomFurniture gate)
    {
        this.type = type;
        this.tryChance = tryChance;
        this.gate = gate;
        this.originLoc = gate.getEntity().getLocation().add(1, 0 ,0);
        active = true;
        if(boss != null)
        {
            boss.remove();
        }
    }
    public void EnterDungeon(Player player)
    {
        inPlayerList.add(player);
        player.teleport(spawnLoc);
        MessageManager.SendFixedBossTitle(player, type);
        tryChance--;
    }
    public int getTryChance()
    {
        return tryChance;
    }
    public boolean getActive()
    {
        return active;
    }
    public void SpawnBoss()
    {
        MythicMob mob;
        switch (type)
        {
            case ENDER_DRAGON:
                spawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), 0, 58, -60);
                bossSpawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), 0, 70, 0);
                mob = MythicBukkit.inst().getMobManager().getMythicMob("ENDER_DRAGON").orElse(null);
                boss = mob.spawn(BukkitAdapter.adapt(bossSpawnLoc), 11);
                boss.getEntity().setModelScale(3);
                break;
            case WARDEN:
                spawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), -519, 62, 0);
                bossSpawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), -519, 65, 23);
                mob = MythicBukkit.inst().getMobManager().getMythicMob("WARDEN").orElse(null);
                boss = mob.spawn(BukkitAdapter.adapt(bossSpawnLoc), 11);
                boss.getEntity().setModelScale(3);
                break;
            case WITHER:
                spawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), 487, 62, 13);
                bossSpawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), 487, 65, -7);
                mob = MythicBukkit.inst().getMobManager().getMythicMob("WITHER").orElse(null);
                boss = mob.spawn(BukkitAdapter.adapt(bossSpawnLoc), 11);
                boss.getEntity().setModelScale(3);
                break;
        }
    }

    private void ClearDungeon()
    {
        active = false;
        for(Player player : inPlayerList)
        {
            MessageManager.SendFixedBossClearTitle(player);
            ItemStack bundle = new ItemStack(Material.BUNDLE);
            BundleMeta bundleMeta = (BundleMeta) bundle.getItemMeta();
            bundleMeta.addItem(CustomItemManager.randomEnchantMax());
            bundleMeta.addItem(CustomItemManager.randomEnchantMax());
            bundleMeta.addItem(CustomItemManager.randomEnchantMax());
            bundleMeta.addItem(CustomItemManager.randomEnchantMax());
            bundleMeta.addItem(CustomItemManager.randomEnchantMax());
            bundle.setItemMeta(bundleMeta);
            player.getInventory().addItem(bundle);
            PointManager.AddPoint(player, 1000000);
        }
        BukkitScheduler playerReturnScheduler = Bukkit.getScheduler();
        playerReturnScheduler.runTaskLater(NRBRPG.getServerInstance(), () ->
        {
            for(Player player : inPlayerList)
            {
                player.teleport(originLoc);
            }
        }, 20L * 60L);
        gate.getEntity().getLocation().getBlock().setType(Material.AIR);
        gate.getEntity().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
        gate.getEntity().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
        gate.remove(false);
        inPlayerList.clear();
    }

    @EventHandler
    public void onBossDeath(MythicMobDeathEvent event)
    {
        if(!event.getEntity().getWorld().getName().equals(WorldManager.superbossWorld))
        {
            return;
        }
        if(event.getMob().equals(boss))
        {
            ClearDungeon();
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equals(WorldManager.superbossWorld))
        {
            return;
        }
        if(inPlayerList.contains(player))
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    player.teleport(originLoc);
                }
            }.runTaskLater(NRBRPG.getServerInstance(), 2L);
            inPlayerList.remove(player);
        }
        if(inPlayerList.isEmpty() && tryChance <= 0)
        {
            if(boss != null)
            {
                boss.remove();
            }
            active = false;
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event)
    {
        if(!event.getEntityType().equals(EntityType.PLAYER))
        {
            return;
        }
        Player player = (Player) event.getEntity();
        if(!player.getWorld().getName().equals(WorldManager.superbossWorld))
        {
            return;
        }
        double value = 0;
        switch (type)
        {
            case ENDER_DRAGON:
                value = 2;
                break;
            case WARDEN:
                value = 1;
                break;
            case WITHER:
                value = 3;
                break;
        }

        double damage = event.getFinalDamage();
        damage += damage * value;
        event.setDamage(damage);
    }
}
