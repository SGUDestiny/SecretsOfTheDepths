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
	@Invoker(value = "spreadFire", remap = false)
	void firespread(Level level, int range);
	@Invoker(value = "canFitInResultSlot", remap = false)
	boolean canFit(ItemStack putIn, int resultSlot);
	@Invoker(value = "resetCookTime", remap = false)
	void resetCookingTime();
	@Invoker(value = "getRecipeFor", remap = false)
	Optional<? extends AbstractCookingRecipe> getCookingRecipe(ItemStack stack);
	@Invoker(value = "syncWithClient", remap = false)
	void syncClientWith();

	@Accessor(value = "items", remap = false)
	NonNullList<ItemStack> getItems();
	@Accessor(value = "currentRecipe", remap = false)
	AbstractCookingRecipe getCurrentRecipe();
	@Accessor(value = "currentRecipe", remap = false)
	void setCurrentRecipe(AbstractCookingRecipe recipe);
	@Accessor(value = "lastInteractedWithPlayer", remap = false)
	Player getLastPlayer();


	@Accessor(value = "cookTime", remap = false)
	int getCookTime();
	@Accessor(value = "cookTime", remap = false)
	void setCookTime(int cookTime);

	@Accessor(value = "maxCookTime", remap = false)
	int getMaxCookTime();
	@Accessor(value = "maxCookTime", remap = false)
	void setMaxCookTime(int maxCookTime);

	@Accessor(value = "fissionTime", remap = false)
	int getFissionTime();
	@Accessor(value = "fissionTime", remap = false)
	void setFissionTime(int fissionTime);

	@Accessor(value = "currentWaste", remap = false)
	int getCurrentWaste();
	@Accessor(value = "currentWaste", remap = false)
	void setCurrentWaste(int currentWaste);

	@Accessor(value = "barrelTime", remap = false)
	int getBarrelTime();
	@Accessor(value = "barrelTime", remap = false)
	void setBarrelTime(int barrelTime);
}
