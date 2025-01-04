package org.osj.nRBRPG.CHUNK_OWNERSHIP;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.MESSAGE.MessageManager;
import org.osj.nRBRPG.NRBRPG;

public class UseLandDocument implements Listener
{
    @EventHandler
    public void onUseChunkPurchaseItem(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack playerHand = player.getInventory().getItemInMainHand();
        CustomStack ticket = CustomStack.byItemStack(playerHand);
        if(ticket == null)
        {
            return;
        }

        if(event.getAction().isRightClick() && ticket.getPermission().equals("nrb.land_document"))
        {
            if(!player.getWorld().getName().equals(WorldManager.lobbyWorld))
            {
                MessageManager.SendChatContent(player, "로비 월드에서만 땅을 소유할 수 있습니다!", TextColor.color(255, 0, 0));
                return;
            }
            if(-2 <= player.getChunk().getX() && player.getChunk().getX() <= 1 && -2 <= player.getChunk().getZ() && player.getChunk().getZ() <= 1)
            {
                MessageManager.SendChatContent(player, "로비 스폰에서 너무 가깝습니다!", TextColor.color(255, 0, 0));
                return;
            }
            if(!NRBRPG.getChunkManager().isOwnerless(player.getChunk().getChunkKey()))
            {
                MessageManager.SendChatContent(player, "이미 누군가가 소유중인 땅입니다!", TextColor.color(255, 0, 0));
                return;
            }
            MessageManager.SendChatContent(player, "청크를 성공적으로 소유하였습니다!", TextColor.color(0, 255, 0));
            playerHand.add(-1);
            NRBRPG.getChunkManager().addMyChunk(player.getUniqueId(), player.getChunk());
        }
    }
}
