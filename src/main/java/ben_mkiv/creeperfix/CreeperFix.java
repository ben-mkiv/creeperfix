package ben_mkiv.creeperfix;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod(
        modid = CreeperFix.MOD_ID,
        name = CreeperFix.MOD_NAME,
        version = CreeperFix.VERSION
)
public class CreeperFix {

    public static final String MOD_ID = "creeperfix";
    public static final String MOD_NAME = "CreeperFix";
    public static final String VERSION = "1.2";

    public static boolean ProtectBlocks = true;
    public static boolean ProtectItems = true;
    public static boolean ProtectPlayers = false;
    public static boolean ProtectAnimals = true;
    public static boolean ProtectMobs = false;


    @Mod.Instance(MOD_ID)
    public static CreeperFix INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        Config.preInit();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        ProtectBlocks = Config.getConfig().getCategory("general").get("protectblocks").getBoolean();
        ProtectItems = Config.getConfig().getCategory("general").get("protectitems").getBoolean();
        ProtectPlayers = Config.getConfig().getCategory("general").get("protectplayers").getBoolean();
        ProtectAnimals = Config.getConfig().getCategory("general").get("protectanimals").getBoolean();
        ProtectMobs = Config.getConfig().getCategory("general").get("protectmobs").getBoolean();
    }

    static class EventHandler {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void onDetonate(ExplosionEvent.Detonate event) {
            if (!(event.getExplosion().getExplosivePlacedBy() instanceof EntityCreeper))
                return;

            if (ProtectBlocks)
                event.getAffectedBlocks().clear();

            ArrayList<Entity> protectedEntities = new ArrayList<>();

            for (Entity entity : event.getAffectedEntities()) {
                if(entity instanceof EntityItemFrame || entity instanceof EntityPainting) {
                    if (ProtectBlocks)
                        protectedEntities.add(entity);
                }
                else if(entity instanceof EntityItem) {
                    if (ProtectItems)
                        protectedEntities.add(entity);
                }
                else if(entity instanceof EntityPlayer) {
                    if (ProtectPlayers)
                        protectedEntities.add(entity);
                }
                else if (entity instanceof EntityLivingBase) {
                    if (ProtectAnimals && !entity.isCreatureType(EnumCreatureType.MONSTER, false)) {
                        protectedEntities.add(entity);
                        continue;
                    }

                    if (ProtectMobs && entity.isCreatureType(EnumCreatureType.MONSTER, false)) {
                        protectedEntities.add(entity);
                    }
                }
            }

            event.getAffectedEntities().removeAll(protectedEntities);
        }
    }

}
