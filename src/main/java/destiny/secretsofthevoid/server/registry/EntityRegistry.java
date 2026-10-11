package destiny.secretsofthevoid.server.registry;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.entity.PowerArmorEntity;
import destiny.secretsofthevoid.server.entity.SievertRodHookEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SecretsOfTheVoid.MODID);

    public static final RegistryObject<EntityType<PowerArmorEntity>> POWER_ARMOR = ENTITIES.register("power_armor",
            () -> EntityType.Builder.of((EntityType<PowerArmorEntity> pEntityType, Level pLevel) -> new PowerArmorEntity(pLevel), MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(64)
                    .setCustomClientFactory((spawnEntity, level) -> new PowerArmorEntity(level))
                    .build("power_armor"));

    public static final RegistryObject<EntityType<SievertRodHookEntity>> SIEVERT_ROD_HOOK = ENTITIES.register("sievert_rod_hook",
            () -> EntityType.Builder.of((EntityType<SievertRodHookEntity> pEntityType, Level pLevel) -> new SievertRodHookEntity(pEntityType, pLevel), MobCategory.MISC)
                    .noSave()
                    .noSummon()
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(5)
                    .build("sievert_rod_hook"));

    public static void register(IEventBus bus)
    {
        ENTITIES.register(bus);
    }
}
