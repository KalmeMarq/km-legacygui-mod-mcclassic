package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.DrawableHelper;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.component.AbstractWidget;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import me.kalmemarq.legacygui.gui.component.EditTextWidget;
import me.kalmemarq.legacygui.util.BufferBuilder;
import me.kalmemarq.legacygui.util.ExtraTesselator;
import me.kalmemarq.legacygui.util.GlConst;
import me.kalmemarq.legacygui.util.RenderHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public abstract class ExtraScreen extends Screen {
    protected String title = "";
    protected List<AbstractWidget> widgets = new ArrayList<>();

    public ExtraScreen() {
        this("");
    }

    public ExtraScreen(String title) {
        super();
        this.title = title;
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        for (AbstractWidget widget : widgets) {
            if (widget instanceof EditTextWidget) {
                ((EditTextWidget) widget).selected = false;
            }
        }

        for (AbstractWidget widget : widgets) {
            if (widget.mouseClicked(x, y, button)) {
                if (widget instanceof EditTextWidget) {
                    ((EditTextWidget) widget).selected = true;
                }
                return;
            }
        }
    }

    protected boolean keyPressedX(char key, int code) {
        for (AbstractWidget widget : this.widgets) {
            if (widget instanceof EditTextWidget) {
                if (((EditTextWidget)widget).selected) {
                    if (((EditTextWidget)(widget)).keyPressed(key, code)) {
                        return true;
                    }
                }
            }
        }
        if (code == Keyboard.KEY_ESCAPE) {
            if (this.minecraft.level != null) {
                this.minecraft.openScreen(null);
            } else {
                this.minecraft.openScreen(new TitleScreen());
            }
            return true;
        }

        return false;
    }

    @Override
    protected void keyPressed(char key, int code) {
        this.keyPressedX(key, code);
    }

    public <T extends AbstractWidget> T addWidget(T widget) {
        this.widgets.add(widget);
        return widget;
    }

    @Override
    public void close() {
        super.close();
        Keyboard.enableRepeatEvents(false);
    }

    public boolean canBeClosed() {
        return true;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        for (AbstractWidget widget : widgets) {
            widget.render(this.minecraft, mouseX, mouseY);
        }
    }

    public void renderBackground() {
        renderBackground(0);
    }

    public void renderBackground(int xOffset) {
        if (this.minecraft.level != null) {
            renderGradientBackground();
        } else {
            renderDirtBackground(xOffset);
        }
    }

    public void renderGradientBackground() {
        ExtraDrawableHelper.fillGradientXX(0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
    }

    public void renderDirtBackground() {
        renderDirtBackground(0);
    }

    public void renderDirtBackground(int xOffset) {
        ExtraTesselator tesselator = ExtraTesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();

        RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.bindTexture("/dirt.png");

        builder.begin(GlConst.GL_QUADS);
        builder.vertex(0.0f, (float)this.height, 0.0f).uv(0.0F, (float)this.height / 32.0F + (float)xOffset).color(64, 64, 64, 255).endVertex();
        builder.vertex((float)this.width, (float)this.height, 0.0f).uv((float)this.width / 32.0F, (float)this.height / 32.0F + (float)xOffset).color(64, 64, 64, 255).endVertex();
        builder.vertex((float)this.width, 0.0f, 0.0f).uv((float)this.width / 32.0F, (float)xOffset).color(64, 64, 64, 255).endVertex();
        builder.vertex(0.0f, 0.0f, 0.0f).uv(0.0F, (float)xOffset).color(64, 64, 64, 255).endVertex();
        tesselator.end();
    }

    protected void drawTooltip(String text, int x, int y) {
        drawTooltip(new String[]{text}, x, y);
    }

    protected void drawTooltip(String[] text, int x, int y) {
        if (text.length > 0) {
            int fontHeight = 10;

            int maxWidth = 0;
            int tooltipHeight = text.length == 1 ? -2 : 0;

            for (int i = 0; i < text.length; i++) {
                int lineWidth = this.minecraft.font.width(text[i]);
                tooltipHeight += fontHeight;
                if (lineWidth > maxWidth) {
                    maxWidth = lineWidth;
                }
            }

            int x0 = x + 12;
            int y0 = y - 12;

            if (x0 + maxWidth > this.width) {
                x0 -= 28 + maxWidth;
            }

            if (y0 + tooltipHeight + 6 > this.height) {
                y0 = this.height - tooltipHeight - 6;
            }

            if (y - tooltipHeight - 8 < 0) {
                y0 = y + 8;
            }

            GL11.glPushMatrix();
            int z = 100;
            ExtraTesselator tesselator = ExtraTesselator.getInstance();
            BufferBuilder builder = tesselator.getBuilder();
            RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);

            RenderHelper.disableTexture();
            RenderHelper.enableBlend();
            RenderHelper.defaultBlendFunc();

            builder.begin(GlConst.GL_QUADS);
            ExtraDrawableHelper.fillGradientXX(builder, x0 - 3, y0 - 4, x0 + maxWidth + 3, y0 - 3, 100, -267386864, -267386864);
            ExtraDrawableHelper.fillGradientXX(builder, x0 - 3, y0 + tooltipHeight + 3, x0 + maxWidth + 3, y0 + tooltipHeight + 4, 100, -267386864, -267386864);
            ExtraDrawableHelper.fillGradientXX(builder, x0 - 3, y0 - 3, x0 + maxWidth + 3, y0 + tooltipHeight + 3, 100, -267386864, -267386864);
            ExtraDrawableHelper.fillGradientXX(builder, x0 - 4, y0 - 3, x0 - 3, y0 + tooltipHeight + 3, 100, -267386864, -267386864);
            ExtraDrawableHelper.fillGradientXX(builder, x0 + maxWidth + 3, y0 - 3, x0 + maxWidth + 4, y0 + tooltipHeight + 3, 100, -267386864, -267386864);
            ExtraDrawableHelper.fillGradientXX(builder, x0 - 3, y0 - 3 + 1, x0 - 3 + 1, y0 + tooltipHeight + 3 - 1, 100, 1347420415, 1344798847);
            ExtraDrawableHelper.fillGradientXX(builder, x0 + maxWidth + 2, y0 - 3 + 1, x0 + maxWidth + 3, y0 + tooltipHeight + 3 - 1, 100, 1347420415, 1344798847);
            ExtraDrawableHelper.fillGradientXX(builder, x0 - 3, y0 - 3, x0 + maxWidth + 3, y0 - 3 + 1, 100, 1347420415, 1347420415);
            ExtraDrawableHelper.fillGradientXX(builder, x0 - 3, y0 + tooltipHeight + 2, x0 + maxWidth + 3, y0 + tooltipHeight + 3, 100, 1344798847, 1344798847);
            ExtraTesselator.endAndDraw();

            RenderHelper.disableBlend();
            RenderHelper.enableTexture();

            GL11.glTranslatef(0.0f, 0.0f, 100.0f);
            int x00 = x0;
            int y00 = y0;
            for (int i = 0; i < text.length; i++) {
                this.minecraft.font.drawShadow(text[i], x00, y00, 0xFFFFFF);
                y00 += fontHeight;
            }

            RenderHelper.setGlobalColor(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
}
