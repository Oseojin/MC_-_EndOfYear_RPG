package org.osj.nRBRPG;

import org.bukkit.Chunk;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import org.osj.nRBRPG.CHUNK_OWNERSHIP.AddLandAllow;
import org.osj.nRBRPG.CHUNK_OWNERSHIP.ChunkManager;
import org.osj.nRBRPG.CHUNK_OWNERSHIP.RemoveLandAllow;
import org.osj.nRBRPG.CHUNK_OWNERSHIP.UseLandDocument;
import org.osj.nRBRPG.DATA_MANAGEMENT.ConfigManager;
import org.osj.nRBRPG.ITEMSADDER.CustomItemManager;
import org.osj.nRBRPG.LOBBY.LobbyPreventEvent;
import org.osj.nRBRPG.MANAGER.InventoryManager;
import org.osj.nRBRPG.MANAGER.LoreCommand;
import org.osj.nRBRPG.MANAGER.SpawnLocationManager;
import org.osj.nRBRPG.MANAGER.TranslateManager;
import org.osj.nRBRPG.MESSAGE.PlayerScoreboardManager;
import org.osj.nRBRPG.PLAYERS.*;
import org.osj.nRBRPG.RPG.*;
import org.osj.nRBRPG.WILD.WildPreventEvent;

public final class NRBRPG extends JavaPlugin
{
    private static NRBRPG serverInstance;
    private static ConfigManager configManager;
    private static TranslateManager translateManager;
    private static SpawnLocationManager spawnLocationManager;
    private static ChunkManager chunkManager;
    private static DungeonManager dungeonManager;
    private static PlayerScoreboardManager scoreboardManager;
    private static CustomItemManager customItemManager;
    private static BossDungeon bossDungeon;
    private static InventoryManager inventoryManager;

    @Override
    public void onEnable()
    {
        serverInstance = this;
        configManager = new ConfigManager();
        translateManager = new TranslateManager();
        spawnLocationManager = new SpawnLocationManager();
        chunkManager = new ChunkManager();
        dungeonManager = new DungeonManager();
        customItemManager = new CustomItemManager();
        scoreboardManager = new PlayerScoreboardManager();
        bossDungeon = new BossDungeon();
        inventoryManager = new InventoryManager();
        // Plugin startup logic

        registerEvent();
        registerCommand();

        //dungeonManager.startGateGenerate();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }
    private void registerEvent()
    {
        getServer().getPluginManager().registerEvents(chunkManager, serverInstance);
        getServer().getPluginManager().registerEvents(dungeonManager, serverInstance);
        getServer().getPluginManager().registerEvents(customItemManager, serverInstance);
        getServer().getPluginManager().registerEvents(scoreboardManager, serverInstance);
        getServer().getPluginManager().registerEvents(bossDungeon, serverInstance);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), serverInstance);
        getServer().getPluginManager().registerEvents(new WildPreventEvent(), serverInstance);
        getServer().getPluginManager().registerEvents(new DungeonPreventEvent(), serverInstance);
        getServer().getPluginManager().registerEvents(new BossDungeonPreventEvent(), serverInstance);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), serverInstance);
        getServer().getPluginManager().registerEvents(new LobbyPreventEvent(), serverInstance);
        getServer().getPluginManager().registerEvents(new UseLandDocument(), serverInstance);
        getServer().getPluginManager().registerEvents(new PreventPVP(), serverInstance);
        getServer().getPluginManager().registerEvents(new BowDamage(), serverInstance);
        getServer().getPluginManager().registerEvents(new InvClickEvent(), serverInstance);
        getServer().getPluginManager().registerEvents(new PlayerShop(), serverInstance);
    }
    private void registerCommand()
    {
        serverInstance.getServer().getPluginCommand("forgive").setExecutor(new ForgiveCommand());
        serverInstance.getServer().getPluginCommand("dungeongen").setExecutor(new DungeonCommand());
        serverInstance.getServer().getPluginCommand("addallow").setExecutor(new AddLandAllow());
        serverInstance.getServer().getPluginCommand("removeallow").setExecutor(new RemoveLandAllow());
        serverInstance.getServer().getPluginCommand("lore").setExecutor(new LoreCommand());
    }

    public static NRBRPG getServerInstance()
    {
        return serverInstance;
    }
    public static ConfigManager getConfigManager()
    {
        return configManager;
    }
    public static ChunkManager getChunkManager()
    {
        return chunkManager;
    }
    public static BossDungeon getBossDungeon()
    {
        return bossDungeon;
    }
}
