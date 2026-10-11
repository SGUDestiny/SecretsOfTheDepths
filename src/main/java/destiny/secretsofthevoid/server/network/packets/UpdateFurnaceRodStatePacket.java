package destiny.secretsofthevoid.server.network.packets;

import destiny.secretsofthevoid.server.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateFurnaceRodStatePacket
{
    public BlockPos pos;
    public boolean gamma;

    public UpdateFurnaceRodStatePacket(BlockPos pos,  boolean gamma)
    {
        this.gamma = gamma;
        this.pos = pos;
    }

    public static void write(UpdateFurnaceRodStatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.gamma);
        buffer.writeBlockPos(packet.pos);
    }

    public static UpdateFurnaceRodStatePacket read(FriendlyByteBuf buffer) {
        boolean gamma = buffer.readBoolean();
        BlockPos pos = buffer.readBlockPos();
        return new UpdateFurnaceRodStatePacket(pos, gamma);
    }

    public static void handle(UpdateFurnaceRodStatePacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientPacketHandler.handleUpdateFurnaceRodPacket(packet));
        context.get().setPacketHandled(true);
    }
}
