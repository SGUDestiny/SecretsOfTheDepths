package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.block.AcidBlock;
import destiny.secretsofthevoid.server.registry.TagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AcidBlock.class)
public class AcidBlockMixin {
    @Inject(method = "entityInside", at = @At(value = "HEAD"), cancellable = true)
    private void entityInside(BlockState blockState, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity.invulnerableTime > 0) {
            ci.cancel();
        }

        if (entity instanceof ItemEntity itemEntity && itemEntity.getItem().is(TagRegistry.RESISTS_ACID)) {
            ci.cancel();
        }
    }
}
