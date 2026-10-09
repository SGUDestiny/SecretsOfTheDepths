package destiny.secretsofthevoid.client.gui;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GUIAncientAlphabet extends Screen {
    public static Component TITLE = Component.literal("Ancient Alphabet");

    public static final ResourceLocation ALPHABET = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/gui/ancient_alphabet_gui.png");
    public static final ResourceLocation ALPHABET_SPELUNKING = new ResourceLocation(SecretsOfTheVoid.MODID, "textures/gui/spelunkery_table_ancient_alphabet.png");
    public GUIAncientAlphabet(Component component)
    {
        super(component);
    }

    public GUIAncientAlphabet()
    {
        super(TITLE);
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(guiGraphics);
        guiGraphics.pose().pushPose();
        guiGraphics.blit(ALPHABET, (width - 80) / 2, (height - 149) / 2, 0, 0, 80, 149);
        guiGraphics.pose().popPose();
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
