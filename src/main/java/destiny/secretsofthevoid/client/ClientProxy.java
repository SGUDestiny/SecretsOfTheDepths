package destiny.secretsofthevoid.client;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.model.baked.BakedModelShadeLayerFullbright;
import com.github.alexmodguy.alexscaves.server.CommonProxy;
import com.google.common.collect.ImmutableList;
import destiny.secretsofthevoid.client.render.SuspiciousBlockEntityRenderer;
import destiny.secretsofthevoid.server.events.ClientEvents;
import destiny.secretsofthevoid.server.registry.BlockEntityRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

public class ClientProxy extends CommonProxy {
    private static final List<String> FULLBRIGHTS = ImmutableList.of("secretsofthevoid:abyssmarine_sigil_");

    public void clientInit() {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::bakeModels);

        BlockEntityRenderers.register(BlockEntityRegistry.SUSPICIOUS_BLOCK.get(), SuspiciousBlockEntityRenderer::new);
    }

    private void bakeModels(final ModelEvent.ModifyBakingResult e) {
        if (AlexsCaves.CLIENT_CONFIG.emissiveBlockModels.get()) {
            long time = System.currentTimeMillis();
            for (ResourceLocation id : e.getModels().keySet()) {
                if (FULLBRIGHTS.stream().anyMatch(str -> id.toString().startsWith(str))) {
                    e.getModels().put(id, new BakedModelShadeLayerFullbright(e.getModels().get(id)));
                }
            }
            AlexsCaves.LOGGER.info("Loaded emissive block models in {} ms", System.currentTimeMillis() - time);

        }
    }


}
