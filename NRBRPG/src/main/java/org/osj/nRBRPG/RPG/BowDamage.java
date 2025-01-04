package org.osj.nRBRPG.RPG;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class BowDamage implements Listener
{
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event)
    {
        if(!event.getEntityType().equals(EntityType.PLAYER))
        {
            return;
        }
        ItemStack bow = event.getBow();
        CustomStack bowCustom = CustomStack.byItemStack(bow);
        if(bowCustom == null)
        {
            return;
        }
        List<Component> loreList = bowCustom.getItemStack().lore();
        int damage = Integer.parseInt(((TextComponent)loreList.get(0)).content().replace("원거리 공격력: ", ""));

        if(!(event.getProjectile() instanceof Arrow))
        {
            return;
        }

        Arrow arrow = (Arrow) event.getProjectile();
        arrow.setDamage(damage);
    }
}
