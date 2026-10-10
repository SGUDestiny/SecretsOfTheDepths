package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(NuclearFurnaceBlockEntity.class)
public interface IAccessNuclearFurnaceBlockEntity
{
	@Invoker("spreadFire")
	void firespread(Level level, int range);
	@Invoker("canFitInResultSlot")
	boolean canFit(ItemStack putIn, int resultSlot);
	@Invoker("resetCookTime")
	void resetCookingTime();
	@Invoker("getRecipeFor")
	Optional<? extends AbstractCookingRecipe> getCookingRecipe(ItemStack stack);
	@Invoker("syncWithClient")
	void syncClientWith();

	@Accessor("items")
	NonNullList<ItemStack> getItems();
	@Accessor("currentRecipe")
	AbstractCookingRecipe getCurrentRecipe();
	@Accessor("currentRecipe")
	void setCurrentRecipe(AbstractCookingRecipe recipe);
	@Accessor("lastInteractedWithPlayer")
	Player getLastPlayer();


	@Accessor("cookTime")
	int getCookTime();
	@Accessor("cookTime")
	void setCookTime(int cookTime);

	@Accessor("maxCookTime")
	int getMaxCookTime();
	@Accessor("maxCookTime")
	void setMaxCookTime(int maxCookTime);

	@Accessor("fissionTime")
	int getFissionTime();
	@Accessor("fissionTime")
	void setFissionTime(int fissionTime);

	@Accessor("currentWaste")
	int getCurrentWaste();
	@Accessor("currentWaste")
	void setCurrentWaste(int currentWaste);

	@Accessor("barrelTime")
	int getBarrelTime();
	@Accessor("barrelTime")
	void setBarrelTime(int barrelTime);
}
