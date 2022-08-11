package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.DrawableHelper;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public abstract class ExtraScreen extends Screen {
    protected String title = "";
    protected List<ButtonWidget> widgets = new ArrayList<>();

    public ExtraScreen() {
        this("");
    }

    public ExtraScreen(String title) {
        super();
        this.title = title;
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        for (ButtonWidget widget : widgets) {
            if (widget.mouseClicked(x, y, button)) {
                return;
            }
        }
    }

    protected boolean keyPressedX(char key, int code) {
        if (code == Keyboard.KEY_ESCAPE) {
            if (TitleScreen.isInGame) {
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

    public ButtonWidget addWidget(ButtonWidget widget) {
        this.widgets.add(widget);
        return widget;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        for (ButtonWidget widget : widgets) {
            widget.render(this.minecraft, mouseX, mouseY);
        }
    }

    public void renderBackground() {
        if (TitleScreen.isInGame) {
            renderGradientBackground();
        } else {
            renderDirtBackground();
        }
    }

    public void renderGradientBackground() {
        fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
    }

    public void renderDirtBackground() {
        int var8 = this.minecraft.width * 240 / this.minecraft.height;
        int var3 = this.minecraft.height * 240 / this.minecraft.height;

        Tesselator var4 = Tesselator.instance;
        int var5 = this.minecraft.textures.getTextureId("/dirt.png");
        GL11.glBindTexture(3553, var5);
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
