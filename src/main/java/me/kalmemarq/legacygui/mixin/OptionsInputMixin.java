package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.player.Input;
import com.mojang.minecraft.player.OptionsInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsInput.class)
public class OptionsInputMixin extends Input {
    @Shadow private boolean[] d;

    @Inject(method = "a()V", at = @At("HEAD"), cancellable = true)
    public void a(CallbackInfo ci) {
        this.a = 0.0F;
        this.b = 0.0F;
        if (this.d[0]) {
            --this.b;
        }

        if (this.d[1]) {
            ++this.b;
        }

        if (this.d[2]) {
            --this.a;
        }

        if (this.d[3]) {
            ++this.a;
        }

        this.c = this.d[4];

        ci.cancel();
    }
}
