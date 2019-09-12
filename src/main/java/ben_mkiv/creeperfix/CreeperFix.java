package ben_mkiv.creeperfix;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = CreeperFix.MOD_ID)
@Mod(value = CreeperFix.MOD_ID)
public class CreeperFix {

    public static final String MOD_ID = "creeperfix";
    public static final String MOD_NAME = "CreeperFix";
    public static final String VERSION = "1.1";


    public CreeperFix(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.spec);
    }

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public static void onDetonate(ExplosionEvent.Detonate event) {
        if(!(event.getExplosion().getExplosivePlacedBy() instanceof CreeperEntity))
            return;

        if(Config.GENERAL.ProtectBlocks.get())
            event.getAffectedBlocks().clear();

        ArrayList<Entity> protectedEntities = new ArrayList<>();

        for (Entity entity : event.getAffectedEntities()) {
            if (Config.GENERAL.ProtectItems.get() && entity instanceof ItemEntity) {
                protectedEntities.add(entity);
                continue;
            }

            if (Config.GENERAL.ProtectPlayers.get() && entity instanceof PlayerEntity) {
                protectedEntities.add(entity);
                continue;
            }

            if(entity instanceof MobEntity){
                if(Config.GENERAL.ProtectAnimals.get() && entity.getType().getClassification().getPeacefulCreature()){
                    protectedEntities.add(entity);
                    continue;
                }

                if(Config.GENERAL.ProtectMobs.get() && !entity.getType().getClassification().getPeacefulCreature()) {
                    protectedEntities.add(entity);
                }
            }
        }

        event.getAffectedEntities().removeAll(protectedEntities);
    }

}
