package me.kalmemarq.legacygui.font;

import me.kalmemarq.legacygui.util.BufferBuilder;
import me.kalmemarq.legacygui.util.RenderHelper;

public class GlyphRenderer {
    private final float minU;
    private final float maxU;
    private final float minV;
    private final float maxV;
    private final float minX;
    private final float maxX;
    private final float minY;
    private final float maxY;

    public GlyphRenderer(float minU, float maxU, float minV, float maxV, float minX, float maxX, float minY, float maxY) {
        this.minU = minU;
        this.maxU = maxU;
        this.minV = minV;
        this.maxV = maxV;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void draw(boolean italic, float x, float y, BufferBuilder bufferBuilder, float red, float green, float blue, float alpha) {
        RenderHelper.bindTexture("/default.png");
        int m = 3;
        float n = x + this.minX;
        float o = x + this.maxX;
        float p = this.minY - 3.0f;
        float q = this.maxY - 3.0f;
        float r = y + p;
        float s = y + q;
        float t = italic ? 1.0f - 0.25f * p : 0.0f;
        float u = italic ? 1.0f - 0.25f * q : 0.0f;
        bufferBuilder.vertex(n + t, r, 0.0f).color(red, green, blue, alpha).uv(this.minU, this.minV).endVertex();
        bufferBuilder.vertex(n + u, s, 0.0f).color(red, green, blue, alpha).uv(this.minU, this.maxV).endVertex();
        bufferBuilder.vertex(o + u, s, 0.0f).color(red, green, blue, alpha).uv(this.maxU, this.maxV).endVertex();
        bufferBuilder.vertex(o + t, r, 0.0f).color(red, green, blue, alpha).uv(this.maxU, this.minV).endVertex();
    }

    public static void drawRectangle(Rectangle rectangle, BufferBuilder bufferBuilder) {
        bufferBuilder.vertex(rectangle.minX, rectangle.maxY, rectangle.zIndex).color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha).endVertex();
        bufferBuilder.vertex(rectangle.maxX, rectangle.maxY, rectangle.zIndex).color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha).endVertex();
        bufferBuilder.vertex(rectangle.maxX, rectangle.minY, rectangle.zIndex).color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha).endVertex();
        bufferBuilder.vertex(rectangle.minX, rectangle.minY, rectangle.zIndex).color(rectangle.red, rectangle.green, rectangle.blue, rectangle.alpha).endVertex();
    }

    public static class Rectangle {
        protected final float minX;
        protected final float minY;
        protected final float maxX;
        protected final float maxY;
        protected final float zIndex;
        protected final float red;
        protected final float green;
        protected final float blue;
        protected final float alpha;

        public Rectangle(float minX, float minY, float maxX, float maxY, float zIndex, float red, float green, float blue, float alpha) {
            this.minX = minX;
            this.minY = minY;
            this.maxX = maxX;
            this.maxY = maxY;
            this.zIndex = zIndex;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
    }
}
