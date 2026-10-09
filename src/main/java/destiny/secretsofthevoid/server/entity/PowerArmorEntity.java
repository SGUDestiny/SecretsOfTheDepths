package destiny.secretsofthevoid.server.entity;

import destiny.secretsofthevoid.server.init.EntityInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PowerArmorEntity extends Entity implements GeoEntity
{
    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    public PowerArmorEntity(Level pLevel)
    {
        super(EntityInit.POWER_ARMOR.get(), pLevel);
    }

    @Override
    protected void defineSynchedData()
    {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound)
    {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound)
    {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers)
    {
        controllers.add(new AnimationController<>(this, "legs", 4, this::legController));
        controllers.add(new AnimationController<>(this, "left_arm", 4, this::leftArmController));
        controllers.add(new AnimationController<>(this, "right_arm", 4, this::rightArmController));

    }

    @Override
    public void tick()
    {
        super.tick();
    }

    protected <E extends PowerArmorEntity> PlayState legController(final AnimationState<E> event)
    {
        return PlayState.STOP;
    }

    protected <E extends PowerArmorEntity> PlayState leftArmController(final AnimationState<E> event)
    {
        return PlayState.STOP;
    }

    protected <E extends PowerArmorEntity> PlayState rightArmController(final AnimationState<E> event)
    {
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return animCache;
    }
}
