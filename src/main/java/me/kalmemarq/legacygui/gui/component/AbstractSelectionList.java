package me.kalmemarq.legacygui.gui.component;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.util.*;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AbstractSelectionList<E extends AbstractSelectionList.Entry<E>> extends ExtraDrawableHelper {
    protected final Minecraft minecraft;
    private boolean renderBackground = true;
    private boolean renderHeader;
    protected int headerHeight;
    protected final int itemHeight;
    protected int width;
    protected int height;
    protected int y0;
    protected int y1;
    protected int x1;
    protected int x0;
    private double scrollAmount;
    private final List<E> children = new ArrayList<>();
    @Nullable
    private E selected;
    @Nullable
    private E hovered;

    public AbstractSelectionList(Minecraft mc, int width, int height, int y0, int y1, int itemHeight) {
        this.minecraft = mc;
        this.width = width;
        this.height = height;
        this.y0 = y0;
        this.y1 = y1;
        this.itemHeight = itemHeight;
        this.x0 = 0;
        this.x1 = width;
    }

    public void setScrollAmount(double var1) {
        this.scrollAmount = MathHelper.clamp(var1, 0.0D, (double)this.getMaxScroll());
    }

    public int getMaxScroll() {
        return Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4));
    }

    protected int getMaxPosition() {
        return this.getItemCount() * this.itemHeight + this.headerHeight;
    }

    public void setSelected(@Nullable E entry) {
        this.selected = entry;
    }

    protected final void clearEntries() {
        this.children.clear();
    }

    protected void replaceEntries(Collection<E> entries) {
        this.children.clear();
        this.children.addAll(entries);
    }

    protected int addEntry(E entry) {
        this.children.add(entry);
        return this.children.size() - 1;
    }

    public final List<E> children() {
        return this.children;
    }

    public int getRowWidth() {
        return 220;
    }

    @Nullable
    public E getSelected() {
        return this.selected;
    }

    @Nullable
    protected final E getEntryAtPosition(double var1, double var3) {
        int var5 = this.getRowWidth() / 2;
        int var6 = this.x0 + this.width / 2;
        int var7 = var6 - var5;
        int var8 = var6 + var5;
        int var9 = (int)Math.floor(var3 - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
        int var10 = var9 / this.itemHeight;
        return var1 < (double)this.getScrollbarPosition() && var1 >= (double)var7 && var1 <= (double)var8 && var10 >= 0 && var9 >= 0 && var10 < this.getItemCount() ? (E) this.children().get(var10) : null;
    }

    protected int getScrollbarPosition() {
        return this.width / 2 + 124;
    }

    protected int getItemCount() {
        return this.children().size();
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseY >= (double)this.y0 && mouseY <= (double)this.y1 && mouseX >= (double)this.x0 && mouseX <= (double)this.x1;
    }

    protected void renderBackground() {
    }

    public double getScrollAmount() {
        return this.scrollAmount;
    }

    protected void renderHeader(int leftRowX0, int var3, Tesselator var4) {
    }

    public void render(int mouseX, int mouseY) {
        this.renderBackground();

        int scrollBarX0 = this.getScrollbarPosition();
        int scrollBarX1 = scrollBarX0 + 6;

        ExtraTesselator tesselator = ExtraTesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();

        this.hovered = this.isMouseOver((double)mouseX, (double)mouseY) ? this.getEntryAtPosition((double)mouseX, (double)mouseY) : null;
        if (this.renderBackground) {
            RenderHelper.bindTexture("/gui/options_background.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            builder.begin(GlConst.GL_QUADS);
            builder.vertex((float)this.x0, (float)this.y1, 0.0f).uv((float)this.x0 / 32.0F, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            builder.vertex((float)this.x1, (float)this.y1, 0.0f).uv((float)this.x1 / 32.0F, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            builder.vertex((float)this.x1, (float)this.y0, 0.0f).uv((float)this.x1 / 32.0F, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            builder.vertex((float)this.x0, (float)this.y0, 0.0f).uv((float)this.x0 / 32.0F, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            tesselator.end();
        }

        int leftRowX0 = this.getRowLeft();
        int var10 = this.y0 + 4 - (int)this.getScrollAmount();

        if (this.renderHeader) {
            this.renderHeader(leftRowX0, var10, Tesselator.instance);
        }

        this.renderList(leftRowX0, var10, mouseX, mouseY);

        if (this.renderBackground) {
            RenderHelper.enableBlend();
            GL14.glBlendFuncSeparate(GlConst.SRC_ALPHA, GlConst.ONE_MINUS_SRC_ALPHA, GlConst.ZERO, GlConst.ONE);
            RenderHelper.disableTexture();

            builder.begin(GlConst.GL_QUADS);
            builder.vertex((float)this.x0, (float)(this.y0 + 4), 0.0f).color(0, 0, 0, 0).endVertex();
            builder.vertex((float)this.x1, (float)(this.y0 + 4), 0.0f).color(0, 0, 0, 0).endVertex();
            builder.vertex((float)this.x1, (float)this.y0, 0.0f).color(0, 0, 0, 255).endVertex();
            builder.vertex((float)this.x0, (float)this.y0, 0.0f).color(0, 0, 0, 255).endVertex();
            builder.vertex((float)this.x0, (float)this.y1, 0.0f).color(0, 0, 0, 255).endVertex();
            builder.vertex((float)this.x1, (float)this.y1, 0.0f).color(0, 0, 0, 255).endVertex();
            builder.vertex((float)this.x1, (float)(this.y1 - 4), 0.0f).color(0, 0, 0, 0).endVertex();
            builder.vertex((float)this.x0, (float)(this.y1 - 4), 0.0f).color(0, 0, 0, 0).endVertex();
            tesselator.end();
        }

        int maxScroll = this.getMaxScroll();
        if (maxScroll > 0) {
            RenderHelper.disableTexture();

            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            int var16 = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            var16 = MathHelper.clamp((int)var16, (int)32, (int)(this.y1 - this.y0 - 8));
            int var17 = (int)this.getScrollAmount() * (this.y1 - this.y0 - var16) / maxScroll + this.y0;
            if (var17 < this.y0) {
                var17 = this.y0;
            }

            builder.begin(GlConst.GL_QUADS);
            builder.vertex((float)scrollBarX0, (float)this.y1, 0.0f).color(0, 0, 0, 255).endVertex();
            builder.vertex((float)scrollBarX1, (float)this.y1, 0.0f).color(0, 0, 0, 255).endVertex();
            builder.vertex((float)scrollBarX1, (float)this.y0, 0.0f).color(0, 0, 0, 255).endVertex();
            builder.vertex((float)scrollBarX0, (float)this.y0, 0.0f).color(0, 0, 0, 255).endVertex();

            builder.vertex((float)scrollBarX0, (float) (var17 + var16), 0.0f).color(128, 128, 128, 255).endVertex();
            builder.vertex((float)scrollBarX1, (float) (var17 + var16), 0.0f).color(128, 128, 128, 255).endVertex();
            builder.vertex((float)scrollBarX1, (float)var17, 0.0f).color(128, 128, 128, 255).endVertex();
            builder.vertex((float)scrollBarX0, (float) var17, 0.0f).color(128, 128, 128, 255).endVertex();

            builder.vertex((float)scrollBarX0, (float) (var17 + var16 - 1), 0.0f).color(192, 192, 192, 255).endVertex();
            builder.vertex((float)(scrollBarX1 - 1), (float) (var17 + var16 - 1), 0.0f).color(192, 192, 192, 255).endVertex();
            builder.vertex((float)(scrollBarX1 - 1), (float) var17, 0.0f).color(192, 192, 192, 255).endVertex();
            builder.vertex((float)scrollBarX0, (float) var17, 0.0f).color(192, 192, 192, 255).endVertex();
            tesselator.end();
        }

        RenderHelper.enableTexture();
        RenderHelper.disableBlend();
    }

    protected E getEntry(int var1) {
        return (E)this.children().get(var1);
    }

    protected void renderList(int leftRowX0, int var3, int mouseX, int mouseY) {
        int itemCount = this.getItemCount();

        for(int i = 0; i < itemCount; ++i) {
            int rowTopY = this.getRowTop(i);
            int rowBottomY = this.getRowBottom(i);

            if (rowBottomY >= this.y0 && rowTopY <= this.y1) {
                int var13 = var3 + i * this.itemHeight + this.headerHeight;
                int var14 = this.itemHeight - 4;
                AbstractSelectionList.Entry entry = this.getEntry(i);

                int rowWidth = this.getRowWidth();
                int rowLeftX0 = this.getRowLeft();

                entry.render(i, rowTopY, rowLeftX0, rowWidth, var14, mouseX, mouseY, Objects.equals(this.hovered, entry));
            }
        }
    }

    private void fillGradientShadows(int x, int y, float z, int color) {
        float var6 = (float)(color >>> 24) / 255.0F;
        float var7 = (float)(color >> 16 & 255) / 255.0F;
        float var8 = (float)(color >> 8 & 255) / 255.0F;
        int col1 = (int) ((float)(color & 255) / 255.0F);
        GL11.glColor4f(var7, var8, col1, var6);
        GL11.glVertex3f((float)x, (float)y, z);
    }

    public int getRowLeft() {
        return this.x0 + this.width / 2 - this.getRowWidth() / 2 + 2;
    }

    protected int getRowTop(int var1) {
        return this.y0 + 4 - (int)this.getScrollAmount() + var1 * this.itemHeight + this.headerHeight;
    }

    private int getRowBottom(int var1) {
        return this.getRowTop(var1) + this.itemHeight;
    }

    public abstract static class Entry<E extends AbstractSelectionList.Entry<E>> {
        @Deprecated
        AbstractSelectionList<E> list;

        public Entry() {
        }

        public abstract void render(int index, int rowTopY, int rowLeftX0, int rowWidth, int var6, int mouseX, int mouseY, boolean isHovered);

        public boolean isMouseOver(double mouseX, double mouseY) {
            return Objects.equals(this.list.getEntryAtPosition(mouseX, mouseY), this);
        }
    }

    protected static enum SelectionDirection {
        UP,
        DOWN;

        SelectionDirection() {
        }
    }
}
