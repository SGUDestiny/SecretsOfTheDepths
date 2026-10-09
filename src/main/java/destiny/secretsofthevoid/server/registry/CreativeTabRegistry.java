package destiny.secretsofthevoid.server.registry;

import com.github.alexmodguy.alexscaves.server.misc.ACCreativeTabRegistry;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.helper.ItemHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SecretsOfTheVoid.MODID);

    public static void register(IEventBus bus)
    {
        TABS.register(bus);
    }

    public static void setupTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ACCreativeTabRegistry.TOXIC_CAVES.getKey()) {
            event.accept(BlockRegistry.GAMMA_ROD.get());

            event.accept(BlockRegistry.IRRADIATED_CRATE);

            //Materials
            event.accept(ItemRegistry.NUCLEAR_PASTA);
            event.accept(ItemRegistry.IRRADIUM_PLATE);

            event.accept(ItemRegistry.CALLIGRAPHY_KIT);
            event.accept(ItemRegistry.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.MAGNETIC_CAVES.getKey()) {
            event.accept(BlockRegistry.POLARIZED_CRATE);

            event.accept(ItemRegistry.CALLIGRAPHY_KIT);
            event.accept(ItemRegistry.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.FORLORN_HOLLOWS.getKey()) {
            event.accept(BlockRegistry.ELDRITCH_CRATE);

            event.accept(ItemRegistry.CALLIGRAPHY_KIT);
            event.accept(ItemRegistry.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.CANDY_CAVITY.getKey()) {
            event.accept(BlockRegistry.LICOROOT_CRATE);

            event.accept(ItemRegistry.CALLIGRAPHY_KIT);
            event.accept(ItemRegistry.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.PRIMORDIAL_CAVES.getKey()) {
            event.accept(BlockRegistry.PREHISTORIC_CRATE);

            event.accept(ItemRegistry.CALLIGRAPHY_KIT);
            event.accept(ItemRegistry.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.ABYSSAL_CHASM.getKey()) {
            //Tools
            event.accept(ItemRegistry.HADAL_SWORD);
            event.accept(ItemRegistry.HADAL_AXE);
            event.accept(ItemRegistry.HADAL_PICKAXE);
            event.accept(ItemRegistry.TRENCHBLEEDER);
            event.accept(ItemRegistry.HADAL_HOE);

            //Armor
            event.accept(ItemRegistry.SCORIA_HELMET);
            event.accept(ItemRegistry.SCORIA_CHESTPLATE);
            event.accept(ItemRegistry.SCORIA_LEGGINGS);
            event.accept(ItemRegistry.SCORIA_BOOTS);

            //Diving Gear
            event.accept(ItemHelper.newMask(ItemRegistry.PEARL_MASK.get(), 1.2));
            event.accept(ItemHelper.newBacktank(ItemRegistry.PEARL_BACKTANK.get(), 180));
            event.accept(ItemHelper.newLegwear(ItemRegistry.PEARL_LEGWEAR.get(), 0.2));
            event.accept(ItemHelper.newFlippers(ItemRegistry.PEARL_FLIPPERS.get(), 0.3));

            event.accept(ItemHelper.newMask(ItemRegistry.NETHERITE_MASK.get(), 1.5));
            event.accept(ItemHelper.newBacktank(ItemRegistry.NETHERITE_BACKTANK.get(), 540));
            event.accept(ItemHelper.newLegwear(ItemRegistry.NETHERITE_LEGWEAR.get(), 0.3));
            event.accept(ItemHelper.newFlippers(ItemRegistry.NETHERITE_FLIPPERS.get(), 0.5));

            event.accept(ItemHelper.newMask(ItemRegistry.ABYSSALITH_MASK.get(), 2.0));
            event.accept(ItemHelper.newBacktank(ItemRegistry.ABYSSALITH_BACKTANK.get(), 1620));
            event.accept(ItemHelper.newLegwear(ItemRegistry.ABYSSALITH_LEGWEAR.get(), 0.5));
            event.accept(ItemHelper.newFlippers(ItemRegistry.ABYSSALITH_FLIPPERS.get(), 0.7));

            //Materials
            event.accept(ItemRegistry.ABYSSALITH_FRAGMENT);
            event.accept(ItemRegistry.ABYSSALITH_CORE);
            event.accept(ItemRegistry.ABYSSALITH_UPGRADE);

            //Discs
            event.accept(ItemRegistry.DISC_FRAGMENT_HADAL);
            event.accept(ItemRegistry.DISC_HADAL);
            event.accept(ItemRegistry.DISC_HADAL_AMBIENT);

            //Natural Blocks
            event.accept(BlockRegistry.OXYGEN_VENT);
            event.accept(BlockRegistry.VOIDSTONE);
            event.accept(BlockRegistry.HADAL_CORAL);

            //Crate Blocks
            event.accept(BlockRegistry.HADAL_CRATE);

            //Craftable Blocks
            event.accept(BlockRegistry.ABYSSMARINE_SIGIL_COMMONER);
            event.accept(BlockRegistry.ABYSSMARINE_SIGIL_KNIGHT);
            event.accept(BlockRegistry.ABYSSMARINE_SIGIL_MAGE);
            event.accept(BlockRegistry.PRESSURE_DRAIN);
            event.accept(BlockRegistry.MUSSEL_FARM);

            event.accept(ItemRegistry.CALLIGRAPHY_KIT);
            event.accept(ItemRegistry.ANCIENT_ALPHABET);
        }
    }
}
