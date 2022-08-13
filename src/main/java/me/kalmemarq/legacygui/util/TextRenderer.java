package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Font;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.mixin.FontAccessor;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class TextRenderer {
    public static void drawString(String text, int x, int y, int color) {
        render(text, x, y, color, false);
    }

    public static void drawStringShadow(String text, int x, int y, int color) {
        render(text, x + 1, y + 1, color, true);
        render(text, x, y, color, false);
    }

    public static void render(String text, int x, int y, int color, boolean darken) {
        if (text != null) {
            Minecraft mc = LegacyGUIMod.getMCInstance();
            Font font = mc.font;

            int textColor = color;

            char[] str = text.toCharArray();

            RenderHelper.bindTexture("/default.png");
            ExtraTesselator tesselator = ExtraTesselator.getInstance();
            BufferBuilder builder = tesselator.getBuilder();
            builder.begin(GlConst.GL_QUADS);

            int var7 = 0;
            boolean bold = false;

            for(int i = 0; i < str.length; ++i) {
                int bv;
                if (str[i] == '&' && str.length > i + 1) {
                    if ((color = "0123456789abcdef".indexOf(str[i + 1])) < 0) {
                        color = 15;
                    } else if ("l".indexOf(str[i + 1]) >= 0) {
                        bold = true;
                    } else if ("r".indexOf(str[i + 1]) >= 0) {
                        bold = false;
                        color = 15;
                    }

                    bv = (color & 8) << 3;
                    int var10 = (color & 1) * 191 + bv;
                    int var11 = ((color & 2) >> 1) * 191 + bv;
                    color = ((color & 4) >> 2) * 191 + bv;
                    if (mc.options.anaglyph3d) {
                        bv = (color * 30 + var11 * 59 + var10 * 11) / 100;
                        var11 = (color * 30 + var11 * 70) / 100;
                        var10 = (color * 30 + var10 * 70) / 100;
                        color = bv;
                        var11 = var11;
                        var10 = var10;
                    }

                    color = color << 16 | var11 << 8 | var10;
                    textColor = color;
                    i += 2;
                }

                int u = str[i] % 16 << 3;
                int v = str[i] / 16 << 3;
                int fontHeight = 8;
                int textureWidth = 128;
                int textureHeight = 128;

                textColor = adjustColor(textColor);
                int a = (textColor >> 24) & 0xFF;
                int r = (textColor >> 16) & 0xFF;
                int g = (textColor >> 8) & 0xFF;
                int b = textColor & 0xFF;

                if (darken) {
                    r *= 0.25f;
                    g *= 0.25f;
                    b *= 0.25f;
                }

                int xL0 = x + var7;
                int xL1 = x + var7 + fontHeight;
                int yL0 = y;
                int yL1 = y + fontHeight;

                float u0 = u / (float)textureWidth;
                float v0 = v / (float)textureHeight;
                float u1 = (u + 8) / (float)textureWidth;
                float v1 = (v + fontHeight) / (float)textureHeight;

                builder.vertex((float)(xL0), (float)yL1, 0.0F).color(r, g, b, a).uv(u0, v1).endVertex();
                builder.vertex((float)(xL1), (float)yL1, 0.0F).color(r, g, b, a).uv(u1, v1).endVertex();
                builder.vertex((float)(xL1), (float)yL0, 0.0F).color(r, g, b, a).uv(u1, v0).endVertex();
                builder.vertex((float)(xL0), (float)yL0, 0.0F).color(r, g, b, a).uv(u0, v0).endVertex();

                ++xL0;
                ++xL1;

                if (bold) {
                    builder.vertex((float)(xL0), (float)yL1, 0.0F).color(r, g, b, a).uv(u0, v1).endVertex();
                    builder.vertex((float)(xL1), (float)yL1, 0.0F).color(r, g, b, a).uv(u1, v1).endVertex();
                    builder.vertex((float)(xL1), (float)yL0, 0.0F).color(r, g, b, a).uv(u1, v0).endVertex();
                    builder.vertex((float)(xL0), (float)yL0, 0.0F).color(r, g, b, a).uv(u0, v0).endVertex();
                }

                var7 += ((FontAccessor)(Object)font).getCharWidths()[str[i]];
                if (bold) {
                    var7 += 1;
                }
            }

            tesselator.end();
        }
    }

    private static int adjustColor(int var0) {
        return (var0 & -67108864) == 0 ? var0 | -16777216 : var0;
    }
}
