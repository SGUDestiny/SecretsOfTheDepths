package destiny.secretsofthevoid.init;

import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.ACSoundTypes;
import destiny.secretsofthevoid.SecretsOfTheVoid;
import destiny.secretsofthevoid.blocks.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SecretsOfTheVoid.MODID);

    //Natural Blocks
    public static final RegistryObject<Block> VOIDSTONE = registerBlock("voidstone",
            () -> new Block(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(35.0F, 2000).sound(SoundType.ANCIENT_DEBRIS)));
    public static final RegistryObject<Block> OXYGEN_VENT = registerBlock("oxygen_vent",
            () -> new OxygenVentBlock(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_GRAY).strength(3.0F, 6).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SUSPICIOUS_MUCK = registerBlock("suspicious_muck",
            () -> new BrushableBlock(ACBlockRegistry.MUCK.get(), BlockBehaviour.
                    Properties.of().mapColor(DyeColor.LIGHT_GRAY).strength(0.5F).sound(SoundType.FROGSPAWN).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED));
    public static final RegistryObject<Block> HADAL_CORAL = registerBlock("hadal_coral",
            () -> new HadalCoralBlock(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_BLUE).strength(6.0F, 10).sound(SoundType.CORAL_BLOCK).requiresCorrectToolForDrops().noOcclusion().lightLevel((p_152680_) -> {
                        return 5 * p_152680_.getValue(HadalCoralBlock.STAGE);
                    })));

    //Craftable Blocks
    public static final RegistryObject<AbyssmarineSigilBlock> ABYSSMARINE_SIGIL_COMMONER = registerBlock("abyssmarine_sigil_commoner",
            () -> new AbyssmarineSigilBlock(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_BLUE).strength(3.0F, 10).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<AbyssmarineSigilBlock> ABYSSMARINE_SIGIL_KNIGHT = registerBlock("abyssmarine_sigil_knight",
            () -> new AbyssmarineSigilBlock(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_BLUE).strength(3.0F, 10).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<AbyssmarineSigilBlock> ABYSSMARINE_SIGIL_MAGE = registerBlock("abyssmarine_sigil_mage",
            () -> new AbyssmarineSigilBlock(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_BLUE).strength(3.0F, 10).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PRESSURE_DRAIN = registerBlock("pressure_drain",
            () -> new PressureDrainBlock(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_CYAN).strength(6.0F, 10).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> MUSSEL_FARM = registerBlock("mussel_farm",
            () -> new MusselFarmBlock(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F, 1).sound(SoundType.MOSS_CARPET).instabreak().noOcclusion().noCollission().randomTicks()));

    //Crate Blocks
    public static final RegistryObject<Block> HADAL_CRATE = registerBlock("hadal_crate",
            () -> new Block(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_BLUE).strength(3.0F, 10).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ELDRITCH_CRATE = registerBlock("eldritch_crate",
            () -> new Block(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(3.0F, 10).sound(ACSoundTypes.PEERING_COPROLITH).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PREHISTORIC_CRATE = registerBlock("prehistoric_crate",
            () -> new Block(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(3.0F, 10).sound(ACSoundTypes.AMBER).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> IRRADIATED_CRATE = registerBlock("irradiated_crate",
            () -> new Block(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_GREEN).strength(3.0F, 10).sound(ACSoundTypes.URANIUM).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POLARIZED_CRATE = registerBlock("polarized_crate",
            () -> new Block(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1.0F, 10).sound(ACSoundTypes.NEODYMIUM)));
    public static final RegistryObject<Block> LICOROOT_CRATE = registerBlock("licoroot_crate",
            () -> new Block(BlockBehaviour.
                    Properties.of().mapColor(MapColor.COLOR_MAGENTA).strength(3.0F, 10).sound(ACSoundTypes.HARD_CANDY).requiresCorrectToolForDrops()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, Supplier<T> block) {
        return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
