package destiny.secretsofthevoid.items;

import destiny.secretsofthevoid.init.NetworkInit;
import destiny.secretsofthevoid.network.packets.OpenGUIAlphabetPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemAncientAlphabet extends Item
{

    public ItemAncientAlphabet(Properties properties)
    {
        super(properties);
    }
    // Should open a texture on RMB; If present in hotbar, adds a texture to Spelunkery table GUI on the right side
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        level.playSound(player, player.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.BLOCKS, 1.0F, 1.0F + (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F);

        ItemStack itemStackIn = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStackIn);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            NetworkInit.sendTo(serverPlayer, new OpenGUIAlphabetPacket());
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, itemStackIn);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag)
    {
        tooltip.add(Component.translatable("tooltip.secretsofthevoid.ancient_alphabet_line_1"));
        tooltip.add(Component.translatable("tooltip.secretsofthevoid.ancient_alphabet_line_2")
                .withStyle(ChatFormatting.DARK_GRAY)
                .withStyle(ChatFormatting.ITALIC)
        );
        tooltip.add(Component.translatable("tooltip.secretsofthevoid.ancient_alphabet_line_3")
                .withStyle(ChatFormatting.DARK_GRAY)
                .withStyle(ChatFormatting.ITALIC));
    }
}
