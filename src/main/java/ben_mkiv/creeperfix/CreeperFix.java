package ben_mkiv.creeperfix;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.item.PaintingEntity;
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
    public static final String VERSION = "1.3";


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

            if(entity instanceof ItemFrameEntity || entity instanceof PaintingEntity) {
                if (Config.GENERAL.ProtectBlocks.get())
                    protectedEntities.add(entity);
            }
            else if(entity instanceof ItemEntity){
                if (Config.GENERAL.ProtectItems.get())
                    protectedEntities.add(entity);
            }
            else if(entity instanceof PlayerEntity) {
                if (Config.GENERAL.ProtectPlayers.get())
                    protectedEntities.add(entity);
            }
            else if(entity instanceof LivingEntity){
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