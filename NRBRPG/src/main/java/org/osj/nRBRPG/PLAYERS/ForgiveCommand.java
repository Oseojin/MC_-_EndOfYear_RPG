package org.osj.nRBRPG.PLAYERS;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;

public class ForgiveCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;

        if(PlayerDeath.deathPlayerReviveMap.containsKey(player.getUniqueId()))
        {
            PlayerDeath.KillPlayer(player);
            player.teleport(SpawnLocationManager.lobbySpawnLoc);
        }
        return false;
    }
}
