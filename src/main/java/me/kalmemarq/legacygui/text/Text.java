package me.kalmemarq.legacygui.text;

import me.kalmemarq.legacygui.text.content.LiteralTextContent;
import me.kalmemarq.legacygui.text.content.TranslatableTextContent;
import me.kalmemarq.legacygui.text.style.Style;
import org.spongepowered.include.com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Text extends StringVisitable {
    public Style getStyle();

    public TextContent getContent();

    @Override
    default public String getString() {
        return StringVisitable.super.getString();
    }

    default public String asTruncatedString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        this.visit(string -> {
            int len = length - stringBuilder.length();
            if (len <= 0) {
                return TERMINATE_VISIT;
            }
            stringBuilder.append(string.length() <= len ? string : string.substring(0, len));
            return Optional.empty();
        });
        return stringBuilder.toString();
    }

    public List<Text> getSiblings();

    default public MutableText copyContentOnly() {
        return MutableText.of(this.getContent());
    }

    default public MutableText copy() {
        return new MutableText(this.getContent(), new ArrayList<Text>(this.getSiblings()), this.getStyle());
    }

    public OrderedText asOrderedText();

    @Override
    default public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style) {
        Style mergedStyle = this.getStyle().withParent(style);
        Optional<T> optional = this.getContent().visit(styledVisitor, mergedStyle);
        if (optional.isPresent()) {
            return optional;
        }

        for (Text lv2 : this.getSiblings()) {
            Optional<T> optional2 = lv2.visit(styledVisitor, mergedStyle);
            if (!optional2.isPresent()) continue;
            return optional2;
        }
        return Optional.empty();
    }

    @Override
    default public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
        Optional<T> optional = this.getContent().visit(visitor);
        if (optional.isPresent()) {
            return optional;
        }
        for (Text lv : this.getSiblings()) {
            Optional<T> optional2 = lv.visit(visitor);
            if (!optional2.isPresent()) continue;
            return optional2;
        }
        return Optional.empty();
    }

    default public List<Text> withoutStyle() {
        return this.getWithStyle(Style.EMPTY);
    }

    default public List<Text> getWithStyle(Style style) {
        ArrayList<Text> list = Lists.newArrayList();
        this.visit((styleOverride, text) -> {
            if (!text.isEmpty()) {
                list.add(Text.literal(text).fillStyle(styleOverride));
            }
            return Optional.empty();
        }, style);
        return list;
    }

    public static MutableText literal(String string) {
        return MutableText.of(new LiteralTextContent(string));
    }

    public static MutableText translatable(String key) {
        return MutableText.of(new TranslatableTextContent(key));
    }

    public static MutableText translatable(String key, Object ...arg) {
        return MutableText.of(new TranslatableTextContent(key, arg));
    }

    public static MutableText empty() {
        return MutableText.of(TextContent.EMPTY);
    }
}
