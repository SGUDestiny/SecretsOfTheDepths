package destiny.secretsofthevoid.server.blocks;

import com.github.alexmodguy.alexscaves.server.block.GlowingAbyssmarineBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbyssmarineSigilBlock extends GlowingAbyssmarineBlock {
    public AbyssmarineSigilBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components, TooltipFlag flag) {
        MutableComponent efficiency = Component.translatable("tooltip.secretsofthevoid.abyssmarine_sigil").withStyle(ChatFormatting.GRAY);

        components.add(efficiency);
    }
}
