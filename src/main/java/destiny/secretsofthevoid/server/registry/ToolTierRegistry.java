package destiny.secretsofthevoid.server.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum ToolTierRegistry implements Tier {
    //Vanilla tiers for reference
    //WOOD(0, 59, 2.0F, 0.0F, 15, () -> {
    //   return Ingredient.of(ItemTags.PLANKS);
    //}),
    //STONE(1, 131, 4.0F, 1.0F, 5, () -> {
    //   return Ingredient.of(ItemTags.STONE_TOOL_MATERIALS);
    //}),
    //IRON(2, 250, 6.0F, 2.0F, 14, () -> {
    //   return Ingredient.of(Items.IRON_INGOT);
    //}),
    //DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> {
    //   return Ingredient.of(Items.DIAMOND);
    //}),
    //GOLD(0, 32, 12.0F, 0.0F, 22, () -> {
    //   return Ingredient.of(Items.GOLD_INGOT);
    //}),
    //NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> {
    //   return Ingredient.of(Items.NETHERITE_INGOT);
    //});

    //Tool tiers
    HADAL(5, 3800, 13.0F, 5F, 18, ItemRegistry.ABYSSALITH_CORE.get()),

    //Special item tiers
    TRENCHBLEEDER(5, 4500, 13.0F, 10F, 25, ItemRegistry.ABYSSALITH_CORE.get());

    private float attackDamage, efficiency;
    private int durability, harvestLevel, enchantability;
    private Item repairMaterial;

    private ToolTierRegistry(int harvestLevel, int durability, float efficiency, float attackDamage, int enchantability, Item repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.durability = durability;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses()
    {
        return this.durability;
    }

    @Override
    public float getSpeed()
    {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus()
    {
        return this.attackDamage;
    }

    @Override
    public int getLevel()
    {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue()
    {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient()
    {
        return Ingredient.of(repairMaterial);
    }
}