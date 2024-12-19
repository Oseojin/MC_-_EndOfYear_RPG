package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomBlock;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.osj.nRBRPG.ITEMSADDER.CustomItemManager;
import org.osj.nRBRPG.MANAGER.PointManager;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.MESSAGE.MessageManager;
import org.osj.nRBRPG.NRBRPG;

import java.util.*;

public class Dungeon implements Listener
{
    public enum CONCEPT
    {
        TUNDRA,
        MINE,
        ABYSS,
        DESSERT,
        NETHER_NORMAL,
        NETHER_FOREST,
        NETHER_FORTRESS,
        VILLAGE,
        WOODLAND_MANSION,
        FOREST
    }
    private List<Player> inPlayerList = new LinkedList<>();
    private List<LivingEntity> monsterList = new LinkedList<>();

    private CONCEPT concept;
    private int dungeonSize;
    private int dungeonNumber;
    private int lv;
    private int progress;
    private Location spawnLoc;
    private Location originLoc;
    private ActiveMob boss;

    public Dungeon(CONCEPT concept, int dungeonNumber, int dungeonSize, int lv, Location originLoc)
    {
        NRBRPG.getServerInstance().getServer().getPluginManager().registerEvents(this, NRBRPG.getServerInstance());
        this.concept = concept;
        this.dungeonNumber = dungeonNumber;
        this.dungeonSize = dungeonSize;
        this.lv = lv;
        this.originLoc = originLoc;

        spawnLoc = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(0,dungeonNumber * 4).getBlock(3 ,2, 15).getLocation();

        progress = 0;
    }

    public void EnterDungeon(Player player)
    {
        inPlayerList.add(player);
        MessageManager.SendEnterDungeonTitle(player, concept, lv);
        player.teleport(spawnLoc);
    }

    public void ClearDungeon()
    {
        for(Player player : inPlayerList)
        {
            MessageManager.SendDungeonClearTitle(player);
        }
        BukkitScheduler playerReturnScheduler = Bukkit.getScheduler();
        playerReturnScheduler.runTaskLater(NRBRPG.getServerInstance(), () ->
        {
            for(Player player : inPlayerList)
            {
                player.teleport(originLoc);
            }
        }, 20L * 60L);
    }

    public void OpenRoom()
    {
        Chunk exitStartChunk = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(progress * 2 + 1, dungeonNumber * 4);
        Location exitStartLoc = exitStartChunk.getBlock(15, 0, 0).getLocation();
        Chunk enterStartChunk = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(progress * 2, dungeonNumber * 4);
        Location enterStartLoc = enterStartChunk.getBlock(0, 0, 0).getLocation();

        for(int z = 0; z < 32; z++)
        {
            for(int y = 0; y < 16; y++)
            {
                Location exitBlockLoc = new Location(exitStartLoc.getWorld(), exitStartLoc.getX(), exitStartLoc.getY() + y, exitStartLoc.getZ() + z);
                Location enterBlockLoc = new Location(enterStartLoc.getWorld(), enterStartLoc.getX(), enterStartLoc.getY() + y, enterStartLoc.getZ() + z);
                Block exitBlock = exitBlockLoc.getBlock();
                Block enterBlock = enterBlockLoc.getBlock();

                if(exitBlock.getType().equals(Material.NOTE_BLOCK))
                {
                    CustomBlock doorBlock = CustomBlock.byAlreadyPlaced(exitBlock);
                    doorBlock.remove();
                }
                if(enterBlock.getType().equals(Material.NOTE_BLOCK))
                {
                    CustomBlock doorBlock = CustomBlock.byAlreadyPlaced(enterBlock);
                    doorBlock.remove();
                }
            }
        }

        progress++;
        ActiveBlockGenerate();
    }

    public void CloseRoom()
    {
        Chunk enterStartChunk = getCurrRoom();
        Location enterStartLoc = enterStartChunk.getBlock(0, 0, 0).getLocation();

        for(int z = 0; z < 32; z++)
        {
            for(int y = 0; y < 16; y++)
            {
                Location enterBlockLoc = new Location(enterStartLoc.getWorld(), enterStartLoc.getX(), enterStartLoc.getY() + y, enterStartLoc.getZ() + z);
                if(enterBlockLoc.getBlock().getType().equals(Material.AIR))
                {
                    CustomBlock.place("nrb:door_block", enterBlockLoc);
                }
            }
        }
    }

    public void ActiveBlockGenerate()
    {
        String activeBlockID;
        if(dungeonSize-1 == progress)
        {
            activeBlockID = "nrb:boss_active_block";
        }
        else
        {
            activeBlockID = "nrb:monster_active_block";
        }
        Chunk currChunk = getCurrRoom();
        Location activeBlockLoc = currChunk.getBlock(15, 3, 15).getLocation();
        Bukkit.getConsoleSender().sendMessage(CustomBlock.place(activeBlockID, activeBlockLoc) + "");
    }

    public Location getSpawnLoc()
    {
        return spawnLoc;
    }

    public Chunk getCurrRoom()
    {
        return Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(progress * 2, dungeonNumber * 4);
    }
    public Chunk getChestRoom()
    {
        return Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(dungeonSize * 2, dungeonNumber * 4);
    }

