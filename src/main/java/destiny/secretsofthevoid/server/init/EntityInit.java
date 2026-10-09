package destiny.secretsofthevoid.server.init;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import destiny.secretsofthevoid.server.entity.PowerArmorEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SecretsOfTheVoid.MODID);

    public static final RegistryObject<EntityType<PowerArmorEntity>> POWER_ARMOR = ENTITIES.register("medusa",
            () -> EntityType.Builder.of((EntityType<PowerArmorEntity> pEntityType, Level pLevel) -> new PowerArmorEntity(pLevel), MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(64).setCustomClientFactory((spawnEntity, level) -> new PowerArmorEntity(level)).build("power_armor"));

    public static void register(IEventBus bus)
    {
        ENTITIES.register(bus);
    }
}
