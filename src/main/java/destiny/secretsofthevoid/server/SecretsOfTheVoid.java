package destiny.secretsofthevoid.server;

import destiny.secretsofthevoid.client.gui.OxygenOverlay;
import destiny.secretsofthevoid.client.render.SuspiciousBlockEntityRenderer;
import destiny.secretsofthevoid.client.render.entity.SievertRodHookEntityRenderer;
import destiny.secretsofthevoid.client.render.particle.NeutronParticle;
import destiny.secretsofthevoid.server.registry.*;
import destiny.secretsofthevoid.server.items.tools.HadalItemProperty;
import destiny.secretsofthevoid.server.worldgen.feature.ModFeatures;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod(SecretsOfTheVoid.MODID)
public class SecretsOfTheVoid {
    public static final String MODID = "secretsofthevoid";

    public SecretsOfTheVoid() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ItemRegistry.register(modBus);
        CreativeTabRegistry.register(modBus);
        BlockRegistry.BLOCKS.register(modBus);
        SoundRegistry.SOUNDS.register(modBus);
        ModFeatures.DEF_REG.register(modBus);
        EntityRegistry.ENTITIES.register(modBus);
        BlockEntityRegistry.DEF_REG.register(modBus);
        ParticleTypeRegistry.PARTICLE_TYPES.register(modBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onCreativeTab(BuildCreativeModeTabContentsEvent event) {
            CreativeTabRegistry.setupTabs(event);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("oxygen", OxygenOverlay.OVERLAY);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(ItemRegistry.HADAL_SWORD.get(), new ResourceLocation(MODID, "active"), new HadalItemProperty());
                ItemProperties.register(ItemRegistry.HADAL_PICKAXE.get(), new ResourceLocation(MODID, "active"), new HadalItemProperty());
                ItemProperties.register(ItemRegistry.HADAL_AXE.get(), new ResourceLocation(MODID, "active"), new HadalItemProperty());
                ItemProperties.register(ItemRegistry.HADAL_HOE.get(), new ResourceLocation(MODID, "active"), new HadalItemProperty());

                EntityRenderers.register(EntityRegistry.SIEVERT_ROD_HOOK.get(), SievertRodHookEntityRenderer::new);

                ItemProperties.register(ItemRegistry.SIEVERT_ROD.get(), new ResourceLocation("cast"),
                        (stack, level, entity, duration) -> {
                    if (entity == null) return 0f;

                    boolean isMainHand = entity.getMainHandItem() == stack;
                    boolean isOffHand = entity.getOffhandItem() == stack;

                    if (isMainHand) {
                        isOffHand = false;
                    }

                    return (isMainHand || isOffHand) && entity instanceof Player player && player.fishing != null ? 1f : 0f;
                });
            });
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpecial(ParticleTypeRegistry.NEUTRON.get(), new NeutronParticle.Provider());
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityRegistry.SUSPICIOUS_BLOCK.get(), SuspiciousBlockEntityRenderer::new);
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkRegistry.registerPackets();
        });
    }
}