package org.osj.nRBRPG.RPG;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.NRBRPG;

public class CurrGateNum implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(!player.isOp())
        {
            return false;
        }
        player.sendMessage("현재 게이트 개수: " + NRBRPG.getConfigManager().getConfig("gatenum").getInt("gatenum"));
        return false;
    }
}
