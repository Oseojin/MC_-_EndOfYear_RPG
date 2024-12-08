package org.osj.nRB_RoglikeRPG;

import org.bukkit.plugin.java.JavaPlugin;

public final class NRB_RoglikeRPG extends JavaPlugin
{
    private static NRB_RoglikeRPG serverInstance;

    @Override
    public void onEnable()
    {
        serverInstance = this;
        // Plugin startup logic

    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }
    private void registerEvent()
    {
        //getServer().getPluginManager().registerEvents(customItemManager, serverInstance);
    }
    private void registerCommand()
    {
        //serverInstance.getServer().getPluginCommand("purchasechunkaccept").setExecutor(new AcceptChunkPurchase());
    }

    public static NRB_RoglikeRPG getServerInstance()
    {
        return serverInstance;
    }
}