package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.widget.ButtonWidget;

public class CustomScreen extends ExtraScreen {
    private final Screen parent;

    public CustomScreen(Screen parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void init() {
        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height - 28, 200, 20, "Cancel", (button -> {
            this.minecraft.openScreen(this.parent);
        })));
    }
}
