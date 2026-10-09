package destiny.secretsofthevoid.server.events;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.capabilities.DivingCapability;
import destiny.secretsofthevoid.server.capabilities.GenericProvider;
import destiny.secretsofthevoid.server.registry.CapabilityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SecretsOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(SecretsOfTheVoid.MODID, "diving"), new GenericProvider<>(CapabilityRegistry.DIVING, new DivingCapability()));
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.END && event.side.isServer() && event.player instanceof ServerPlayer player)
            event.player.getCapability(CapabilityRegistry.DIVING).ifPresent(cap -> cap.tick(event.player.level(), player));
    }
}
