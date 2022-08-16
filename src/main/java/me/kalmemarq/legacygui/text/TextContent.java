package me.kalmemarq.legacygui.text;

import me.kalmemarq.legacygui.text.style.Style;

import java.util.Optional;

public interface TextContent {
    public static final TextContent EMPTY = new TextContent() {
        public String toString() {
            return "empty";
        }
    };

    default public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> visitor, Style style) {
        return Optional.empty();
    }

    default public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
        return Optional.empty();
    }
}
