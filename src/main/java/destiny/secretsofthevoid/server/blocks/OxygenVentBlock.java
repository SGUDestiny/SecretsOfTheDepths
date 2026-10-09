package destiny.secretsofthevoid.server.blocks;

import destiny.secretsofthevoid.server.blocks.blockentity.OxygenVentBlockEntity;
import destiny.secretsofthevoid.server.init.BlockEntitiesInit;
import destiny.secretsofthevoid.server.init.BlockInit;
import destiny.secretsofthevoid.server.items.tools.HadalSwordItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class OxygenVentBlock extends BaseEntityBlock {
    public static final BooleanProperty SPAWNING_PARTICLES = BooleanProperty.create("spawning_particles");
    public static final BooleanProperty DRAINING_OXYGEN = BooleanProperty.create("draining_oxygen");

    public OxygenVentBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SPAWNING_PARTICLES, true).setValue(DRAINING_OXYGEN, false));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        return this.defaultBlockState().setValue(SPAWNING_PARTICLES, isSpawningParticles(blockpos, levelaccessor)).setValue(DRAINING_OXYGEN, isDrainingOxygen(blockpos, levelaccessor));
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        return state.setValue(SPAWNING_PARTICLES, isSpawningParticles(blockPos, levelAccessor)).setValue(DRAINING_OXYGEN, isDrainingOxygen(blockPos, levelAccessor));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPAWNING_PARTICLES, DRAINING_OXYGEN);
    }

    public boolean isSpawningParticles(BlockPos pos, LevelAccessor level) {
        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());

        return (above.isAir() && below.is(Blocks.MAGMA_BLOCK) || !above.blocksMotion() && below.is(Blocks.MAGMA_BLOCK));
    }

    public boolean isDrainingOxygen(BlockPos pos, LevelAccessor level) {
        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());

        return (above.is(BlockInit.PRESSURE_DRAIN.get()) && below.is(Blocks.MAGMA_BLOCK));
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {

    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        if (level.isClientSide) {
            return state.getValue(SPAWNING_PARTICLES) ? createTickerHelper(entityType, BlockEntitiesInit.OXYGEN_VENT.get(), OxygenVentBlockEntity::particleTick) : null;
        } else {
            return null;
        }
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof Player player && state.getValue(SPAWNING_PARTICLES)) {
            player.setAirSupply(Math.min(player.getAirSupply() + 3, player.getMaxAirSupply()));
        } else if (state.getValue(SPAWNING_PARTICLES)) {
            entity.setDeltaMovement(0, 1.5, 0);
        }

        if (entity instanceof ItemEntity itemEntity && itemEntity.getItem().getItem() instanceof HadalSwordItem) {}
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OxygenVentBlockEntity(pos, state);
    }
}
