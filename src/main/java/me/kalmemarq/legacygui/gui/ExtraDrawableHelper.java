package me.kalmemarq.legacygui.gui;

import com.mojang.minecraft.gui.DrawableHelper;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.util.BufferBuilder;
import me.kalmemarq.legacygui.util.ExtraTesselator;
import me.kalmemarq.legacygui.util.GlConst;
import org.lwjgl.opengl.GL11;

public class ExtraDrawableHelper extends DrawableHelper {
    protected void hLineXX(int x0, int x1, int y, int color) {
        if (x1 < x0) {
            int temp = x0;
            x0 = x1;
            x1 = temp;
        }

        fillXX(x0, y, x1 + 1, y + 1, color);
    }

    protected void vLineXX(int x0, int y0, int y1, int color) {
        if (y1 < y0) {
            int temp = y0;
            y0 = y1;
            y1 = temp;
        }

        fill(x0, y0 + 1, x0 + 1, y1, color);
    }

    public static void fillXX(int x0, int y0, int x1, int y1, int color) {
        int temp;
        if (x0 < x1) {
            temp = x0;
            x0 = x1;
            x1 = temp;
        }

        if (y0 < y1) {
            temp = y0;
            y0 = y1;
            y1 = temp;
        }

        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        GL11.glEnable(GlConst.GL_BLEND);
        GL11.glDisable(GlConst.GL_TEXTURE_2D);
        GL11.glBlendFunc(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA);

        BufferBuilder builder = ExtraTesselator.getInstance().getBuilder();
        builder.begin(GlConst.GL_QUADS);
        builder.vertex(x0, y1, 20).color(r, g, b, a).endVertex();
        builder.vertex(x1, y1, 20).color(r, g, b, a).endVertex();
        builder.vertex(x1, y0, 20).color(r, g, b, a).endVertex();
        builder.vertex(x0, y0, 20).color(r, g, b, a).endVertex();
        ExtraTesselator.endAndDraw();

        GL11.glEnable(GlConst.GL_TEXTURE_2D);
        GL11.glDisable(GlConst.GL_BLEND);
    }

    public static void fillGradientXX(int x0, int y0, int x1, int y1, int colorStart, int colorEnd) {
        fillGradientXX(x0, y0, x1, y1, 0, colorStart, colorEnd);
    }

    public static void fillGradientXX(BufferBuilder builder, int x0, int y0, int x1, int y1, int colorStart, int colorEnd) {
        fillGradientXX(builder, x0, y0, x1, y1, 0, colorStart, colorEnd);
    }

    public static void fillGradientXX(int x0, int y0, int x1, int y1, int z, int colorStart, int colorEnd) {
        GL11.glDisable(GlConst.GL_TEXTURE_2D);
        GL11.glEnable(GlConst.GL_BLEND);
        GL11.glBlendFunc(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA);

        BufferBuilder builder = ExtraTesselator.getInstance().getBuilder();
        builder.begin(GlConst.GL_QUADS);
        fillGradientXX(builder, x0, y0, x1, y1, z, colorStart, colorEnd);
        ExtraTesselator.endAndDraw();

        GL11.glEnable(GlConst.GL_TEXTURE_2D);
        GL11.glDisable(GlConst.GL_BLEND);
    }

    public static void fillGradientXX(BufferBuilder builder, int x0, int y0, int x1, int y1, int z, int colorStart, int colorEnd) {
        float sA = (float)(colorStart >>> 24) / 255.0F;
        float sR = (float)(colorStart >> 16 & 255) / 255.0F;
        float sG = (float)(colorStart >> 8 & 255) / 255.0F;
        float sB = (int) ((float)(colorStart & 255) / 255.0F);

        float eA = (float)(colorEnd >>> 24) / 255.0F;
        float eR = (float)(colorEnd >> 16 & 255) / 255.0F;
        float eG = (float)(colorEnd >> 8 & 255) / 255.0F;
        float eB = (int) ((float)(colorEnd & 255) / 255.0F);

        builder.vertex((float)x1, (float)y0, (float)z).color(sR, sG, sB, sA).endVertex();
        builder.vertex((float)x0, (float)y0, (float)z).color(sR, sG, sB, sA).endVertex();
        builder.vertex((float)x0, (float)y1, (float)z).color(eR, eG, eB, eA).endVertex();
        builder.vertex((float)x1, (float)y1, (float)z).color(eR, eG, eB, eA).endVertex();
    }

    public static void drawTextureXX(int x, int y, int u, int v, int width, int height) {
        drawTextureXX(x, y, u, v, width, height, 256, 256);
    }

    public static void drawTextureXX(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        drawTextureXX(x, y, width, height, u, v, width, height, textureWidth, textureHeight);
    }

    public static void drawTextureXX(int x, int y, int width, int height, int u, int v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        int x1 = x + width;
        int y1 = y + height;
        float u0 = u / (float)textureWidth;
        float v0 = v / (float)textureHeight;
        float u1 = (u + regionWidth) / (float)textureWidth;
        float v1 = (v + regionHeight) / (float)textureHeight;

        BufferBuilder builder = ExtraTesselator.getInstance().getBuilder();
        builder.begin(GlConst.GL_QUADS);
        builder.vertex((float)x, (float)y1, (float)0).uv(u0, v1).endVertex();
        builder.vertex((float)x1, (float)y1, (float)0).uv(u1, v1).endVertex();
        builder.vertex((float)x1, (float)y, (float)0).uv(u1, v0).endVertex();
        builder.vertex((float)x, (float)y, (float)0).uv(u0, v0).endVertex();
        ExtraTesselator.endAndDraw();
    }

//    public static enum GradientDir {
//        VERTICAL,
//        HORIZONTAL
//    }

