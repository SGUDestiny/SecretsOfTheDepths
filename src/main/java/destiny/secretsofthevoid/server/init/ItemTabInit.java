package destiny.secretsofthevoid.server.init;

import com.github.alexmodguy.alexscaves.server.misc.ACCreativeTabRegistry;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.helper.ItemHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ItemTabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SecretsOfTheVoid.MODID);

    public static void register(IEventBus bus)
    {
        TABS.register(bus);
    }

    public static void setupTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ACCreativeTabRegistry.TOXIC_CAVES.getKey()) {
            event.accept(ItemInit.CALLIGRAPHY_KIT);
            event.accept(ItemInit.ANCIENT_ALPHABET);

            //Materials
            event.accept(ItemInit.NUCLEAR_PASTA);
            event.accept(ItemInit.IRRADIUM_PLATE);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.MAGNETIC_CAVES.getKey()) {
            event.accept(ItemInit.CALLIGRAPHY_KIT);
            event.accept(ItemInit.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.FORLORN_HOLLOWS.getKey()) {
            event.accept(ItemInit.CALLIGRAPHY_KIT);
            event.accept(ItemInit.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.CANDY_CAVITY.getKey()) {
            event.accept(ItemInit.CALLIGRAPHY_KIT);
            event.accept(ItemInit.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.PRIMORDIAL_CAVES.getKey()) {
            event.accept(ItemInit.CALLIGRAPHY_KIT);
            event.accept(ItemInit.ANCIENT_ALPHABET);
        }
        if (event.getTabKey() == ACCreativeTabRegistry.ABYSSAL_CHASM.getKey()) {
            event.accept(ItemInit.CALLIGRAPHY_KIT);
            event.accept(ItemInit.ANCIENT_ALPHABET);

            //Tools
            event.accept(ItemInit.HADAL_SWORD);
            event.accept(ItemInit.HADAL_AXE);
            event.accept(ItemInit.HADAL_PICKAXE);
            event.accept(ItemInit.TRENCHBLEEDER);
            event.accept(ItemInit.HADAL_HOE);

            //Armor
            event.accept(ItemInit.SCORIA_HELMET);
            event.accept(ItemInit.SCORIA_CHESTPLATE);
            event.accept(ItemInit.SCORIA_LEGGINGS);
            event.accept(ItemInit.SCORIA_BOOTS);

            //Diving Gear
            event.accept(ItemHelper.newMask(ItemInit.PEARL_MASK.get(), 1.2));
            event.accept(ItemHelper.newBacktank(ItemInit.PEARL_BACKTANK.get(), 180));
            event.accept(ItemHelper.newLegwear(ItemInit.PEARL_LEGWEAR.get(), 0.2));
            event.accept(ItemHelper.newFlippers(ItemInit.PEARL_FLIPPERS.get(), 0.3));

            event.accept(ItemHelper.newMask(ItemInit.NETHERITE_MASK.get(), 1.5));
            event.accept(ItemHelper.newBacktank(ItemInit.NETHERITE_BACKTANK.get(), 540));
            event.accept(ItemHelper.newLegwear(ItemInit.NETHERITE_LEGWEAR.get(), 0.3));
            event.accept(ItemHelper.newFlippers(ItemInit.NETHERITE_FLIPPERS.get(), 0.5));

            event.accept(ItemHelper.newMask(ItemInit.ABYSSALITH_MASK.get(), 2.0));
            event.accept(ItemHelper.newBacktank(ItemInit.ABYSSALITH_BACKTANK.get(), 1620));
            event.accept(ItemHelper.newLegwear(ItemInit.ABYSSALITH_LEGWEAR.get(), 0.5));
            event.accept(ItemHelper.newFlippers(ItemInit.ABYSSALITH_FLIPPERS.get(), 0.7));

            //Materials
            event.accept(ItemInit.ABYSSALITH_FRAGMENT);
            event.accept(ItemInit.ABYSSALITH_CORE);
            event.accept(ItemInit.ABYSSALITH_UPGRADE);

            //Discs
            event.accept(ItemInit.DISC_FRAGMENT_HADAL);
            event.accept(ItemInit.DISC_HADAL);
            event.accept(ItemInit.DISC_HADAL_AMBIENT);

            //Natural Blocks
            event.accept(BlockInit.OXYGEN_VENT);
            event.accept(BlockInit.VOIDSTONE);
            event.accept(BlockInit.HADAL_CORAL);

            //Crate Blocks
            event.accept(BlockInit.HADAL_CRATE);
            event.accept(BlockInit.ELDRITCH_CRATE);
            event.accept(BlockInit.IRRADIATED_CRATE);
            event.accept(BlockInit.PREHISTORIC_CRATE);
            event.accept(BlockInit.POLARIZED_CRATE);
            event.accept(BlockInit.LICOROOT_CRATE);

            //Craftable Blocks
            event.accept(BlockInit.ABYSSMARINE_SIGIL_COMMONER);
            event.accept(BlockInit.ABYSSMARINE_SIGIL_KNIGHT);
            event.accept(BlockInit.ABYSSMARINE_SIGIL_MAGE);
            event.accept(BlockInit.PRESSURE_DRAIN);
            event.accept(BlockInit.MUSSEL_FARM);
        }
    }
}
