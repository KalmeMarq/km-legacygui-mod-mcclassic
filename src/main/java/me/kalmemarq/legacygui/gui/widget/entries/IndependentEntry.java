package me.kalmemarq.legacygui.gui.widget.entries;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.gui.widget.ModListWidget;
import net.fabricmc.loader.api.ModContainer;

public class IndependentEntry extends ModListEntry {
    public IndependentEntry(Minecraft mc, ModContainer container, ModListWidget list) {
        super(mc, container, list);
    }
}
