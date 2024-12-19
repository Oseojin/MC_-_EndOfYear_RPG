package org.osj.nRBRPG.RPG;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.osj.nRBRPG.ITEMSADDER.CustomItemManager;
import org.osj.nRBRPG.MANAGER.TranslateManager;
import org.osj.nRBRPG.MANAGER.WorldManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterSpawnManager
{
    public static ActiveMob SpawnBoss(Dungeon.CONCEPT concept, Location spawnLoc, int lv)
    {
        List<String> mobIDList = new ArrayList<>();
        switch (concept)
        {
            case TUNDRA:
                mobIDList.add("BossGiantPolarBear");
                break;
            case MINE:
                mobIDList.add("BossSilverFish");
                break;
            case ABYSS:
                mobIDList.add("BossElderGuardian");
                break;
            case DESSERT:
                mobIDList.add("BossSkeletonHorse");
                break;
            case NETHER_NORMAL:
                mobIDList.add("BossGhast");
                break;
            case NETHER_FOREST:
                mobIDList.add("BossZoglin");
                mobIDList.add("BossHoglin");
                break;
            case NETHER_FORTRESS:
                mobIDList.add("BossGiantWitherSkeleton");
                break;
            case VILLAGE:
                mobIDList.add("BossGiantIronGolem");
                break;
            case WOODLAND_MANSION:
                mobIDList.add("BossEvoker");
                break;
            case FOREST:
                mobIDList.add("BossBee");
                break;
        }
        Random random = new Random();
        int randNum = random.nextInt(0, mobIDList.size());
        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(mobIDList.get(randNum)).orElse(null);
        ActiveMob boss = mob.spawn(BukkitAdapter.adapt(spawnLoc), lv);

        LivingEntity livingEntity = (LivingEntity) boss.getEntity().getBukkitEntity();
        if(mobIDList.get(randNum).equals("SkeletonHorse"))
        {
            LivingEntity horse = (LivingEntity) Bukkit.getWorld(WorldManager.dungeonWorld).spawnEntity(spawnLoc, EntityType.SKELETON_HORSE);
            horse.addPassenger(livingEntity);
        }


        List<ItemStack> armorList = CustomItemManager.getArmorOnLv(lv);
        livingEntity.getEquipment().setHelmet(armorList.get(0));
        livingEntity.getEquipment().setChestplate(armorList.get(1));
        livingEntity.getEquipment().setLeggings(armorList.get(2));
        livingEntity.getEquipment().setBoots(armorList.get(3));

        return boss;
    }

    public static List<LivingEntity> SpawnMonster(Dungeon.CONCEPT concept, Location spawnLoc, int lv)
    {
        List<EntityType> entityTypeList = new ArrayList<>();
        switch (concept)
        {
            case TUNDRA:
                entityTypeList.add(EntityType.STRAY);
                entityTypeList.add(EntityType.ZOMBIE);
                entityTypeList.add(EntityType.RABBIT);
                break;
            case MINE:
                entityTypeList.add(EntityType.CREEPER);
                entityTypeList.add(EntityType.CAVE_SPIDER);
                entityTypeList.add(EntityType.SILVERFISH);
                entityTypeList.add(EntityType.SKELETON);
                break;
            case ABYSS:
                entityTypeList.add(EntityType.DROWNED);
                entityTypeList.add(EntityType.GUARDIAN);
                entityTypeList.add(EntityType.PUFFERFISH);
                break;
            case DESSERT:
                entityTypeList.add(EntityType.HUSK);
                entityTypeList.add(EntityType.BREEZE);
                entityTypeList.add(EntityType.PHANTOM);
                break;
            case NETHER_NORMAL:
                entityTypeList.add(EntityType.ZOMBIFIED_PIGLIN);
                entityTypeList.add(EntityType.BLAZE);
                entityTypeList.add(EntityType.GHAST);
                break;
            case NETHER_FOREST:
                entityTypeList.add(EntityType.ZOGLIN);
                entityTypeList.add(EntityType.HOGLIN);
                entityTypeList.add(EntityType.MAGMA_CUBE);
                break;
            case NETHER_FORTRESS:
                entityTypeList.add(EntityType.PIGLIN);
                entityTypeList.add(EntityType.PIGLIN_BRUTE);
                entityTypeList.add(EntityType.SKELETON);
                break;
            case VILLAGE:
                entityTypeList.add(EntityType.PILLAGER);
                entityTypeList.add(EntityType.RAVAGER);
                entityTypeList.add(EntityType.ZOMBIE_VILLAGER);
                break;
            case WOODLAND_MANSION:
                entityTypeList.add(EntityType.ILLUSIONER);
                entityTypeList.add(EntityType.VEX);
                entityTypeList.add(EntityType.VINDICATOR);
                break;
            case FOREST:
                entityTypeList.add(EntityType.WOLF);
                entityTypeList.add(EntityType.SPIDER);
                entityTypeList.add(EntityType.BOGGED);
                break;
        }

        Random random = new Random();
        int randNum = random.nextInt(0, entityTypeList.size());
        EntityType entityType = entityTypeList.get(randNum);

        List<LivingEntity> livingEntityList = new ArrayList<>();
        LivingEntity livingEntity = (LivingEntity) Bukkit.getWorld(WorldManager.dungeonWorld).spawnEntity(spawnLoc, entityType);
        livingEntityList.add(livingEntity);

        if(entityType.equals(EntityType.RABBIT))
        {
            ((Rabbit) livingEntity).setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
        }
        else if(entityType.equals(EntityType.WOLF))
        {
            ((Wolf) livingEntity).setAngry(true);
        }
        else if(entityType.equals(EntityType.ZOMBIFIED_PIGLIN))
        {
            ((PigZombie) livingEntity).setAngry(true);
        }
        else if(entityType.equals(EntityType.ZOMBIE))
        {
            ((Ageable)livingEntity).setBaby();
            LivingEntity chicken = (LivingEntity) Bukkit.getWorld(WorldManager.dungeonWorld).spawnEntity(spawnLoc, EntityType.CHICKEN);
            chicken.addPassenger(livingEntity);
            livingEntityList.add(chicken);
        }
        else if(entityType.equals(EntityType.GHAST))
        {
            double size = livingEntity.getAttribute(Attribute.GENERIC_SCALE).getBaseValue();
            livingEntity.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(size / 3);
        }

        int randomLv = random.nextInt(lv-1, lv+2);

        if(randomLv < 1)
        {
            randomLv = 1;
        }
        else if(randomLv > 10)
        {
            randomLv = 10;
        }

        if(randomLv != 1)
        {
            List<ItemStack> armorList = CustomItemManager.getArmorOnLv(randomLv);
            livingEntity.getEquipment().setHelmet(armorList.get(0));
            livingEntity.getEquipment().setChestplate(armorList.get(1));
            livingEntity.getEquipment().setLeggings(armorList.get(2));
            livingEntity.getEquipment().setBoots(armorList.get(3));
        }

        double maxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 0.5 * (randomLv + 1);
        livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        livingEntity.setHealth(maxHealth);

        Component monsterName = Component.text(TranslateManager.englishToKoreanMap.get(livingEntity.getName().toLowerCase()) + " lv." + randomLv);
        livingEntity.customName(monsterName);

        return livingEntityList;
    }

    public static LivingEntity SpawnCreeper(Location spawnLoc, int lv)
    {
        Random random = new Random();
        EntityType entityType = EntityType.CREEPER;

        LivingEntity livingEntity = (LivingEntity) Bukkit.getWorld(WorldManager.dungeonWorld).spawnEntity(spawnLoc, entityType);

        int randomLv = random.nextInt(lv-1, lv+2);

        if(randomLv < 1)
        {
            randomLv = 1;
        }
        else if(randomLv > 10)
        {
            randomLv = 10;
        }

        if(randomLv != 1)
        {
            List<ItemStack> armorList = CustomItemManager.getArmorOnLv(randomLv);
            livingEntity.getEquipment().setHelmet(armorList.get(0));
            livingEntity.getEquipment().setChestplate(armorList.get(1));
            livingEntity.getEquipment().setLeggings(armorList.get(2));
            livingEntity.getEquipment().setBoots(armorList.get(3));
        }

        double maxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 0.5 * (randomLv + 1);
        livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        livingEntity.setHealth(maxHealth);

        Component monsterName = Component.text(TranslateManager.englishToKoreanMap.get(livingEntity.getName().toLowerCase()) + " lv." + randomLv);
        livingEntity.customName(monsterName);

        return livingEntity;
    }
}
