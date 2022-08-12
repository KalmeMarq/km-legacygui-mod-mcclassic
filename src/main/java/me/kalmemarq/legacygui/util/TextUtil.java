package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.gui.Font;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {
    public static List<String> splitToFit(String input, Font font, int maxWidth) {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();

        for (String word : input.split(" ")) {
            if (font.width(line + " " + word) < maxWidth ||line.length() == 0) {
                if (line.length() != 0) {
                    line.append(" ");
                }
                line.append(word);
            } else {
                lines.add(line.toString());
                line = new StringBuilder().append(word);
            }
        }

        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines;
    }
}
