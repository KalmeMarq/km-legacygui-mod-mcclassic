package me.kalmemarq.legacygui.gui.component;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.DrawableHelper;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.IRenderable;
import org.lwjgl.opengl.GL11;

public class ButtonWidget extends ExtraDrawableHelper implements IRenderable {
    public static final ButtonWidget.TooltipSupplier NO_TOOLTIP = (button, mouseX, mouseY) -> {
    };

    public int x;
    public int y;
    private int width;
    private int height;

    private String message;

    public boolean visible;
    public boolean active;

    protected final ButtonWidget.PressAction onPress;
    protected final ButtonWidget.TooltipSupplier onTooltip;

    private boolean hovered;

    public ButtonWidget(int x, int y, int width, int height, String title, ButtonWidget.PressAction onPress) {
        this(x, y, width, height, title, onPress, NO_TOOLTIP);
    }

    public ButtonWidget(int x, int y, int width, int height, String title, ButtonWidget.PressAction onPress, ButtonWidget.TooltipSupplier onTooltip) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.message = title;
        this.onPress = onPress;
        this.onTooltip = onTooltip;
        this.visible = true;
        this.active = true;
    }

    @Override
    public void render(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
            this.renderButton(mc, mouseX, mouseY);
        }
    }

    protected int getYOffset(boolean isHovered) {
        if (!this.active) return 0;
        if (isHovered) return 2;
        return 1;
    }

    protected void renderButton(Minecraft mc, int mouseX, int mouseY) {
        int id = mc.textures.getTextureId("/gui/gui.png");
        GL11.glBindTexture(3553, id);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        int yOff = getYOffset(this.hovered);
        drawTexture(this.x, this.y, this.width / 2, this.height, 0, 46 + (20 * yOff), this.width / 2, 20, 256, 256);
        drawTexture(this.x + this.width / 2, this.y, this.width / 2, this.height, 200 - this.width / 2, 46 + (20 * yOff), this.width / 2, 20, 256, 256);

        drawCenteredString(mc.font, this.message, this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF);

        if (this.hovered) {
            this.renderToolTip(mouseX, mouseY);
        }
    }

    public void onClick(int mouseX, int mouseY) {
        this.onPress.onPress(this);
    }

    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isValidButton(button)) {
            if (this.clicked(mouseX, mouseY)) {
                this.onClick(mouseX, mouseY);
                return true;
            }
        }

        return false;
    }

    public boolean clicked(int mouseX, int mouseY) {
        return this.visible && this.active && mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }

    public boolean isValidButton(int button) {
        return button == 0;
    }

    public void renderToolTip(int mouseX, int mouseY) {
        this.onTooltip.onTooltip(this, mouseX, mouseY);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface PressAction {
        void onPress(ButtonWidget button);
    }

    public interface TooltipSupplier {
        void onTooltip(ButtonWidget button, int mouseX, int mouseY);
    }
}
