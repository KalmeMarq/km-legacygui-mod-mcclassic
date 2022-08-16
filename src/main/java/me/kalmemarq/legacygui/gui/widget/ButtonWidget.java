package me.kalmemarq.legacygui.gui.widget;

import com.mojang.minecraft.sound.Sound;
import com.mojang.minecraft.sound.WorldSound;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.gui.IRenderable;
import me.kalmemarq.legacygui.text.Text;
import org.lwjgl.opengl.GL11;

public class ButtonWidget extends AbstractWidget implements IRenderable {
    public static final ButtonWidget.TooltipSupplier NO_TOOLTIP = (button, mouseX, mouseY) -> {
    };

    protected ButtonWidget.PressAction onPress;
    protected ButtonWidget.TooltipSupplier onTooltip;

    public ButtonWidget(int x, int y, int width, int height, String title, ButtonWidget.PressAction onPress) {
        this(x, y, width, height, Text.literal(title), onPress, NO_TOOLTIP);
    }

    public ButtonWidget(int x, int y, int width, int height, Text title, ButtonWidget.PressAction onPress) {
        this(x, y, width, height, title, onPress, NO_TOOLTIP);
    }

    public ButtonWidget(int x, int y, int width, int height, String title, ButtonWidget.PressAction onPress, ButtonWidget.TooltipSupplier onTooltip) {
        this(x, y, width, height, Text.literal(title), onPress, onTooltip);
    }

    public ButtonWidget(int x, int y, int width, int height, Text title, ButtonWidget.PressAction onPress, ButtonWidget.TooltipSupplier onTooltip) {
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
    protected void renderButton(int mouseX, int mouseY) {
        LegacyGUIMod.textureManager.bind("/gui/gui.png");

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        int yOff = getYOffset(this.hovered);

        drawTextureXX(this.x, this.y, 0, 46 + (20 * yOff), this.width / 2, 20);
        drawTextureXX(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + (20 * yOff), this.width / 2, 20);

        int textColor = !this.active ? -6250336 : this.hovered ? 16777120 : 14737632;
        drawCenteredText(LegacyGUIMod.textRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, textColor);

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
                Sound s = LegacyGUIMod.getMCInstance().soundManager.getSound("click", 1.0f, 1.0f);

                if (s != null) {
                    LegacyGUIMod.getMCInstance().soundPlayer.a(s, new WorldSound() {
                        @Override
                        public float a() {
                            return 0.5f;
                        }

                        @Override
                        public float getVolume() {
                            return 1.0f;
                        }
                    });
                } else {
                    LegacyGUIMod.LOGGER.info("NULL NULL NULL");
                }

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
