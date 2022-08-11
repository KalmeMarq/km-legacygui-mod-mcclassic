package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.PauseScreen;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {
    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
//        this.buttons.add(new Button(200, this.width / 2 - 100, this.height / 4 + 96, "Quit to title"));
    }

    @Inject(method = "buttonClicked", at = @At("TAIL"))
    private void buttonClicked(Button button, CallbackInfo ci) {
        if (button.id == 200) {
            this.minecraft.openScreen(new TitleScreen());
        }
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/PauseScreen;fillGradient(IIIIII)V"), index = 4)
    private int injectedBgColor1(int color) {
        return -1072689136;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/PauseScreen;fillGradient(IIIIII)V"), index = 5)
    private int injectedBgColor2(int color) {
        return -804253680;
    }
}
