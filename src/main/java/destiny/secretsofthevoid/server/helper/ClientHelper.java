package destiny.secretsofthevoid.server.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ClientHelper {
    public static void openGUI(Screen screen) {
        Minecraft.getInstance().setScreen(screen);
    }
}
