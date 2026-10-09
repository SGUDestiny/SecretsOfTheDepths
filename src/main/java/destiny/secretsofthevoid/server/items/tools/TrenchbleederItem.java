package destiny.secretsofthevoid.server.items.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

public class TrenchbleederItem extends ShovelItem {
    public int miningMultiplier = 10;

    public TrenchbleederItem(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;

            if (player.isUnderWater()) {
                return super.getDestroySpeed(stack, state) * miningMultiplier;
            }
        }
        return super.getDestroySpeed(stack, state);
    }
}
