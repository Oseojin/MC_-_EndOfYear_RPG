package org.osj.nRBRPG.MANAGER;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoreCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if(!player.isOp())
        {
            return false;
        }

        //lore p/s price
        if(strings[0].equals("p"))
        {
            List<Component> loreList = new ArrayList<>();
            loreList.add(Component.text("구매가: " + strings[1] + "P").color(TextColor.color(68, 255, 13)).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
            mainHand.lore(loreList);
        }
        else if(strings[0].equals("s"))
        {
            List<Component> loreList = new ArrayList<>();
            loreList.add(Component.text("판매가: " + strings[1] + "P").color(TextColor.color(255, 0, 7)).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
            mainHand.lore(loreList);
        }
        else if(strings[0].equals("r"))
        {
            List<Component> loreList = new ArrayList<>();
            loreList.clear();
            mainHand.lore(loreList);
        }

        return false;
    }
}
