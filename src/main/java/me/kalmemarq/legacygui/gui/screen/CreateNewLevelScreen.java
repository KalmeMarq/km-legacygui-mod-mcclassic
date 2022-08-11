package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gamemode.CreativeMode;
import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.SurvivalMode;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;

public class CreateNewLevelScreen extends ExtraScreen {
    private final Screen parent;

    private ButtonWidget gameModeBtn;
    private boolean isCreative = true;

    public CreateNewLevelScreen(Screen screen) {
        super("Generate new level");
        this.parent = screen;
    }

    public void init() {
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4, 200, 20, "Small", button -> {
            this.generateWorld(0);
        }));
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 24, 200, 20, "Normal", button -> {
            this.generateWorld(1);
        }));
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 48, 200, 20, "Huge", button -> {
            this.generateWorld(2);
        }));

        gameModeBtn = new ButtonWidget(this.width / 2 - 100, this.height / 4 + 72, 200, 20, "Game Mode: " + (isCreative ? "Creative" : "Survival"), button -> {
            isCreative = !isCreative;
            gameModeBtn.setMessage("Game Mode: " + (isCreative ? "Creative" : "Survival"));
        });

        this.addWidget(gameModeBtn);

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20, "Cancel", button -> {
            this.minecraft.openScreen(this.parent);
        }));
    }

    private void generateWorld(int id) {
        TitleScreen.isInGame = true;
        this.minecraft.gameMode = isCreative ? new CreativeMode(this.minecraft) : new SurvivalMode(this.minecraft);
        this.minecraft.generateNewLevel(id);
        this.minecraft.openScreen((Screen)null);
        this.minecraft.grabMouse();
    }

    protected final void buttonClicked(Button button) {
        if (button.id == 3) {
            this.minecraft.openScreen(this.parent);
        } else {
            this.minecraft.generateNewLevel(button.id);
            this.minecraft.openScreen((Screen)null);
            this.minecraft.grabMouse();
        }
    }

    public final void render(int mouseX, int mouseY) {
        this.renderBackground();
        drawCenteredString(this.font, this.title, this.width / 2, 40, 16777215);
        super.render(mouseX, mouseY);
    }
}
