package org.osj.nRBRPG.PLAYERS;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDeath implements Listener
{
    public static HashMap<UUID, Integer> deathPlayerReviveMap = new HashMap<>();

    @EventHandler
    public void onRevive(PlayerInteractAtEntityEvent event)
    {
        if(!event.getRightClicked().getType().equals(EntityType.PLAYER))
        {
            return;
        }

        Player player = event.getPlayer();
        Player deathPlayer = (Player) event.getRightClicked();
        if(deathPlayerReviveMap.containsKey(deathPlayer.getUniqueId()))
        {
            int progress = deathPlayerReviveMap.get(deathPlayer.getUniqueId());
            progress += 5;
            Component reviveProgressComponent = Component.text(progress + "/100").color(TextColor.color(0,255,0));
            player.sendActionBar(reviveProgressComponent);
            deathPlayer.sendActionBar(reviveProgressComponent);
            deathPlayer.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1f, 1f);
            player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_AMETHYST_BLOCK_BREAK, 1f, 1f);

            if(progress >= 100)
            {
                deathPlayer.clearActivePotionEffects();
                double deathPlayerMaxHealth = deathPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                deathPlayer.setHealth(deathPlayerMaxHealth / 10.0);
                deathPlayerReviveMap.remove(deathPlayer.getUniqueId());
                deathPlayer.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
                player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
                return;
            }
            deathPlayerReviveMap.put(deathPlayer.getUniqueId(), progress);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event)
    {
        if(event.getEntityType().equals(EntityType.PLAYER) && deathPlayerReviveMap.containsKey(event.getEntity().getUniqueId()))
        {
            if(event.getCause().equals(EntityDamageEvent.DamageCause.VOID))
            {
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if(deathPlayerReviveMap.containsKey(player.getUniqueId()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if(deathPlayerReviveMap.containsKey(player.getUniqueId()))
        {
            KillPlayer(player);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        if(deathPlayerReviveMap.containsKey(player.getUniqueId()))
        {
            deathPlayerReviveMap.remove(event.getEntity().getUniqueId());
            return;
        }
        event.setCancelled(true);
        player.setHealth(1);
        deathPlayerReviveMap.put(player.getUniqueId(), 0);
        PotionEffect deathBlindEffect = new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 0, false, true);
        PotionEffect deathGlowEffect = new PotionEffect(PotionEffectType.GLOWING, PotionEffect.INFINITE_DURATION, 0, false, true);
        player.clearActivePotionEffects();
        player.addPotionEffect(deathBlindEffect);
        player.addPotionEffect(deathGlowEffect);

        Component deathComponent = Component.text("사망하였습니다... 서버를 나가면 포기한 것으로 간주합니다.").color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD);
        player.sendMessage(deathComponent);
        Component forgiveMessage = Component.text("[포기]").hoverEvent(HoverEvent.showText(Component.text("생존을 포기하고 돌아갑니다... (인벤세이브)")))
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/forgive")).color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD);
        player.sendMessage(forgiveMessage);
    }

    public static void KillPlayer(Player player)
    {
        player.damage(10000, DamageSource.builder(DamageType.OUT_OF_WORLD).build());
    }

    @EventHandler
    public void onPlayerAttackPlayer(PrePlayerAttackEntityEvent event)
    {
        if(deathPlayerReviveMap.containsKey(event.getPlayer().getUniqueId()))
        {
            event.setCancelled(true);
        }
    }
}
