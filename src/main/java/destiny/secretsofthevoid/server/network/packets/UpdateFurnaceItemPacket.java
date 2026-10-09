package destiny.secretsofthevoid.server.network.packets;

import destiny.secretsofthevoid.server.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateFurnaceItemPacket
{
    public BlockPos pos;
    public int slot;
    public ItemStack stack;

    public UpdateFurnaceItemPacket(BlockPos pos, int slot, ItemStack stack) {
        this.stack = stack;
        this.slot = slot;
        this.pos = pos;
    }

    public static void write(UpdateFurnaceItemPacket packet, FriendlyByteBuf buffer) {
        buffer.writeItem(packet.stack);
        buffer.writeInt(packet.slot);
        buffer.writeBlockPos(packet.pos);
    }

    public static UpdateFurnaceItemPacket read(FriendlyByteBuf buffer) {
        ItemStack stack = buffer.readItem();
        int slot = buffer.readInt();
        BlockPos pos = buffer.readBlockPos();
        return new UpdateFurnaceItemPacket(pos, slot, stack);
    }

    public static void handle(UpdateFurnaceItemPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientPacketHandler.handleUpdateFurnaceItemPacket(packet));
        context.get().setPacketHandled(true);
    }
}
