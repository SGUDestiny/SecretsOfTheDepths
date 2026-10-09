package destiny.secretsofthevoid.server.network.packets;

import destiny.secretsofthevoid.server.network.SoundPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SoundPackets
{
    public static class RebreatherInhale extends SoundPackets
    {
        public BlockPos pos;

        public RebreatherInhale(BlockPos pos)
        {
            this.pos = pos;
        }

        public static void write(RebreatherInhale packet, FriendlyByteBuf buffer)
        {
            buffer.writeBlockPos(packet.pos);
        }

        public static RebreatherInhale read(FriendlyByteBuf buffer)
        {
            BlockPos pos = buffer.readBlockPos();

            return new RebreatherInhale(pos);
        }

        public static void handle(RebreatherInhale packet, Supplier<NetworkEvent.Context> context)
        {
            context.get().enqueueWork(() -> SoundPacketHandler.handleRebreatherInhale(packet));
            context.get().setPacketHandled(true);
        }
    }

    public static class RebreatherExhale extends SoundPackets
    {
        public BlockPos pos;

        public RebreatherExhale(BlockPos pos)
        {
            this.pos = pos;
        }

        public static void write(RebreatherExhale packet, FriendlyByteBuf buffer)
        {
            buffer.writeBlockPos(packet.pos);
        }

        public static RebreatherExhale read(FriendlyByteBuf buffer)
        {
            BlockPos pos = buffer.readBlockPos();

            return new RebreatherExhale(pos);
        }

        public static void handle(RebreatherExhale packet, Supplier<NetworkEvent.Context> context)
        {
            context.get().enqueueWork(() -> SoundPacketHandler.handleRebreatherExhale(packet));
            context.get().setPacketHandled(true);
        }
    }

    public static class TankRefill extends SoundPackets
    {
        public BlockPos pos;

        public TankRefill(BlockPos pos)
        {
            this.pos = pos;
        }

        public static void write(TankRefill packet, FriendlyByteBuf buffer)
        {
            buffer.writeBlockPos(packet.pos);
        }

        public static TankRefill read(FriendlyByteBuf buffer)
        {
            BlockPos pos = buffer.readBlockPos();

            return new TankRefill(pos);
        }

        public static void handle(TankRefill packet, Supplier<NetworkEvent.Context> context)
        {
            context.get().enqueueWork(() -> SoundPacketHandler.handleTankRefill(packet));
            context.get().setPacketHandled(true);
        }
    }
}
