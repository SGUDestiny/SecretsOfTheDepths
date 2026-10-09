package destiny.secretsofthevoid.server.blocks.blockentity;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import destiny.secretsofthevoid.server.blocks.OxygenVentBlock;
import destiny.secretsofthevoid.server.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OxygenVentBlockEntity extends BlockEntity {
    private static final double PARTICLE_DIST = 120 * 120;

    private int soundTime = 0;
    public OxygenVentBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.OXYGEN_VENT.get(), pos, state);
    }

    public static void particleTick(Level level, BlockPos pos, BlockState state, OxygenVentBlockEntity blockEntity) {
        Player player = AlexsCaves.PROXY.getClientSidePlayer();
        if (player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > PARTICLE_DIST || level.random.nextBoolean()) {
            return;
        }
        if (!state.getValue(OxygenVentBlock.SPAWNING_PARTICLES)) {
            return;
        }
        ParticleOptions particle = ParticleTypes.BUBBLE;

        float x = (level.random.nextFloat() - 0.5F) * 0.25F;
        float z = (level.random.nextFloat() - 0.5F) * 0.25F;
        level.addAlwaysVisibleParticle(particle, true, pos.getX() + 0.5F + x, pos.getY() + 1.0F, pos.getZ() + 0.5F + z, x * 0.3F, 0.03F + level.random.nextFloat() * 3F, z * 0.3F);

        x = (level.random.nextFloat() - 0.5F) * 0.25F;
        z = (level.random.nextFloat() - 0.5F) * 0.25F;
        level.addAlwaysVisibleParticle(particle, true, pos.getX() + 0.5F + x, pos.getY() + 1.0F, pos.getZ() + 0.5F + z, x * 0.3F, 0.03F + level.random.nextFloat() * 3F, z * 0.3F);

        x = (level.random.nextFloat() - 0.5F) * 0.25F;
        z = (level.random.nextFloat() - 0.5F) * 0.25F;
        level.addAlwaysVisibleParticle(particle, true, pos.getX() + 0.5F + x, pos.getY() + 1.0F, pos.getZ() + 0.5F + z, x * 0.3F, 0.03F + level.random.nextFloat() * 3F, z * 0.3F);

        if(blockEntity.soundTime-- <= 0){
            blockEntity.soundTime = level.getRandom().nextInt(10) + 30;
            boolean underwater = !state.getFluidState().isEmpty() || !level.getBlockState(pos.above()).getFluidState().isEmpty();
            level.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, underwater ? ACSoundRegistry.GEOTHERMAL_VENT_BUBBLE_UNDERWATER.get() : ACSoundRegistry.GEOTHERMAL_VENT_BUBBLE.get(), SoundSource.BLOCKS, underwater ? 2.5F : 1.5F, level.random.nextFloat() * 0.4F + 0.8F, false);
        }
    }
}
