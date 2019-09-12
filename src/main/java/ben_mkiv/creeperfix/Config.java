package ben_mkiv.creeperfix;

/**
 * @author ben_mkiv, based on MinecraftByExample Templates
 */
import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.server.permission.PermissionAPI;

public class Config extends PermissionAPI {
    private static Configuration config = null;

    public static void preInit(){
        File configFile = new File(Loader.instance().getConfigDir(), CreeperFix.MOD_ID + ".cfg");
        config = new Configuration(configFile);

        syncConfig(true);
    }

    public static void clientPreInit() {
        MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
    }

    public static Configuration getConfig() {
        return config;
    }

    private static void syncConfig(boolean loadConfigFromFile) {
        if (loadConfigFromFile)
            config.load();

        Property protectBlocks = config.get("general", "protectblocks", true);
        protectBlocks.setLanguageKey("gui.config.general.protectblocks");
        protectBlocks.setComment("Protect Blocks");

        Property protectItems = config.get("general", "protectitems", true);
        protectItems.setLanguageKey("gui.config.general.protectitems");
        protectItems.setComment("Protect Items");

        Property protectPlayers = config.get("general", "protectplayers", false);
        protectPlayers.setLanguageKey("gui.config.general.protectplayers");
        protectPlayers.setComment("Protect Players");

        Property protectAnimals = config.get("general", "protectanimals", true);
        protectAnimals.setLanguageKey("gui.config.general.protectanimals");
        protectAnimals.setComment("Protect Animals");

        Property protectMobs = config.get("general", "protectmobs", true);
        protectMobs.setLanguageKey("gui.config.general.protectmobs");
        protectMobs.setComment("Protect hostile mobs");


        if (config.hasChanged())
            config.save();
    }

    public static class ConfigEventHandler{
        @SubscribeEvent(priority = EventPriority.NORMAL)
        public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event){
            if (!event.getModID().equals(CreeperFix.MOD_ID))
                return;

            syncConfig(false);
        }
    }
}