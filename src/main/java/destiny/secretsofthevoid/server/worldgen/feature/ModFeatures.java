package destiny.secretsofthevoid.server.worldgen.feature;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.FEATURES, SecretsOfTheVoid.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> OXYGEN_VENT = DEF_REG.register("oxygen_vent", () -> new OxygenVentFeature(NoneFeatureConfiguration.CODEC));
}
