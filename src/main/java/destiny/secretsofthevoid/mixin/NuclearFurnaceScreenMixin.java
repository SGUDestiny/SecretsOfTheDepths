package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.client.gui.NuclearFurnaceScreen;
import com.github.alexmodguy.alexscaves.server.inventory.NuclearFurnaceMenu;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NuclearFurnaceScreen.class)
public abstract class NuclearFurnaceScreenMixin extends AbstractContainerScreen<NuclearFurnaceMenu> {
    @Unique
    private static final ResourceLocation TEXTURE = new ResourceLocation("alexscaves", "textures/gui/nuclear_furnace.png");
    @Unique
    private static final ResourceLocation TEXTURE_GAMMA = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/gui/nuclear_furnace_gamma.png");

    public NuclearFurnaceScreenMixin(NuclearFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

/*    @Inject(method = "renderBg", at = @At(value = "RETURN"))
    private void renderGammaFissionProgress(GuiGraphics guiGraphics, float f, int x, int y, CallbackInfo ci) {
        int i = this.leftPos;
        int j = this.topPos;

        float fissionAmount = this.menu.getFissionScale();
        if (fissionAmount > 0.0F) {
            int fissionPixels = (int)Math.ceil((double)(14.0F * fissionAmount));
            guiGraphics.blit(TEXTURE, i + 68, j + 36 + (14 - fissionPixels), 176, 14 - fissionPixels, 14, fissionPixels);
        }
    }*/
}
