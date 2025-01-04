package org.osj.nRBRPG.PLAYERS;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;

public class CheckChangeCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;

        if(!player.isOp())
        {
            return false;
        }

        if(PlayerJoin.isCheck)
        {
            player.sendMessage("점검 끝");
        }
        else
        {
            player.sendMessage("점검 시작");
        }

        PlayerJoin.isCheck = !PlayerJoin.isCheck;

        return false;
    }
}
