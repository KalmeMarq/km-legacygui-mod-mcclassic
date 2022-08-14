package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.widget.ButtonWidget;
import me.kalmemarq.legacygui.gui.widget.EditTextWidget;
import me.kalmemarq.legacygui.util.Language;

public class DirectConnectScreen extends ExtraScreen {
    EditTextWidget serverAddressBox;

    private final Screen parent;
    private final DoConnectFunc doConnectFunc;

    public DirectConnectScreen(Screen parent, DoConnectFunc doConnectFunc) {
        super(Language.translate("multiplayer.direct_connection"));
        this.parent = parent;
        this.doConnectFunc = doConnectFunc;
    }

    @Override
    public void init() {
        this.serverAddressBox = this.addWidget(new EditTextWidget(this.width / 2 - 100, this.height / 4 - 12 + 12, 200, 20));

        ButtonWidget joinBtn = this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 - 12 + 12 + 48, 200, 20, Language.translate("multiplayer.join_server"), (button) -> {
            this.doConnectFunc.doConnect(this.serverAddressBox.getValue());
        }));

        serverAddressBox.setOnChangeistener((newValue -> {
            joinBtn.active = !newValue.isEmpty();
        }));

        serverAddressBox.setValue("localhost:25565");

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 4 - 12 + 12 + 48 + 24, 200, 20, Language.translate("gui.cancel"), (button) -> {
            this.minecraft.openScreen(this.parent);
        }));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.serverAddressBox != null) this.serverAddressBox.tick();
    }

    @Override
    public void render(int mouseX, int mouseY) {
        renderBackground();
        drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(mouseX, mouseY);
    }

    @FunctionalInterface
    public interface DoConnectFunc {
        void doConnect(String address);
    }
}
