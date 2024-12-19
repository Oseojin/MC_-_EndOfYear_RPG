package org.osj.nRBRPG.MANAGER;

import org.bukkit.entity.Player;
import org.osj.nRBRPG.NRBRPG;

public class PointManager
{
    public static void AddPoint(Player player, int amount)
    {
        if(!NRBRPG.getConfigManager().getConfig("point").contains("players." + player.getUniqueId()))
        {
            NRBRPG.getConfigManager().getConfig("point").set("players." + player.getUniqueId(), 0);
        }
        NRBRPG.getConfigManager().getConfig("point").set("players." + player.getUniqueId()
                , NRBRPG.getConfigManager().getConfig("point").getInt("players." + player.getUniqueId()) + amount);
        NRBRPG.getConfigManager().saveConfig("point");
    }

    public static int GetPoint(Player player)
    {
        if(!NRBRPG.getConfigManager().getConfig("point").contains("players." + player.getUniqueId()))
        {
            return 0;
        }
        return NRBRPG.getConfigManager().getConfig("point").getInt("players." + player.getUniqueId());
    }
}
