package destiny.secretsofthevoid.server.worldgen.feature;

import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.mojang.serialization.Codec;
import destiny.secretsofthevoid.server.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class OxygenVentFeature extends Feature<NoneFeatureConfiguration> {
    OxygenVentFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos.MutableBlockPos ventBottom = new BlockPos.MutableBlockPos();
        ventBottom.set(context.origin());
        while (!level.getBlockState(ventBottom).getFluidState().isEmpty() && ventBottom.getY() > level.getMinBuildHeight()) {
            ventBottom.move(0, -1, 0);
        }
        if (level.getBlockState(ventBottom.below()).equals(ACBlockRegistry.MUCK.get().defaultBlockState())) {
            drawVent(level, ventBottom.above().immutable());
            return true;
        }
        return false;
    }

    private static void drawVent(WorldGenLevel level, BlockPos ventBottom) {
        ventBottom = ventBottom.below().offset(0, -1, 0);
        level.setBlock(ventBottom.north(), Blocks.DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(ventBottom.south(), Blocks.DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(ventBottom.east(), Blocks.DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(ventBottom.west(), Blocks.DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(ventBottom.below(), Blocks.DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(ventBottom, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
        level.setBlock(ventBottom.above(), BlockInit.OXYGEN_VENT.get().defaultBlockState(), 3);

        ventBottom = ventBottom.offset(0, 1, 0);
        level.setBlock(ventBottom.north(), Blocks.DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(ventBottom.south(), Blocks.DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(ventBottom.east(), Blocks.DEEPSLATE.defaultBlockState(), 3);
        level.setBlock(ventBottom.west(), Blocks.DEEPSLATE.defaultBlockState(), 3);
    }
}
