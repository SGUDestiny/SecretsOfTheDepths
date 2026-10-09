package destiny.secretsofthevoid.server.init;

import destiny.secretsofthevoid.client.network.ClientBoundParticlePacket;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.network.packets.OpenGUIAlphabetPacket;
import destiny.secretsofthevoid.server.network.packets.SoundPackets;
import destiny.secretsofthevoid.server.network.packets.UpdateDivingPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkInit {
    public static final String NET_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(SecretsOfTheVoid.MODID, "main"), () -> NET_VERSION, NET_VERSION::equals, NET_VERSION::equals);

    public static int ID = 0;

    public static void registerPackets(){
        INSTANCE.registerMessage(id(), UpdateDivingPacket.class, UpdateDivingPacket::write, UpdateDivingPacket::read, UpdateDivingPacket::handle);
        INSTANCE.registerMessage(id(), SoundPackets.RebreatherInhale.class, SoundPackets.RebreatherInhale::write, SoundPackets.RebreatherInhale::read, SoundPackets.RebreatherInhale::handle);
        INSTANCE.registerMessage(id(), SoundPackets.RebreatherExhale.class, SoundPackets.RebreatherExhale::write, SoundPackets.RebreatherExhale::read, SoundPackets.RebreatherExhale::handle);
        INSTANCE.registerMessage(id(), SoundPackets.TankRefill.class, SoundPackets.TankRefill::write, SoundPackets.TankRefill::read, SoundPackets.TankRefill::handle);
        INSTANCE.registerMessage(id(), OpenGUIAlphabetPacket.class, OpenGUIAlphabetPacket::write, OpenGUIAlphabetPacket::read, OpenGUIAlphabetPacket::handle);
        INSTANCE.messageBuilder(ClientBoundParticlePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ClientBoundParticlePacket::encode)
                .decoder(ClientBoundParticlePacket::decode)
                .consumerMainThread(ClientBoundParticlePacket::handle)
                .add();
    }

    public static void sendPacketToAll(Object message){
        NetworkInit.INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static void sendPacketToDimension(ResourceKey<Level> level, Object mes){
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> level), mes);
    }

    public static void sendToTracking(Entity e, Object mes) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), mes);
    }

    public static void sendToTracking(BlockEntity tile, Object mes){
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> tile.getLevel().getChunkAt(tile.getBlockPos())), mes);
    }

    public static void sendTo(ServerPlayer player, Object mes){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), mes);
    }

    public static void sendToServer(Object mes) {
        INSTANCE.sendToServer(mes);
    }

    public static int id(){
        return ++ID;
    }
}
