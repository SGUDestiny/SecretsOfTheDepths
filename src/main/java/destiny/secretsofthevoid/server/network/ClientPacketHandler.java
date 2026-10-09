package destiny.secretsofthevoid.server.network;

import com.eliotlash.mclib.math.functions.limit.Min;
import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import destiny.secretsofthevoid.client.gui.GUIAncientAlphabet;
import destiny.secretsofthevoid.server.network.packets.UpdateFurnaceItemPacket;
import destiny.secretsofthevoid.server.registry.CapabilityRegistry;
import destiny.secretsofthevoid.server.network.packets.OpenGUIAlphabetPacket;
import destiny.secretsofthevoid.server.network.packets.UpdateDivingPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public class ClientPacketHandler
{
    public static void handleUpdateBreathingPacket(UpdateDivingPacket packet)
    {
        getPlayer().ifPresent(player -> player.getCapability(CapabilityRegistry.DIVING).ifPresent(cap ->
        {
            cap.setOxygen(packet.oxygen);
            cap.setMaxOxygen(packet.maxOxygen);
            cap.setMaskEfficiency(packet.oxygenEfficiency);
        }));
    }

    public static void handleOpenGUIAlphabetPacket(OpenGUIAlphabetPacket packet)
    {
        Minecraft.getInstance().setScreen(new GUIAncientAlphabet());
    }


    public static Optional<Level> getLevel() {
        Minecraft minecraft = Minecraft.getInstance();

        return minecraft.level == null ? Optional.empty() : Optional.of(Minecraft.getInstance().level);
    }

    public static Optional<LocalPlayer> getPlayer() {
        Minecraft minecraft = Minecraft.getInstance();

        return minecraft.player == null ? Optional.empty() : Optional.of(Minecraft.getInstance().player);
    }

    public static void handleUpdateFurnaceItemPacket(UpdateFurnaceItemPacket packet)
    {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        if(level == null)
            return;

        BlockEntity blockEntity = level.getBlockEntity(packet.pos);
        if(blockEntity instanceof NuclearFurnaceBlockEntity furnace)
            furnace.setItem(packet.slot, packet.stack);
    }
}
