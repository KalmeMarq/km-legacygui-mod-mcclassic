package me.kalmemarq.legacygui.gui.widget;

import me.kalmemarq.legacygui.font.TextRenderer;
import me.kalmemarq.legacygui.text.Text;
import me.kalmemarq.legacygui.text.Texts;
import me.kalmemarq.legacygui.text.style.Style;
import me.kalmemarq.legacygui.util.MathHelper;

public class PressableTextWidget extends ButtonWidget {
    private final TextRenderer textRenderer;
    private final Text text;
    private final Text hoverText;
    private final int color;

    public PressableTextWidget(int x, int y, int width, int height, Text text, int color, ButtonWidget.PressAction onPress, TextRenderer textRenderer) {
        super(x, y, width, height, text, onPress);
        this.textRenderer = textRenderer;
        this.text = text;
        this.hoverText = Texts.setStyleIfAbsent(text.copy(), Style.EMPTY.withUnderline(true));
        this.color = color;
    }

    @Override
    public void renderButton(int mouseX, int mouseY) {
        Text txt = this.hovered ? this.hoverText : this.text;
        PressableTextWidget.drawTextWithShadow(this.textRenderer, txt, this.x, this.y, this.color | MathHelper.ceil(this.alpha * 255.0f) << 24);
    }
}
