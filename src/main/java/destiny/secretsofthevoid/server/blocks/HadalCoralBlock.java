package destiny.secretsofthevoid.server.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class HadalCoralBlock extends Block implements SimpleWaterloggedBlock {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 1, 3);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public HadalCoralBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(STAGE, 1));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(WATERLOGGED, STAGE);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        if (state.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(state, direction, state1, levelAccessor, blockPos, blockPos1);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED,context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected IntegerProperty getStageProperty() {
        return STAGE;
    }

    public int getMaxStage() {
        return 3;
    }

    public int getStage(BlockState pState) {
        return pState.getValue(this.getStageProperty());
    }

    public BlockState getStateForStage(int pAge) {
        return this.defaultBlockState().setValue(this.getStageProperty(), pAge).setValue(WATERLOGGED, true);
    }

    public boolean isRandomlyTicking(BlockState pState) {
        return !this.isMaxStage(pState);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pState.getValue(WATERLOGGED)) {
            int i = this.getStage(pState);
            if (i < this.getMaxStage()) {
                float f = getGrowthSpeed(this, pLevel, pPos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, this.getStateForStage(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }

    }

    protected static float getGrowthSpeed(Block pBlock, BlockGetter pLevel, BlockPos pPos) {
        float f = 1.0F;

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 1;

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pPos.north();
        BlockPos blockpos2 = pPos.south();
        BlockPos blockpos3 = pPos.west();
        BlockPos blockpos4 = pPos.east();
        boolean flag = pLevel.getBlockState(blockpos3).is(pBlock) || pLevel.getBlockState(blockpos4).is(pBlock);
        boolean flag1 = pLevel.getBlockState(blockpos1).is(pBlock) || pLevel.getBlockState(blockpos2).is(pBlock);
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = pLevel.getBlockState(blockpos3.north()).is(pBlock) || pLevel.getBlockState(blockpos4.north()).is(pBlock) || pLevel.getBlockState(blockpos4.south()).is(pBlock) || pLevel.getBlockState(blockpos3.south()).is(pBlock);
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    public final boolean isMaxStage(BlockState pState) {
        return this.getStage(pState) >= this.getMaxStage();
    }
}
