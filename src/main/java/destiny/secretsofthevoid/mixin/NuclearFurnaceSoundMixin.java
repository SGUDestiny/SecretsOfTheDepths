package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.client.sound.BlockEntityTickableSound;
import com.github.alexmodguy.alexscaves.client.sound.NuclearFurnaceSound;
import com.github.alexmodguy.alexscaves.server.block.blockentity.NuclearFurnaceBlockEntity;
import destiny.secretsofthevoid.server.util.IGammaRodHandleFurnace;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.*;

@Mixin(NuclearFurnaceSound.class)
public class NuclearFurnaceSoundMixin extends BlockEntityTickableSound<NuclearFurnaceBlockEntity> {
    @Mutable
    @Final
    @Shadow(remap = false)
    private final int criticality;
    @Shadow(remap = false)
    private int fade = 0;

    public NuclearFurnaceSoundMixin(SoundEvent soundEvent, NuclearFurnaceBlockEntity furnace) {
        super(soundEvent, furnace);
        this.criticality = furnace.getCriticality();
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void tick() {
        if ((this.blockEntity.isUndergoingFission() || this.criticality > 0) && this.criticality == this.blockEntity.getCriticality()) {
            this.x = (double) this.blockEntity.getBlockPos().getX() + (double)1.0F;
            this.y = (double) this.blockEntity.getBlockPos().getY() + (double)1.0F;
            this.z = (double) this.blockEntity.getBlockPos().getZ() + (double)1.0F;

            IGammaRodHandleFurnace gammaRodHandler = ((IGammaRodHandleFurnace) blockEntity);
            if (gammaRodHandler.isFissioningGammaRod()) {
                this.pitch = 1.25F;
            } else {
                this.pitch = 1.0F;
            }

            if (this.fade > 0) {
                --this.fade;
            }
        } else {
            ++this.fade;
        }

        this.volume = Mth.clamp(1.0F - (float)this.fade / 40.0F, 0.0F, 1.0F);
        if (this.fade > 40) {
            this.stop();
        }
    }
}
