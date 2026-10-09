package destiny.secretsofthevoid.server.items.diving_gear;

import destiny.secretsofthevoid.server.helper.IMask;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static destiny.secretsofthevoid.server.helper.ItemHelper.OXYGEN_EFFICIENCY;

public class AbyssalithMaskItem extends AbyssalithGearItem implements IMask {
    public AbyssalithMaskItem(ArmorMaterial pMaterial, Type pType, Properties pProperties)
    {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public double getMaskEfficiency(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(OXYGEN_EFFICIENCY))
            return stack.getTag().getDouble(OXYGEN_EFFICIENCY);
        else return 0;
    }

    @Override
    public void setMaskEfficiency(ItemStack stack, double maskEfficiency)
    {
        if(stack.getTag() != null)
            stack.getTag().putDouble(OXYGEN_EFFICIENCY, maskEfficiency);
        else stack.getOrCreateTag().putDouble(OXYGEN_EFFICIENCY, maskEfficiency);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if (stack.getTag() != null && stack.getTag().getDouble(OXYGEN_EFFICIENCY) != 2) {
            stack.getOrCreateTag().putDouble(OXYGEN_EFFICIENCY, 2);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> components, TooltipFlag flag)
    {
        if(!stack.getTag().isEmpty()) {
            MutableComponent efficiency = Component.translatable("tooltip.secretsofthevoid.mask_efficiency").withStyle(ChatFormatting.BLUE);
            efficiency.append(Component.literal(getMaskEfficiency(stack) + "").withStyle(ChatFormatting.BLUE));

            components.add(efficiency);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
