package destiny.secretsofthevoid.server.items;

import destiny.secretsofthevoid.server.init.ItemInit;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CalligraphyKitItem extends Item
{
    public CalligraphyKitItem(Properties props)
    {
        super(props);
    }

    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.BOW;
    }

    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count)
    {
        if(entity instanceof Player player) {
            if(isEnchantingTable(player, level)) {
                if (count % 10 == 0) {
                    player.gameEvent(GameEvent.ITEM_INTERACT_START);
                    level.playSound(player, player.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.BLOCKS, 1.0F, 1.0F + (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F);
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
    {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);

        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 60;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        if(!level.isClientSide() && entity instanceof Player player)
        {

            if(isEnchantingTable(player, level))
            {
                player.setItemInHand(player.getUsedItemHand(), new ItemStack(ItemInit.ANCIENT_ALPHABET.get()));
                level.playSound(player, player.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1, 1);
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag)
    {
        tooltip.add(Component.translatable("tooltip.secretsofthevoid.calligraphy_kit_desc"));
    }

    public boolean isEnchantingTable(Player player, Level level){
        Vec3 targetPos = player.position().add(0, 3, 0).add(player.getViewVector(1).multiply(3, 3, 3));
        BlockHitResult result = level.clip(new ClipContext(player.position(), targetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, player));
        boolean isEnchantingTable = level.getBlockState(result.getBlockPos()).getBlock().equals(Blocks.ENCHANTING_TABLE);

        return isEnchantingTable;
    }
}
