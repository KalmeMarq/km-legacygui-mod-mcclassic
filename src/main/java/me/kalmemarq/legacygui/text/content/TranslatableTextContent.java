package me.kalmemarq.legacygui.text.content;

import me.kalmemarq.legacygui.text.StringVisitable;
import me.kalmemarq.legacygui.text.Text;
import me.kalmemarq.legacygui.text.TextContent;
import me.kalmemarq.legacygui.text.style.Style;
import me.kalmemarq.legacygui.util.Language;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslatableTextContent implements TextContent {
    private static final Object[] EMPTY_ARGUMENTS = new Object[0];
    private static final StringVisitable LITERAL_PERCENT_SIGN = StringVisitable.plain("%");
    private static final StringVisitable NULL_ARGUMENT = StringVisitable.plain("null");
    private static final Pattern ARG_FORMAT = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    private List<StringVisitable> translations = ImmutableList.of();
    private final String key;
    private final Object[] args;

    public TranslatableTextContent(String key) {
        this.key = key;
        this.args = EMPTY_ARGUMENTS;
    }

    public TranslatableTextContent(String key, Object ... args) {
        this.key = key;
        this.args = args;
    }

    private void updateTranslations() {
        String string = Language.translate(this.key);
        try {
            ImmutableList.Builder<StringVisitable> builder = ImmutableList.builder();
            this.forEachPart(string, builder::add);
            this.translations = builder.build();
        }
        catch (TranslationException lv2) {
            this.translations = ImmutableList.of(StringVisitable.plain(string));
        }
    }

    private void forEachPart(String translation, Consumer<StringVisitable> partsConsumer) {
        Matcher matcher = ARG_FORMAT.matcher(translation);
        try {
            int i = 0;
            int start = 0;
            while (matcher.find(start)) {
                String string2;
                int k = matcher.start();
                int l = matcher.end();
                if (k > start) {
                    string2 = translation.substring(start, k);
                    if (string2.indexOf(37) != -1) {
                        throw new IllegalArgumentException();
                    }
                    partsConsumer.accept(StringVisitable.plain(string2));
                }
                string2 = matcher.group(2);
                String string3 = translation.substring(k, l);
                if ("%".equals(string2) && "%%".equals(string3)) {
                    partsConsumer.accept(LITERAL_PERCENT_SIGN);
                } else if ("s".equals(string2)) {
                    int m;
                    String string4 = matcher.group(1);
                    int n = m = string4 != null ? Integer.parseInt(string4) - 1 : i++;
                    if (m < this.args.length) {
                        partsConsumer.accept(this.getArg(m));
                    }
                } else {
                    throw new TranslationException(this, "Unsupported format: '" + string3 + "'");
                }
                start = l;
            }
            if (start < translation.length()) {
                String string5 = translation.substring(start);
                if (string5.indexOf(37) != -1) {
                    throw new IllegalArgumentException();
                }
                partsConsumer.accept(StringVisitable.plain(string5));
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new TranslationException(this, (Throwable)illegalArgumentException);
        }
    }

    private StringVisitable getArg(int index) {
        if (index >= this.args.length) {
            throw new TranslationException(this, index);
        }
        Object object = this.args[index];
        if (object instanceof Text) {
            return (Text)object;
        }
        return object == null ? NULL_ARGUMENT : StringVisitable.plain(object.toString());
    }

    @Override
    public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
        this.updateTranslations();
        for (StringVisitable visitable : this.translations) {
            Optional<T> optional = visitable.visit(visitor);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> visitor, Style style) {
        this.updateTranslations();
        for (StringVisitable visitable : this.translations) {
            Optional<T> optional = visitable.visit(visitor, style);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TranslatableTextContent)) return false;
        TranslatableTextContent content = (TranslatableTextContent)obj;
        if (!this.key.equals(content.key)) return false;
        if (!Arrays.equals(this.args, content.args)) return false;
        return true;
    }

    public String getKey() {
        return this.key;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public int hashCode() {
        int i = this.key.hashCode();
        i = 31 * i + Arrays.hashCode(this.args);
        return i;
    }

    public String toString() {
        return "translation{key='" + this.key + "', args=" + Arrays.toString(this.args) + "}";
    }

    public static class TranslationException extends IllegalArgumentException {
        public TranslationException(TranslatableTextContent text, String message) {
            super(String.format(Locale.ROOT, "Error parsing: %s: %s", text, message));
        }

        public TranslationException(TranslatableTextContent text, int index) {
            super(String.format(Locale.ROOT, "Invalid index %d requested for %s", index, text));
        }

        public TranslationException(TranslatableTextContent text, Throwable cause) {
            super(String.format(Locale.ROOT, "Error while parsing: %s", text), cause);
        }
    }
}
