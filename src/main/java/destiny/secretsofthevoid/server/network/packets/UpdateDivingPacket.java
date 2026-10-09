package destiny.secretsofthevoid.server.network.packets;

import destiny.secretsofthevoid.server.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateDivingPacket
{
    public double oxygen;
    public double maxOxygen;
    public double oxygenEfficiency;

    public UpdateDivingPacket(double oxygen, double maxOxygen, double oxygenModifier) {
        this.oxygen = oxygen;
        this.maxOxygen = maxOxygen;
        this.oxygenEfficiency = oxygenModifier;
    }

    public static void write(UpdateDivingPacket packet, FriendlyByteBuf buffer) {
        buffer.writeDouble(packet.oxygen);
        buffer.writeDouble(packet.maxOxygen);
        buffer.writeDouble(packet.oxygenEfficiency);
    }

    public static UpdateDivingPacket read(FriendlyByteBuf buffer) {
        double oxygen = buffer.readDouble();
        double maxOxygen = buffer.readDouble();
        double oxygenModifier = buffer.readDouble();

        return new UpdateDivingPacket(oxygen, maxOxygen, oxygenModifier);
    }

    public static void handle(UpdateDivingPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientPacketHandler.handleUpdateBreathingPacket(packet));
        context.get().setPacketHandled(true);
    }
}
