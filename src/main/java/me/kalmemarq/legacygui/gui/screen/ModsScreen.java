package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.Util;
import me.kalmemarq.legacygui.gui.widget.ButtonWidget;
import me.kalmemarq.legacygui.gui.widget.ModListWidget;
import me.kalmemarq.legacygui.gui.widget.entries.ModListEntry;
import me.kalmemarq.legacygui.util.Language;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class ModsScreen extends ExtraScreen {
    private ModListWidget list;

    private final Screen parent;
    private double scrollPercent = 0;
    private ModListEntry selected;
    private int paneY;
    private int paneWidth;
    private int rightPaneX;
    private int searchBoxX;

    public ModsScreen(Screen parent) {
        super(Language.translate("menu.mods"));
        this.parent = parent;
    }

    @Override
    public void init() {
        paneY = 48;
        paneWidth = this.width / 2 - 8;
        rightPaneX = width - paneWidth;

        int searchBoxWidth = paneWidth - 32 - 22;
        searchBoxX = paneWidth / 2 - searchBoxWidth / 2 - 22 / 2;

        this.list = new ModListWidget(this.minecraft, this.paneWidth, this.height, paneY + 19, this.height - 36, 36, "", list, this);
        this.list.reloadFilters();

        this.addWidget(new ButtonWidget(this.width / 2 - 154, this.height - 28, 150, 20, Language.translate("Open Mods Folder"), button -> {
            Util.getOperatingSystem().openFile(new File(FabricLoader.getInstance().getGameDir().toFile(), "mods"));
        }));

        this.addWidget(new ButtonWidget(this.width / 2 + 4, this.height - 28, 150, 20, Language.translate("gui.done"), button -> {
            this.minecraft.openScreen(this.parent);
        }));
    }

    public ModListEntry getSelectedEntry() {
        return selected;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderDirtBackground();
        drawCenteredString(this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        if (list != null) {
            list.render(mouseX, mouseY);
        }
        super.render(mouseX, mouseY);
    }

    public void updateScrollPercent(double scrollPercent) {
        this.scrollPercent = scrollPercent;
    }

    public void updateSelectedEntry(ModListEntry entry) {
        if (entry != null) {
            this.selected = entry;
        }
    }

    public double getScrollPercent() {
        return scrollPercent;
    }
}
