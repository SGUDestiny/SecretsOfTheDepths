package destiny.secretsofthevoid.server.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.dedicated.Settings;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SuspiciousBlock extends BrushableBlock {
    public SuspiciousBlock(Block pTurnsInto, BlockBehaviour.Properties pProperties, SoundEvent pBrushSound, SoundEvent pBrushCompletedSound) {
        super(pTurnsInto, pProperties, pBrushSound, pBrushCompletedSound);
    }

    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BrushableBlockEntity(pPos, pState);
    }
}
