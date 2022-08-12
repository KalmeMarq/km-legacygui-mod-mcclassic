package me.kalmemarq.legacygui.gui.component;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.util.GlConst;
import me.kalmemarq.legacygui.util.RenderHelper;
import org.lwjgl.opengl.GL11;

public class TexturedButtonWidget extends ButtonWidget {
    private final String location;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffTex;
    private final int textureWidth;
    private final int textureHeight;

    public TexturedButtonWidget(int x, int y, int width, int height, int xTexStart, int yTexStart, String location, ButtonWidget.PressAction onPress) {
        this(x, y, width, height, xTexStart, yTexStart, height, location, 256, 256, onPress);
    }

    public TexturedButtonWidget(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, String location, ButtonWidget.PressAction onPress) {
        this(x, y, width, height, xTexStart, yTexStart, yDiffTex, location, 256, 256, onPress);
    }

    public TexturedButtonWidget(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, String location, int textureWidth, int textureHeight, ButtonWidget.PressAction onPress) {
        this(x, y, width, height, xTexStart, yTexStart, yDiffTex, location, textureWidth, textureHeight, onPress, "");
    }

    public TexturedButtonWidget(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, String location, int textureWidth, int textureHeight, ButtonWidget.PressAction onPress, String title) {
        this(x, y, width, height, xTexStart, yTexStart, yDiffTex, location, textureWidth, textureHeight, onPress, ButtonWidget.NO_TOOLTIP, title);
    }

    public TexturedButtonWidget(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, String location, int textureWidth, int textureHeight, ButtonWidget.PressAction onPress, ButtonWidget.TooltipSupplier var12, String title) {
        super(x, y, width, height, title, onPress, var12);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        this.yDiffTex = yDiffTex;
        this.location = location;
    }

    @Override
    protected void renderButton(Minecraft mc, int mouseX, int mouseY) {

        int vOff = this.yTexStart;
        if (this.hovered) {
            vOff += this.yDiffTex;
        }

        RenderHelper.bindTexture(mc, "/" + this.location);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        drawTextureXX(this.x, this.y, xTexStart, vOff, this.width, this.height, this.textureWidth, this.textureHeight);

        if (this.hovered) {
            this.renderToolTip(mouseX, mouseY);
        }
    }
}
