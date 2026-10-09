package destiny.secretsofthevoid.server.registry;

import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.item.RadioactiveItem;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.items.*;
import destiny.secretsofthevoid.server.items.tools.*;
import destiny.secretsofthevoid.server.items.diving_gear.*;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SecretsOfTheVoid.MODID);

    public static final Rarity RARITY_ABYSSAL = Rarity.create("secretsofthevoid:abyssal", ChatFormatting.BLUE);

    //Materials
    public static final RegistryObject<Item> ABYSSALITH_FRAGMENT = ITEMS.register("abyssalith_fragment",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> ABYSSALITH_CORE = ITEMS.register("abyssalith_core",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(16)));
    public static final RegistryObject<SmithingTemplateItem> ABYSSALITH_UPGRADE = ITEMS.register("abyssalith_upgrade_smithing_template",
            () -> new SmithingTemplateItem(
                    Component.translatable(Util.makeDescriptionId("item",
                            new ResourceLocation(SecretsOfTheVoid.MODID, "smithing_template.abyssalith_upgrade.applies_to"))).withStyle(ChatFormatting.BLUE),
                    Component.translatable(Util.makeDescriptionId("item",
                            new ResourceLocation(SecretsOfTheVoid.MODID, "smithing_template.abyssalith_upgrade.ingredients"))).withStyle(ChatFormatting.BLUE),
                    Component.translatable(Util.makeDescriptionId("upgrade",
                            new ResourceLocation(SecretsOfTheVoid.MODID, "abyssalith_upgrade"))).withStyle(ChatFormatting.GRAY),
                    Component.translatable(Util.makeDescriptionId("item",
                            new ResourceLocation(SecretsOfTheVoid.MODID, "smithing_template.abyssalith_upgrade.base_slot_description"))),
                    Component.translatable(Util.makeDescriptionId("item",
                            new ResourceLocation(SecretsOfTheVoid.MODID, "smithing_template.abyssalith_upgrade.additions_slot_description"))),
                    List.of(
                            new ResourceLocation("item/empty_armor_slot_helmet"),
                            new ResourceLocation("item/empty_armor_slot_chestplate"),
                            new ResourceLocation("item/empty_armor_slot_leggings"),
                            new ResourceLocation("item/empty_armor_slot_boots"),
                            new ResourceLocation("item/empty_slot_sword"),
                            new ResourceLocation("item/empty_slot_axe"),
                            new ResourceLocation("item/empty_slot_pickaxe"),
                            new ResourceLocation("item/empty_slot_shovel"),
                            new ResourceLocation("item/empty_slot_hoe")
                    ),
                    List.of(
                            new ResourceLocation("item/empty_slot_ingot")
                    )
            )
    );
    public static final RegistryObject<Item> NUCLEAR_PASTA = ITEMS.register("nuclear_pasta",
            () -> new RadioactiveItem(new Item.Properties().rarity(ACItemRegistry.RARITY_NUCLEAR).fireResistant(), 0.001F));
    public static final RegistryObject<Item> IRRADIUM_PLATE = ITEMS.register("irradium_plate",
            () -> new RadioactiveItem(new Item.Properties().rarity(ACItemRegistry.RARITY_NUCLEAR).fireResistant(), 0.001F));

    //Discs
    public static final RegistryObject<Item> DISC_HADAL = ITEMS.register("disc_hadal",
            () -> new RecordItem(0, SoundRegistry.DISK_HADAL,
                    new Item.Properties().rarity(Rarity.EPIC).stacksTo(1), 7620));
    public static final RegistryObject<Item> DISC_HADAL_AMBIENT = ITEMS.register("disc_hadal_ambient",
            () -> new RecordItem(0, SoundRegistry.DISK_HADAL_AMBIENT,
                    new Item.Properties().rarity(Rarity.EPIC).stacksTo(1), 7600));
    public static final RegistryObject<Item> DISC_FRAGMENT_HADAL = ITEMS.register("disc_fragment_hadal",
            () -> new DiscFragmentItem(new Item.Properties().rarity(Rarity.RARE)));

    //Diving Gear
    public static final RegistryObject<PearlMaskItem> PEARL_MASK = ITEMS.register("pearl_mask",
            () -> new PearlMaskItem(ArmorMaterialRegistry.PEARL_DIVING_GEAR, ArmorItem.Type.HELMET,
                    new Item.Properties().stacksTo(1)));
    public static final RegistryObject<PearlBacktankItem> PEARL_BACKTANK = ITEMS.register("pearl_backtank",
            () -> new PearlBacktankItem(ArmorMaterialRegistry.PEARL_DIVING_GEAR, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1)));
    public static final RegistryObject<PearlLegwearItem> PEARL_LEGWEAR = ITEMS.register("pearl_legwear",
            () -> new PearlLegwearItem(ArmorMaterialRegistry.PEARL_DIVING_GEAR, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().stacksTo(1)));
    public static final RegistryObject<PearlFlippersItem> PEARL_FLIPPERS = ITEMS.register("pearl_flippers",
            () -> new PearlFlippersItem(ArmorMaterialRegistry.PEARL_DIVING_GEAR, ArmorItem.Type.BOOTS,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<NetheriteMaskItem> NETHERITE_MASK = ITEMS.register("netherite_mask",
            () -> new NetheriteMaskItem(ArmorMaterialRegistry.NETHERITE_DIVING_GEAR, ArmorItem.Type.HELMET,
                    new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<NetheriteBacktankItem> NETHERITE_BACKTANK = ITEMS.register("netherite_backtank",
            () -> new NetheriteBacktankItem(ArmorMaterialRegistry.NETHERITE_DIVING_GEAR, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<NetheriteLegwearItem> NETHERITE_LEGWEAR = ITEMS.register("netherite_legwear",
            () -> new NetheriteLegwearItem(ArmorMaterialRegistry.NETHERITE_DIVING_GEAR, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistryObject<NetheriteFlippersItem> NETHERITE_FLIPPERS = ITEMS.register("netherite_flippers",
            () -> new NetheriteFlippersItem(ArmorMaterialRegistry.NETHERITE_DIVING_GEAR, ArmorItem.Type.BOOTS,
                    new Item.Properties().stacksTo(1).fireResistant()));

    public static final RegistryObject<AbyssalithMaskItem> ABYSSALITH_MASK = ITEMS.register("abyssalith_mask",
            () -> new AbyssalithMaskItem(ArmorMaterialRegistry.ABYSSALITH_DIVING_GEAR, ArmorItem.Type.HELMET,
                    new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<AbyssalithBacktankItem> ABYSSALITH_BACKTANK = ITEMS.register("abyssalith_backtank",
            () -> new AbyssalithBacktankItem(ArmorMaterialRegistry.ABYSSALITH_DIVING_GEAR, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<AbyssalithLegwearItem> ABYSSALITH_LEGWEAR = ITEMS.register("abyssalith_legwear",
            () -> new AbyssalithLegwearItem(ArmorMaterialRegistry.ABYSSALITH_DIVING_GEAR, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<AbyssalithFlippersItem> ABYSSALITH_FLIPPERS = ITEMS.register("abyssalith_flippers",
            () -> new AbyssalithFlippersItem(ArmorMaterialRegistry.ABYSSALITH_DIVING_GEAR, ArmorItem.Type.BOOTS,
                    new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE)));

    //Armor
    public static final RegistryObject<Item> SCORIA_HELMET = ITEMS.register("scoria_helmet",
            () -> new ArmorItem(ArmorMaterialRegistry.ABYSSALITH, ArmorItem.Type.HELMET,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> SCORIA_CHESTPLATE = ITEMS.register("scoria_chestplate",
            () -> new ArmorItem(ArmorMaterialRegistry.ABYSSALITH, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> SCORIA_LEGGINGS = ITEMS.register("scoria_leggings",
            () -> new ArmorItem(ArmorMaterialRegistry.ABYSSALITH, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> SCORIA_BOOTS = ITEMS.register("scoria_boots",
            () -> new ArmorItem(ArmorMaterialRegistry.ABYSSALITH, ArmorItem.Type.BOOTS,
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant()));

    //Tools
    public static final RegistryObject<HadalSwordItem> HADAL_SWORD = ITEMS.register("hadal_sword",
            () -> new HadalSwordItem(ToolTierRegistry.HADAL, 4, -1.9f,
                    new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<HadalPickaxeItem> HADAL_PICKAXE = ITEMS.register("hadal_pickaxe",
            () -> new HadalPickaxeItem(ToolTierRegistry.HADAL, 2, -2.3f,
                    new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<HadalAxeItem> HADAL_AXE = ITEMS.register("hadal_axe",
            () -> new HadalAxeItem(ToolTierRegistry.HADAL, 6.0f, -2.5f,
                    new Item.Properties().fireResistant().rarity(Rarity.RARE)));
    public static final RegistryObject<HadalHoeItem> HADAL_HOE = ITEMS.register("hadal_hoe",
            () -> new HadalHoeItem(ToolTierRegistry.HADAL, 0, 1f,
                    new Item.Properties().fireResistant().rarity(Rarity.RARE)));

    //Special tools
    public static final RegistryObject<TrenchbleederItem> TRENCHBLEEDER = ITEMS.register("trenchbleeder",
            () -> new TrenchbleederItem(ToolTierRegistry.TRENCHBLEEDER, 2.5f, -2.0f,
                    new Item.Properties().fireResistant().rarity(RARITY_ABYSSAL)));

    //Misc Items
    public static final RegistryObject<CalligraphyKitItem> CALLIGRAPHY_KIT = ITEMS.register("calligraphy_kit",
            () -> new CalligraphyKitItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<AncientAlphabetItem> ANCIENT_ALPHABET = ITEMS.register("ancient_alphabet",
            () -> new AncientAlphabetItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }
}
