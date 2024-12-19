package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomFurniture;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.osj.nRBRPG.MANAGER.WorldManager;

import java.util.Random;

public class GateGenerator
{
    private static int xOffset;
    private static int zOffset;

    public static void generateGate()
    {
        Random random = new Random();
        int lv = random.nextInt(0, 100) + 1;

        if(lv <= 33)
        {
            lv = 1;
        }
        else if(lv <= 33 + 17)
        {
            lv = 2;
        }
        else if(lv <= 33 + 17 + 13)
        {
            lv = 3;
        }
        else if(lv <= 33 + 17 + 13 + 9)
        {
            lv = 4;
        }
        else if(lv <= 33 + 17 + 13 + 9 + 8)
        {
            lv = 4;
        }
        else if(lv <= 33 + 17 + 13 + 9 + 8 + 7)
        {
             lv = 6;
        }
        else if(lv <= 33 + 17 + 13 + 9 + 8 + 7 + 6)
        {
            lv = 7;
        }
        else if(lv <= 33 + 17 + 13 + 9 + 8 + 7 + 6 + 4)
        {
            lv = 8;
        }
        else if(lv <= 33 + 17 + 13 + 9 + 8 + 7 + 6 + 4 + 2)
        {
            lv = 9;
        }
        else if(lv <= 33 + 17 + 13 + 9 + 8 + 7 + 6 + 4 + 2 + 1)
        {
            lv = 10;
        }


        int x = random.nextInt(0, 60) * 100 + xOffset;
        int z = random.nextInt(0, 60) * 100 + zOffset;
        int y = 300;
        while(Bukkit.getWorld(WorldManager.wildWorlds.get(0)).getBlockAt(x, y, z).getType() == Material.AIR)
        {
            y--;
        }
        y++;
        Location gateLoc = new Location(Bukkit.getWorld(WorldManager.wildWorlds.get(0)), x, y, z);

        CustomFurniture.spawn("nrb:gate_" + lv, gateLoc.getBlock());
        Bukkit.getConsoleSender().sendMessage(x + " " + z);
    }
}
