package org.osj.nRBRPG.MANAGER;

import org.bukkit.entity.Player;
import org.osj.nRBRPG.NRBRPG;

public class CoinManager
{
    public static void AddCoin(Player player, int amount)
    {
        if(!NRBRPG.getConfigManager().getConfig("coin").contains("players." + player.getUniqueId()))
        {
            NRBRPG.getConfigManager().getConfig("coin").set("players." + player.getUniqueId(), 0);
        }
        NRBRPG.getConfigManager().getConfig("coin").set("players." + player.getUniqueId()
                , NRBRPG.getConfigManager().getConfig("coin").getInt("players." + player.getUniqueId()));
    }

    public static int GetCoin(Player player)
    {
        if(!NRBRPG.getConfigManager().getConfig("coin").contains("players." + player.getUniqueId()))
        {
            return 0;
        }
        return NRBRPG.getConfigManager().getConfig("coin").getInt("players." + player.getUniqueId());
    }
}
