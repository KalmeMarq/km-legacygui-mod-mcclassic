package me.kalmemarq.legacygui.gui.widget;

import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.IRenderable;
import me.kalmemarq.legacygui.text.Text;

public abstract class AbstractWidget extends ExtraDrawableHelper implements IRenderable {
    public int x;
    public int y;
    protected int width;
    protected int height;

    public boolean visible = true;
    public boolean active = true;
    public float alpha = 1.0f;

    private Text message;
    protected boolean hovered;

    public AbstractWidget(int x, int y, int width, int height, Text title) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.message = title;
    }

    public void render(int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
            this.renderButton(mouseX, mouseY);
        }
    }

    protected void renderButton(int mouseX, int mouseY) {
    }

    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        return false;
    }

    public void setMessage(Text message) {
        this.message = message;
    }

    @Deprecated
    public void setMessage(String message) {
        this.message = Text.literal(message);
    }

    public Text getMessage() {
        return this.message;
    }

    public boolean clicked(int mouseX, int mouseY) {
        return this.visible && this.active && mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }

    public boolean isValidButton(int button) {
        return button == 0;
    }
}
