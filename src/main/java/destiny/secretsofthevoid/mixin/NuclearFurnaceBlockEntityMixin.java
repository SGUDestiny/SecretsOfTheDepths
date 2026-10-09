package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import destiny.secretsofthevoid.server.ServerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NuclearFurnaceBlockEntity.class)
public class NuclearFurnaceBlockEntityMixin {
    @Inject(method = "getMaxFissionTime", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getMaxFissionTime(CallbackInfoReturnable<Integer> cir) {
        if (ServerConfig.nuclearFurnaceDebuff) return;

        cir.setReturnValue((int)Math.ceil(6400 * 0.411));
    }

    @Inject(method = "getSpeedReduction", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getSpeedReduction(CallbackInfoReturnable<Float> cir) {
        if (ServerConfig.nuclearFurnaceDebuff) return;

        cir.setReturnValue(0.2f);
    }
}
