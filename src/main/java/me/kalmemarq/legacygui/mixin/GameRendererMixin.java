package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.gui.screen.ExtraScreen;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import com.mojang.minecraft.b.d;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(d.class)
public class GameRendererMixin {
    @Shadow public Minecraft minecraft;

    @Inject(method = "a()V", at = @At("HEAD"), cancellable = true)
    public final void a(CallbackInfo ci) {
        int var1 = this.minecraft.width / ExtraScreen.scale;
        int var2 = this.minecraft.height / ExtraScreen.scale;
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)var1, (double)var2, 0.0, 100.0, 300.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -200.0F);
        ci.cancel();
    }
}
