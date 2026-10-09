package destiny.secretsofthevoid.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
//
//    @Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidFallingAdjustedMovement(DZLnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
//    public void dontSink(Vec3 pTravelVector, CallbackInfo ci, double d0, AttributeInstance gravity, boolean flag, FluidState fluidstate, double d9, float f4, float f5, float f6, Vec3 vec36)
//    {
//        LivingEntity living = ((LivingEntity) (Object) this);
//        if(living instanceof Player player)
//        {
//            Optional<DivingCapability> capability = player.getCapability(CapabilityRegistry.DIVING).resolve();
//            if (capability.isPresent() && !capability.get().getEquipmentFlippers(player, null).isEmpty())
//                d0 = 0D;
//        }
//    }
}
