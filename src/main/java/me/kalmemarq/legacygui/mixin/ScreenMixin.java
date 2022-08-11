package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow protected List buttons;

    @Inject(method = "init(Lcom/mojang/minecraft/Minecraft;II)V", at = @At("HEAD"))
    public void init(Minecraft width, int height, int par3, CallbackInfo ci) {
        this.buttons.clear();
    }
}
