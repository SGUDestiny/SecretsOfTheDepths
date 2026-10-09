package destiny.secretsofthevoid.server.blocks;

import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.MusselBlock;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACMath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MusselFarmBlock extends Block {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty MUSSELS = IntegerProperty.create("mussels", 0, 16);
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final VoxelShape SHAPE_UP_DOWN = ACMath.buildShape(
            Block.box(7, 0, 7, 9, 16, 9)
    );
    public static final VoxelShape SHAPE_NORTH_SOUTH = ACMath.buildShape(
            Block.box(7, 7, 0, 9, 9, 16)
    );
    public static final VoxelShape SHAPE_WEST_EAST = ACMath.buildShape(
            Block.box(0, 7, 7, 16, 9, 9)
    );

    public MusselFarmBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(MUSSELS, 0).setValue(FACING, Direction.UP));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(WATERLOGGED, MUSSELS, FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        switch (pState.getValue(FACING)) {
            case NORTH, SOUTH:
                return SHAPE_NORTH_SOUTH;
            case EAST, WEST:
                return SHAPE_WEST_EAST;
            case UP, DOWN:
                return SHAPE_UP_DOWN;
            default:
                return SHAPE_NORTH_SOUTH;
        }
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        if (!isOnBlock(levelAccessor, blockPos, state)) {
            levelAccessor.destroyBlock(blockPos, true);
        }

        if (state.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(state, direction, state1, levelAccessor, blockPos, blockPos1);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getClickedFace().getOpposite();
        return this.defaultBlockState().setValue(FACING, facing).setValue(WATERLOGGED,context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public boolean isOnBlock(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        Direction blockDirection = pState.getValue(FACING);
        Block parentBlock = pLevel.getBlockState(pPos.relative(blockDirection)).getBlock();
        return !parentBlock.equals(Blocks.AIR);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        int mussels = pState.getValue(MUSSELS);

        if (pState.getBlock() != pNewState.getBlock()) {
            if (mussels > 0) {
                ItemStack musselStack = new ItemStack(ACBlockRegistry.MUSSEL.get(), mussels);
                pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), musselStack));
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        int mussels = pState.getValue(MUSSELS);

        if (stack.getItem() == ACBlockRegistry.MUSSEL.get().asItem()) {

            if (mussels != 16) {
                if (!pLevel.isClientSide) {
                    pLevel.setBlock(pPos, pState.setValue(MUSSELS, mussels + 1), 11);
                }

                pLevel.playSound(null, pPos, SoundEvents.LEASH_KNOT_PLACE, SoundSource.BLOCKS, 1, 1f);

                if (!pPlayer.isCreative()) {
                    stack.shrink(1);
                }

                return InteractionResult.SUCCESS;
            }
        } else if (stack.isEmpty()) {
            if (mussels > 0) {
                if (!pLevel.isClientSide) {
                    pLevel.setBlock(pPos, pState.setValue(MUSSELS, mussels - 1), 11);
                }

                pLevel.playSound(null, pPos, SoundEvents.LEASH_KNOT_BREAK, SoundSource.BLOCKS, 1, 1f);

                pPlayer.addItem(new ItemStack(ACBlockRegistry.MUSSEL.get(), 1));

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int mussels = pState.getValue(MUSSELS);
        if (pState.getValue(WATERLOGGED) && mussels > 0 && mussels != 16) {
            pLevel.setBlock(pPos, pState.setValue(MUSSELS, mussels + 1), 11);
        }
    }
}
