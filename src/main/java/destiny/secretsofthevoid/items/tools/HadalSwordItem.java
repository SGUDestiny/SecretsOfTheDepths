package destiny.secretsofthevoid.items.tools;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class HadalSwordItem extends SwordItem {
    public static final String ACTIVE = "active";
    public static final UUID ACTIVE_MULTIPLIER_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB6CF");
    public int damageMultiplier = 2;
    public int speedMultiplier = 2;
    public int knockbackMultiplier = 5;

    public HadalSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level pLevel, Entity entity, int pSlotId, boolean pIsSelected) {
        if (entity instanceof Player player) {
            boolean damageModifierPresent = player.getAttribute(Attributes.ATTACK_DAMAGE).getModifiers().stream().anyMatch(modifier -> modifier.getId().equals(ACTIVE_MULTIPLIER_UUID));
            boolean speedModifierPresent = player.getAttribute(Attributes.ATTACK_SPEED).getModifiers().stream().anyMatch(modifier -> modifier.getId().equals(ACTIVE_MULTIPLIER_UUID));
            boolean knockbackModifierPresent = player.getAttribute(Attributes.ATTACK_KNOCKBACK).getModifiers().stream().anyMatch(modifier -> modifier.getId().equals(ACTIVE_MULTIPLIER_UUID));

            if (player.isUnderWater()) {
                stack.getOrCreateTag().putBoolean(ACTIVE, true);

                if (!damageModifierPresent) {
                    player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(ACTIVE_MULTIPLIER_UUID, "Active Multiplier", damageMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
                if (!speedModifierPresent) {
                    player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(new AttributeModifier(ACTIVE_MULTIPLIER_UUID, "Active Multiplier", speedMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
                if (!knockbackModifierPresent) {
                    player.getAttribute(Attributes.ATTACK_KNOCKBACK).addTransientModifier(new AttributeModifier(ACTIVE_MULTIPLIER_UUID, "Active Multiplier", knockbackMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL));
                }
            } else {
                stack.getOrCreateTag().putBoolean(ACTIVE, false);

                if (damageModifierPresent) {
                    player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ACTIVE_MULTIPLIER_UUID);
                }
                if (speedModifierPresent) {
                    player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(ACTIVE_MULTIPLIER_UUID);
                }
                if (knockbackModifierPresent) {
                    player.getAttribute(Attributes.ATTACK_KNOCKBACK).removeModifier(ACTIVE_MULTIPLIER_UUID);
                }
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
