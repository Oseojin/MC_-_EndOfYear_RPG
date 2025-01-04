package org.osj.nRBRPG.MANAGER;

import io.lumine.mythic.bukkit.MythicBukkit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.osj.nRBRPG.RPG.BossDungeon;
import org.osj.nRBRPG.RPG.Dungeon;

import java.util.HashMap;

public class TranslateManager
{
    public static HashMap<String, String> englishToKoreanMap = new HashMap<>();
    public static HashMap<Dungeon.CONCEPT, Component> dungeonNameMap = new HashMap<>();
    public static HashMap<BossDungeon.TYPE, Component> fixedBossEnterMap = new HashMap<>();

    public TranslateManager()
    {
        englishToKoreanMap.put("polar bear", "북극곰");
        englishToKoreanMap.put("stray", "스트레이");
        englishToKoreanMap.put("zombie", "치킨조키");
        englishToKoreanMap.put("the killer bunny", "살인토끼");
        englishToKoreanMap.put("creeper", "크리퍼");
        englishToKoreanMap.put("cave spider", "동굴거미");
        englishToKoreanMap.put("silverfish", "좀벌레");
        englishToKoreanMap.put("skeleton", "스켈레톤");
        englishToKoreanMap.put("drowned", "드라운드");
        englishToKoreanMap.put("guardian", "가디언");
        englishToKoreanMap.put("pufferfish", "복어");
        englishToKoreanMap.put("husk", "허스크");
        englishToKoreanMap.put("breeze", "브리즈");
        englishToKoreanMap.put("phantom", "팬텀");
        englishToKoreanMap.put("ghast", "가스트");
        englishToKoreanMap.put("zombified piglin", "좀비피글린");
        englishToKoreanMap.put("blaze", "블레이즈");
        englishToKoreanMap.put("zoglin", "조글린");
        englishToKoreanMap.put("hoglin", "호글린");
        englishToKoreanMap.put("magma cube", "마그마큐브");
        englishToKoreanMap.put("piglin", "피글린");
        englishToKoreanMap.put("piglin brute", "난폭한 피글린");
        englishToKoreanMap.put("wither skeleton", "위더스켈레톤");
        englishToKoreanMap.put("pillager", "약탈자");
        englishToKoreanMap.put("ravager", "파괴수");
        englishToKoreanMap.put("iron golem", "철 골렘");
        englishToKoreanMap.put("zombie villager", "좀비 주민");
        englishToKoreanMap.put("illusioner", "환술사");
        englishToKoreanMap.put("vex", "벡스");
        englishToKoreanMap.put("vindicator", "변명자");
        englishToKoreanMap.put("evoker", "소환사");
        englishToKoreanMap.put("wolf", "늑대");
        englishToKoreanMap.put("spider", "거미");
        englishToKoreanMap.put("bogged", "보그드");
        englishToKoreanMap.put("bee", "꿀벌");

        dungeonNameMap.put(Dungeon.CONCEPT.TUNDRA, Component.text("툰드라 게이트").color(TextColor.color(0, 231, 255)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.MINE, Component.text("광산 게이트").color(TextColor.color(255, 241, 0)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.ABYSS, Component.text("심해 게이트").color(TextColor.color(0, 2, 137)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.DESSERT, Component.text("사막 게이트").color(TextColor.color(255, 250, 201)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.NETHER_NORMAL, Component.text("지옥 게이트").color(TextColor.color(255, 6, 0)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.NETHER_FOREST, Component.text("지옥 사이 숲 게이트").color(TextColor.color(0, 145, 91)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.NETHER_FORTRESS, Component.text("지옥 요새 게이트").color(TextColor.color(62, 61, 62)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.VILLAGE, Component.text("몰락한 마을 게이트").color(TextColor.color(160, 92, 52)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.WOODLAND_MANSION, Component.text("산장 게이트").color(TextColor.color(26, 9, 4)).decorate(TextDecoration.BOLD));
        dungeonNameMap.put(Dungeon.CONCEPT.FOREST, Component.text("숲 게이트").color(TextColor.color(68, 255, 13)).decorate(TextDecoration.BOLD));

        fixedBossEnterMap.put(BossDungeon.TYPE.ENDER_DRAGON, Component.text("세계의 끝").color(TextColor.color(110, 0, 111)).decorate(TextDecoration.BOLD));
        fixedBossEnterMap.put(BossDungeon.TYPE.WARDEN, Component.text("감염된 땅").color(TextColor.color(0, 96, 111)).decorate(TextDecoration.BOLD));
        fixedBossEnterMap.put(BossDungeon.TYPE.WITHER, Component.text("지옥 밑바닥").color(TextColor.color(111, 0, 8)).decorate(TextDecoration.BOLD));
    }
}
