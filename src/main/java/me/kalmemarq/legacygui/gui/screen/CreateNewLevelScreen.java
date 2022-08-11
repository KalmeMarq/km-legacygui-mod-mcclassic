package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gamemode.CreativeMode;
import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.SurvivalMode;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import me.kalmemarq.legacygui.util.Language;

public class CreateNewLevelScreen extends ExtraScreen {
    private final Screen parent;

    private ButtonWidget gameModeBtn;
    private boolean isCreative = true;

    public CreateNewLevelScreen(Screen screen) {
        super(Language.translate("generateLevel.title"));
        this.parent = screen;
    }

    public void init() {
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4, 200, 20, Language.translate("generateLevel.world_size.small"), button -> {
            this.generateWorld(0);
        }));
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 24, 200, 20, Language.translate("generateLevel.world_size.normal"), button -> {
            this.generateWorld(1);
        }));
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 48, 200, 20, Language.translate("generateLevel.world_size.huge"), button -> {
            this.generateWorld(2);
        }));

        gameModeBtn = new ButtonWidget(this.width / 2 - 100, this.height / 4 + 72, 200, 20, Language.translate("generateLevel.gamemode") + ": " + (isCreative ? Language.translate("generateLevel.gamemode.creative") : Language.translate("generateLevel.gamemode.survival")), button -> {
            isCreative = !isCreative;
            gameModeBtn.setMessage(Language.translate("generateLevel.gamemode") + ": " + (isCreative ? Language.translate("generateLevel.gamemode.creative") : Language.translate("generateLevel.gamemode.survival")));
        });

        this.addWidget(gameModeBtn);

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20, Language.translate("gui.cancel"), button -> {
            this.minecraft.openScreen(this.parent);
        }));
    }

    private void generateWorld(int id) {
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
        drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(mouseX, mouseY);
    }
}
