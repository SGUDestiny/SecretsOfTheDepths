package destiny.secretsofthevoid.server.helper;

import net.minecraft.world.item.ItemStack;

public interface IBacktank
{
    double getMaxOxygen(ItemStack stack);
    void setMaxOxygen(ItemStack stack, double oxygen);

    double getStoredOxygen(ItemStack stack);
    void setStoredOxygen(ItemStack stack, double oxygen);
}
