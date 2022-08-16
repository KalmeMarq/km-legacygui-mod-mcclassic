package me.kalmemarq.legacygui.text.style;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;
import org.spongepowered.include.com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextColor {
    private static final Map<Formatting, TextColor> FORMATTING_TO_COLOR = Stream.of(Formatting.values()).filter(Formatting::isColor).collect(Collectors.toMap(Function.identity(), f -> new TextColor(f.getColorValue(), f.getName())));

    private final int rgb;
    @Nullable
    private final String name;

    private TextColor(int rgb, String name) {
        this.rgb = rgb;
        this.name = name;
    }

    private TextColor(int rgb) {
        this.rgb = rgb;
        this.name = null;
    }

    public int getRgb() {
        return this.rgb;
    }

    public String getName() {
        if (this.name != null) {
            return this.name;
        }
        return this.getHexCode();
    }

    private String getHexCode() {
        return String.format(Locale.ROOT, "#%06X", this.rgb);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        TextColor tColor = (TextColor)obj;
        return this.rgb == tColor.rgb;
    }

    public int hashCode() {
        return Objects.hash(this.rgb, this.name);
    }

    public String toString() {
        return this.name != null ? this.name : this.getHexCode();
    }

    @Nullable
    public static TextColor fromFormatting(Formatting formatting) {
        return FORMATTING_TO_COLOR.get(formatting);
    }

    public static TextColor fromRgb(int rgb) {
        return new TextColor(rgb);
    }
}
