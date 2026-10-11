package destiny.secretsofthevoid.server.network.packets;

import destiny.secretsofthevoid.server.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateFurnaceMenuPosPacket
{
    public BlockPos pos;

    public UpdateFurnaceMenuPosPacket(BlockPos pos)
    {
        this.pos = pos;
    }

    public static void write(UpdateFurnaceMenuPosPacket packet, FriendlyByteBuf buffer)
    {
        buffer.writeBlockPos(packet.pos);
    }

    public static UpdateFurnaceMenuPosPacket read(FriendlyByteBuf buffer)
    {
        BlockPos pos = buffer.readBlockPos();
        return new UpdateFurnaceMenuPosPacket(pos);
    }

    public static void handle(UpdateFurnaceMenuPosPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientPacketHandler.handleUpdateFurnacePosPacket(packet));
        context.get().setPacketHandled(true);
    }
}
