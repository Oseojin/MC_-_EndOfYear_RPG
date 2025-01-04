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
import org.osj.nRBRPG.MANAGER.PointManager;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;
import org.osj.nRBRPG.MANAGER.WorldManager;

public class AddPointCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;

        if(!player.isOp())
        {
            return false;
        }

        Player target = Bukkit.getPlayer(strings[0]);
        int point = Integer.parseInt(strings[1]);

        if(point < 0 && PointManager.GetPoint(target) + point < 0)
        {
            player.sendMessage("해당 플레이어에게 재화가 부족합니다!!");
            return false;
        }
        PointManager.AddPoint(target, point);

        return false;
    }
}
