package destiny.secretsofthevoid.server.init;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.capabilities.DivingCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SecretsOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilitiesInit {
    public static final Capability<DivingCapability> DIVING = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(DivingCapability.class);
    }
}
