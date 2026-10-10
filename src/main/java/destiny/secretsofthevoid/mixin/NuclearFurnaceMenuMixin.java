package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.inventory.NuclearFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NuclearFurnaceMenu.class)
public class NuclearFurnaceMenuMixin
{
	@Shadow @Final private ContainerData data;

//	@Inject(method = "getFissionScale()F", at = @At("HEAD"), cancellable = true, remap = false)
//	public void dontStaticTime(CallbackInfoReturnable<Float> cir)
//	{
//		boolean isGamma = data.get(5) == 1;
//		cir.setReturnValue(data.get(2)/ (isGamma ? 6400 * 0.4f : 6400 * 0.2f));
//	}
}
