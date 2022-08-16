package me.kalmemarq.legacygui.text;

import me.kalmemarq.legacygui.text.style.Style;
import org.spongepowered.include.com.google.common.collect.ImmutableList;
import org.spongepowered.include.com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface OrderedText {
    public static final OrderedText EMPTY = visitor -> true;
    public boolean accept(CharacterVisitor visitor);

    public static OrderedText styled(String chr, Style style) {
        return visitor -> visitor.accept(style, chr);
    }

    public static OrderedText of(OrderedText text) {
        return text;
    }

    public static OrderedText empty() {
        return EMPTY;
    }

    public static OrderedText concat(OrderedText first, OrderedText second) {
        return OrderedText.innerConcat(first, second);
    }

    public static OrderedText concat(OrderedText ... texts) {
        return OrderedText.innerConcat(Arrays.stream(texts).collect(Collectors.toList()));
    }

    public static OrderedText concat(List<OrderedText> texts) {
        int i = texts.size();
        switch (i) {
            case 0: {
                return EMPTY;
            }
            case 1: {
                return texts.get(0);
            }
            case 2: {
                return OrderedText.innerConcat(texts.get(0), texts.get(1));
            }
        }
        return OrderedText.innerConcat(texts);
    }

    public static OrderedText innerConcat(OrderedText text1, OrderedText text2) {
        return visitor -> text1.accept(visitor) && text2.accept(visitor);
    }

    public static OrderedText innerConcat(List<OrderedText> texts) {
        return visitor -> {
            for (OrderedText orderedText : texts) {
                if (orderedText.accept(visitor)) continue;
                return false;
            }
            return true;
        };
    }
}
