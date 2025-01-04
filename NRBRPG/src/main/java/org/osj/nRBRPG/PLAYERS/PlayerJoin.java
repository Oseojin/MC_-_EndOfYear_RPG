package org.osj.nRBRPG.PLAYERS;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.NRBRPG;

public class PlayerJoin implements Listener
{
    private static Location guideBookChestLoc;

    public static boolean isCheck = false;

    private static FileConfiguration ricecakesoupConfig = NRBRPG.getConfigManager().getConfig("ricecakesoup");

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        if (event.getPlayer().isOp())
        {
            return;
        }
        if (isCheck)
        {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("23시 00분 부터 00시 00분 까지는 점검 시간입니다."));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        player.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.0);

        if(!ricecakesoupConfig.contains("players." + player.getUniqueId()))
        {
            ricecakesoupConfig.set("players." + player.getUniqueId(), 0);
            NRBRPG.getConfigManager().saveConfig("ricecakesoup");
        }

        Component listNameComponent = Component.empty()
                        .append(Component.text("[" + ricecakesoupConfig.getInt("players."+player.getUniqueId()) + "세]").color(TextColor.color(255, 85, 255)))
                        .append(Component.text(player.getName()));
        player.playerListName(listNameComponent);

        if(!player.hasPlayedBefore())
        {
            guideBookChestLoc = new Location(Bukkit.getWorld(WorldManager.lobbyWorld), -2, -63, 1);
            Chest chest = (Chest) guideBookChestLoc.getBlock().getState();

            player.getInventory().addItem(chest.getBlockInventory().getItem(0));
            player.getInventory().addItem(chest.getBlockInventory().getItem(1));
            player.getInventory().addItem(chest.getBlockInventory().getItem(2));

            player.getInventory().addItem(CustomStack.getInstance("nrb:land_document").getItemStack());
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    player.teleport(SpawnLocationManager.lobbySpawnLoc);
                }
            }.runTaskLater(NRBRPG.getServerInstance(), 4L);
            return;
        }
        if(player.getWorld().getName().equals(WorldManager.lobbyWorld) || WorldManager.wildWorlds.contains(player.getWorld().getName()))
        {
            return;
        }
        player.teleport(SpawnLocationManager.lobbySpawnLoc);
    }
}