    @EventHandler
    public void onPlayerBreakActiveBlock(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equals(WorldManager.dungeonWorld))
        {
            return;
        }
        Block activeBlock = event.getBlock();
        CustomBlock activeBlockCustom = CustomBlock.byAlreadyPlaced(activeBlock);
        if(activeBlockCustom == null)
        {
            return;
        }
        if(activeBlockCustom.getPermission().equals("nrb.monster_active_block"))
        {
            Random random = new Random();
            int randomMonsterNum = random.nextInt(4, 29);
            for(int i = 0; i < randomMonsterNum; i++)
            {
                Location randomLoc = null;
                do
                {
                    int randomX = random.nextInt(1, 30);
                    int randomZ = random.nextInt(1, 30);
                    randomLoc = getCurrRoom().getBlock(0, 3, 0).getLocation();
                    randomLoc.add(randomX, 0, randomZ);
                } while (!randomLoc.getBlock().getType().equals(Material.AIR));

                monsterList.addAll(MonsterSpawnManager.SpawnMonster(concept, randomLoc, lv));
            }
            for(Player inPlayer : inPlayerList)
            {
                MessageManager.SendActiveMonsterTitle(player);
                inPlayer.teleport(activeBlock.getLocation());
            }
            CloseRoom();
        }
        else if(activeBlockCustom.getPermission().equals("nrb.boss_active_block"))
        {
            boss = MonsterSpawnManager.SpawnBoss(concept, getCurrRoom().getBlock(15, 3, 15).getLocation(), lv);
            for(Player inPlayer : inPlayerList)
            {
                MessageManager.SendActiveBossTitle(player);
                inPlayer.teleport(activeBlock.getLocation());
            }
            CloseRoom();
        }
        else if(activeBlockCustom.getPermission().equals("nrb.chest_block"))
        {
            // 드랍 조정(부순 플레이어 인벤토리로 전송)
            player.getInventory().addItem(CustomItemManager.randomEnchant());
        }
        else
        {
            return;
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equals(WorldManager.dungeonWorld))
        {
            return;
        }
        if(inPlayerList.contains(player))
        {
            player.teleport(originLoc);
            inPlayerList.remove(player);
        }
    }

    @EventHandler
    public void onMonsterDeath(EntityDeathEvent event)
    {
        if(!event.getEntity().getWorld().getName().equals(WorldManager.dungeonWorld))
        {
            return;
        }
        if(monsterList.contains(event.getEntity()))
        {
            monsterList.remove(event.getEntity());
            for(Player player : inPlayerList)
            {
                Random random = new Random();
                int rewardPoint = random.nextInt(-50, 51) + (lv * 100);
                PointManager.AddPoint(player, rewardPoint);
            }
            if(monsterList.isEmpty())
            {
                for(Player player : inPlayerList)
                {
                    MessageManager.SendRoomClearTitle(player);
                }
                OpenRoom();
            }
            event.getDrops().clear();
            Random random = new Random();
            // 드랍템 체크
            if(random.nextInt(0, 100) + 1 >= 90)
            {
                event.getDrops().add(CustomItemManager.SetDropItem(lv));
            }
            // 상자 체크
            if(random.nextInt(0, 100) + 1 >= 99)
            {
                CustomBlock.place("nrb:chest_block", event.getEntity().getLocation());
            }
        }
    }

    @EventHandler
    public void onCreeperBoom(ExplosionPrimeEvent event)
    {
        Location explodeLoc = event.getEntity().getLocation();
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                monsterList.add(MonsterSpawnManager.SpawnCreeper(explodeLoc, lv));
            }
        }.runTaskLater(NRBRPG.getServerInstance(), 4L);
    }

    @EventHandler
    public void onBossDeath(MythicMobDeathEvent event)
    {
        if(event.getMob().equals(boss))
        {
            OpenRoom();
            ClearDungeon();
            event.getDrops().clear();
            // 드랍 조정(플레이어에게 직접 투입)
            for(Player player : inPlayerList)
            {
                MessageManager.SendDungeonClearTitle(player);
                player.getInventory().addItem(CustomItemManager.randomEnchant());
                Random random = new Random();
                int rewardPoint = random.nextInt(-50, 51) + (lv * 100) * 10;
                PointManager.AddPoint(player, rewardPoint);
            }
            List<Entity> bossSummonEntityList = new ArrayList<>();
            bossSummonEntityList.addAll(Arrays.stream(Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(progress * 2, dungeonNumber * 4).getEntities()).toList());
            bossSummonEntityList.addAll(Arrays.stream(Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(progress * 2 + 1, dungeonNumber * 4).getEntities()).toList());
            bossSummonEntityList.addAll(Arrays.stream(Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(progress * 2, dungeonNumber * 4 + 1).getEntities()).toList());
            bossSummonEntityList.addAll(Arrays.stream(Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(progress * 2 + 1, dungeonNumber * 4 + 1).getEntities()).toList());
            for(Entity entity : bossSummonEntityList)
            {
                if(!entity.getType().equals(EntityType.PLAYER))
                {
                    ((LivingEntity)entity).damage(10000);
                }
            }
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
        if(!player.getWorld().getName().equals(WorldManager.dungeonWorld))
        {
            return;
        }

        double damage = event.getFinalDamage();
        damage += damage * lv/5.0;
        event.setDamage(damage);
    }
}