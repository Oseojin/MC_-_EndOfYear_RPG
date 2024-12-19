package org.osj.nRBRPG.RPG;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Beehive;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MANAGER.InventoryManager;
import org.osj.nRBRPG.MANAGER.PointManager;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;

public class DungeonCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(!player.isOp())
        {
            return false;
        }

        int x = Integer.parseInt(strings[0]);
        int y = Integer.parseInt(strings[1]);
        int z = Integer.parseInt(strings[2]);

        player.getWorld().setBiome(x, y, z, Biome.NETHER_WASTES);

        return false;
    }
}
