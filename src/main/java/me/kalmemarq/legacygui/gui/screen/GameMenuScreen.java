package me.kalmemarq.legacygui.gui.screen;

import me.kalmemarq.legacygui.gui.widget.ButtonWidget;
import me.kalmemarq.legacygui.util.Language;
import org.lwjgl.input.Keyboard;

public class GameMenuScreen extends ExtraScreen {
    public GameMenuScreen() {
        super(Language.translate("menu.game"));
    }

    @Override
    public void init() {
        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, Language.translate("menu.return_to_game"), (button -> {
            this.minecraft.openScreen(null);
            this.minecraft.grabMouse();
        })));

        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 48 + -16, 204, 20, Language.translate("menu.generateLevel"), button -> {
            this.minecraft.openScreen(new CreateNewLevelScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, Language.translate("menu.saveLevel"), button -> {
            this.minecraft.openScreen(new LevelSaveScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, Language.translate("menu.loadLevel"), button -> {
            this.minecraft.openScreen(new LevelLoadScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 96 + -16, 204, 20, Language.translate("menu.options"), button -> {
            this.minecraft.openScreen(new OptionsScreen(this, this.minecraft.options));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, this.minecraft.connectionManager != null ? Language.translate("menu.disconnect") : Language.translate("menu.return_to_title"), button -> {
            if (this.minecraft.connectionManager != null) {
                this.minecraft.connectionManager.connection.disconnect();
                this.minecraft.connectionManager = null;
            }

            this.minecraft.level = null;
            this.minecraft.openScreen(new TitleScreen());
        }));
    }

    @Override
    protected boolean keyPressedX(char key, int code) {
        if (code == Keyboard.KEY_ESCAPE) {
            this.minecraft.openScreen(null);
            this.minecraft.grabMouse();
            return true;
        }

        return super.keyPressedX(key, code);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderGradientBackground();
        drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(mouseX, mouseY);
    }
}
