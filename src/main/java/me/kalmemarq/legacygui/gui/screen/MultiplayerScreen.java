package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.Options;
import com.mojang.minecraft.User;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.net.ConnectionManager;
import me.kalmemarq.legacygui.gui.component.ButtonWidget;
import me.kalmemarq.legacygui.gui.component.EditTextWidget;
import me.kalmemarq.legacygui.util.Language;
import org.lwjgl.input.Keyboard;

public class MultiplayerScreen extends ExtraScreen {
    private final Screen parent;

    public MultiplayerScreen(Screen parent) {
        super(Language.translate("multiplayer.title"));
        this.parent = parent;
    }

    EditTextWidget playerNameBox;
    EditTextWidget serverAddressBox;

    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);

        this.playerNameBox = this.addWidget(new EditTextWidget(this.width / 2 - 100, this.height / 4 - 12 + 12, 200, 20));
        this.serverAddressBox = this.addWidget(new EditTextWidget(this.width / 2 - 100, this.height / 4 - 12 + 40 + 12, 200, 20));

        this.playerNameBox.setValue("KalmeMarq");
        this.serverAddressBox.setValue("localhost:25565");

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168 - 24, 200, 20, Language.translate("multiplayer.connect"), (button) -> {
            if (!this.serverAddressBox.getValue().isEmpty()) {
                try {
                    Level level = new Level();
                    level.setData(8, 8, 8, new byte[512]);
                    this.minecraft.setLevel(level);

                    String[] ip = this.serverAddressBox.getValue().split(":");
                    User user = this.minecraft.user;
                    String name;
                    String pass;

                    if (!this.playerNameBox.getValue().isEmpty()) {
                        name = this.playerNameBox.getValue();
                    } else if (user == null || user.name == null) {
                        name = "Player";
                    } else {
                        name = user.name;
                    }

                    if (user == null || user.mpPass == null || !this.playerNameBox.getValue().isEmpty()) {
                        pass = "";
                    } else {
                        pass = user.mpPass;
                    }

                    this.minecraft.connectionManager = new ConnectionManager(this.minecraft, ip[0], ip.length == 1 ? 25565 : Integer.parseInt(ip[1]), name, pass);
                    this.minecraft.openScreen(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));

        this.addWidget(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 168, 200, 20, Language.translate("gui.cancel"), (button) -> {
            this.minecraft.openScreen(this.parent);
        }));
    }

    public void tick() {
        if (this.playerNameBox != null) this.playerNameBox.tick();
        if (this.serverAddressBox != null) this.serverAddressBox.tick();
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderBackground();
        drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        drawString(this.font, Language.translate("multiplayer.username"), this.width / 2 - 100, this.height / 4 - 12, 0xbbbbbb);
        drawString(this.font, Language.translate("multiplayer.server_address"), this.width / 2 - 100, this.height / 4 - 12 + 40, 0xbbbbbb);
        super.render(mouseX, mouseY);
    }
}
