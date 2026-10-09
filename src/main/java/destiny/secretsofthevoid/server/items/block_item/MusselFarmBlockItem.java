package destiny.secretsofthevoid.server.items.block_item;

import destiny.secretsofthevoid.server.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MusselFarmBlockItem extends BlockItem {
    public MusselFarmBlockItem(Properties pProperties) {
        super(BlockRegistry.MUSSEL_FARM.get(), pProperties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        return isOnBlock(pContext.getLevel(), pContext.getClickedPos(), pContext.getClickedFace().getOpposite());
    }

    public boolean isOnBlock(Level pLevel, BlockPos pPos, Direction blockDirection) {
        Block parentBlock = pLevel.getBlockState(pPos.relative(blockDirection)).getBlock();
        return !parentBlock.equals(Blocks.AIR);
    }
}
