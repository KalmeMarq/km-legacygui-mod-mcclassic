package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.LoadLevelScreen;
import com.mojang.minecraft.gui.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadLevelScreen.class)
public class LoadLevelScreenMixin extends Screen {
    @Shadow protected Screen parent;

    @Inject(method = "buttonClicked", at = @At(value = "TAIL"))
    private void buttonClicked(Button par1, CallbackInfo ci) {
       if (par1.id == 6) {
           this.minecraft.openScreen(this.parent);
       }
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/LoadLevelScreen;fillGradient(IIIIII)V"), index = 4)
    private int injectedBgColor1(int color) {
        return -1072689136;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/LoadLevelScreen;fillGradient(IIIIII)V"), index = 5)
    private int injectedBgColor2(int color) {
        return -804253680;
    }
}
