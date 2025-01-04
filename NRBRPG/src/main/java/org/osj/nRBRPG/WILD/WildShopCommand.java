package org.osj.nRBRPG.WILD;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.osj.nRBRPG.MANAGER.InventoryManager;
import org.osj.nRBRPG.MANAGER.WorldManager;

public class WildShopCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(player.getWorld().getName().equals(WorldManager.lobbyWorld) || player.getWorld().getName().equals(WorldManager.dungeonWorld))
        {
            player.sendMessage(Component.text("해당 명령어는 야생 월드에서만 사용할 수 있습니다.").color(TextColor.color(255, 6, 0)).decorate(TextDecoration.BOLD));
            return false;
        }
        if(strings[0].equals("자재") && strings.length == 2)
        {
            if(strings[1].equals("1"))
            {
                player.openInventory(InventoryManager.makeStructureShopWild(0));
            }
            else if(strings[1].equals("2"))
            {
                player.openInventory(InventoryManager.makeStructureShopWild(1));
            }
            else if(strings[1].equals("3"))
            {
                player.openInventory(InventoryManager.makeStructureShopWild(2));
            }
        }
        else if(strings[0].equals("일반"))
        {
            player.openInventory(InventoryManager.makeNormalShopWild());
        }
        return false;
    }
}
