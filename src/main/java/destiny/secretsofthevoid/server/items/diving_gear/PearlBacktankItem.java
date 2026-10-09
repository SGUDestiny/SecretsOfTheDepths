package destiny.secretsofthevoid.server.items.diving_gear;

import destiny.secretsofthevoid.server.helper.IBacktank;
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

import static destiny.secretsofthevoid.server.helper.ItemHelper.*;

public class PearlBacktankItem extends PearlGearItem implements IBacktank {


    public PearlBacktankItem(ArmorMaterial pMaterial, Type pType, Properties pProperties)
    {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * (float)this.getStoredOxygen(stack) / (float)this.getMaxOxygen(stack));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 5592575;
    }

    @Override
    public double getMaxOxygen(ItemStack stack) {
        if(stack.getTag() != null && stack.getTag().contains(MAX_OXYGEN))
            return stack.getTag().getDouble(MAX_OXYGEN);
        else return 0;
    }

    @Override
    public void setMaxOxygen(ItemStack stack, double oxygen) {
        if(stack.getTag() != null)
            stack.getTag().putDouble(MAX_OXYGEN, oxygen);
        else stack.getOrCreateTag().putDouble(MAX_OXYGEN, oxygen);
    }

    @Override
    public double getStoredOxygen(ItemStack stack) {
        if(stack.getTag() != null && stack.getTag().contains(STORED_OXYGEN))
            return stack.getTag().getDouble(STORED_OXYGEN);
        else return 0;
    }

    @Override
    public void setStoredOxygen(ItemStack stack, double oxygen) {
        if(stack.getTag() != null)
            stack.getTag().putDouble(STORED_OXYGEN, oxygen);
        else stack.getOrCreateTag().putDouble(STORED_OXYGEN, oxygen);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if (stack.getTag() != null && stack.getTag().getDouble(MAX_OXYGEN) != 180) {
            stack.getOrCreateTag().putDouble(MAX_OXYGEN, 180);
            stack.getOrCreateTag().putDouble(STORED_OXYGEN, 180);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> components, TooltipFlag flag) {
        if(!stack.getTag().isEmpty()) {
            MutableComponent oxygen = Component.translatable("tooltip.secretsofthevoid.backtank_oxygen").withStyle(ChatFormatting.BLUE);

            oxygen.append(Component.literal(Math.round(getStoredOxygen(stack)) + "").withStyle(ChatFormatting.BLUE));
            oxygen.append(Component.literal(" / ")).withStyle(ChatFormatting.BLUE);
            oxygen.append(Component.literal("" + Math.round(getMaxOxygen(stack))).withStyle(ChatFormatting.BLUE));

            components.add(oxygen);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
