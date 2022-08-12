package me.kalmemarq.legacygui.gui.component;

import com.mojang.minecraft.Minecraft;

public class ObjectSelectionList<E extends ObjectSelectionList.Entry<E>> extends AbstractSelectionList<E> {
    public ObjectSelectionList(Minecraft mc, int width, int height, int y0, int y1, int itemHeight) {
        super(mc, width, height, y0, y1, itemHeight);
    }

    public abstract static class Entry<E extends ObjectSelectionList.Entry<E>> extends AbstractSelectionList.Entry<E> {
        public Entry() {
        }
    }
}
