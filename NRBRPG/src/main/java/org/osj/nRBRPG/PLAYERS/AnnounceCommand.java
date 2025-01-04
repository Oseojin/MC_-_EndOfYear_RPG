package org.osj.nRBRPG.PLAYERS;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MESSAGE.MessageManager;

public class AnnounceCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;

        if(!player.isOp())
        {
            return false;
        }

        TextColor textColor = TextColor.color(0,0,0);
        String content = "";
        switch (strings[0])
        {
            case "normal":
                textColor = TextColor.color(100, 233, 255);
                for(int i = 1; i < strings.length; i++)
                {
                    content += strings[i] +" ";
                }
                break;
            case "boss":
                textColor = TextColor.color(97, 15, 16);
                content += "                  보스 게이트가 출현했습니다!";
                break;
        }

        for(Player online : Bukkit.getOnlinePlayers())
        {
            MessageManager.SendChatForm(online);
            MessageManager.SendChatContent(online, content, textColor);
            MessageManager.SendChatForm(online);
        }

        return false;
    }
}
