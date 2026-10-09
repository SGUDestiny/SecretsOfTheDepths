package destiny.secretsofthevoid.server.network;

import destiny.secretsofthevoid.server.init.SoundInit;
import destiny.secretsofthevoid.server.network.packets.SoundPackets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class SoundPacketHandler
{
    public static Minecraft minecraft = Minecraft.getInstance();

    public static void handleRebreatherInhale(SoundPackets.RebreatherInhale packet)
    {
        SoundInstance sound = new SimpleSoundInstance(SoundInit.MASK_INTAKE.get(), SoundSource.PLAYERS, 1.0f, 1.0f, SoundInstance.createUnseededRandom(), packet.pos);
        minecraft.getSoundManager().play(sound);
    }

    public static void handleRebreatherExhale(SoundPackets.RebreatherExhale packet)
    {
        SoundInstance sound = new SimpleSoundInstance(SoundInit.MASK_EXPEL.get(), SoundSource.PLAYERS, 1.0f, 1.0f, SoundInstance.createUnseededRandom(), packet.pos);
        minecraft.getSoundManager().play(sound);
    }

    public static void handleTankRefill(SoundPackets.TankRefill packet)
    {
        SoundInstance sound = new SimpleSoundInstance(SoundInit.BACKTANK_REFILL.get(), SoundSource.PLAYERS, 1.0f, 1.0f, SoundInstance.createUnseededRandom(), packet.pos);
        minecraft.getSoundManager().play(sound);
    }
}
