package org.osj.nRBRPG.CHUNK_OWNERSHIP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.NRBRPG;
import org.osj.nRBRPG.PLAYERS.PlayerScaleCommand;

public class RemoveChunkCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(!player.isOp())
        {
            return false;
        }
        if(NRBRPG.getChunkManager().isOwnerless(player.getChunk().getChunkKey()))
        {
            player.sendMessage("주인 없는 청크입니다.");
           return false;
        }
        NRBRPG.getChunkManager().removeMyChunk(NRBRPG.getChunkManager().whosChunk(player.getChunk().getChunkKey()), player.getChunk());
        return false;
    }
}
