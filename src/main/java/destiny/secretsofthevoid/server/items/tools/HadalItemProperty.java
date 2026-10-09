package destiny.secretsofthevoid.server.items.tools;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HadalItemProperty implements ClampedItemPropertyFunction {
    @Override
    public float unclampedCall(@NotNull ItemStack stack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
        if (stack.getTag().getBoolean("active")) {
            return 1;
        }
        return 0;
    }
}