package org.osj.nRBRPG.PLAYERS;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.osj.nRBRPG.NRBRPG;

public class PlayerChat implements Listener
{
    private static FileConfiguration ricecakesoupConfig = NRBRPG.getConfigManager().getConfig("ricecakesoup");
    @EventHandler
    public void onChat(AsyncChatEvent event)
    {
        event.setCancelled(true);
        Player player = event.getPlayer();
        Component chatNameComponent = Component.empty()
                .append(Component.text("[" + ricecakesoupConfig.getInt("players."+player.getUniqueId()) + "세]").color(TextColor.color(255, 85, 255)))
                .append(Component.text(player.getName()));
        Component chat = event.renderer().render(player, chatNameComponent, event.message(), Audience.audience(Bukkit.getOnlinePlayers()));
        for(Player online : Bukkit.getOnlinePlayers())
        {
            online.sendMessage(chat);
        }
    }
}
