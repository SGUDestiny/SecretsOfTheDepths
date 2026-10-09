package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.block.blockentity.AbyssalAltarBlockEntity;
import com.github.alexmodguy.alexscaves.server.entity.ai.DeepOneBarterGoal;
import com.github.alexmodguy.alexscaves.server.entity.living.DeepOneBaseEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.DeepOneEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.DeepOneKnightEntity;
import com.github.alexmodguy.alexscaves.server.entity.living.DeepOneMageEntity;
import destiny.secretsofthevoid.server.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DeepOneBarterGoal.class)
public class DeepBarterGoalMixin
{
    @Shadow(remap = false)
    private DeepOneBaseEntity mob;

    @Inject(method = "canUse()Z", at = @At(value = "RETURN", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void canUseInject(CallbackInfoReturnable<Boolean> cir, LivingEntity target, BlockPos pos)
    {
        DeepOneBarterGoal goal = ((DeepOneBarterGoal) (Object) this);
        if (mob.level() != null && mob.level().getBlockEntity(mob.getLastAltarPos()) instanceof AbyssalAltarBlockEntity altar)
        {
            BlockState sigil = mob.level().getBlockState(altar.getBlockPos().below());
            if(sigil.is(BlockRegistry.ABYSSMARINE_SIGIL_COMMONER.get()))
            {
                if(mob instanceof DeepOneEntity)
                {
                    cir.setReturnValue(true);
                } else {
                    pos = null;
                    cir.setReturnValue(false);
                }
            }
            else if(sigil.is(BlockRegistry.ABYSSMARINE_SIGIL_KNIGHT.get()))
            {
                if(mob instanceof DeepOneKnightEntity)
                {
                    cir.setReturnValue(true);
                } else {
                    pos = null;
                    cir.setReturnValue(false);
                }
            }
            else if(sigil.is(BlockRegistry.ABYSSMARINE_SIGIL_MAGE.get()))
            {
                if(mob instanceof DeepOneMageEntity)
                {
                    cir.setReturnValue(true);
                } else {
                    pos = null;
                    cir.setReturnValue(false);
                }
            }
            else cir.setReturnValue(true);
        }
    }

}
