package org.osj.nRBRPG.CHUNK_OWNERSHIP;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MESSAGE.MessageManager;
import org.osj.nRBRPG.NRBRPG;

import java.util.UUID;

public class RemoveLandAllow implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(strings.length != 1)
        {
            return false;
        }

        UUID friendUUID = player.getServer().getPlayerUniqueId(strings[0]);
        if(friendUUID == null)
        {
            MessageManager.SendChatContent(player, "존재하지 않는 플레이어 입니다.", TextColor.color(255, 0, 0));
            return false;
        }

        NRBRPG.getChunkManager().removeFriendChunk(friendUUID, player.getUniqueId());

        return false;
    }
}
