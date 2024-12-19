package org.osj.nRBRPG.ITEMSADDER;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import net.kyori.adventure.inventory.Book;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomItemManager implements Listener
{
    @EventHandler
    public void onCustomItemLoaded(ItemsAdderLoadDataEvent event)
    {

    }

    public static List<ItemStack> getArmorOnLv(int lv)
    {
        List<ItemStack> armorList = new ArrayList<>();
        switch (lv)
        {
            case 1:
                armorList.add(new ItemStack(Material.IRON_HELMET));
                armorList.add(new ItemStack(Material.IRON_CHESTPLATE));
                armorList.add(new ItemStack(Material.IRON_LEGGINGS));
                armorList.add(new ItemStack(Material.IRON_BOOTS));
                break;
            case 2:
                armorList.add(new ItemStack(Material.DIAMOND_HELMET));
                armorList.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
                armorList.add(new ItemStack(Material.DIAMOND_LEGGINGS));
                armorList.add(new ItemStack(Material.DIAMOND_BOOTS));
                break;
            case 3:
                armorList.add(new ItemStack(Material.NETHERITE_HELMET));
                armorList.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
                armorList.add(new ItemStack(Material.NETHERITE_LEGGINGS));
                armorList.add(new ItemStack(Material.NETHERITE_BOOTS));
                break;
            case 4:
                armorList.add(CustomStack.getInstance("nrb:amethyst_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:amethyst_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:amethyst_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:amethyst_boots").getItemStack());
                break;
            case 5:
                armorList.add(CustomStack.getInstance("nrb:emerald_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:emerald_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:emerald_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:emerald_boots").getItemStack());
                break;
            case 6:
                armorList.add(CustomStack.getInstance("nrb:ruby_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:ruby_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:ruby_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:ruby_boots").getItemStack());
                break;
            case 7:
                armorList.add(CustomStack.getInstance("nrb:obsidian_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:obsidian_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:obsidian_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:obsidian_boots").getItemStack());
                break;
            case 8:
                armorList.add(CustomStack.getInstance("nrb:carbon_fiber_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:carbon_fiber_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:carbon_fiber_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:carbon_fiber_boots").getItemStack());
                break;
            case 9:
                armorList.add(CustomStack.getInstance("nrb:mithril_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:mithril_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:mithril_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:mithril_boots").getItemStack());
                break;
            case 10:
                armorList.add(CustomStack.getInstance("nrb:adamantium_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:adamantium_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:adamantium_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:adamantium_boots").getItemStack());
                break;
        }

        return armorList;
    }

    public static ItemStack SetDropItem(int lv)
    {
        CustomStack dropItem = null;

        switch (lv)
        {
            case 1:
                return new ItemStack(Material.DIAMOND, 1);
            case 2:
                return new ItemStack(Material.NETHERITE_SCRAP, 1);
            case 3:
                dropItem = CustomStack.getInstance("nrb:amethyst");
                break;
            case 4:
                dropItem = CustomStack.getInstance("nrb:emerald");
                break;
            case 5:
                dropItem = CustomStack.getInstance("nrb:ruby");
                break;
            case 6:
                dropItem = CustomStack.getInstance("nrb:obsidian");
                break;
            case 7:
                dropItem = CustomStack.getInstance("nrb:carbon_fiber");
                break;
            case 8:
                dropItem = CustomStack.getInstance("nrb:mithril");
                break;
            case 9:
                dropItem = CustomStack.getInstance("nrb:adamantium");
                break;
            case 10:
                return randomEnchant();
        }

        return dropItem.getItemStack();
    }

    public static ItemStack randomEnchant()
    {
        ItemStack enchantBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) enchantBook.getItemMeta();
        Random random = new Random();
        Enchantment enchantment = Enchantment.values()[random.nextInt(0, Enchantment.values().length)];
        int enchantLv = 1;
        if(enchantment.getStartLevel() != enchantment.getMaxLevel())
        {
            enchantLv = random.nextInt(enchantment.getStartLevel(), enchantment.getMaxLevel());
        }
        enchantmentStorageMeta.addStoredEnchant(enchantment, enchantLv, true);
        enchantBook.setItemMeta(enchantmentStorageMeta);

        return enchantBook;
    }

    public static ItemStack randomEnchantMax()
    {
        ItemStack enchantBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) enchantBook.getItemMeta();
        Random random = new Random();
        Enchantment enchantment = Enchantment.values()[random.nextInt(0, Enchantment.values().length)];
        int enchantLv = enchantment.getMaxLevel();
        enchantmentStorageMeta.addStoredEnchant(enchantment, enchantLv, true);
        enchantBook.setItemMeta(enchantmentStorageMeta);

        return enchantBook;
    }
}
