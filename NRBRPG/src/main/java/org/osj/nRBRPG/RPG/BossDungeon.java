package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomFurniture;
import io.lumine.mythic.api.adapters.AbstractLocation;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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
import java.util.Random;

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
    private ActiveMob gate;
    private Location spawnLoc;
    private Location originLoc;
    private TYPE type;
    private int maxTryChance = 5;
    private int tryChance = 0;
    private ActiveMob boss;
    private boolean active = false;
    public void init(ActiveMob gate)
    {
        this.gate = gate;
        Random random = new Random();
        type = TYPE.values()[random.nextInt(0, TYPE.values().length)];
        tryChance = maxTryChance;
        originLoc = gate.getEntity().getBukkitEntity().getLocation().add(1, 0, 1);
        active = false;
    }
    public void reset()
    {
        Random random = new Random();
        double x = 0;
        double y = 0;
        double z = 0;

        switch (random.nextInt(0,3))
        {
            case 0:
                x = 1142.5;
                y = 73.5;
                z = 1556.0;
                break;
            case 1:
                x = -1920.5;
                y = 63.5;
                z = -1791.5;
                break;
            case 2:
                x = -848.5;
                y = 169.5;
                z = 1082.5;
                break;
        }

        type = TYPE.values()[random.nextInt(0, TYPE.values().length)];
        tryChance = maxTryChance;
        originLoc = gate.getEntity().getBukkitEntity().getLocation().add(1, 0, 1);
        active = false;

        AbstractLocation newGateLoc = new AbstractLocation(gate.getEntity().getWorld(), x, y, z);

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                gate = MythicBukkit.inst().getMobManager().getActiveMob(gate.getEntity().getBukkitEntity().getUniqueId()).orElse(null);
                gate.getEntity().teleport(newGateLoc);
                Bukkit.getConsoleSender().sendMessage("보스 포탈 이동!");
            }
        }.runTaskLater(NRBRPG.getServerInstance(), 20L);
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
        if(boss != null)
        {
            boss.remove();
        }
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
                spawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), -519, 62, 3);
                bossSpawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), -519, 65, 23);
                mob = MythicBukkit.inst().getMobManager().getMythicMob("WARDEN").orElse(null);
                boss = mob.spawn(BukkitAdapter.adapt(bossSpawnLoc), 11);
                boss.getEntity().setModelScale(3);
                break;
            case WITHER:
                spawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), 487, 62, 13);
                bossSpawnLoc = new Location(Bukkit.getWorld(WorldManager.superbossWorld), 488, 63, -22);
                mob = MythicBukkit.inst().getMobManager().getMythicMob("WITHER").orElse(null);
                boss = mob.spawn(BukkitAdapter.adapt(bossSpawnLoc), 11);
                boss.getEntity().setModelScale(3);
                break;
        }
    }

    private void ClearDungeon()
    {
        for(Player player : inPlayerList)
        {
            MessageManager.SendFixedBossClearTitle(player);
            List<ItemStack> dropItemList = CustomItemManager.SetBossDrop(type);
            for(ItemStack drop : dropItemList)
            {
                player.getInventory().addItem(drop);
            }
            PointManager.AddPoint(player, 500000);
        }
        BukkitScheduler playerReturnScheduler = Bukkit.getScheduler();
        playerReturnScheduler.runTaskLater(NRBRPG.getServerInstance(), () ->
        {
            for(Player player : inPlayerList)
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.teleport(originLoc);
                        inPlayerList.remove(player);
                    }
                }.runTaskLater(NRBRPG.getServerInstance(), 2L);
            }
            reset();
            Bukkit.getConsoleSender().sendMessage("클리어 리셋");
        }, 20L * 30L);
    }

    @EventHandler
    public void onBossDeath(MythicMobDeathEvent event)
    {
        if(!event.getEntity().getWorld().getName().equals(WorldManager.superbossWorld))
        {
            return;
        }
        if(event.getMob().equals(boss) && type.equals(TYPE.ENDER_DRAGON))
        {
            ClearDungeon();
        }
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)
    {
        if(!event.getEntity().getWorld().getName().equals(WorldManager.superbossWorld))
        {
            return;
        }
        event.getDrops().clear();
        if(MythicBukkit.inst().getMobManager().isMythicMob(event.getEntity()) && !type.equals(TYPE.ENDER_DRAGON))
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
            reset();
            Bukkit.getConsoleSender().sendMessage("사망 리셋");
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
        if(type == null)
        {
            return;
        }
        double value = 0;
        switch (type)
        {
            case ENDER_DRAGON:
                value = 4;
                break;
            case WARDEN:
                value = 2;
                break;
            case WITHER:
                value = 6;
                break;
        }

        double damage = event.getFinalDamage();
        damage += damage * value;
        event.setDamage(damage);
    }
}
