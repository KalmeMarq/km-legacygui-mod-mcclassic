package me.kalmemarq.legacygui.mixin;


import com.mojang.minecraft.gui.OptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin {
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/OptionsScreen;fillGradient(IIIIII)V"), index = 4)
    private int injectedBgColor1(int color) {
        return -1072689136;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/OptionsScreen;fillGradient(IIIIII)V"), index = 5)
    private int injectedBgColor2(int color) {
        return -804253680;
    }
}
