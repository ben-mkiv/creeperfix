package ben_mkiv.creeperfix;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Boolean> ProtectBlocks;
        public final ForgeConfigSpec.ConfigValue<Boolean> ProtectItems;
        public final ForgeConfigSpec.ConfigValue<Boolean> ProtectPlayers;
        public final ForgeConfigSpec.ConfigValue<Boolean> ProtectAnimals;
        public final ForgeConfigSpec.ConfigValue<Boolean> ProtectMobs;

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            ProtectBlocks = builder
                    .comment("protect blocks")
                    .translation("config.protectblocks")
                    .define("protectBlocks", true);

            ProtectItems = builder
                    .comment("protect ground items")
                    .translation("config.protectitems")
                    .define("protectItems", true);

            ProtectPlayers = builder
                    .comment("protect blocks")
                    .translation("config.protectplayers")
                    .define("protectPlayers", false);

            ProtectAnimals = builder
                    .comment("protect animals")
                    .translation("config.protectanimals")
                    .define("protectAnimals", true);

            ProtectMobs = builder
                    .comment("protect hostile mobs")
                    .translation("config.protectmobs")
                    .define("protectMobs", false);

            builder.pop();
        }
    }
}