package destiny.secretsofthevoid.server.blocks;

import com.mojang.datafixers.util.Pair;
import destiny.secretsofthevoid.server.helper.IBacktank;
import destiny.secretsofthevoid.server.init.BlockInit;
import destiny.secretsofthevoid.server.init.CapabilitiesInit;
import destiny.secretsofthevoid.server.init.SoundInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.github.alexmodguy.alexscaves.server.misc.ACMath;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

    public class PressureDrainBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE_NORTH = ACMath.buildShape(
            Block.box(4, 1, 6, 12, 16, 10),
            Block.box(7, 5, 10, 9, 7, 12),
            Block.box(5, 3, 11, 11, 9, 12),
            Block.box(3, 11, 1, 13, 13, 11),
            Block.box(9, 10, 2, 12, 15, 5),
            Block.box(4, 10, 2, 7, 15, 5),
            Block.box(10, 9, 3, 11, 10, 4),
            Block.box(5, 9, 3, 6, 10, 4),
            Block.box(5, 15, 3, 11, 16, 4),
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(5, 1, 1, 11, 2, 6)
    );
    private static final VoxelShape SHAPE_SOUTH = ACMath.buildShape(
            Block.box(4, 1, 6, 12, 16, 10),
            Block.box(7, 5, 4, 9, 7, 6),
            Block.box(5, 3, 4, 11, 9, 5),
            Block.box(3, 11, 5, 13, 13, 15),
            Block.box(4, 10, 11, 7, 15, 14),
            Block.box(9, 10, 11, 12, 15, 14),
            Block.box(5, 9, 12, 6, 10, 13),
            Block.box(10, 9, 12, 11, 10, 13),
            Block.box(5, 15, 12, 11, 16, 13),
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(5, 1, 10, 11, 2, 15)
    );
    private static final VoxelShape SHAPE_EAST = ACMath.buildShape(
            Block.box(6, 1, 4, 10, 16, 12),
            Block.box(4, 5, 7, 6, 7, 9),
            Block.box(4, 3, 5, 5, 9, 11),
            Block.box(5, 11, 3, 15, 13, 13),
            Block.box(11, 10, 9, 14, 15, 12),
            Block.box(11, 10, 4, 14, 15, 7),
            Block.box(12, 9, 10, 13, 10, 11),
            Block.box(12, 9, 5, 13, 10, 6),
            Block.box(12, 15, 5, 13, 16, 11),
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(10, 1, 5, 15, 2, 11)
    );
    private static final VoxelShape SHAPE_WEST = ACMath.buildShape(
            Block.box(6, 1, 4, 10, 16, 12),
            Block.box(10, 5, 7, 12, 7, 9),
            Block.box(11, 3, 5, 12, 9, 11),
            Block.box(1, 11, 3, 11, 13, 13),
            Block.box(2, 10, 4, 5, 15, 7),
            Block.box(2, 10, 9, 5, 15, 12),
            Block.box(3, 9, 5, 4, 10, 6),
            Block.box(3, 9, 10, 4, 10, 11),
            Block.box(3, 15, 5, 4, 16, 11),
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(1, 1, 5, 6, 2, 11)
    );
    public PressureDrainBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH));
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return SHAPE_NORTH;
            case SOUTH:
                return SHAPE_SOUTH;
            case WEST:
                return SHAPE_WEST;
            case EAST:
                return SHAPE_EAST;
            default:
                return SHAPE_NORTH;
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockState(pos.below()).getBlock() == BlockInit.OXYGEN_VENT.get() && level.getBlockState(pos.below()).getValue(OxygenVentBlock.DRAINING_OXYGEN)) {
            player.getCapability(CapabilitiesInit.DIVING).ifPresent(cap -> {
                List<Pair<ItemStack, IBacktank>> sortedTanks = cap.getEquipmentBacktank(player, Comparator.comparing(airTank -> airTank.getSecond().getMaxOxygen(airTank.getFirst())));
                for (Pair<ItemStack, IBacktank> airTank : sortedTanks) {
                    ItemStack stack = airTank.getFirst();
                    IBacktank tank = airTank.getSecond();

                    tank.setStoredOxygen(stack, tank.getMaxOxygen(stack));

                    level.playLocalSound(pos, SoundInit.BACKTANK_REFILL.get(), SoundSource.BLOCKS, 1F, 1F, true);
                }
            });
        }
        return InteractionResult.SUCCESS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(WATERLOGGED, FACING);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        if (state.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(state, direction, state1, levelAccessor, blockPos, blockPos1);
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {

    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter getter, List<Component> components, TooltipFlag flag) {
        MutableComponent efficiency = Component.translatable("tooltip.secretsofthevoid.pressure_drain");

        components.add(efficiency);
    }
}
