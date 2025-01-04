package org.osj.nRBRPG.ITEMSADDER;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.osj.nRBRPG.RPG.BossDungeon;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CustomItemManager implements Listener
{
    public static CustomStack ricecake;
    public static CustomStack green_onion;
    public static CustomStack ricecakesoup;
    @EventHandler
    public void onCustomItemLoaded(ItemsAdderLoadDataEvent event)
    {
        ricecake = CustomStack.getInstance("nrb:ricecake");
        green_onion = CustomStack.getInstance("nrb:green_onion");
        ricecakesoup = CustomStack.getInstance("nrb:ricecakesoup");
    }

    public static List<ItemStack> getArmorOnLv(int lv)
    {
        List<ItemStack> armorList = new ArrayList<>();
        switch (lv)
        {
            case 1:
                armorList.add(new ItemStack(Material.CHAINMAIL_HELMET));
                armorList.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                armorList.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                armorList.add(new ItemStack(Material.CHAINMAIL_BOOTS));
                break;
            case 2:
                armorList.add(new ItemStack(Material.IRON_HELMET));
                armorList.add(new ItemStack(Material.IRON_CHESTPLATE));
                armorList.add(new ItemStack(Material.IRON_LEGGINGS));
                armorList.add(new ItemStack(Material.IRON_BOOTS));
                break;
            case 3:
                armorList.add(new ItemStack(Material.DIAMOND_HELMET));
                armorList.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
                armorList.add(new ItemStack(Material.DIAMOND_LEGGINGS));
                armorList.add(new ItemStack(Material.DIAMOND_BOOTS));
                break;
            case 4:
                armorList.add(new ItemStack(Material.NETHERITE_HELMET));
                armorList.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
                armorList.add(new ItemStack(Material.NETHERITE_LEGGINGS));
                armorList.add(new ItemStack(Material.NETHERITE_BOOTS));
                break;
            case 5:
                armorList.add(CustomStack.getInstance("nrb:amethyst_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:amethyst_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:amethyst_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:amethyst_boots").getItemStack());
                break;
            case 6:
                armorList.add(CustomStack.getInstance("nrb:emerald_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:emerald_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:emerald_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:emerald_boots").getItemStack());
                break;
            case 7:
                armorList.add(CustomStack.getInstance("nrb:ruby_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:ruby_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:ruby_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:ruby_boots").getItemStack());
                break;
            case 8:
                armorList.add(CustomStack.getInstance("nrb:obsidian_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:obsidian_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:obsidian_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:obsidian_boots").getItemStack());
                break;
            case 9:
                armorList.add(CustomStack.getInstance("nrb:carbon_fiber_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:carbon_fiber_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:carbon_fiber_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:carbon_fiber_boots").getItemStack());
                break;
            case 10:
                armorList.add(CustomStack.getInstance("nrb:mithril_helmet").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:mithril_chestplate").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:mithril_leggings").getItemStack());
                armorList.add(CustomStack.getInstance("nrb:mithril_boots").getItemStack());
                break;
        }

        return armorList;
    }

    public static List<ItemStack> SetBossDrop(BossDungeon.TYPE type)
    {
        List<ItemStack> dropItemList = new ArrayList<>();

        ItemStack bundle = new ItemStack(Material.BUNDLE);
        BundleMeta bundleMeta = (BundleMeta) bundle.getItemMeta();
        bundleMeta.addItem(CustomItemManager.randomEnchantMax());
        bundleMeta.addItem(CustomItemManager.randomEnchantMax());
        bundleMeta.addItem(CustomItemManager.randomEnchantMax());
        bundleMeta.addItem(CustomItemManager.randomEnchantMax());
        bundleMeta.addItem(CustomItemManager.randomEnchantMax());
        bundle.setItemMeta(bundleMeta);
        dropItemList.add(bundle);

        ItemStack ricecakesoupStack = ricecakesoup.getItemStack();
        ricecakesoupStack.setAmount(5);
        dropItemList.add(ricecakesoupStack);

        switch (type)
        {
            case ENDER_DRAGON:
                dropItemList.add(new ItemStack(Material.DRAGON_HEAD));
                dropItemList.add(new ItemStack(Material.SHULKER_SHELL, 2));
                dropItemList.add(new ItemStack(Material.DRAGON_BREATH));
                break;
            case WARDEN:
                dropItemList.add(new ItemStack(Material.REINFORCED_DEEPSLATE, 16));
                dropItemList.add(new ItemStack(Material.SCULK_SHRIEKER, 16));
                dropItemList.add(new ItemStack(Material.SCULK_SENSOR, 16));
                dropItemList.add(new ItemStack(Material.SCULK_VEIN, 16));
                dropItemList.add(new ItemStack(Material.ECHO_SHARD, 3));
                break;
            case WITHER:
                dropItemList.add(new ItemStack(Material.NETHER_STAR));
                dropItemList.add(new ItemStack(Material.WITHER_ROSE, 16));
                ItemStack enchantBook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) enchantBook.getItemMeta();
                enchantmentStorageMeta.addStoredEnchant(Enchantment.VANISHING_CURSE, 1, true);
                enchantBook.setItemMeta(enchantmentStorageMeta);
                dropItemList.add(enchantBook);
                dropItemList.add(randomGoatHorn());
                dropItemList.add(randomDisk());
                break;
        }

        Random random = new Random();
        int randNum = random.nextInt(0, 100) + 1;
        if(randNum <= 10)
        {
            dropItemList.add(new ItemStack(Material.ELYTRA));
        }
        else if(randNum <= 10 + 30)
        {
            dropItemList.add(randomMineralLastThree());
        }
        else if(randNum <= 10 + 30 + 30)
        {
            dropItemList.add(randomArmorTemplate());
        }

        return dropItemList;
    }

    public static ItemStack DropRiceCakeMaterial()
    {
        Random random = new Random();
        boolean riceOnion = random.nextBoolean();
        if(riceOnion)
        {
            return ricecake.getItemStack();
        }
        else
        {
            return green_onion.getItemStack();
        }
    }

    public static ItemStack SetDropItem(int lv)
    {
        CustomStack dropItem = null;

        switch (lv)
        {
            case 0:
                return new ItemStack(Material.IRON_INGOT, 1);
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
            case 9, 10:
                dropItem = CustomStack.getInstance("nrb:adamantium");
                break;
        }

        return dropItem.getItemStack();
    }

    private static ItemStack randomArmorTemplate()
    {
        List<Material> armorTemplateList = new LinkedList<>();

        armorTemplateList.add(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE);
        armorTemplateList.add(Material.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE);

        Random random = new Random();

        return new ItemStack(armorTemplateList.get(random.nextInt(0, armorTemplateList.size())));
    }

    private static ItemStack randomMineralLastThree()
    {
        Random random = new Random();
        List<String> mineralList = new LinkedList<>();
        mineralList.add("nrb:carbon_fiber");
        mineralList.add("nrb:mithril");
        mineralList.add("nrb:adamantium");

        ItemStack mineral = CustomStack.getInstance(mineralList.get(random.nextInt(0,mineralList.size()))).getItemStack();
        mineral.setAmount(16);
        return mineral;
    }

    private static ItemStack randomDisk()
    {
        List<Material> diskList = new LinkedList<>();

        diskList.add(Material.MUSIC_DISC_13);
        diskList.add(Material.MUSIC_DISC_CAT);
        diskList.add(Material.MUSIC_DISC_BLOCKS);
        diskList.add(Material.MUSIC_DISC_CHIRP);
        diskList.add(Material.MUSIC_DISC_FAR);
        diskList.add(Material.MUSIC_DISC_MALL);
        diskList.add(Material.MUSIC_DISC_MELLOHI);
        diskList.add(Material.MUSIC_DISC_STAL);
        diskList.add(Material.MUSIC_DISC_STRAD);
        diskList.add(Material.MUSIC_DISC_WARD);
        diskList.add(Material.MUSIC_DISC_11);
        diskList.add(Material.MUSIC_DISC_CREATOR_MUSIC_BOX);
        diskList.add(Material.MUSIC_DISC_WAIT);
        diskList.add(Material.MUSIC_DISC_CREATOR);
        diskList.add(Material.MUSIC_DISC_PRECIPICE);
        diskList.add(Material.MUSIC_DISC_OTHERSIDE);
        diskList.add(Material.MUSIC_DISC_RELIC);
        diskList.add(Material.MUSIC_DISC_5);
        diskList.add(Material.MUSIC_DISC_PIGSTEP);

        Random random = new Random();

        return new ItemStack(diskList.get(random.nextInt(0, diskList.size())));
    }

    private static ItemStack randomGoatHorn()
    {
        ItemStack goatHorn = new ItemStack(Material.GOAT_HORN);
        MusicInstrumentMeta goatMeta = (MusicInstrumentMeta) goatHorn.getItemMeta();
        Random random = new Random();
        goatMeta.setInstrument(MusicInstrument.values().stream().toList().get(random.nextInt(0, MusicInstrument.values().size())));
        goatHorn.setItemMeta(goatMeta);
        return goatHorn;
    }

    public static ItemStack randomEnchant()
    {
        ItemStack enchantBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
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
