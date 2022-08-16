package me.kalmemarq.legacygui.text;

import me.kalmemarq.legacygui.text.style.Formatting;
import me.kalmemarq.legacygui.text.style.Style;
import org.spongepowered.include.com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class MutableText implements Text {
    private final TextContent content;
    private final List<Text> siblings;
    private Style style;

    MutableText(TextContent content, List<Text> siblings, Style style) {
        this.content = content;
        this.siblings = siblings;
        this.style = style;
    }

    public static MutableText of(TextContent content) {
        return new MutableText(content, Lists.newArrayList(), Style.EMPTY);
    }

    @Override
    public TextContent getContent() {
        return this.content;
    }

    @Override
    public List<Text> getSiblings() {
        return this.siblings;
    }

    public MutableText append(String text) {
        return this.append(Text.literal(text));
    }

    public MutableText append(Text text) {
        this.siblings.add(text);
        return this;
    }

    public MutableText setStyle(Style style) {
        this.style = style;
        return this;
    }

    @Override
    public Style getStyle() {
        return this.style;
    }

    public MutableText styled(UnaryOperator<Style> styleUpdater) {
        this.setStyle((Style)styleUpdater.apply(this.getStyle()));
        return this;
    }

    public MutableText fillStyle(Style styleOverride) {
        this.setStyle(styleOverride.withParent(this.getStyle()));
        return this;
    }

    public MutableText formatted(Formatting...formattings) {
        this.setStyle(this.getStyle().withFormatting(formattings));
        return this;
    }

    public MutableText formatted(Formatting formatting) {
        this.setStyle(this.getStyle().withFormatting(formatting));
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof MutableText) {
            MutableText mt = (MutableText)obj;
            return this.content.equals(mt.content) && this.siblings.equals(mt.siblings);
        }

        return false;
    }

    public int hashCode() {
        return Objects.hash(this.content, this.siblings);
    }

    @Override
    public OrderedText asOrderedText() {
       return this.reorder(this);
    }

    public OrderedText reorder(StringVisitable text) {
        return visitor -> text.visit((style, string) -> TextVisitFactory.visitFormatted(string, style, visitor) ? Optional.empty() : StringVisitable.TERMINATE_VISIT, Style.EMPTY).isPresent();
    }

    @Override
    public String getString() {
        return Text.super.getString();
    }
}
