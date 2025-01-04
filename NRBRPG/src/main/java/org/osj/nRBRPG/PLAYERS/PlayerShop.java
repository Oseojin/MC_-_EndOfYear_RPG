package org.osj.nRBRPG.PLAYERS;

import io.lumine.mythic.bukkit.events.MythicMobInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.osj.nRBRPG.MANAGER.InventoryManager;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;

public class PlayerShop implements Listener
{
    //PlayerInteraction 사용해서 MythicMob 확인하고 종류에 따라 상점 열기
    @EventHandler
    public void onInteractNPC(MythicMobInteractEvent event)
    {
        Player player = event.getPlayer();
        if(event.getActiveMob().getType().getDisplayName().get().contains("자재 상인"))
        {
            if(event.getActiveMob().getType().getDisplayName().get().contains("_1"))
            {
                player.openInventory(InventoryManager.makeStructureShop(0));
            }
            else if(event.getActiveMob().getType().getDisplayName().get().contains("_2"))
            {
                player.openInventory(InventoryManager.makeStructureShop(1));
            }
            else if(event.getActiveMob().getType().getDisplayName().get().contains("_3"))
            {
                player.openInventory(InventoryManager.makeStructureShop(2));
            }
        }
        else if(event.getActiveMob().getType().getDisplayName().get().contains("게이트 광물 상인"))
        {
            player.openInventory(InventoryManager.makeGateMineralShop());
        }
        else if(event.getActiveMob().getType().getDisplayName().get().contains("일반 상인"))
        {
            player.openInventory(InventoryManager.makeNormalShop());
        }
        else if(event.getActiveMob().getType().getDisplayName().get().contains("야생 이동"))
        {
            player.teleport(SpawnLocationManager.wildSpawnLoc);
        }
    }
}
