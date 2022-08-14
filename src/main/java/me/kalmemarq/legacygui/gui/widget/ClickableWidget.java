package me.kalmemarq.legacygui.gui.widget;

import me.kalmemarq.legacygui.gui.Drawable;
import me.kalmemarq.legacygui.gui.Element;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;

public class ClickableWidget extends ExtraDrawableHelper implements Drawable, Element {
    public int x;
    public int y;
    protected int width;
    protected int height;

    private String message;

    protected boolean hovered;
    public boolean visible = true;
    private boolean focused;
    public boolean active = true;
    protected float alpha = 1.0f;

    public ClickableWidget(int x, int y, int width, int height, String title) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.message = title;
    }

    @Override
    public void render(int mouseX, int mouseY, float deltaTime) {
    }
}
