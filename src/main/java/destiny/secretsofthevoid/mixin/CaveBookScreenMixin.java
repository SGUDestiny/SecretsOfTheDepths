package destiny.secretsofthevoid.mixin;

import com.github.alexmodguy.alexscaves.client.gui.book.CaveBookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CaveBookScreen.class)
public class CaveBookScreenMixin {
//    @Inject(method = "getBookFileDirectory()Ljava/lang/String;", at = @At("HEAD"), cancellable = true, remap = false)
//    private static void getBookFileDirectory(CallbackInfoReturnable<String> cir) {
//        cir.setReturnValue(SecretsOfTheVoid.MODID + ":books/");
//    }
}
