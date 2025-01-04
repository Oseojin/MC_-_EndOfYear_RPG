package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.view.AnvilView;
import org.osj.nRBRPG.MANAGER.PointManager;
import org.osj.nRBRPG.MANAGER.WorldManager;

import java.util.*;

public class InvClickEvent implements Listener
{
    private static HashMap<Inventory, Integer> anvilCostMap = new HashMap<>();
    private static List<Player> playerRepairCheckList = new LinkedList<>();
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        if(event.getClickedInventory() == null)
        {
            return;
        }
        if((event.getView().getOriginalTitle().contains("Shop")))
        {
            event.setCancelled(true);
            if(event.getClickedInventory().equals(event.getView().getTopInventory()))
            {
                if(event.getCurrentItem() == null)
                {
                    return;
                }
                if(!event.isLeftClick())
                {
                    return;
                }
                Player player = (Player) event.getWhoClicked();
                int price;
                if(((TextComponent)(event.getCurrentItem().lore().get(0))).content().contains("구매가: "))
                {
                    price = Integer.parseInt(((TextComponent)(event.getCurrentItem().lore().get(0))).content().replace("구매가: ", "").replace("P", ""));
                    PurchaseItem(player, price, event.isShiftClick(), event.getCurrentItem());
                }
                else if(((TextComponent)(event.getCurrentItem().lore().get(0))).content().contains("판매가: "))
                {
                    price = Integer.parseInt(((TextComponent) (event.getCurrentItem().lore().get(0))).content().replace("판매가: ", "").replace("P", ""));
                    SellItem(player, price, event.getCurrentItem());
                }
            }
        }
        else if(event.getView().getType().equals(InventoryType.ANVIL))
        {
            if(event.getCurrentItem().equals(((AnvilInventory)event.getView().getTopInventory()).getResult()))
            {
                Player player = (Player) event.getWhoClicked();
                int cost = anvilCostMap.get(event.getView().getTopInventory());
                if(!playerRepairCheckList.contains(player))
                {
                    player.sendMessage("강화 비용: " + cost);
                    playerRepairCheckList.add(player);
                    event.setCancelled(true);
                    return;
                }
                if(PointManager.GetPoint(player) >= cost)
                {
                    PointManager.AddPoint(player, -cost);
                    ((AnvilView)event.getView()).setRepairCost(0);
                    playerRepairCheckList.remove(player);
                }
                else
                {
                    player.sendMessage(Component.text("포인트가 부족합니다! 필요 포인트: " + cost).color(TextColor.color(255, 6, 0)).decorate(TextDecoration.BOLD));
                    playerRepairCheckList.remove(player);
                    event.setCancelled(true);
                }
            }
        }
    }

    private void SellItem(Player player, int price, ItemStack currItem)
    {
        CustomStack currCustom = CustomStack.byItemStack(currItem);
        ItemStack inInventoryItem = null;
        if(currCustom != null)
        {
            for(int i = 0; i < 36; i++)
            {
                if(player.getInventory().getItem(i) == null)
                {
                    continue;
                }
                CustomStack customStack = CustomStack.byItemStack(player.getInventory().getItem(i));
                if (customStack == null)
                {
                    continue;
                }
                if(customStack.getNamespacedID().equals(currCustom.getNamespacedID()))
                {
                    inInventoryItem = player.getInventory().getItem(i);
                    break;
                }
            }
        }
        else
        {
            for(int i = 0; i < 36; i++)
            {
                if(player.getInventory().getItem(i) == null)
                {
                    continue;
                }
                if(player.getInventory().getItem(i).isSimilar(currItem))
                {
                    inInventoryItem = player.getInventory().getItem(i);
                    break;
                }
            }
        }
        if(inInventoryItem == null)
        {
            player.sendMessage(Component.text("아이템이 없습니다!").color(TextColor.color(255, 6, 0)).decorate(TextDecoration.BOLD));
        }
        else
        {
            inInventoryItem.add(-1);
            PointManager.AddPoint(player, price);
        }
    }

    private void PurchaseItem(Player player, int price, boolean isShiftClicked, ItemStack currItem)
    {
        int num = 1;
        if(isShiftClicked)
        {
            num = 64;
        }
        int point = PointManager.GetPoint(player);
        if(point >= price * num)
        {
            if(currItem.getType().equals(Material.WRITTEN_BOOK))
            {
                ItemStack cloneBook = currItem.clone();
                List<Component> loreList = cloneBook.lore();
                loreList.clear();
                cloneBook.lore(loreList);
                player.getInventory().addItem(cloneBook);
            }
            else if(currItem.getType().equals(Material.COOKED_CHICKEN))
            {
                ItemStack cloneBook = currItem.clone();
                List<Component> loreList = cloneBook.lore();
                loreList.clear();
                cloneBook.lore(loreList);
                cloneBook.setAmount(num);
                player.getInventory().addItem(cloneBook);
                PointManager.AddPoint(player, -price * num);
            }
            else
            {
                player.getInventory().addItem(new ItemStack(currItem.getType(), num));
                PointManager.AddPoint(player, -price * num);
            }
        }
        else
        {
            player.sendMessage(Component.text("포인트가 부족합니다!").color(TextColor.color(255, 6, 0)).decorate(TextDecoration.BOLD));
        }
    }

    @EventHandler
    public void onInhancement(PrepareAnvilEvent event)
    {
        int cost = event.getView().getRepairCost();
        if(cost < 1)
        {
            cost *= -1;
        }
        if(cost == 1)
        {
            cost = 1000;
        }
        else if(cost < 12)
        {
            cost *= 5000;
        }
        else
        {
            cost *= 25000;
        }
        if(anvilCostMap.containsKey(event.getView().getTopInventory()))
        {
            playerRepairCheckList.remove((Player) event.getView().getPlayer());
        }
        anvilCostMap.put(event.getView().getTopInventory(), cost);
        event.getView().setRepairCost(0);
        ItemStack firstItem = event.getInventory().getFirstItem();
        ItemStack enchantBook = event.getInventory().getSecondItem();
        if(firstItem == null || enchantBook == null)
        {
            return;
        }
        if(!(firstItem.getItemMeta() instanceof EnchantmentStorageMeta firstEnchantmentStorageMeta) || !(enchantBook.getItemMeta() instanceof EnchantmentStorageMeta secondEnchantmentStorageMeta))
        {
            return;
        }

        secondEnchantmentStorageMeta.getStoredEnchants().forEach((key, value) ->
        {
            if(firstEnchantmentStorageMeta.hasStoredEnchant(key))
            {
                if(firstEnchantmentStorageMeta.getStoredEnchantLevel(key) < value)
                {
                    firstEnchantmentStorageMeta.addStoredEnchant(key, value, true);
                }
            }
            else
            {
                firstEnchantmentStorageMeta.addStoredEnchant(key, value, true);
            }
        });
        ItemStack resultItem = new ItemStack(firstItem);
        resultItem.setItemMeta(firstEnchantmentStorageMeta);
        event.getInventory().setResult(resultItem);
    }
}
