package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.osj.nRBRPG.MANAGER.WorldManager;

import java.util.Random;

public class DungeonGenerator
{
    public enum ROOMTYPE
    {
        SPAWN,
        MONSTER,
        BOSS
    }
    public static Dungeon NewDungeon(int dungeonNum, Location originLoc, int dungeonLv)
    {
        Random random = new Random();
        Dungeon.CONCEPT concept = Dungeon.CONCEPT.values()[random.nextInt(0, Dungeon.CONCEPT.values().length)];
        int dungeonSize = random.nextInt(4, 9);

        StartGenerate(concept, dungeonNum, dungeonSize);

        Dungeon newDungeon = new Dungeon(concept, dungeonNum, dungeonSize, dungeonLv, originLoc);

        return newDungeon;
    }

    public static void StartGenerate(Dungeon.CONCEPT concept, int dungeonNumber, int dungeonSize)
    {
        ROOMTYPE roomtype = ROOMTYPE.SPAWN;
        Chunk buildStartChunk = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(0, dungeonNumber * 4);
        Location buildStartLoc = buildStartChunk.getBlock(0,0,0).getLocation();
        Chunk copyStartChunk = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(roomtype.ordinal() * 2, concept.ordinal() * -2 - 2);
        Location copyStartLoc = copyStartChunk.getBlock(0,0,0).getLocation();

        CopyRoom(buildStartLoc, copyStartLoc, concept);
        int i;

        for(i = 1; i < dungeonSize - 1; i++)
        {
            roomtype = ROOMTYPE.MONSTER;
            buildStartChunk = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(i * 2, dungeonNumber * 4);
            buildStartLoc = buildStartChunk.getBlock(0,0,0).getLocation();
            copyStartChunk = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(roomtype.ordinal() * 2, concept.ordinal() * -2 - 2);
            copyStartLoc = copyStartChunk.getBlock(0,0,0).getLocation();

            CopyRoom(buildStartLoc, copyStartLoc, concept);
        }
        roomtype = ROOMTYPE.BOSS;
        buildStartChunk = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(i * 2, dungeonNumber * 4);
        buildStartLoc = buildStartChunk.getBlock(0,0,0).getLocation();
        copyStartChunk = Bukkit.getWorld(WorldManager.dungeonWorld).getChunkAt(roomtype.ordinal() * 2, concept.ordinal() * -2 - 2);
        copyStartLoc = copyStartChunk.getBlock(0,0,0).getLocation();
        CopyRoom(buildStartLoc, copyStartLoc, concept);
    }

    private static void CopyRoom(Location buildStartLoc, Location copyStartLoc, Dungeon.CONCEPT concept)
    {
        boolean isNether = concept.equals(Dungeon.CONCEPT.NETHER_NORMAL) || concept.equals(Dungeon.CONCEPT.NETHER_FOREST) || concept.equals(Dungeon.CONCEPT.NETHER_FORTRESS);
        for(int x = 0; x < 32; x++)
        {
            for(int y = 0; y < 16; y++)
            {
                for(int z = 0; z < 32; z++)
                {
                    Location copyLoc = new Location(copyStartLoc.getWorld(), copyStartLoc.getX() + x, copyStartLoc.getY() + y, copyStartLoc.getZ() + z);
                    Location buildLoc = new Location(buildStartLoc.getWorld(), buildStartLoc.getX() + x, buildStartLoc.getY() + y, buildStartLoc.getZ() + z);
                    Block copyBlock = copyLoc.getBlock();
                    if(copyBlock.getType().equals(Material.BEDROCK))
                    {
                        CustomBlock.place("nrb:door_block", buildLoc);
                    }
                    else
                    {
                        if(copyBlock.getType().equals(Material.AIR))
                        {
                            CustomBlock customBlock = CustomBlock.byAlreadyPlaced(buildLoc.getBlock());
                            if(customBlock != null && (customBlock.getPermission().equals("nrb.door_block")
                                    || customBlock.getPermission().equals("nrb.monster_active_block")
                                    || customBlock.getPermission().equals("nrb.boss_active_block")
                                    || customBlock.getPermission().equals("nrb.chest_block")))
                            {
                                customBlock.remove();
                            }
                        }
                        buildLoc.getBlock().setType(copyBlock.getType());
                        if(isNether)
                        {
                            buildLoc.getWorld().setBiome(buildLoc, Biome.NETHER_WASTES);
                        }
                        else
                        {
                            buildLoc.getWorld().setBiome(buildLoc, Biome.PLAINS);
                        }
                    }
                }
            }
        }
    }
}
