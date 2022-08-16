package me.kalmemarq.legacygui.text.content;

import me.kalmemarq.legacygui.text.StringVisitable;
import me.kalmemarq.legacygui.text.TextContent;
import me.kalmemarq.legacygui.text.style.Style;

import java.util.Optional;

public class LiteralTextContent implements TextContent {
    private final String content;

    public LiteralTextContent(String content) {
        this.content = content;
    }

    @Override
    public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
        return visitor.accept(this.content);
    }

    @Override
    public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> visitor, Style style) {
        return visitor.accept(style, this.content);
    }

    @Override
    public String toString() {
        return "literal{" + this.content + "}";
    }
}
