package me.bigjaymalcolm.wgregiontitles;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.*;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import me.bigjaymalcolm.wgregiontitles.regionevents.RegionEventsListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private RegionEventsListener _listener;
    private static WorldGuardPlugin s_worldGuard;

    public static final Flag GREETING_TITLE_FLAG = new StringFlag("greeting-title", "");
    public static final Flag GREETING_SUBTITLE_FLAG = new StringFlag("greeting-subtitle", "");
    public static final Flag FAREWELL_TITLE_FLAG = new StringFlag("farewell-title", "");
    public static final Flag FAREWELL_SUBTITLE_FLAG = new StringFlag("farewell-subtitle", "");

    @Override
    public void onLoad()
    {
        s_worldGuard = getWGPlugin();

        if (s_worldGuard == null)
        {
            getLogger().warning("Could not find World Guard, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        FlagRegistry registry = s_worldGuard.getFlagRegistry();

        try
        {
            registry.register(GREETING_TITLE_FLAG);
            registry.register(GREETING_SUBTITLE_FLAG);
            registry.register(FAREWELL_TITLE_FLAG);
            registry.register(FAREWELL_SUBTITLE_FLAG);
        }
        catch (FlagConflictException e)
        {
            getLogger().warning("Failed to register custom flags.");
        }

        getLogger().info("WGRegionTitles has been loaded.");
    }

    @Override
    public void onEnable()
    {
        _listener = new RegionEventsListener(this, s_worldGuard);

        getServer().getPluginManager().registerEvents(_listener, s_worldGuard);

        getLogger().info("WGRegionTitles has been enabled.");
    }

    @Override
    public void onDisable()
    {
        getLogger().info("WGRegionTitles has been disabled.");
    }

    private WorldGuardPlugin getWGPlugin()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin))
        {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }
}