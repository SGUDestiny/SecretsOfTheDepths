package destiny.secretsofthevoid.server;

import com.github.alexmodguy.alexscaves.server.CommonProxy;
import destiny.secretsofthevoid.client.gui.OxygenOverlay;
import destiny.secretsofthevoid.server.init.*;
import destiny.secretsofthevoid.server.items.tools.HadalItemProperty;
import destiny.secretsofthevoid.server.worldgen.feature.ModFeatures;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import destiny.secretsofthevoid.client.ClientProxy;

@Mod(SecretsOfTheVoid.MODID)
public class SecretsOfTheVoid {
    public static final String MODID = "secretsofthevoid";
    public static CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public SecretsOfTheVoid()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ItemInit.register(modBus);
        ItemTabInit.register(modBus);
        BlockInit.BLOCKS.register(modBus);
        SoundInit.SOUNDS.register(modBus);
        ModFeatures.DEF_REG.register(modBus);
        BlockEntitiesInit.DEF_REG.register(modBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> PROXY.clientInit());
    }

    @Mod.EventBusSubscriber(modid = MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents
    {
        @SubscribeEvent
        public static void onCreativeTab(BuildCreativeModeTabContentsEvent event)
        {
            ItemTabInit.setupTabs(event);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerOverlays(RegisterGuiOverlaysEvent event)
        {
            event.registerAboveAll("oxygen", OxygenOverlay.OVERLAY);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                ItemProperties.register(ItemInit.HADAL_SWORD.get(), new ResourceLocation(MODID, "active"), new HadalItemProperty());
                ItemProperties.register(ItemInit.HADAL_PICKAXE.get(), new ResourceLocation(MODID, "active"), new HadalItemProperty());
                ItemProperties.register(ItemInit.HADAL_AXE.get(), new ResourceLocation(MODID, "active"), new HadalItemProperty());
                ItemProperties.register(ItemInit.HADAL_HOE.get(), new ResourceLocation(MODID, "active"), new HadalItemProperty());
            });
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkInit.registerPackets();
        });
    }
}