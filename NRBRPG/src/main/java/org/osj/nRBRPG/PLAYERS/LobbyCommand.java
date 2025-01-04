package org.osj.nRBRPG.PLAYERS;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;
import org.osj.nRBRPG.MANAGER.WorldManager;

public class LobbyCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;

        if(player.getWorld().getName().equals(WorldManager.dungeonWorld) || player.getWorld().getName().equals(WorldManager.superbossWorld))
        {
            player.sendMessage(Component.text("던전에서는 로비로 이동할 수 없습니다.").color(TextColor.color(255,0,0)).decorate(TextDecoration.BOLD));
            return false;
        }
        if(PlayerDeath.deathPlayerReviveMap.containsKey(player.getUniqueId()))
        {
            player.sendMessage(Component.text("빈사 상태입니다! 누군가가 소생시키거나 [포기] 버튼을 눌러 로비로 돌아갈 수 있습니다.").color(TextColor.color(255,0,0)).decorate(TextDecoration.BOLD));
            return false;
        }
        player.teleport(SpawnLocationManager.lobbySpawnLoc);

        return false;
    }
}
