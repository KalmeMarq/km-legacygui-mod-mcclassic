package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.Options;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import me.kalmemarq.legacygui.util.IOptionsExtra;
import me.kalmemarq.legacygui.util.Language;

public class LanguageScreen extends ExtraScreen {

    private final Screen parent;

    public LanguageScreen(Screen parent) {
        super(Language.translate("options.language"));
        this.parent = parent;
    }

    @Override
    public void init() {
        for(int i = 0; i < Language.languages.size(); ++i) {
            int finalI = i;
            this.addWidget(new ButtonWidget(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, Language.languages.get(i).getFullName(), button -> {
                Language.language = Language.languages.get(finalI).getCode();
                Language.load();
                this.minecraft.openScreen(this);
                ((IOptionsExtra)(Object)this.minecraft.options).saveX();
            }));
        }

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height - 28, 200, 20, Language.translate("gui.done"), button -> {
            this.minecraft.openScreen(this.parent);
        }));
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderBackground();
        drawCenteredString(this.font, this.title, this.width / 2, 15, 0xFFFFFF);
        super.render(mouseX, mouseY);
    }
}
