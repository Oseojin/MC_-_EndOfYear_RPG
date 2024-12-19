package org.osj.nRBRPG.MESSAGE;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.osj.nRBRPG.MANAGER.TranslateManager;
import org.osj.nRBRPG.NRBRPG;
import org.osj.nRBRPG.RPG.BossDungeon;
import org.osj.nRBRPG.RPG.Dungeon;

public class MessageManager
{
    public static void SendChatForm(Player player)
    {
        Component messageForm = Component.text("============================================").color(TextColor.color(0, 0,0)).decorate(TextDecoration.BOLD);
        player.sendMessage(messageForm);
    }
    public static void SendChatContent(Player player, String content, TextColor textColor)
    {
        Component messageContent = Component.text(content).color(textColor).decorate(TextDecoration.BOLD);
        player.sendMessage(messageContent);

    }
    public static void SendFixedBossClearTitle(Player player)
    {
        Component mainComponent = Component.text("위대한 업적").color(TextColor.color(136, 0, 9)).decorate(TextDecoration.BOLD);
        Component subComponent = Component.text("세계를 위협하는 재앙 중 하나를 제거했습니다.");
        Component coment = Component.text("60초 뒤 귀환합니다.").color(TextColor.color(111, 0, 8));
        player.sendMessage(coment);
        player.showTitle(Title.title(mainComponent, subComponent, Title.DEFAULT_TIMES));
    }
    public static void SendDungeonClearTitle(Player player)
    {
        Component mainComponent = Component.text("던전 클리어!!").color(TextColor.color(255, 232, 0)).decorate(TextDecoration.BOLD);
        Component subComponent = Component.text("인벤토리에 보상이 지급되었습니다.");
        Component coment = Component.text("60초 뒤 귀환합니다.").color(TextColor.color(255, 241, 0));
        player.sendMessage(coment);
        player.showTitle(Title.title(mainComponent, subComponent, Title.DEFAULT_TIMES));
    }
    public static void SendActiveBossTitle(Player player)
    {
        Component mainComponent = Component.text("보스 등장!").color(TextColor.color(255, 5, 10)).decorate(TextDecoration.BOLD);
        Component subComponent = Component.text("");
        player.showTitle(Title.title(mainComponent, subComponent, Title.DEFAULT_TIMES));
    }
    public static void SendRoomClearTitle(Player player)
    {
        Component mainComponent = Component.text("클리어!!").color(TextColor.color(255, 232, 0)).decorate(TextDecoration.BOLD);
        Component subComponent = Component.text("다음 방으로 넘어가세요.");
        player.showTitle(Title.title(mainComponent, subComponent, Title.DEFAULT_TIMES));
    }
    public static void SendActiveMonsterTitle(Player player)
    {
        Component mainComponent = Component.text("모든 몬스터를 처치하세요!").color(TextColor.color(255, 255, 255)).decorate(TextDecoration.BOLD);
        Component subComponent = Component.text("");
        player.showTitle(Title.title(mainComponent, subComponent, Title.DEFAULT_TIMES));
    }
    public static void SendFixedBossTitle(Player player, BossDungeon.TYPE type)
    {
        Component subContent = Component.text("Level: ??").color(TextColor.color(255, 0, 17));
        player.showTitle(Title.title(TranslateManager.fixedBossEnterMap.get(type), subContent, Title.DEFAULT_TIMES));
    }
    public static void SendEnterDungeonTitle(Player player, Dungeon.CONCEPT concept, int lv)
    {
        Component subContent = Component.text("Level: " + lv).color(TextColor.color(147, 147, 147));
        player.showTitle(Title.title(TranslateManager.dungeonNameMap.get(concept), subContent, Title.DEFAULT_TIMES));
    }
    public static void SendTitle(Player player, String title, TextColor titleTextColor, String subTitle, TextColor subTextColor)
    {
        Component titleContent = Component.text(title).color(titleTextColor).decorate(TextDecoration.BOLD);
        Component subContent = Component.text(subTitle).color(subTextColor);
        player.showTitle(Title.title(titleContent, subContent, Title.DEFAULT_TIMES));
    }
    public static void SendTitle(Player player, String title, TextColor titleTextColor, String subTitle, TextColor subTextColor, Title.Times titleTime)
    {
        Component titleContent = Component.text(title).color(titleTextColor).decorate(TextDecoration.BOLD);
        Component subContent = Component.text(subTitle).color(subTextColor);
        player.showTitle(Title.title(titleContent, subContent, titleTime));
    }
    public static void SendTitle(Player player, String title, TextColor titleTextColor, Component subtitle, Title.Times titleTime)
    {
        Component titleContent = Component.text(title).color(titleTextColor).decorate(TextDecoration.BOLD);;
        player.showTitle(Title.title(titleContent, subtitle, titleTime));
    }
    public static void SendNoticeChat(String content, TextColor textColor)
    {
        Component messageForm = Component.text("============================================").color(TextColor.color(0, 0,0)).decorate(TextDecoration.BOLD);
        NRBRPG.getServerInstance().getServer().sendMessage(messageForm);
        Component messageContent = Component.text(content).color(textColor).decorate(TextDecoration.BOLD);
        NRBRPG.getServerInstance().getServer().sendMessage(messageContent);
        NRBRPG.getServerInstance().getServer().sendMessage(messageForm);
    }
}
