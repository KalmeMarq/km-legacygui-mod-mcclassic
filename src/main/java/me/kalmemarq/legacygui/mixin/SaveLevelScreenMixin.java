package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.SaveLevelScreen;
import com.mojang.minecraft.gui.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SaveLevelScreen.class)
public class SaveLevelScreenMixin extends Screen {
    @Inject(method = "a([Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void a(String[] strings, CallbackInfo ci) {
        for(int var2 = 0; var2 < 5; ++var2) {
            ((Button)this.buttons.get(var2)).msg = strings[var2];
            ((Button)this.buttons.get(var2)).visible = true;
            ((Button)this.buttons.get(var2)).enabled = true;
        }

        ci.cancel();
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(int mouseX, int mouseY, CallbackInfo ci) {
        super.render(mouseX, mouseY);
        ci.cancel();
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/SaveLevelScreen;fillGradient(IIIIII)V"), index = 4)
    private int injectedBgColor1(int color) {
        return -1072689136;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/SaveLevelScreen;fillGradient(IIIIII)V"), index = 5)
    private int injectedBgColor2(int color) {
        return -804253680;
    }
}
