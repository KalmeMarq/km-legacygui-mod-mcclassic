package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.LevelLoaderListener;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.StopGameException;
import me.kalmemarq.legacygui.gui.screen.ExtraScreen;
import me.kalmemarq.legacygui.util.BufferBuilder;
import me.kalmemarq.legacygui.util.ExtraTesselator;
import me.kalmemarq.legacygui.util.GlConst;
import me.kalmemarq.legacygui.util.RenderHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoaderListener.class)
public class LevelLoaderListenerMixin {
    @Shadow private Minecraft minecraft;

    @Shadow private String title;

    @Shadow private String stage;

    @Shadow private long lastUpdateTime;

    public int calculateScale(int var1, int width, int height) {
        int var3;
        for(var3 = 1; var3 != var1 && var3 < width && var3 < height && width / (var3 + 1) >= 320 && height / (var3 + 1) >= 240; ++var3) {
        }

        return var3;
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(int progress, CallbackInfo ci) {
        if (!((MinecraftAccessor)(Object)this.minecraft).getRunning()) {
            throw new StopGameException();
        } else {
            long var2;
            if ((var2 = System.currentTimeMillis()) - this.lastUpdateTime < 0L || var2 - this.lastUpdateTime >= 20L) {
                GL11.glClear(16640);

                ExtraTesselator tesselator = ExtraTesselator.getInstance();
                BufferBuilder builder = tesselator.getBuilder();

                RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderHelper.bindTexture("/dirt.png");

                int width = this.minecraft.parent.getWidth();
                int height = this.minecraft.parent.getHeight();
                int scale = this.calculateScale(3, width, height);
                width = width / scale;
                height = height / scale;

                builder.begin(GlConst.GL_QUADS);
                builder.vertex(0.0f, (float)height, 0.0f).uv(0.0F, (float)height / 32.0F + (float)0).color(64, 64, 64, 255).endVertex();
                builder.vertex((float)width, (float)height, 0.0f).uv((float)width / 32.0F, (float)height / 32.0F + (float)0).color(64, 64, 64, 255).endVertex();
                builder.vertex((float)width, 0.0f, 0.0f).uv((float)width / 32.0F, (float)0).color(64, 64, 64, 255).endVertex();
                builder.vertex(0.0f, 0.0f, 0.0f).uv(0.0F, (float)0).color(64, 64, 64, 255).endVertex();
                tesselator.end();

                this.minecraft.font.drawShadow(this.title, (width - this.minecraft.font.width(title)) / 2, height / 2 - 4 - 16, 0xFFFFFF);
                this.minecraft.font.drawShadow(this.stage, (width - this.minecraft.font.width(stage)) / 2, height / 2 - 4 + 8, 0xFFFFFF);

                Display.update();

                try {
                    Thread.yield();
                } catch (Exception ignored) {
                }
            }
        }

        ci.cancel();
    }
}
