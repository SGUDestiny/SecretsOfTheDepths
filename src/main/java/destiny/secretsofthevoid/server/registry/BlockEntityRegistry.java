package destiny.secretsofthevoid.server.registry;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.blocks.blockentity.OxygenVentBlockEntity;
import destiny.secretsofthevoid.server.blocks.blockentity.SuspiciousBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SecretsOfTheVoid.MODID);

    public static final RegistryObject<BlockEntityType<OxygenVentBlockEntity>> OXYGEN_VENT = DEF_REG.register("oxygen_vent", () -> BlockEntityType.
            Builder.of(OxygenVentBlockEntity::new, BlockRegistry.OXYGEN_VENT.get()).build(null));

    public static final RegistryObject<BlockEntityType<SuspiciousBlockEntity>> SUSPICIOUS_BLOCK = DEF_REG.register("suspicious_block", () -> BlockEntityType.
            Builder.of(SuspiciousBlockEntity::new, BlockRegistry.SUSPICIOUS_MUCK.get()).build(null));
}
