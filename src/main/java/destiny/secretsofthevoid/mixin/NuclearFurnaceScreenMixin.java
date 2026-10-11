package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.client.gui.NuclearFurnaceScreen;
import com.github.alexmodguy.alexscaves.server.inventory.NuclearFurnaceMenu;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.util.IGammaRodHandleFurnace;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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

    @Redirect(method = "renderBg",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 5))
    private void renderGammaFissionProgress(GuiGraphics instance, ResourceLocation pAtlasLocation, int pX, int pY,
                                            int pUOffset, int pVOffset, int pUWidth, int pVHeight)
    {
        IGammaRodHandleFurnace menu = ((IGammaRodHandleFurnace) this.menu);
        boolean isGammaRod = menu.isFissioningGammaRod();
        instance.blit(isGammaRod ? TEXTURE_GAMMA : TEXTURE, pX, pY, pUOffset, pVOffset, pUWidth, pVHeight);
    }
}
