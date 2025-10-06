package destiny.secretsofthevoid.blocks.blockentity;

import destiny.secretsofthevoid.init.BlockEntitiesInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SuspiciousBlockEntity extends BrushableBlockEntity {
    public SuspiciousBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntitiesInit.SUSPICIOUS_BLOCK.get();
    }
}
