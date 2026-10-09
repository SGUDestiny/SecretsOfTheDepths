package destiny.secretsofthevoid.server.init;

import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import destiny.secretsofthevoid.server.SecretsOfTheVoid;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ArmorMaterialsInit implements ArmorMaterial {

    //Armor
    ABYSSALITH("tools", 46, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
        p_266655_.put(ArmorItem.Type.BOOTS, 4);
        p_266655_.put(ArmorItem.Type.LEGGINGS, 7);
        p_266655_.put(ArmorItem.Type.CHESTPLATE, 9);
        p_266655_.put(ArmorItem.Type.HELMET, 4);
    }), 18, SoundEvents.ARMOR_EQUIP_TURTLE, 5.0F, 0.3F, () -> {
        return Ingredient.of(ItemInit.ABYSSALITH_CORE.get());
    }),

    //Diving Gear
    PEARL_DIVING_GEAR("pearl_diving_gear", 28, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
        p_266655_.put(ArmorItem.Type.BOOTS, 2);
        p_266655_.put(ArmorItem.Type.LEGGINGS, 2);
        p_266655_.put(ArmorItem.Type.CHESTPLATE, 2);
        p_266655_.put(ArmorItem.Type.HELMET, 1);
    }), 13, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> {
        return Ingredient.of(ACItemRegistry.PEARL.get());
    }),
    NETHERITE_DIVING_GEAR("netherite_diving_gear", 32, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
        p_266655_.put(ArmorItem.Type.BOOTS, 2);
        p_266655_.put(ArmorItem.Type.LEGGINGS, 3);
        p_266655_.put(ArmorItem.Type.CHESTPLATE, 3);
        p_266655_.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 1.5F, 0.05F, () -> {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }),
    ABYSSALITH_DIVING_GEAR("abyssalith_diving_gear", 41, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
        p_266655_.put(ArmorItem.Type.BOOTS, 2);
        p_266655_.put(ArmorItem.Type.LEGGINGS, 4);
        p_266655_.put(ArmorItem.Type.CHESTPLATE, 5);
        p_266655_.put(ArmorItem.Type.HELMET, 2);
    }), 18, SoundEvents.ARMOR_EQUIP_GOLD, 2.5F, 0.15F, () -> {
        return Ingredient.of(ItemInit.ABYSSALITH_CORE.get());
    });

    public static final StringRepresentable.EnumCodec<ArmorMaterials> CODEC = StringRepresentable.fromEnum(ArmorMaterials::values);
    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
        p_266653_.put(ArmorItem.Type.BOOTS, 13);
        p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
        p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
        p_266653_.put(ArmorItem.Type.HELMET, 11);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private ArmorMaterialsInit(String pName, int pDurabilityMultiplier, EnumMap<ArmorItem.Type, Integer> pProtectionFunctionForType, int pEnchantmentValue, SoundEvent pSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient) {
        this.name = pName;
        this.durabilityMultiplier = pDurabilityMultiplier;
        this.protectionFunctionForType = pProtectionFunctionForType;
        this.enchantmentValue = pEnchantmentValue;
        this.sound = pSound;
        this.toughness = pToughness;
        this.knockbackResistance = pKnockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
    }

    public int getDurabilityForType(ArmorItem.Type pType) {
        return HEALTH_FUNCTION_FOR_TYPE.get(pType) * this.durabilityMultiplier;
    }

    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionFunctionForType.get(pType);
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return SecretsOfTheVoid.MODID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public String getSerializedName() {
        return this.name;
    }
}
