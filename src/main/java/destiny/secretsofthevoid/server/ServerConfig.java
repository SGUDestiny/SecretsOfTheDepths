package destiny.secretsofthevoid.server;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = SecretsOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue DISABLE_NUCLEAR_FURNACE_DEBUFF = BUILDER
            .comment("Disable Nuclear Furnace smelting capacity being halved when Alex's Caves blasting only config option is set to true?")
            .comment("Default: false")
            .define("disable_nuclear_furnace_debuff", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean disableNuclearFurnaceDebuff;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) return;

        disableNuclearFurnaceDebuff = DISABLE_NUCLEAR_FURNACE_DEBUFF.get();
    }
}