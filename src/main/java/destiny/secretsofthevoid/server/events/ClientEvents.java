package destiny.secretsofthevoid.server.events;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.init.CapabilitiesInit;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SecretsOfTheVoid.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents
{

    @SubscribeEvent
    public static void fovCalc(ComputeFovModifierEvent event)
    {
        event.getPlayer().getCapability(CapabilitiesInit.DIVING).ifPresent(cap -> {
            Player player = event.getPlayer();
            if(!cap.getEquipmentMask(player, null).isEmpty() && player.getEyeInFluidType().canDrownIn(player)) {
                event.setNewFovModifier(1.4F);
            } else if(!cap.getEquipmentFlippers(player, null).isEmpty())
                event.setNewFovModifier(1F);
        });
    }

}