    //Old
    protected void drawTexture(int x, int y, int width, int height, int u, int v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        int x0 = x;
        int x1 = x + width;
        int y0 = y;
        int y1 = y + height;

        float u0 = u / (float)textureWidth;
        float v0 = v / (float)textureHeight;
        float u1 = (u + regionWidth) / (float)textureWidth;
        float v1 = (v + regionHeight) / (float)textureHeight;

        Tesselator builder = Tesselator.instance;
        builder.begin();
        builder.vertexUV(x0, y1, this.blitOffset, u0, v1);
        builder.vertexUV(x1, y1, this.blitOffset, u1, v1);
        builder.vertexUV(x1, y0, this.blitOffset, u1, v0);
        builder.vertexUV(x0, y0, this.blitOffset, u0, v0);
        builder.end();
    }


    public static void fillGradient(int x0, int y0, int x1, int y1, int z, int color1, int color2) {
        float var6 = (float)(color1 >>> 24) / 255.0F;
        float var7 = (float)(color1 >> 16 & 255) / 255.0F;
        float var8 = (float)(color1 >> 8 & 255) / 255.0F;
        int col1 = (int) ((float)(color1 & 255) / 255.0F);
        float var9 = (float)(color2 >>> 24) / 255.0F;
        float var10 = (float)(color2 >> 16 & 255) / 255.0F;
        float var11 = (float)(color2 >> 8 & 255) / 255.0F;
        int col2 = (int) ((float)(color2 & 255) / 255.0F);
        GL11.glDisable(GlConst.GL_TEXTURE_2D);
        GL11.glEnable(GlConst.GL_BLEND);
        GL11.glBlendFunc(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glBegin(GlConst.GL_QUADS);
        GL11.glColor4f(var7, var8, col1, var6);
        GL11.glVertex3f((float)x1, (float)y0, z);
        GL11.glVertex3f((float)x0, (float)y0, z);
        GL11.glColor4f(var10, var11, col2, var9);
        GL11.glVertex3f((float)x0, (float)y1, z);
        GL11.glVertex3f((float)x1, (float)y1, z);
        GL11.glEnd();
        GL11.glDisable(GlConst.GL_BLEND);
        GL11.glEnable(GlConst.GL_TEXTURE_2D);
    }

    public static void fillGradientX(int x0, int y0, int x1, int y1, int z, int colorStart, int colorEnd) {
        GL11.glDisable(GlConst.GL_TEXTURE_2D);
        GL11.glEnable(GlConst.GL_BLEND);
        GL11.glBlendFunc(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA);

        Tesselator tesselator = Tesselator.instance;
        tesselator.begin();
        fillGradientX(tesselator, x0, y0, x1, y1, z, colorStart, colorEnd);
        tesselator.end();

        GL11.glDisable(GlConst.GL_BLEND);
        GL11.glEnable(GlConst.GL_TEXTURE_2D);
    }

    public static void fillGradientX(Tesselator tesselator, int x0, int y0, int x1, int y1, int z, int colorStart, int colorEnd) {
        float sA = (float)(colorStart >>> 24) / 255.0F;
        float sR = (float)(colorStart >> 16 & 255) / 255.0F;
        float sG = (float)(colorStart >> 8 & 255) / 255.0F;
        float sB = (int) ((float)(colorStart & 255) / 255.0F);

        float eA = (float)(colorStart >>> 24) / 255.0F;
        float eR = (float)(colorStart >> 16 & 255) / 255.0F;
        float eG = (float)(colorStart >> 8 & 255) / 255.0F;
        float eB = (int) ((float)(colorStart & 255) / 255.0F);

        tesselator.color(sA, sG, sB);
        tesselator.vertex(x0, y1, z);

        tesselator.color(sA, sG, sB);
        tesselator.vertex(x1, y1, z);

        tesselator.color(sA, sG, sB);
        tesselator.vertex(x1, y0, z);

        tesselator.color(sA, sG, sB);
        tesselator.vertex(x0, y0, z);
    }

    public static void fillX(int x0, int y0, int x1, int y1, int color) {
        float sA = (float)(color >>> 24) / 255.0F;
        float sR = (float)(color >> 16 & 255) / 255.0F;
        float sG = (float)(color >> 8 & 255) / 255.0F;
        float sB = ((float)(color & 255) / 255.0F);

        Tesselator tesselator = Tesselator.instance;
        GL11.glEnable(GlConst.GL_BLEND);
        GL11.glDisable(GlConst.GL_TEXTURE_2D);
        GL11.glBlendFunc(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA);

        tesselator.begin();
        tesselator.color(sR, sG, sB);
        tesselator.vertex(x0, y1, 0);

        tesselator.color(sR, sG, sB);
        tesselator.vertex(x1, y1, 0);

        tesselator.color(sR, sG, sB);
        tesselator.vertex(x1, y0, 0);

        tesselator.color(sR, sG, sB);
        tesselator.vertex(x0, y0, 0);
        tesselator.end();
        GL11.glEnable(GlConst.GL_TEXTURE_2D);
        GL11.glDisable(GlConst.GL_BLEND);
    }
}
