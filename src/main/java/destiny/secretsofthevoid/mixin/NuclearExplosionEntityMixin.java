package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.NuclearExplosionEntity;
import destiny.secretsofthevoid.server.init.ItemInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NuclearExplosionEntity.class)
public abstract class NuclearExplosionEntityMixin extends Entity {
    public NuclearExplosionEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Unique
    public int affected;
    @Shadow
    public abstract float getSize();

    @Inject(method = "getChunksAffected()I", at = @At("HEAD"), remap = false)
    public void getChunkAffected(CallbackInfoReturnable<Integer> cir) {
        affected = (int)Math.ceil(this.getSize());
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    public void tickInject(CallbackInfo ci) {
        int chunksAffected = affected;
        int radius = chunksAffected * 15;

        AABB killBox = this.getBoundingBox().inflate(radius + radius * 0.5F, radius * 0.6, radius + radius * 0.5F);
        for (ItemEntity itemEntity : this.level().getEntitiesOfClass(ItemEntity.class, killBox)) {
            if (itemEntity.getItem().getItem().equals(ACBlockRegistry.BLOCK_OF_URANIUM.get().asItem())) {
                int count = itemEntity.getItem().getCount();
                itemEntity.setItem(ItemInit.NUCLEAR_PASTA.get().getDefaultInstance());
                itemEntity.getItem().setCount(count);
            }
        }
    }
}
