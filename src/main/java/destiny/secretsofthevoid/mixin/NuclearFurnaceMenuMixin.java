package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import com.github.alexmodguy.alexscaves.server.inventory.NuclearFurnaceMenu;
import destiny.secretsofthevoid.server.network.packets.UpdateFurnaceMenuPosPacket;
import destiny.secretsofthevoid.server.registry.NetworkRegistry;
import destiny.secretsofthevoid.server.util.IGammaRodHandleFurnace;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NuclearFurnaceMenu.class)
public class NuclearFurnaceMenuMixin implements IGammaRodHandleFurnace
{
	@Mutable
	@Final
	@Shadow private ContainerData data;

	@Shadow @Final private Container container;
	private boolean isFissioningGammaRod;
	private BlockPos furnacePos;

	@Inject(method = "getFissionScale()F", at = @At("HEAD"), cancellable = true, remap = false)
	public void dontStaticTime(CallbackInfoReturnable<Float> cir)
	{
		boolean isGamma = isFissioningGammaRod();
		cir.setReturnValue(data.get(2) / (isGamma ? 6400 * 0.6f : 6400 * 0.2f));
	}

	@Inject(method = "stillValid(Lnet/minecraft/world/entity/player/Player;)Z",
	at = @At("RETURN"))
	public void stillValidInject(Player player, CallbackInfoReturnable<Boolean> cir)
	{
		if(container instanceof NuclearFurnaceBlockEntity blockEntity)
		{
			setFurnacePos(blockEntity.getBlockPos());
			NetworkRegistry.sendToTracking(blockEntity, new UpdateFurnaceMenuPosPacket(blockEntity.getBlockPos()));
		}
	}

	@Override
	public boolean isFissioningGammaRod()
	{
		return isFissioningGammaRod;
	}

	@Override
	public void setFissioningGammaRod(boolean val)
	{
		isFissioningGammaRod = val;
	}

	@Override
	public BlockPos getFurnacePos()
	{
		return furnacePos;
	}

	@Override
	public void setFurnacePos(BlockPos pos)
	{
		furnacePos = pos;
	}
}
