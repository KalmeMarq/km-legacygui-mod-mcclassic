package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.Options;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.widget.ButtonWidget;
import me.kalmemarq.legacygui.util.Language;
import org.lwjgl.input.Keyboard;

public class OptionsScreen extends ExtraScreen {
    private final Screen parent;
    private final Options options;

    public OptionsScreen(Screen parent, Options options) {
        super(Language.translate("options.title"));
        this.parent = parent;
        this.options  = options;
    }

    @Override
    public void init() {
        int i;
        for(i = 0; i < this.options.optionCount; ++i) {
            int finalI = i;
            this.addWidget(new ButtonWidget(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, this.options.getOptionLabel(i), button -> {
                this.options.cycleOption(finalI, 1);
                button.setMessage(this.options.getOptionLabel(finalI));
            }));
        }

        ++i;

        this.addWidget(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 120 + 12, 150, 20, Language.translate("options.controls"), (button -> {
            this.minecraft.openScreen(new KeybindScreen(this, this.options));
        })));

        this.addWidget(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 120 + 12, 150, 20, Language.translate("options.language"), (button -> {
            this.minecraft.openScreen(new LanguageScreen(this));
        })));

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, Language.translate("gui.done"), (button -> {
            this.minecraft.openScreen(this.parent);
        })));
    }

    @Override
    protected boolean keyPressedX(char key, int code) {
        if (code == Keyboard.KEY_ESCAPE) {
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
