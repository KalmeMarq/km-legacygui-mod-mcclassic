package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.Options;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import me.kalmemarq.legacygui.util.Language;
import org.lwjgl.input.Keyboard;

public class KeybindScreen extends ExtraScreen {
    private final Screen parent;
    private final Options options;

    private int selected = -1;

    public KeybindScreen(Screen parent, Options options) {
        super(Language.translate("options.keybinds"));
        this.parent = parent;
        this.options  = options;
    }

    @Override
    public void init() {
        for(int i = 0; i < this.options.keybinds.length; ++i) {
            int finalI = i;
            this.addWidget(new ButtonWidget(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, this.options.getKeybindLabel(i), button -> {
                button.setMessage(this.options.getKeybindLabel(finalI));

                this.selected = finalI;
                button.setMessage("> " + this.options.getKeybindLabel(finalI) + " <");
            }));
        }

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, Language.translate("gui.done"), button -> {
            this.minecraft.openScreen(this.parent);
        }));
    }

    @Override
    protected boolean keyPressedX(char key, int code) {
        if (this.selected >= 0) {
            this.options.setKeybind(this.selected, code);
            (this.widgets.get(this.selected)).setMessage(this.options.getKeybindLabel(this.selected));
            this.selected = -1;
            return true;
        } else if (code == Keyboard.KEY_ESCAPE) {
            this.minecraft.openScreen(this.parent);
            return true;
        }

        return super.keyPressedX(key, code);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderBackground();
        drawCenteredString(this.minecraft.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(mouseX, mouseY);
    }
}
