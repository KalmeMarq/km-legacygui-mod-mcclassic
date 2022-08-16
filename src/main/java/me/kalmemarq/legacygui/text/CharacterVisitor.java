package me.kalmemarq.legacygui.text;

import me.kalmemarq.legacygui.text.style.Style;

@FunctionalInterface
public interface CharacterVisitor {
    public boolean accept(Style style, String chr);
}
