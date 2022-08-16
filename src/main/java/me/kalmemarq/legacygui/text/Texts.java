package me.kalmemarq.legacygui.text;

import me.kalmemarq.legacygui.text.style.Style;

public class Texts {
    public static MutableText setStyleIfAbsent(MutableText text, Style style) {
        if (style.isEmpty()) {
            return text;
        }
        Style sl = text.getStyle();
        if (sl.isEmpty()) {
            return text.setStyle(style);
        }
        if (sl.equals(style)) {
            return text;
        }
        return text.setStyle(sl.withParent(style));
    }
}
