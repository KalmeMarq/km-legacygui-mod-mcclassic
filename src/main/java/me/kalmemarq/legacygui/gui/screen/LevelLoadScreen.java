package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import me.kalmemarq.legacygui.util.Language;
import org.lwjgl.input.Keyboard;

public class LevelLoadScreen extends ExtraScreen {
    private boolean save;
    private final Screen parent;

    public LevelLoadScreen(Screen parent) {
        super(Language.translate("loadLevel.title"));
        this.parent = parent;
    }

    @Override
    public void init() {
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 120 + 12, 200, 20, Language.translate("menu.loadLevel"), button -> {
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, Language.translate("gui.cancel"), button -> {
            this.minecraft.openScreen(parent);
        }));
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
        super.render(mouseX, mouseY);
    }
}
