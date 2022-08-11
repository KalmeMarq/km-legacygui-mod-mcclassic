package me.kalmemarq.legacygui.gui.component;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.DrawableHelper;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.IRenderable;
import me.kalmemarq.legacygui.util.GlConst;
import org.lwjgl.opengl.GL11;

public class ButtonWidget extends AbstractWidget implements IRenderable {
    public static final ButtonWidget.TooltipSupplier NO_TOOLTIP = (button, mouseX, mouseY) -> {
    };

    protected ButtonWidget.PressAction onPress;
    protected ButtonWidget.TooltipSupplier onTooltip;

    public ButtonWidget(int x, int y, int width, int height, String title, ButtonWidget.PressAction onPress) {
        this(x, y, width, height, title, onPress, NO_TOOLTIP);
    }

    public ButtonWidget(int x, int y, int width, int height, String title, ButtonWidget.PressAction onPress, ButtonWidget.TooltipSupplier onTooltip) {
        super(x, y, width, height, title);
        this.onPress = onPress;
        this.onTooltip = onTooltip;
    }

    protected int getYOffset(boolean isHovered) {
        if (!this.active) return 0;
        if (isHovered) return 2;
        return 1;
    }

    @Override
    protected void renderButton(Minecraft mc, int mouseX, int mouseY) {
        int id = mc.textures.getTextureId("/gui/gui.png");
        GL11.glBindTexture(GlConst.GL_TEXTURE_2D, id);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        int yOff = getYOffset(this.hovered);
        drawTexture(this.x, this.y, this.width / 2, this.height, 0, 46 + (20 * yOff), this.width / 2, 20, 256, 256);
        drawTexture(this.x + this.width / 2, this.y, this.width / 2, this.height, 200 - this.width / 2, 46 + (20 * yOff), this.width / 2, 20, 256, 256);

        int textColor = !this.active ? -6250336 : this.hovered ? 16777120 : 14737632;
        drawCenteredString(mc.font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, textColor);

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

    public void renderToolTip(int mouseX, int mouseY) {
        this.onTooltip.onTooltip(this, mouseX, mouseY);
    }

    public interface PressAction {
        void onPress(ButtonWidget button);
    }

    public interface TooltipSupplier {
        void onTooltip(ButtonWidget button, int mouseX, int mouseY);
    }
}
