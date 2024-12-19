package org.osj.nRBRPG.MANAGER;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager
{
    private static List<Location> structureShopChestList = new ArrayList<>();
    private static Location gateMineralShopChestLoc;
    private static Location normalShopChestLoc;

    public InventoryManager()
    {
        structureShopChestList.add(new Location(Bukkit.getWorld(WorldManager.lobbyWorld), 0, -63, 0));
        structureShopChestList.add(new Location(Bukkit.getWorld(WorldManager.lobbyWorld), 2, -63, 0));
        structureShopChestList.add(new Location(Bukkit.getWorld(WorldManager.lobbyWorld), 4, -63, 0));
        gateMineralShopChestLoc = new Location(Bukkit.getWorld(WorldManager.lobbyWorld), -2, -63, 0);
        normalShopChestLoc = new Location(Bukkit.getWorld(WorldManager.lobbyWorld), -4, -63, 0);
    }
    public static Inventory makeStructureShop(int num)
    {
        Component invName = Component.text("StructureShop_" + num);
        Inventory inv = Bukkit.createInventory(null, 54, invName);
        Chest chest1 = (Chest) structureShopChestList.get(num).getBlock().getState();
        Chest chest2 = (Chest) structureShopChestList.get(num).getBlock().getLocation().add(1, 0, 0).getBlock().getState();
        for(int i = 0; i < 27; i++)
        {
            ItemStack item = chest1.getBlockInventory().getItem(i);
            if(item != null && !item.getType().equals(Material.AIR))
            {
                List<Component> loreList = new ArrayList<>();
                loreList.add(Component.text("구매가: 50P").color(TextColor.color(68, 255, 13)).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
                loreList.add(Component.text("쉬프트 클릭하면 64개 씩 거래할 수 있습니다.").color(TextColor.color(255, 255, 255)).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
                item.lore(loreList);
                inv.setItem(i, item);
            }
        }
        for(int i = 27; i < 54; i++)
        {
            ItemStack item = chest2.getBlockInventory().getItem(i-27);
            if(item != null && !item.getType().equals(Material.AIR))
            {
                List<Component> loreList = new ArrayList<>();
                loreList.add(Component.text("구매가: 50P").color(TextColor.color(68, 255, 13)).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
                loreList.add(Component.text("쉬프트 클릭하면 64개 씩 거래할 수 있습니다.").color(TextColor.color(255, 255, 255)).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false));
                item.lore(loreList);
                inv.setItem(i, item);
            }
        }

        return inv;
    }
    public static Inventory makeGateMineralShop()
    {
        Component invName = Component.text("GateMineralShop");
        Inventory inv = Bukkit.createInventory(null, 54, invName);
        Chest chest1 = (Chest) gateMineralShopChestLoc.getBlock().getState();
        Chest chest2 = (Chest) gateMineralShopChestLoc.getBlock().getLocation().add(1, 0, 0).getBlock().getState();
        for(int i = 0; i < 27; i++)
        {
            ItemStack item = chest1.getBlockInventory().getItem(i);
            if(item != null && !item.getType().equals(Material.AIR))
            {
                inv.setItem(i, item);
            }
        }
        for(int i = 27; i < 54; i++)
        {
            ItemStack item = chest2.getBlockInventory().getItem(i-27);
            if(item != null && !item.getType().equals(Material.AIR))
            {
                inv.setItem(i, item);
            }
        }

        return inv;
    }
    public static Inventory makeNormalShop()
    {
        Component invName = Component.text("NormalShop");
        Inventory inv = Bukkit.createInventory(null, 54, invName);
        Chest chest1 = (Chest) normalShopChestLoc.getBlock().getState();
        Chest chest2 = (Chest) normalShopChestLoc.getBlock().getLocation().add(1, 0, 0).getBlock().getState();
        for(int i = 0; i < 27; i++)
        {
            ItemStack item = chest1.getBlockInventory().getItem(i);
            if(item != null && !item.getType().equals(Material.AIR))
            {
                inv.setItem(i, item);
            }
        }
        for(int i = 27; i < 54; i++)
        {
            ItemStack item = chest2.getBlockInventory().getItem(i-27);
            if(item != null && !item.getType().equals(Material.AIR))
            {
                inv.setItem(i, item);
            }
        }

        return inv;
    }
}
