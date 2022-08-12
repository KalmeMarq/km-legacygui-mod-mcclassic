package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gamemode.CreativeMode;
import com.mojang.minecraft.gamemode.GameMode;
import me.kalmemarq.legacygui.gui.screen.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeMode.class)
public class CreativeModeMixin extends GameMode {
    public CreativeModeMixin(Minecraft minecraft) {
        super(minecraft);
    }

    @Inject(method = "openBuildScreen", at = @At("HEAD"))
    public void a(CallbackInfo ci) {
        this.minecraft.openScreen(new CreativeInventoryScreen());
    }
}
