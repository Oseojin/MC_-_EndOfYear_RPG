package org.osj.nRBRPG.DATA_MANAGEMENT;

import org.bukkit.configuration.file.FileConfiguration;
import org.osj.nRBRPG.NRBRPG;

import java.util.HashMap;

public class ConfigManager
{
    private final String path = NRBRPG.getServerInstance().getDataFolder().getAbsolutePath();

    private HashMap<String, ConfigMaker> configSet = new HashMap<>();


    // config 전체 재시작
    public ConfigManager()
    {
        configSet.put("chunkownership", new ConfigMaker(path, "chunkownership.yml"));
        configSet.put("point", new ConfigMaker(path, "point.yml"));
        configSet.put("coin", new ConfigMaker(path, "coin.yml"));
        loadSettings();
        saveConfigs();
    }


    // config 전체 리로드
    public void reloadConfigs()
    {
        for(String key : configSet.keySet())
        {
            NRBRPG.getServerInstance().getLogger().info(key);
            configSet.get(key).reloadConfig();
        }
    }


    // 특정 config 리로드
    public void reloadConfig(String fileName)
    {
        configSet.get(fileName).reloadConfig();
    }


    // config 전체 저장
    public void saveConfigs()
    {
        for(String key : configSet.keySet())
        {
            configSet.get(key).saveConfig();
        }
    }


    // 특정 config 저장
    public void saveConfig(String fileName)
    {
        configSet.get(fileName).saveConfig();
    }


    // config 가져오기
    public FileConfiguration getConfig(String fileName)
    {
        return configSet.get(fileName).getConfig();
    }

    // config 기본 세팅
    public void loadSettings()
    {
        FileConfiguration chunkownershipConfig = getConfig("chunkownership");
        FileConfiguration pointConfig = getConfig("point");
        FileConfiguration coinConfig = getConfig("coin");

        chunkownershipConfig.options().copyDefaults(true);
        pointConfig.options().copyDefaults(true);
        coinConfig.options().copyDefaults(true);
    }
}
