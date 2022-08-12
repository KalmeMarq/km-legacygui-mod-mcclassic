package me.kalmemarq.legacygui.gui.component.entries;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.gui.component.ModListWidget;
import net.fabricmc.loader.api.ModContainer;

public class IndependentEntry extends ModListEntry {
    public IndependentEntry(Minecraft mc, ModContainer container, ModListWidget list) {
        super(mc, container, list);
    }
}
