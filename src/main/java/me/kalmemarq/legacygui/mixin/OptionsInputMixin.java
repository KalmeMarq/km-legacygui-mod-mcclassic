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
    @Shadow private boolean[] keys;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void a(CallbackInfo ci) {
        this.xxa = 0.0F;
        this.yya = 0.0F;
        if (this.keys[0]) {
            --this.yya;
        }

        if (this.keys[1]) {
            ++this.yya;
        }

        if (this.keys[2]) {
            --this.xxa;
        }

        if (this.keys[3]) {
            ++this.xxa;
        }

        this.jumping = this.keys[4];

        ci.cancel();
    }
}
