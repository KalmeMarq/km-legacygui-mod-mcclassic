package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import org.lwjgl.input.Keyboard;

public class GameMenuScreen extends ExtraScreen {
    public GameMenuScreen() {
        super("Game Menu");
    }

    @Override
    public void init() {
        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, "Return to Game", (button -> {
            this.minecraft.openScreen(null);
            this.minecraft.grabMouse();
        })));

        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 48 + -16, 204, 20, "Generate new level...", button -> {
            this.minecraft.openScreen(new CreateNewLevelScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, "Save level...", button -> {
            this.minecraft.openScreen(new LevelSaveScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, "Load level...", button -> {
            this.minecraft.openScreen(new LevelLoadScreen(this));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 96 + -16, 204, 20, "Options...", button -> {
            this.minecraft.openScreen(new OptionsScreen(this, this.minecraft.options));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, "Quit to title screen", button -> {
            TitleScreen.isInGame = false;
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
