package me.kalmemarq.legacygui.mixin;

import org.spongepowered.asm.mixin.Mixin;
import com.mojang.minecraft.e.c;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(c.class)
public class ControlsScreenMixin {
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/e/c;fillGradient(IIIIII)V"), index = 4)
    private int injectedBgColor1(int color) {
        return -1072689136;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/e/c;fillGradient(IIIIII)V"), index = 5)
    private int injectedBgColor2(int color) {
        return -804253680;
    }
}
