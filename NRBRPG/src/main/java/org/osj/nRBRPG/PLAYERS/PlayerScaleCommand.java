package org.osj.nRBRPG.PLAYERS;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PlayerScaleCommand implements CommandExecutor
{
    private FileConfiguration soupConfig = NRBRPG.getConfigManager().getConfig("ricecakesoup");
    public static List<Player> playerList = new LinkedList<>();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(playerList.contains(player))
        {
            playerList.remove(player);
            player.sendMessage(Component.text("기본 크기로 되돌아갑니다.").color(TextColor.color(0, 255, 0)));
            player.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.0);
        }
        else
        {
            if(player.getWorld().getName().equals(WorldManager.dungeonWorld))
            {
                player.sendMessage(Component.text("던전에서는 성장할 수 없습니다!").color(TextColor.color(255, 0 ,0)));
                return false;
            }
            playerList.add(player);
            player.sendMessage(Component.text("성장한 크기로 되돌아갑니다.").color(TextColor.color(0, 255, 0)));
            if(!soupConfig.contains("players." + player.getUniqueId()))
            {
                soupConfig.set("players." + player.getUniqueId(), 0);
            }
            double scale = soupConfig.getInt("players." + player.getUniqueId()) * 0.02;
            player.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.0 + scale);
        }

        return false;
    }
}
