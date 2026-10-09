package destiny.secretsofthevoid.server.capabilities;

import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.mojang.datafixers.util.Pair;
import destiny.secretsofthevoid.client.network.ClientBoundParticlePacket;
import destiny.secretsofthevoid.server.helper.IBacktank;
import destiny.secretsofthevoid.server.helper.IFlippers;
import destiny.secretsofthevoid.server.helper.IMask;
import destiny.secretsofthevoid.server.init.NetworkInit;
import destiny.secretsofthevoid.server.init.SoundInit;
import destiny.secretsofthevoid.server.network.packets.SoundPackets;
import destiny.secretsofthevoid.server.network.packets.UpdateDivingPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DivingCapability implements INBTSerializable<CompoundTag>
{
    public static final String OXYGEN = "oxygen";
    public static final String MAX_OXYGEN = "maxOxygen";
    public static final String MASK_EFFICIENCY = "maskEfficiency";
    public static final String SWIMMING_SPEED = "swimmingSpeed";
    public static final String REFILL_SOUND = "refillSound";

    public double oxygen = 0.0;
    public double maxOxygen = 0.0;
    public double maskEfficiency = 0.0;
    public double swimmingSpeed = 0.0;
    public boolean refillSound = false;
    public int intakeTicker = 0;
    public int expelTicker = -1;
    public int bubbleTicker = -1;

    public final double oxygenDepthModifier = 0.02;
    public final double oxygenPerBreath = 10;

    public DivingCapability()
    {
    }

    //Code that runs each tick
    public void tick(Level level, Player player)
    {
        if(level.isClientSide() || level.getServer() == null)
            return;

        clientUpdate(level, player);

        //Tank
        calculateMaxOxygen(player);
        calculateStoredOxygen(player);
        calculateMaskEfficiency(player);
        calculateDepthEfficiency(player);
        refillTank(level, player);

        if (shouldConsumeOxygen(player) && isPlayerSurvival(player))
            consumeOxygen(level, player);

        if (hasOxygen(player))
            maxOutAirSupply(player);
    }

    public boolean isPlayerSurvival(Player player) {
        return !player.isCreative() && !player.isSpectator();
    }

    public boolean shouldConsumeOxygen(Player player)
    {
        return player.canDrownInFluidType(player.getEyeInFluidType())
                && getOxygen() > 0
                && !getEquipmentBacktank(player, null).isEmpty()
                && !player.getActiveEffects().contains(MobEffects.WATER_BREATHING);
    }

    public boolean hasOxygen(Player player)
    {
        return player.canDrownInFluidType(player.getEyeInFluidType()) && getOxygen() > 0;
    }

    public void refillTank(Level level, Player player) {
        List<Pair<ItemStack, IBacktank>> backtanks = getEquipmentBacktank(player, Comparator.comparing(backtank -> backtank.getSecond().getMaxOxygen(backtank.getFirst())));
        for(Pair<ItemStack, IBacktank> backtank : backtanks)
        {
            ItemStack stack = backtank.getFirst();
            IBacktank tank = backtank.getSecond();

            if (!player.getEyeInFluidType().canDrownIn(player) && getOxygen() < getMaxOxygen()) {
                if (tank.getStoredOxygen(stack) != tank.getMaxOxygen(stack)) {
                    tank.setStoredOxygen(stack, tank.getStoredOxygen(stack) + (tank.getMaxOxygen(stack) / 60));
                }
                if (!getRefillSound()) {
                    level.playSound(null, player.blockPosition().above(), SoundInit.BACKTANK_REFILL.get(), SoundSource.PLAYERS, 0.5f, 1f);
                    setRefillSound(true);
                }
            }
            if (getRefillSound() && player.getEyeInFluidType().canDrownIn(player)) {
                setRefillSound(false);
            }
        }
    }

    public void calculateDepthEfficiency(Player player) {
        if (player.getY() < 64) {
            double depth = (64 - player.getY()) * oxygenDepthModifier;

            setMaskEfficiency(getMaskEfficiency() + depth);
        }
    }

    public void consumeOxygen(Level level, Player player)
    {
        List<Pair<ItemStack, IBacktank>> backtanks = getEquipmentBacktank(player, Comparator.comparing(backtank -> backtank.getSecond().getStoredOxygen(backtank.getFirst())));
        Pair<ItemStack, IBacktank> backtank = backtanks.get(0);
        ItemStack stack = backtank.getFirst();
        IBacktank tank = backtank.getSecond();

        if (intakeTicker > 200) {
            tank.setStoredOxygen(stack, Math.max(0, tank.getStoredOxygen(stack) - (oxygenPerBreath * getMaskEfficiency())));

            if(!getEquipmentMask(player, null).isEmpty()) {
                NetworkInit.sendTo((ServerPlayer) player, new SoundPackets.RebreatherInhale(player.blockPosition()));
            }

            intakeTicker = 0;
            expelTicker = 0;
        } else intakeTicker++;

        if (expelTicker > 60)
        {
            level.playSound(null, player.blockPosition().above(), SoundInit.MASK_EXPEL.get(), SoundSource.PLAYERS, 0.5f, 1f);

            expelTicker = -1;
            bubbleTicker = 0;
        } else if (expelTicker >= 0) {
            expelTicker++;
        }

        if (bubbleTicker > 60) {
            bubbleTicker = -1;
        } else if (bubbleTicker >= 0) {
            Vec3 positionVec = player.getEyePosition();
            Vec3 lookVec = player.getLookAngle();
            positionVec = positionVec.add(lookVec);
            positionVec = positionVec.lerp(player.getEyePosition(), 0.75);

            level.addParticle(ParticleTypes.BUBBLE, positionVec.x, positionVec.y, positionVec.z, 0, 0.1, 0);

            NetworkInit.INSTANCE.send(
                    PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(player.getX(), player.getY(), player.getZ(), 32.0, level.dimension())),
                    new ClientBoundParticlePacket(ForgeRegistries.PARTICLE_TYPES.getKey(ParticleTypes.BUBBLE), positionVec.x, positionVec.y, positionVec.z, 0, 0.1, 0, 1)
            );

            bubbleTicker++;
        }
    }


    public void maxOutAirSupply(Player player)
    {
        player.setAirSupply(player.getMaxAirSupply());
    }

    public void clientUpdate(Level level, Player player)
    {
        if(level != null && !level.isClientSide() && player instanceof ServerPlayer serverPlayer)
        {
            NetworkInit.sendTo(serverPlayer, new UpdateDivingPacket(getOxygen(), getMaxOxygen(), getMaskEfficiency()));
        }
    }

    public void calculateMaxOxygen(Player player) {
        List<Pair<ItemStack, IBacktank>> backtanks = getEquipmentBacktank(player, null);
        double maxOxygen = 0.0D;

        for(Pair<ItemStack, IBacktank> backtank : backtanks)
        {
            ItemStack stack = backtank.getFirst();
            IBacktank tank = backtank.getSecond();

            maxOxygen += tank.getMaxOxygen(stack);
        }

        setMaxOxygen(maxOxygen);
    }

    public void calculateStoredOxygen(Player player) {
        List<Pair<ItemStack, IBacktank>> backtanks = getEquipmentBacktank(player, null);

        double storedOxygen = 0.0D;
        for(Pair<ItemStack, IBacktank> backtank : backtanks)
        {
            ItemStack stack = backtank.getFirst();
            IBacktank tank = backtank.getSecond();

            storedOxygen += tank.getStoredOxygen(stack);
        }
        setOxygen(storedOxygen);

        if(!backtanks.isEmpty()) {
            fillTanks(getEquipmentBacktank(player, Comparator.comparing(backtank -> backtank.getSecond().getMaxOxygen(backtank.getFirst()))), storedOxygen);
        }
    }
    
    public void calculateMaskEfficiency(Player player)
    {
        double maskEfficiency = 0.0D;
        List<Pair<ItemStack, IMask>> masks = getEquipmentMask(player, null);
        for(Pair<ItemStack, IMask> mask : masks)
        {
            ItemStack stack = mask.getFirst();
            IMask maskInternal = mask.getSecond();

            maskEfficiency += maskInternal.getMaskEfficiency(stack);
        }

        setMaskEfficiency(maskEfficiency);
    }
    
    public List<Pair<ItemStack, IBacktank>> getEquipmentBacktank(Player player, @Nullable Comparator<Pair<ItemStack, IBacktank>> comparator)
    {
        List<Pair<ItemStack, IBacktank>> equipmentList = new ArrayList<>();
        for (ItemStack stack : player.getArmorSlots())
        {
            if (stack.getItem() instanceof IBacktank tank)
                equipmentList.add(new Pair<>(stack, tank));
        }

        if (comparator != null) return equipmentList.stream().sorted(comparator).toList();

        return equipmentList;
    }

    public List<Pair<ItemStack, IMask>> getEquipmentMask(Player player, @Nullable Comparator<Pair<ItemStack, IMask>> comparator)
    {
        List<Pair<ItemStack, IMask>> equipmentList = new ArrayList<>();
        for (ItemStack stack : player.getArmorSlots())
        {
            if (stack.getItem() instanceof IMask rebreather)
                equipmentList.add(new Pair<>(stack, rebreather));
        }

        if (comparator != null) return equipmentList.stream().sorted(comparator).toList();

        return equipmentList;
    }

    public List<Pair<ItemStack, IFlippers>> getEquipmentFlippers(Player player, @Nullable Comparator<Pair<ItemStack, IFlippers>> comparator)
    {
        List<Pair<ItemStack, IFlippers>> equipmentList = new ArrayList<>();
        for (ItemStack stack : player.getArmorSlots())
        {
            if (stack.getItem() instanceof IFlippers flippers)
                equipmentList.add(new Pair<>(stack, flippers));
        }

        if (comparator != null) return equipmentList.stream().sorted(comparator).toList();

        return equipmentList;
    }

    public void fillTanks(List<Pair<ItemStack, IBacktank>> backtanks, double storedOxygen)
    {
        for(double i = storedOxygen; i > 0D;) {
            for(Pair<ItemStack, IBacktank> backtank : backtanks)
            {
                ItemStack stack = backtank.getFirst();
                IBacktank tank = backtank.getSecond();
                double maxCapacity = tank.getMaxOxygen(stack);

                tank.setStoredOxygen(stack, Math.min(storedOxygen, maxCapacity));
                i -= Math.min(storedOxygen, maxCapacity);
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putDouble(OXYGEN, oxygen);
        tag.putDouble(MAX_OXYGEN, maxOxygen);
        tag.putDouble(MASK_EFFICIENCY, maskEfficiency);
        tag.putDouble(SWIMMING_SPEED, swimmingSpeed);
        tag.putBoolean(REFILL_SOUND, refillSound);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag)
    {
        this.oxygen = tag.getDouble(OXYGEN);
        this.maxOxygen = tag.getDouble(MAX_OXYGEN);
        this.maskEfficiency = tag.getDouble(MASK_EFFICIENCY);
        this.swimmingSpeed = tag.getDouble(SWIMMING_SPEED);
        this.refillSound = tag.getBoolean(REFILL_SOUND);
    }

    public double getOxygen()
    {
        return oxygen;
    }
    public void setOxygen(double oxygen)
    {
        this.oxygen = oxygen;
    }

    public double getMaxOxygen()
    {
        return maxOxygen;
    }
    public void setMaxOxygen(double maxOxygen)
    {
        this.maxOxygen = maxOxygen;
    }

    public double getMaskEfficiency()
    {
        return maskEfficiency;
    }
    public void setMaskEfficiency(double maskEfficiency)
    {
        this.maskEfficiency = Math.max(0.1, maskEfficiency);
    }

    public double getSwimmingSpeed()
    {
        return swimmingSpeed;
    }
    public void setSwimmingSpeed(double swimmingSpeed)
    {
        this.swimmingSpeed = swimmingSpeed;
    }

    public void setRefillSound(boolean refillSound) {
        this.refillSound = refillSound;
    }
    public boolean getRefillSound() {
        return refillSound;
    }
}
