package destiny.secretsofthevoid.server.network.packets;

import destiny.secretsofthevoid.server.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenGUIAlphabetPacket
{

    public OpenGUIAlphabetPacket()
    {

    }

    public static void write(OpenGUIAlphabetPacket packet, FriendlyByteBuf buffer)
    {

    }

    public static OpenGUIAlphabetPacket read(FriendlyByteBuf buffer)
    {
        return new OpenGUIAlphabetPacket();
    }

    public static void handle(OpenGUIAlphabetPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientPacketHandler.handleOpenGUIAlphabetPacket(packet));
        context.get().setPacketHandled(true);
    }
}
