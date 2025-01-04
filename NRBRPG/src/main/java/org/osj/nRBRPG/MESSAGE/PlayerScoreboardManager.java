package org.osj.nRBRPG.MESSAGE;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;
import org.osj.nRBRPG.DATA_MANAGEMENT.ConfigManager;
import org.osj.nRBRPG.NRBRPG;

import java.util.List;
import java.util.UUID;

public class PlayerScoreboardManager implements Listener
{
    public PlayerScoreboardManager()
    {
        BukkitScheduler scoreboardUpdateScheduler = Bukkit.getScheduler();
        scoreboardUpdateScheduler.runTaskTimer(NRBRPG.getServerInstance(), this::updateScoreBoard, 20L, 20L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        createScoreBoard(event.getPlayer());
    }


    public void createScoreBoard(Player player)
    {
        UUID uuid = player.getUniqueId();
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Component nameComponent = Component.text("노라방방").color(TextColor.color(255, 229, 0)).decorate(TextDecoration.BOLD);
        Objective o = board.registerNewObjective("NRB", Criteria.DUMMY, nameComponent, RenderType.HEARTS);
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score formatScore1 = o.getScore(ChatColor.GRAY + "===============\n");
        formatScore1.setScore(4);
        Score nameScore = o.getScore(ChatColor.AQUA + player.getName());
        nameScore.setScore(3);
        Score pointScore = o.getScore(ChatColor.GREEN + "Point: " + NRBRPG.getConfigManager().getConfig("point").getInt("players." + uuid));
        pointScore.setScore(2);
        Score ageScore = o.getScore(ChatColor.LIGHT_PURPLE +"나이: " + NRBRPG.getConfigManager().getConfig("ricecakesoup").getInt("players." + uuid) + "세");
        ageScore.setScore(1);
        Score formatScore2 = o.getScore(ChatColor.GRAY + "===============\t");
        formatScore2.setScore(0);
        player.setScoreboard(board);
    }

    public void updateScoreBoard()
    {
        for(Player online : Bukkit.getOnlinePlayers())
        {
            createScoreBoard(online);
        }
    }
}
