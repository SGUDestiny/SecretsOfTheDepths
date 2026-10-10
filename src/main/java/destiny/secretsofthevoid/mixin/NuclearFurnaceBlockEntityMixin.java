package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.particle.ACParticleRegistry;
import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import com.github.alexmodguy.alexscaves.server.misc.ACAdvancementTriggerRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACTagRegistry;
import destiny.secretsofthevoid.server.util.IGammaRodHandleFurnace;
import destiny.secretsofthevoid.server.network.packets.UpdateFurnaceItemPacket;
import destiny.secretsofthevoid.server.registry.BlockRegistry;
import destiny.secretsofthevoid.server.registry.NetworkRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity.MAX_BARRELING_TIME;
import static com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity.MAX_WASTE;

@Mixin(NuclearFurnaceBlockEntity.class)
public abstract class NuclearFurnaceBlockEntityMixin implements IGammaRodHandleFurnace
{
    @Shadow
    protected NonNullList<ItemStack> items;
    @Shadow private int maxCookTime;
//    @Shadow private int currentWaste;
//    @Shadow private int barrelTime;
//    @Shadow private int fissionTime;
//    @Shadow private int cookTime;

//    @Mutable
//    @Shadow @Final protected ContainerData dataAccess;
    @Unique
    private boolean fissioningGammaRod = false;

//    @Inject(method = "<init>", at = @At("RETURN"))
//    public void overrideDataAccess(BlockPos pos, BlockState state, CallbackInfo ci)
//    {
//        dataAccess = new ContainerData() {
//            public int get(int type) {
//                switch (type) {
//                    case 0 -> {
//                        return currentWaste;
//                    }
//                    case 1 -> {
//                        return barrelTime;
//                    }
//                    case 2 -> {
//                        return fissionTime;
//                    }
//                    case 3 -> {
//                        return cookTime;
//                    }
//                    case 4 -> {
//                        return maxCookTime;
//                    }
//                    case 5 ->
//                    {
//                        return isFissioningGammaRod() ? 1 : 0;
//                    }
//                    default -> {
//                        return 0;
//                    }
//                }
//            }
//
//            public void set(int type, int value) {
//                switch (type) {
//                    case 0:
//                        currentWaste = value;
//                    case 1:
//                        barrelTime = value;
//                    case 2:
//                        fissionTime = value;
//                    case 3:
//                        cookTime = value;
//                    case 4:
//                        maxCookTime = value;
//                    case 5:
//                        setFissioningGammaRod(value == 1);
//                    default:
//                }
//            }
//
//            public int getCount() {
//                return 6;
//            }
//        };
//    }

