package me.kalmemarq.legacygui.font;

import me.kalmemarq.legacygui.text.OrderedText;
import me.kalmemarq.legacygui.text.StringVisitable;
import me.kalmemarq.legacygui.text.TextVisitFactory;
import me.kalmemarq.legacygui.text.style.Style;

import java.security.PublicKey;
import java.util.concurrent.atomic.AtomicReference;

public class TextHandler {
    private final WidthRetriever widthRetriever;

    public TextHandler(WidthRetriever widthRetriever) {
        this.widthRetriever = widthRetriever;
    }

    public float getWidth(String text) {
        if (text == null) {
            return 0.0f;
        }

        MutableFloat width = new MutableFloat();
        TextVisitFactory.visitFormatted(text, Style.EMPTY, (style, chr) -> {
            width.add(this.widthRetriever.getWidth(chr, style));
            return true;
        });

        return width.floatValue();
    }

    public float getWidth(StringVisitable text) {
        MutableFloat width = new MutableFloat();
        TextVisitFactory.visitFormatted(text, Style.EMPTY, (style, chr) -> {
            width.add(this.widthRetriever.getWidth(chr, style));
            return true;
        });

        return width.floatValue();
    }

    public float getWidth(OrderedText text) {
        MutableFloat width = new MutableFloat();
        text.accept((style, chr) -> {
            width.add(this.widthRetriever.getWidth(chr, style));
            return true;
        });

        return width.floatValue();
    }

    @FunctionalInterface
    public static interface WidthRetriever {
        public float getWidth(String chr, Style style);
    }

    public static class MutableFloat {
        private final AtomicReference<Float> value;

        public MutableFloat() {
            this.value = new AtomicReference<>(0.0f);
        }

        public void add(float amount) {
            this.value.updateAndGet(v -> v + amount);
        }

        public float floatValue() {
            return value.get();
        }
    }
}
