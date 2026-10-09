package destiny.secretsofthevoid.server.helper;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemHelper {
    public static final String STORED_OXYGEN = "storedOxygen";
    public static final String MAX_OXYGEN = "maxOxygen";
    public static final String OXYGEN_EFFICIENCY = "maskEfficiency";
    public static final String SWIMMING_SPEED = "swimmingSpeed";

    public static  <T extends Item & IMask> ItemStack newMask(T item, double maskEfficiency) {
        ItemStack stack = new ItemStack(item);
        item.setMaskEfficiency(stack, maskEfficiency);

        return stack;
    }

    public static  <T extends Item & IBacktank> ItemStack newBacktank(T item, double maxOxygen) {
        ItemStack stack = new ItemStack(item);
        item.setMaxOxygen(stack, maxOxygen);
        item.setStoredOxygen(stack, maxOxygen);

        return stack;
    }

    public static  <T extends Item & ILegwear> ItemStack newLegwear(T item, double swimmingSpeed) {
        ItemStack stack = new ItemStack(item);
        item.setSwimmingSpeed(stack, swimmingSpeed);

        return stack;
    }

    public static  <T extends Item & IFlippers> ItemStack newFlippers(T item, double swimmingSpeed) {
        ItemStack stack = new ItemStack(item);
        item.setSwimmingSpeed(stack, swimmingSpeed);

        return stack;
    }
}
