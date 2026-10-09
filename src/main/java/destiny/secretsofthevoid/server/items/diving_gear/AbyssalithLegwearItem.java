package destiny.secretsofthevoid.server.items.diving_gear;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import destiny.secretsofthevoid.server.helper.ILegwear;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;

import static destiny.secretsofthevoid.server.helper.ItemHelper.SWIMMING_SPEED;

public class AbyssalithLegwearItem extends AbyssalithGearItem implements ILegwear {
    private static final EnumMap<Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266744_) -> {
        p_266744_.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        p_266744_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        p_266744_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        p_266744_.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

    public AbyssalithLegwearItem(ArmorMaterial pMaterial, Type pType, Properties pProperties)
    {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        if(this.getType().getSlot().equals(slot))
        {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(this.getType());
            builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double) this.getDefense(), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double) this.getToughness(), AttributeModifier.Operation.ADDITION));
            if (this.knockbackResistance > 0)
            {
                builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double) this.knockbackResistance, AttributeModifier.Operation.ADDITION));
            }
            builder.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, "Swimming speed", this.getSwimmingSpeed(stack), AttributeModifier.Operation.MULTIPLY_TOTAL));
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Walking speed", -(this.getSwimmingSpeed(stack)/10), AttributeModifier.Operation.MULTIPLY_TOTAL));

            return builder.build();
        }
        else return ImmutableMultimap.of();
    }

    @Override
    public double getSwimmingSpeed(ItemStack stack)
    {
        if(stack.getTag() != null && stack.getTag().contains(SWIMMING_SPEED))
            return stack.getTag().getDouble(SWIMMING_SPEED);
        else return 0;
    }

    @Override
    public void setSwimmingSpeed(ItemStack stack, double swimmingSpeed)
    {
        if(stack.getTag() != null)
            stack.getTag().putDouble(SWIMMING_SPEED, swimmingSpeed);
        else stack.getOrCreateTag().putDouble(SWIMMING_SPEED, swimmingSpeed);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if (stack.getTag() != null && stack.getTag().getDouble(SWIMMING_SPEED) != 0.5) {
            stack.getOrCreateTag().putDouble(SWIMMING_SPEED, 0.5);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> components, TooltipFlag flag)
    {
        if(!stack.getTag().isEmpty()) {
            MutableComponent speed = Component.translatable("tooltip.secretsofthevoid.flippers_speed").withStyle(ChatFormatting.BLUE);
            speed.append(Component.literal(getSwimmingSpeed(stack) + "").withStyle(ChatFormatting.BLUE));

            components.add(speed);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
