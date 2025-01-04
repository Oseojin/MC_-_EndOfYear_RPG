package org.osj.nRBRPG.PLAYERS;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;

public class TeleportLandCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;

        if(!player.getWorld().getName().equals(WorldManager.lobbyWorld))
        {
            player.sendMessage(Component.text("로비 월드에서만 내 땅으로 이동할 수 있습니다.").color(TextColor.color(255,0,0)).decorate(TextDecoration.BOLD));
            return false;
        }
        Chunk myChunk = NRBRPG.getChunkManager().getMyChunk(player.getUniqueId());
        if(myChunk == null)
        {
            player.sendMessage(Component.text("가진 땅이 없습니다.").color(TextColor.color(255,0,0)).decorate(TextDecoration.BOLD));
            return false;
        }
        player.teleport(myChunk.getBlock(0, -60, 0).getLocation());

        return false;
    }
}
