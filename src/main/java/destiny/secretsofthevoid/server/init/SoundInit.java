package destiny.secretsofthevoid.server.init;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SecretsOfTheVoid.MODID);

    //Diving Mask
    public static RegistryObject<SoundEvent> MASK_INTAKE = registerSoundEvent("mask_intake");
    public static RegistryObject<SoundEvent> MASK_EXPEL = registerSoundEvent("mask_expel");

    //Backtank
    public static RegistryObject<SoundEvent> BACKTANK_REFILL = registerSoundEvent("backtank_refill");

    //Discs
    public static RegistryObject<SoundEvent> DISK_HADAL = registerSoundEvent("disc_hadal");
    public static RegistryObject<SoundEvent> DISK_HADAL_AMBIENT = registerSoundEvent("disc_hadal_ambient");

    private static RegistryObject<SoundEvent> registerSoundEvent(String sound) {
        return SOUNDS.register(sound, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SecretsOfTheVoid.MODID, sound)));
    }
}
