package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
    public static void bindTexture(Minecraft mc, String path) {
        GL11.glBindTexture(GlConst.GL_TEXTURE_2D, mc.textures.getTextureId(path));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
