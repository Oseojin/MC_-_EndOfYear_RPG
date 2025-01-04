package org.osj.nRBRPG.RPG;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;
import org.osj.nRBRPG.NRBRPG;
import org.osj.nRBRPG.PLAYERS.PlayerDeath;

public class GateGenOnOff implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(!player.isOp())
        {
            return false;
        }

        if(NRBRPG.getDungeonManager().generate)
        {
            player.sendMessage("게이트 생성 꺼짐");
        }
        else
        {
            player.sendMessage("게이트 생성 켜짐");
        }
        NRBRPG.getDungeonManager().generate = !NRBRPG.getDungeonManager().generate;
        return false;
    }
}
