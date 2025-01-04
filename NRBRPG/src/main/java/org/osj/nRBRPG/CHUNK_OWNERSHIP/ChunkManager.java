package org.osj.nRBRPG.CHUNK_OWNERSHIP;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.osj.nRBRPG.MANAGER.WorldManager;
import org.osj.nRBRPG.MESSAGE.MessageManager;
import org.osj.nRBRPG.NRBRPG;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ChunkManager implements Listener
{
    public static HashMap<Player, Chunk> currentPlayerChunk = new HashMap<>();
    FileConfiguration chunkConfig = NRBRPG.getConfigManager().getConfig("chunkownership");
    private final HashMap<UUID, List<Long>> chunkMasterDataMap = new HashMap<>();
    public static HashMap<UUID, List<String>> chunkAllowedDataMap = new HashMap<>();
    private static int defaultY = 151;

    public ChunkManager()
    {
        if(chunkConfig.getConfigurationSection("chunks.master.") != null)
        {
            List<String> configUUIDKeyList = chunkConfig.getConfigurationSection("chunks.master.").getKeys(true).stream().toList();
            configUUIDKeyList.forEach((key) -> {
                chunkMasterDataMap.put(UUID.fromString(key), chunkConfig.getLongList("chunks.master." + key));
            });
        }
        if(chunkConfig.getConfigurationSection("chunks.allow.") != null)
        {
            List<String> configFriendKeyList = chunkConfig.getConfigurationSection("chunks.allow.").getKeys(true).stream().toList();
            configFriendKeyList.forEach((key) -> {
                chunkAllowedDataMap.put(UUID.fromString(key), chunkConfig.getStringList("chunks.allow." + key));
            });
        }
    }

    @EventHandler
    public void onPlayerEnterChunk(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Chunk chunk = player.getChunk();

        if(!player.getWorld().getName().equals(WorldManager.lobbyWorld))
        {
            return;
        }
        if(!currentPlayerChunk.containsKey(player))
        {
            currentPlayerChunk.put(player, chunk);
        }
        if(currentPlayerChunk.get(player).equals(chunk))
        {
            return;
        }
        if(!isOwnerless(chunk.getChunkKey()))
        {
            if(whosChunk(chunk.getChunkKey()).equals(whosChunk(currentPlayerChunk.get(player).getChunkKey())))
            {
                currentPlayerChunk.put(player, chunk);
                return;
            }

            Player masterPlayer = NRBRPG.getServerInstance().getServer().getPlayer(whosChunk(chunk.getChunkKey()));
            if(masterPlayer == null)
            {
                OfflinePlayer masterOfflinePlayer = NRBRPG.getServerInstance().getServer().getOfflinePlayer(whosChunk(chunk.getChunkKey()));
                MessageManager.SendTitle(player, masterOfflinePlayer.getName(), TextColor.color(0, 255, 0), "의 땅", TextColor.color(255, 255 ,255));
            }
            else
            {
                MessageManager.SendTitle(player, masterPlayer.getName(), TextColor.color(0, 255, 0), "의 땅", TextColor.color(255, 255 ,255));
            }

        }
        currentPlayerChunk.put(player, chunk);
    }

    public Chunk getMyChunk(UUID uuid)
    {
        if(!chunkMasterDataMap.containsKey(uuid))
        {
            return null;
        }
        return Bukkit.getWorld(WorldManager.lobbyWorld).getChunkAt(chunkMasterDataMap.get(uuid).get(0));
    }

    public void addMyChunk(UUID uuid, Chunk chunk)
    {
        boolean isOwnerless = isOwnerless(chunk.getChunkKey());
        if(isOwnerless)
        {
            if(chunkMasterDataMap.get(uuid) == null)
            {
                List<Long> newChunkList = new LinkedList<>();
                newChunkList.add(chunk.getChunkKey());
                chunkMasterDataMap.put(uuid, newChunkList);
            }
            else
            {
                chunkMasterDataMap.get(uuid).add(chunk.getChunkKey());
            }

            chunkConfig.set("chunks.master." + uuid, chunkMasterDataMap.get(uuid));
            NRBRPG.getConfigManager().saveConfig("chunkownership");
        }
    }

    public void removeMyChunk(UUID uuid, Chunk chunk)
    {
        chunkMasterDataMap.get(uuid).remove(chunk.getChunkKey());
        chunkConfig.set("chunks.master." + uuid, chunkMasterDataMap.get(uuid));
        NRBRPG.getConfigManager().saveConfig("chunkownership");
    }

    public void addFriendChunk(UUID friend, UUID master)
    {
        Player player = NRBRPG.getServerInstance().getServer().getPlayer(master);
        if(chunkAllowedDataMap.get(friend) == null)
        {
            List<String> newFriendList = new LinkedList<>();
            newFriendList.add(master.toString());
            chunkAllowedDataMap.put(friend, newFriendList);
        }
        else if(chunkAllowedDataMap.get(friend).contains(master.toString()))
        {
            MessageManager.SendChatContent(player, "이미 허용된 플레이어 입니다.", TextColor.color(255, 0, 0));
            return;
        }
        else
        {
            chunkAllowedDataMap.get(friend).add(master.toString());
        }

        chunkConfig.set("chunks.allow." + friend, chunkAllowedDataMap.get(friend));
        NRBRPG.getConfigManager().saveConfig("chunkownership");
    }

    public void removeFriendChunk(UUID friend, UUID master)
    {
        Player player = NRBRPG.getServerInstance().getServer().getPlayer(master);
        if(chunkAllowedDataMap.get(friend) == null || !chunkAllowedDataMap.get(friend).contains(master.toString()))
        {
            MessageManager.SendChatContent(player, "추가되어 있지 않은 플레이어입니다!", TextColor.color(255, 0, 0));
            return;
        }

        chunkAllowedDataMap.get(friend).remove(master.toString());
        chunkConfig.set("chunks.allow." + friend, chunkAllowedDataMap.get(friend));
        NRBRPG.getConfigManager().saveConfig("chunkownership");
    }

    public boolean isContainMasterMap(UUID uuid)
    {
        return chunkMasterDataMap.containsKey(uuid);
    }
    public boolean isMyChunk(UUID uuid, Chunk chunk)
    {
        return chunkMasterDataMap.containsKey(uuid) && chunkMasterDataMap.get(uuid).contains(chunk.getChunkKey());
    }

    public boolean isMyFriendChunk(UUID friendUUID, UUID masterUUID)
    {
        if(masterUUID == null)
        {
            return false;
        }
        return chunkAllowedDataMap.containsKey(friendUUID) && chunkAllowedDataMap.get(friendUUID).contains(masterUUID.toString());
    }

    public boolean isOwnerless(Long chunkKey)
    {
        for(UUID key : chunkMasterDataMap.keySet())
        {
            if(chunkMasterDataMap.get(key).contains(chunkKey))
            {
                return false;
            }
        }
        return true;
    }
    public UUID whosChunk(Long chunkKey)
    {
        for(UUID key : chunkMasterDataMap.keySet())
        {
            if(chunkMasterDataMap.get(key).contains(chunkKey))
            {
                return key;
            }
        }
        return null;
    }
    public boolean canInteractChunk(Player player, Chunk chunk)
    {
        UUID uuid = player.getUniqueId();
        if(player.getWorld().getName().equals(WorldManager.lobbyWorld))
        {
            if(isMyChunk(uuid, chunk))
            {
                return true;
            }
            else if(isMyFriendChunk(uuid, whosChunk(chunk.getChunkKey())))
            {
                return true;
            }
        }
        return false;
    }
}
