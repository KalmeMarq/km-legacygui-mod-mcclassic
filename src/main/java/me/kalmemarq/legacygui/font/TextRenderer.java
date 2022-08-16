package me.kalmemarq.legacygui.font;

import me.kalmemarq.legacygui.text.*;
import me.kalmemarq.legacygui.text.style.Style;
import me.kalmemarq.legacygui.text.style.TextColor;
import me.kalmemarq.legacygui.util.BufferBuilder;
import me.kalmemarq.legacygui.util.ExtraTesselator;
import me.kalmemarq.legacygui.util.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.Lists;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class TextRenderer {
    public static Map<String, Integer> GLYPH_WITHDS = Maps.newHashMap();
    public static Map<String, GlyphRenderer> GLYPH_RENDERERS = Maps.newHashMap();
    private final TextHandler handler;

    public TextRenderer() {
        this.handler = new TextHandler((chr, style) -> GLYPH_WITHDS.get(chr) + (style.isBold() ? 1 : 0));
    }

    static {
        Map<String, Integer> map = Maps.newHashMap();
        map.put(" ", 0 << 8 | 16); GLYPH_WITHDS.put(" ", 4);
        map.put("!", 8 << 8 | 16); GLYPH_WITHDS.put("!", 2);
        map.put("\"", 16 << 8 | 16); GLYPH_WITHDS.put("\"", 6);
        map.put("#", 24 << 8 | 16); GLYPH_WITHDS.put("#", 6);
        map.put("$", 32 << 8 | 16); GLYPH_WITHDS.put("$", 6);
        map.put("%", 40 << 8 | 16); GLYPH_WITHDS.put("%", 7);
        map.put("&", 48 << 8 | 16); GLYPH_WITHDS.put("&", 7);
        map.put("'", 56 << 8 | 16); GLYPH_WITHDS.put("'", 6);
        map.put("(", 64 << 8 | 16); GLYPH_WITHDS.put("(", 5);
        map.put(")", 72 << 8 | 16); GLYPH_WITHDS.put(")", 5);
        map.put("*", 80 << 8 | 16); GLYPH_WITHDS.put("*", 6);
        map.put("+", 88 << 8 | 16); GLYPH_WITHDS.put("+", 6);
        map.put(",", 96 << 8 | 16); GLYPH_WITHDS.put(",", 2);
        map.put("-", 104 << 8 | 16); GLYPH_WITHDS.put("-", 6);
        map.put(".", 112 << 8 | 16); GLYPH_WITHDS.put(".", 2);
        map.put("/", 120 << 8 | 16); GLYPH_WITHDS.put("/", 6);

        map.put("0", 0 << 8 | 24); GLYPH_WITHDS.put("0", 6);
        map.put("1", 8 << 8 | 24); GLYPH_WITHDS.put("1", 6);
        map.put("2", 16 << 8 | 24); GLYPH_WITHDS.put("2", 6);
        map.put("3", 24 << 8 | 24); GLYPH_WITHDS.put("3", 6);
        map.put("4", 32 << 8 | 24); GLYPH_WITHDS.put("4", 6);
        map.put("5", 40 << 8 | 24); GLYPH_WITHDS.put("5", 6);
        map.put("6", 48 << 8 | 24); GLYPH_WITHDS.put("6", 6);
        map.put("7", 56 << 8 | 24); GLYPH_WITHDS.put("7", 6);
        map.put("8", 64 << 8 | 24); GLYPH_WITHDS.put("8", 6);
        map.put("9", 72 << 8 | 24); GLYPH_WITHDS.put("9", 6);
        map.put(":", 80 << 8 | 24); GLYPH_WITHDS.put(":", 2);
        map.put(";", 88 << 8 | 24); GLYPH_WITHDS.put(";", 3);
        map.put("<", 96 << 8 | 24); GLYPH_WITHDS.put("<", 6);
        map.put("=", 104 << 8 | 24); GLYPH_WITHDS.put("=", 6);
        map.put(">", 112 << 8 | 24); GLYPH_WITHDS.put(">", 6);
        map.put("?", 120 << 8 | 24); GLYPH_WITHDS.put("?", 6);

        map.put("@", 0 << 8 | 32); GLYPH_WITHDS.put("@", 7);
        map.put("A", 8 << 8 | 32); GLYPH_WITHDS.put("A", 6);
        map.put("B", 16 << 8 | 32); GLYPH_WITHDS.put("B", 6);
        map.put("C", 24 << 8 | 32); GLYPH_WITHDS.put("C", 6);
        map.put("D", 32 << 8 | 32); GLYPH_WITHDS.put("D", 6);
        map.put("E", 40 << 8 | 32); GLYPH_WITHDS.put("E", 6);
        map.put("F", 48 << 8 | 32); GLYPH_WITHDS.put("F", 6);
        map.put("G", 56 << 8 | 32); GLYPH_WITHDS.put("G", 6);
        map.put("H", 64 << 8 | 32); GLYPH_WITHDS.put("H", 6);
        map.put("I", 72 << 8 | 32); GLYPH_WITHDS.put("I", 4);
        map.put("J", 80 << 8 | 32); GLYPH_WITHDS.put("J", 6);
        map.put("K", 88 << 8 | 32); GLYPH_WITHDS.put("K", 6);
        map.put("L", 96 << 8 | 32); GLYPH_WITHDS.put("L", 6);
        map.put("M", 104 << 8 | 32); GLYPH_WITHDS.put("M", 6);
        map.put("N", 112 << 8 | 32); GLYPH_WITHDS.put("N", 6);
        map.put("O", 120 << 8 | 32); GLYPH_WITHDS.put("O", 6);

        map.put("P", 0 << 8 | 40); GLYPH_WITHDS.put("P", 6);
        map.put("Q", 8 << 8 | 40); GLYPH_WITHDS.put("Q", 6);
        map.put("R", 16 << 8 | 40); GLYPH_WITHDS.put("R", 6);
        map.put("S", 24 << 8 | 40); GLYPH_WITHDS.put("S", 6);
        map.put("T", 32 << 8 | 40); GLYPH_WITHDS.put("T", 6);
        map.put("U", 40 << 8 | 40); GLYPH_WITHDS.put("U", 6);
        map.put("V", 48 << 8 | 40); GLYPH_WITHDS.put("V", 6);
        map.put("W", 56 << 8 | 40); GLYPH_WITHDS.put("W", 6);
        map.put("X", 64 << 8 | 40); GLYPH_WITHDS.put("X", 6);
        map.put("Y", 72 << 8 | 40); GLYPH_WITHDS.put("Y", 6);
        map.put("Z", 80 << 8 | 40); GLYPH_WITHDS.put("Z", 6);
        map.put("[", 88 << 8 | 40); GLYPH_WITHDS.put("[", 6);
        map.put("\\", 96 << 8 | 40); GLYPH_WITHDS.put("\\", 6);
        map.put("]", 104 << 8 | 40); GLYPH_WITHDS.put("]", 6);
        map.put("^", 112 << 8 | 40); GLYPH_WITHDS.put("^", 6);
        map.put("_", 120 << 8 | 40); GLYPH_WITHDS.put("_", 6);

        map.put("`", 0 << 8 | 48); GLYPH_WITHDS.put("`", 6);
        map.put("a", 8 << 8 | 48); GLYPH_WITHDS.put("a", 6);
        map.put("b", 16 << 8 | 48); GLYPH_WITHDS.put("b", 6);
        map.put("c", 24 << 8 | 48); GLYPH_WITHDS.put("c", 6);
        map.put("d", 32 << 8 | 48); GLYPH_WITHDS.put("d", 6);
        map.put("e", 40 << 8 | 48); GLYPH_WITHDS.put("e", 6);
        map.put("f", 48 << 8 | 48); GLYPH_WITHDS.put("f", 5);
        map.put("g", 56 << 8 | 48); GLYPH_WITHDS.put("g", 6);
        map.put("h", 64 << 8 | 48); GLYPH_WITHDS.put("h", 6);
        map.put("i", 72 << 8 | 48); GLYPH_WITHDS.put("i", 2);
        map.put("j", 80 << 8 | 48); GLYPH_WITHDS.put("j", 6);
        map.put("k", 88 << 8 | 48); GLYPH_WITHDS.put("k", 6);
        map.put("l", 96 << 8 | 48); GLYPH_WITHDS.put("l", 3);
        map.put("m", 104 << 8 | 48); GLYPH_WITHDS.put("m", 6);
        map.put("n", 112 << 8 | 48); GLYPH_WITHDS.put("n", 6);
        map.put("o", 120 << 8 | 48); GLYPH_WITHDS.put("o", 6);

        map.put("p", 0 << 8 | 56); GLYPH_WITHDS.put("p", 6);
        map.put("q", 8 << 8 | 56); GLYPH_WITHDS.put("q", 6);
        map.put("r", 16 << 8 | 56); GLYPH_WITHDS.put("r", 6);
        map.put("s", 24 << 8 | 56); GLYPH_WITHDS.put("s", 6);
        map.put("t", 32 << 8 | 56); GLYPH_WITHDS.put("t", 4);
        map.put("u", 40 << 8 | 56); GLYPH_WITHDS.put("u", 6);
        map.put("v", 48 << 8 | 56); GLYPH_WITHDS.put("v", 6);
        map.put("w", 56 << 8 | 56); GLYPH_WITHDS.put("w", 6);
        map.put("x", 64 << 8 | 56); GLYPH_WITHDS.put("x", 6);
        map.put("y", 72 << 8 | 56); GLYPH_WITHDS.put("y", 6);
        map.put("z", 80 << 8 | 56); GLYPH_WITHDS.put("z", 6);
        map.put("{", 88 << 8 | 56); GLYPH_WITHDS.put("{", 6);
        map.put("|", 96 << 8 | 56); GLYPH_WITHDS.put("|", 6);
        map.put("}", 104 << 8 | 56); GLYPH_WITHDS.put("}", 6);
        map.put("~", 112 << 8 | 56); GLYPH_WITHDS.put("~", 6);

        for (Map.Entry<String, Integer> e : map.entrySet()) {
            int va = e.getValue();
            float u = (float) (va >> 8 & 0xFF);
            float v = (float) (va & 0xFF);

            float width = GLYPH_WITHDS.get(e.getKey());

            GLYPH_RENDERERS.put(e.getKey(), new GlyphRenderer(u / 128.0f, (u + width) / 128.0f, v / 128.0f, (v + 8.0f) / 128.0f, 0.0f, width, 3.0f, 3.0f + 8.0f));
        }
    }

    private static int tweakTransparency(int argb) {
        if ((argb & 0xFC000000) == 0) {
            return argb | 0xFF000000;
        }
        return argb;
    }


    public void drawWithShadow(String text, float x, float y, int color) {
        this.draw(text, x, y, color, true);
    }

    public void draw(String text, float x, float y, int color) {
        this.draw(text, x, y, color, false);
    }

    public void drawWithShadow(Text text, float x, float y, int color) {
        this.draw(text.asOrderedText(), x, y, color, true);
    }

    public void drawWithShadow(OrderedText text, float x, float y, int color) {
        this.draw(text, x, y, color, true);
    }

    public void draw(Text text, float x, float y, int color) {
        this.draw(text.asOrderedText(), x, y, color, false);
    }

    private void draw(String text, float x, float y, int color, boolean shadow) {
        if (text == null) {
            return;
        }

        BufferBuilder bufferBuilder = ExtraTesselator.getInstance().getBuilder();
        bufferBuilder.begin();
        this.drawInternal(text, x, y, color, shadow, bufferBuilder);
        bufferBuilder.draw();
    }

    private void draw(OrderedText text, float x, float y, int color, boolean shadow) {
        if (text == null) {
            return;
        }

        BufferBuilder bufferBuilder = ExtraTesselator.getInstance().getBuilder();
        bufferBuilder.begin();
        this.drawInternal(text, x, y, color, shadow, bufferBuilder);
        bufferBuilder.draw();
    }

    private void drawInternal(String text, float x, float y, int color, boolean shadow, BufferBuilder bufferBuilder) {
        color = TextRenderer.tweakTransparency(color);

        if (shadow) {
            this.drawLayer(text, x, y, color, true, bufferBuilder);
        }
        this.drawLayer(text, x, y, color, false, bufferBuilder);
    }

    private void drawInternal(OrderedText text, float x, float y, int color, boolean shadow, BufferBuilder bufferBuilder) {
        color = TextRenderer.tweakTransparency(color);

        if (shadow) {
            this.drawLayer(text, x, y, color, true, bufferBuilder);
        }
        this.drawLayer(text, x, y, color, false, bufferBuilder);
    }

    private void drawLayer(String text, float x, float y, int color, boolean shadow, BufferBuilder bufferBuilder) {
        Drawer drawer = new Drawer(bufferBuilder, x, y, color, shadow);
        TextVisitFactory.visitFormatted(text, Style.EMPTY, drawer);
        drawer.drawLayer(color, x);
    }

    private void drawLayer(OrderedText text, float x, float y, int color, boolean shadow, BufferBuilder bufferBuilder) {
        Drawer drawer = new Drawer(bufferBuilder, x, y, color, shadow);
        text.accept(drawer);
        drawer.drawLayer(color, x);
    }

    private void drawGlyph(GlyphRenderer glyphRenderer, boolean bold, boolean italic, float weight, float x, float y, BufferBuilder bufferBuilder, float red, float green, float blue, float alpha) {
        glyphRenderer.draw(italic, x, y, bufferBuilder, red, green, blue, alpha);
        if (bold) {
            glyphRenderer.draw(italic, x + weight, y, bufferBuilder, red, green, blue, alpha);
        }
    }

    public int getWidth(String text) {
        return MathHelper.ceil(this.handler.getWidth(text));
    }

    public int getWidth(StringVisitable text) {
        return MathHelper.ceil(this.handler.getWidth(text));
    }

    public int getWidth(OrderedText text) {
        return MathHelper.ceil(this.handler.getWidth(text));
    }

    private class Drawer implements CharacterVisitor {
        private final BufferBuilder bufferBuilder;
        private final boolean shadow;
        private final float brightnessMultiplier;
        private final float red;
        private final float green;
        private final float blue;
        private final float alpha;
        private float x;
        private float y;
        @Nullable
        private List<GlyphRenderer.Rectangle> rectangles;

        public Drawer(BufferBuilder bufferBuilder, float x, float y, int color, boolean shadow) {
            this.bufferBuilder = bufferBuilder;
            this.x = x;
            this.y = y;
            this.shadow = shadow;
            this.brightnessMultiplier = shadow ? 0.25f : 1.0f;
            this.red = (float)(color >> 16 & 0xFF) / 255.0f * this.brightnessMultiplier;
            this.green = (float)(color >> 8 & 0xFF) / 255.0f * this.brightnessMultiplier;
            this.blue = (float)(color & 0xFF) / 255.0f * this.brightnessMultiplier;
            this.alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        }

        @Override
        public boolean accept(Style style, String chr) {
            GlyphRenderer glyphRenderer = GLYPH_RENDERERS.get(chr);

            float width = GLYPH_WITHDS.get(chr);
            if (glyphRenderer != null) {
                boolean bold = style.isBold();
                boolean italic = style.isItalic();

                TextColor tC = style.getColor();

                float r = this.red;
                float g = this.green;
                float b = this.blue;

                if (tC != null) {
                    int k = tC.getRgb();
                    r = (float)(k >> 16 & 0xFF) / 255.0f * this.brightnessMultiplier;
                    g = (float)(k >> 8 & 0xFF) / 255.0f * this.brightnessMultiplier;
                    b = (float)(k & 0xFF) / 255.0f * this.brightnessMultiplier;
                }

                float shadowOff = this.shadow ? 1.0f : 0.0f;

                float ad = 1.0f + (style.isBold() ? 1.0f : 0.0f);

                if (style.isStrikethrough()) {
                    this.addRectangle(new GlyphRenderer.Rectangle(this.x + shadowOff - 1.0f, this.y + shadowOff + 4.5f, this.x + shadowOff + ad, this.y + shadowOff + 4.5f - 1.0f, 0.01f, r, g, b, this.alpha));
                }
                if (style.isUnderlined()) {
                    this.addRectangle(new GlyphRenderer.Rectangle(this.x + shadowOff - 1.0f, this.y + shadowOff + 8.0f, this.x + shadowOff + ad, this.y + shadowOff + 9.0f, 0.01f, r, g, b, this.alpha));
                }

                TextRenderer.this.drawGlyph(glyphRenderer, bold, italic, 1.0f, this.x + shadowOff, this.y + shadowOff, this.bufferBuilder, r, g, b, this.alpha);
            }

            this.x += width + (style.isBold() ? 1.0f : 0.0f);
            return true;
        }

        private void addRectangle(GlyphRenderer.Rectangle rectangle) {
            if (this.rectangles == null) {
                this.rectangles = Lists.newArrayList();
            }
            this.rectangles.add(rectangle);
        }

        public float drawLayer(int underlineColor, float x) {
//            if (underlineColor != 0) {
//                float g = (float)(underlineColor >> 24 & 0xFF) / 255.0f;
//                float h = (float)(underlineColor >> 16 & 0xFF) / 255.0f;
//                float j = (float)(underlineColor >> 8 & 0xFF) / 255.0f;
//                float k = (float)(underlineColor & 0xFF) / 255.0f;
//                this.addRectangle(new GlyphRenderer.Rectangle(x - 1.0f, this.y + 9.0f, this.x + 1.0f, this.y - 1.0f, 0.01f, h, j, k, g));
//            }
            if (this.rectangles != null) {
                this.bufferBuilder.draw();
                this.bufferBuilder.begin();
                for (GlyphRenderer.Rectangle lv3 : this.rectangles) {
                    GlyphRenderer.drawRectangle(lv3, this.bufferBuilder);
                }
            }
            return this.x;
        }
    }
}