    @Inject(method = "getMaxFissionTime", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getMaxFissionTime(CallbackInfoReturnable<Integer> cir)
    {
        cir.setReturnValue((int) Math.ceil(6400 * 0.2));
    }

    @Inject(method = "getSpeedReduction", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getSpeedReduction(CallbackInfoReturnable<Float> cir)
    {
        cir.setReturnValue(0.2f);
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"), remap = false)
    private void saveGammaRod(CompoundTag tag, CallbackInfo ci)
    {
        tag.putBoolean("gamma_rod", fissioningGammaRod);
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"), remap = false)
    private void loadGammaRod(CompoundTag tag, CallbackInfo ci)
    {
        if(tag.contains("gamma_rod"))
            fissioningGammaRod = tag.getBoolean("gamma_rod");
    }

    /**
     * @author mistersecret312
     * @reason confusing and bad, so here's better :3
     */
    @Overwrite(remap = false)
    public static void tick(Level level, BlockPos blockPos, BlockState state, NuclearFurnaceBlockEntity entity)
    {
        ++entity.age;
        IAccessNuclearFurnaceBlockEntity blockEntity = ((IAccessNuclearFurnaceBlockEntity) entity);
        IGammaRodHandleFurnace gammaRodHandler = ((IGammaRodHandleFurnace) entity);

        if (entity.getCriticality() >= 3)
        {
            Vec3 vec3 = entity.getExhaustPos();
            if (!level.isClientSide && (double)level.random.nextFloat() < 0.2)
                blockEntity.firespread(level, 6);

            level.addAlwaysVisibleParticle(
					level.random.nextInt(3) == 0 ? ParticleTypes.LAVA : ACParticleRegistry.MUSHROOM_CLOUD_SMOKE.get(), true, vec3.x, vec3.y + (double)1.0F, vec3.z,
					(level.random.nextFloat() - 0.5F) * 0.2F, 0.1F + level.random.nextFloat() * 0.2F, (level.random.nextFloat() - 0.5F) * 0.2F);
        }
        else if (entity.getCriticality() == 2)
        {
            Vec3 vec3 = entity.getExhaustPos();
            if (!level.isClientSide && (double)level.random.nextFloat() < 0.05)
                blockEntity.firespread(level, 2);

            level.addAlwaysVisibleParticle(ParticleTypes.LARGE_SMOKE, true, vec3.x, vec3.y, vec3.z,
					(level.random.nextFloat() - 0.5F) * 0.1F, level.random.nextFloat() * 0.1F, (level.random.nextFloat() - 0.5F) * 0.1F);
        }
        else if (entity.isUndergoingFission() && level.random.nextFloat() < (float) entity.getCriticality() * 0.35F + 0.15F)
        {
            Vec3 vec3 = entity.getExhaustPos();
            ParticleOptions particleOptions = ACParticleRegistry.HAZMAT_BREATHE.get();
            if(gammaRodHandler.isFissioningGammaRod())
                particleOptions = ACParticleRegistry.BLUE_HAZMAT_BREATHE.get();
            if (entity.getCriticality() == 1 && level.random.nextFloat() < 0.1F)
                particleOptions = ParticleTypes.LARGE_SMOKE;

            level.addAlwaysVisibleParticle(particleOptions, true, vec3.x, vec3.y, vec3.z,
					(level.random.nextFloat() - 0.5F) * 0.7F, level.random.nextFloat() * 0.1F, (level.random.nextFloat() - 0.5F) * 0.7F);
        }

        if (!level.isClientSide)
        {
            boolean flag;
            ItemStack cookStack = blockEntity.getItems().get(0);
            ItemStack rodStack = blockEntity.getItems().get(1);
            ItemStack barrelStack = blockEntity.getItems().get(2);
            if (!cookStack.isEmpty()) {
                if (blockEntity.getCurrentRecipe() != null && blockEntity.getCurrentRecipe().getIngredients().get(0).test(cookStack))
                {
                    ItemStack cookResult = blockEntity.getCurrentRecipe().getResultItem(level.registryAccess());
                    blockEntity.setMaxCookTime(20);
                    if (blockEntity.canFit(cookResult, 3))
                    {
                        if (blockEntity.getFissionTime() <= 0)
                        {
                            if (!rodStack.isEmpty() && rodStack.is(BlockRegistry.GAMMA_ROD.get().asItem()))
                            {
                                gammaRodHandler.setFissioningGammaRod(true);
                                blockEntity.setFissionTime(2440);
                                rodStack.shrink(1);
                                blockEntity.setCurrentWaste(blockEntity.getCurrentWaste() + 100);
                            }
                            else if (!rodStack.isEmpty() && rodStack.is(ACTagRegistry.NUCLEAR_FURNACE_RODS))
                            {
                                blockEntity.setFissionTime(1360);
                                rodStack.shrink(1);
                                blockEntity.setCurrentWaste(blockEntity.getCurrentWaste() + 100);
                            }
                            blockEntity.resetCookingTime();
                        }
                        else if (blockEntity.getCookTime() < blockEntity.getMaxCookTime())
                        {
                            flag = true;
                            blockEntity.setCookTime(blockEntity.getCookTime()+1);
                        }
                        else
                        {
                            entity.setRecipeUsed(blockEntity.getCurrentRecipe());
                            blockEntity.resetCookingTime();
                            cookStack.shrink(1);
                            if (ItemStack.isSameItem(blockEntity.getItems().get(3), cookResult))
                                blockEntity.getItems().get(3).grow(cookResult.getCount());
                            else entity.setItem(3, cookResult.copy());

                            flag = true;
                        }
                    }
                    else blockEntity.resetCookingTime();
                }
                else blockEntity.setCurrentRecipe(blockEntity.getCookingRecipe(cookStack).orElse(null));

            }
            else
            {
                blockEntity.setCurrentRecipe(null);
                blockEntity.resetCookingTime();
            }

            if (blockEntity.getFissionTime() > 0)
            {
                if(gammaRodHandler.isFissioningGammaRod() && blockEntity.getCookTime() < blockEntity.getMaxCookTime())
                    blockEntity.setFissionTime(blockEntity.getFissionTime()-1);

                if(!gammaRodHandler.isFissioningGammaRod())
                    blockEntity.setFissionTime(blockEntity.getFissionTime()-1);

                if(blockEntity.getFissionTime() == 0 && gammaRodHandler.isFissioningGammaRod())
                    gammaRodHandler.setFissioningGammaRod(false);

                flag = true;
            }

            if (blockEntity.getCurrentWaste() >= 100 && barrelStack.is(ACTagRegistry.NUCLEAR_FURNACE_BARRELS) && blockEntity.canFit(new ItemStack(ACBlockRegistry.WASTE_DRUM.get()), 4))
            {
                flag = true;
                if (blockEntity.getBarrelTime() < MAX_BARRELING_TIME)
                    blockEntity.setBarrelTime(blockEntity.getBarrelTime() + 1);
                else
                {
                    ItemStack wasteDrum = new ItemStack(ACBlockRegistry.WASTE_DRUM.get());
                    blockEntity.setBarrelTime(0);
                    barrelStack.shrink(1);
                    float prevCriticality = (float)entity.getCriticality();
                    blockEntity.setCurrentWaste(blockEntity.getCurrentWaste() - 100);
                    if (prevCriticality == 3.0F && entity.getCriticality() <= 2 && blockEntity.getLastPlayer() != null)
                        ACAdvancementTriggerRegistry.STOP_NUCLEAR_FURNACE_MELTDOWN.triggerForEntity(blockEntity.getLastPlayer());

                    if (ItemStack.isSameItem(blockEntity.getItems().get(4), wasteDrum))
                        blockEntity.getItems().get(4).grow(1);
                    else entity.setItem(4, wasteDrum);
                }
            }
            else
            {
                blockEntity.setBarrelTime(0);
                flag = true;
            }

            if (flag)
                blockEntity.syncClientWith();

            if (blockEntity.getCurrentWaste() >= MAX_WASTE)
                entity.destroyWhileCritical(true);
        }
        else if (entity.isUndergoingFission() && !entity.isRemoved())
            AlexsCaves.PROXY.playWorldSound(entity, (byte)7);
    }


    @Inject(method = "setItem", at = @At("TAIL"), remap = false)
    public void updateFurnaceItem(int slot, ItemStack itemStack, CallbackInfo ci)
    {
        NuclearFurnaceBlockEntity blockEntity = ((NuclearFurnaceBlockEntity) (Object) this);
        BlockPos pos = blockEntity.getBlockPos();
        NetworkRegistry.sendToTracking(blockEntity, new UpdateFurnaceItemPacket(pos, slot, itemStack));
    }

    @Inject(method = "resetCookTime()V", at = @At("TAIL"), remap = false)
    public void resetMaxCookTime(CallbackInfo ci)
    {
        this.maxCookTime = 0;
    }

    @Override
    public boolean isFissioningGammaRod()
    {
        return fissioningGammaRod;
    }

    @Override
    public void setFissioningGammaRod(boolean val)
    {
        this.fissioningGammaRod = val;
    }
}
