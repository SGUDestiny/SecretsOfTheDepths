package destiny.secretsofthevoid.server;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = SecretsOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue NUCLEAR_FURNACE_DEBUFF = BUILDER
            .comment("Should Nuclear Furnace's smelting capacity be halved when Alex's Caves smelt anything config option is set to true?")
            .comment("Default: false")
            .define("nuclear_furnace_debuff", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean nuclearFurnaceDebuff;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) return;

        nuclearFurnaceDebuff = NUCLEAR_FURNACE_DEBUFF.get();
    }
}