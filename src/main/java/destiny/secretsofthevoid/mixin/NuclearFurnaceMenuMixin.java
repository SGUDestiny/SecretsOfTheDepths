package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.inventory.NuclearFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NuclearFurnaceMenu.class)
public class NuclearFurnaceMenuMixin
{
//	@Final
//	@Shadow private ContainerData data;
//
//	@Inject(method = "getFissionScale()F", at = @At("HEAD"), cancellable = true, remap = false)
//	public void dontStaticTime(CallbackInfoReturnable<Float> cir)
//	{
//		boolean isGamma = data.get(5) == 1;
//		cir.setReturnValue(data.get(2)/ (isGamma ? 6400 * 0.4f : 6400 * 0.2f));
//	}

//	@Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;)V", at = @At("RETURN"))
//	public void overrideDataAccess(int id, Inventory inventory, CallbackInfo ci)
//	{
//		this(id, inventory, new SimpleContainer(5), new SimpleContainerData(5));
//	}
}
