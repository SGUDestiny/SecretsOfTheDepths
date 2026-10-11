package destiny.secretsofthevoid.server.registry;

import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagRegistry {
    public TagRegistry() {
    }

    public static final TagKey<Item> RESISTS_ACID = registerItemTag("resists_acid");

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(SecretsOfTheVoid.MODID, name));
    }
}
