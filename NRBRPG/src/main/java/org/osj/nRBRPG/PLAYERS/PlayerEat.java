package org.osj.nRBRPG.PLAYERS;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.NRBRPG;

public class PlayerEat implements Listener
{
    private FileConfiguration soupConfig = NRBRPG.getConfigManager().getConfig("ricecakesoup");
    @EventHandler
    public void PlayerEatRiceCakeSoup(PlayerItemConsumeEvent event)
    {
        Player player = event.getPlayer();
        CustomStack ricecake = CustomStack.byItemStack(event.getItem());
        if(ricecake == null)
        {
            return;
        }
        if(ricecake.getPermission().equals("nrb.ricecakesoup"))
        {
            int num = soupConfig.getInt("players."+player.getUniqueId()) + 1;
            soupConfig.set("players."+player.getUniqueId(), num);
            NRBRPG.getConfigManager().saveConfig("ricecakesoup");
            Component listNameComponent = Component.empty()
                    .append(Component.text("[" + soupConfig.getInt("players."+player.getUniqueId()) + "세]").color(TextColor.color(255, 85, 255)))
                    .append(Component.text(player.getName()));
            player.playerListName(listNameComponent);
            if(PlayerScaleCommand.playerList.contains(player))
            {
                double scale = num * 0.02 + 1.0;
                player.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(scale);
            }
        }
    }
}
