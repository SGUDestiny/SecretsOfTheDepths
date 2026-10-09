package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import destiny.secretsofthevoid.server.ServerConfig;
import destiny.secretsofthevoid.server.network.packets.UpdateFurnaceItemPacket;
import destiny.secretsofthevoid.server.registry.NetworkRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NuclearFurnaceBlockEntity.class)
public abstract class NuclearFurnaceBlockEntityMixin {
    @Shadow
    protected NonNullList<ItemStack> items;

    @Shadow protected abstract void syncWithClient();

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

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void setItem(int slot, ItemStack itemStack)
    {
        NuclearFurnaceBlockEntity blockEntity = ((NuclearFurnaceBlockEntity)(Object)this);
        this.items.set(slot, itemStack);
        if (itemStack.getCount() > blockEntity.getMaxStackSize()) {
            itemStack.setCount(blockEntity.getMaxStackSize());
        }

        BlockPos pos = blockEntity.getBlockPos();

        blockEntity.setChanged();
        syncWithClient();
        NetworkRegistry.sendToTracking(blockEntity, new UpdateFurnaceItemPacket(pos, slot, itemStack));
    }
}
