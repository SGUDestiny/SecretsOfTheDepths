package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.client.gui.SpelunkeryTableScreen;
import destiny.secretsofthevoid.server.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static destiny.secretsofthevoid.client.gui.GUIAncientAlphabet.ALPHABET_SPELUNKING;

@Mixin(SpelunkeryTableScreen.class)
public class SpelunkeryTableScreenMixin
{
    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("TAIL"), remap = true)
    public void render(GuiGraphics guiGraphics, int x, int y, float partialTick, CallbackInfo ci)
    {
        renderAlphabet(guiGraphics);
    }

    public void renderAlphabet(GuiGraphics guiGraphics)
    {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().player.getInventory().hasAnyMatching(predicate -> predicate.is(ItemInit.ANCIENT_ALPHABET.get()))) {
            int width = guiGraphics.guiWidth();
            int height = guiGraphics.guiHeight();
            int leftPos = (width - 208) / 2;
            int topPos = (height - 256) / 2;

            int xPos = leftPos + 170;
            int yPos = topPos + 130;
            guiGraphics.pose().pushPose();
            guiGraphics.blit(ALPHABET_SPELUNKING, xPos + 38, yPos - 120, 0, 0, 80, 149);
            guiGraphics.pose().popPose();
        }
    }

}
