package destiny.secretsofthevoid.server.registry;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SecretsOfTheVoid.MODID);

    public static final RegistryObject<SimpleParticleType> NEUTRON = PARTICLE_TYPES.register("neutron", () -> new SimpleParticleType(false));
}
