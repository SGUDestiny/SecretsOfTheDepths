package destiny.secretsofthevoid.server.worldgen;

import com.github.alexmodguy.alexscaves.server.level.biome.ACBiomeRegistry;
import com.github.alexmodguy.alexscaves.server.level.biome.BiomeSourceAccessor;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_DEEPSLATE_SCORIA_ORE = registerKey("add_deepslate_scoria_ore");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_DEEPSLATE_SCORIA_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(Holder.direct(biomes.getOrThrow(ACBiomeRegistry.ABYSSAL_CHASM).value())),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DEEPSLATE_SCORIA_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(SecretsOfTheVoid.MODID, name));
    }
}
