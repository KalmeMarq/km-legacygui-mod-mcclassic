package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.DrawableHelper;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.gui.component.AbstractWidget;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import me.kalmemarq.legacygui.gui.component.EditTextWidget;
import me.kalmemarq.legacygui.util.GlConst;
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
    protected void keyPressed(char c, int key) {
        this.keyPressedX(c, key);
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
        if (this.minecraft.level != null) {
            renderGradientBackground();
        } else {
            renderDirtBackground();
        }
    }

    public void renderGradientBackground() {
        fillGradient(0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
    }

    public void renderDirtBackground() {
        int var8 = this.minecraft.width * 240 / this.minecraft.height;
        int var3 = this.minecraft.height * 240 / this.minecraft.height;

        Tesselator var4 = Tesselator.instance;
        int var5 = this.minecraft.textures.getTextureId("/dirt.png");
        GL11.glBindTexture(GlConst.GL_TEXTURE_2D, var5);
        float var9 = 32.0F;
        var4.begin();
        var4.color(4210752);
        var4.vertexUV(0.0F, (float)var3, 0.0F, 0.0F, (float)var3 / var9);
        var4.vertexUV((float)var8, (float)var3, 0.0F, (float)var8 / var9, (float)var3 / var9);
        var4.vertexUV((float)var8, 0.0F, 0.0F, (float)var8 / var9, 0.0F);
        var4.vertexUV(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        var4.end();
    }
}
