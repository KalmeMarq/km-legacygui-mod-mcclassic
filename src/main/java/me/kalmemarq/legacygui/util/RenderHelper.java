package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.LegacyGUIMod;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
    public static void bindTexture(String path) {
        LegacyGUIMod.textureManager.bind(path);
    }

    public static void setGlobalColor(float red, float green, float blue, float alpha) {
        GL11.glColor4f(red, green, blue, alpha);
    }

    @Deprecated
    public static void bindTexture(Minecraft mc, String path) {
        LegacyGUIMod.textureManager.bind(path);
    }

    public static void disableTexture() {
        GL11.glDisable(GlConst.GL_TEXTURE_2D);
    }

    public static void enableTexture() {
        GL11.glEnable(GlConst.GL_TEXTURE_2D);
    }

    public static void disableBlend() {
        GL11.glDisable(GlConst.GL_BLEND);
    }

    public static void enableBlend() {
        GL11.glEnable(GlConst.GL_BLEND);
    }

    public static void defaultBlendFunc() {
        GL11.glBlendFunc(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA);
    }
}
