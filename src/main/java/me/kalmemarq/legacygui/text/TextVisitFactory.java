package me.kalmemarq.legacygui.text;

import me.kalmemarq.legacygui.text.style.Formatting;
import me.kalmemarq.legacygui.text.style.Style;
import me.kalmemarq.legacygui.util.Unit;

import java.util.Optional;

public class TextVisitFactory {
    private static final Optional<Object> VISIT_TERMINATED = Optional.of(Unit.INSTANCE);

    private static boolean visitRegularCharacter(Style style, CharacterVisitor visitor, int index, char c) {
        if (Character.isSurrogate(c)) {
            return visitor.accept(style, String.valueOf(65533));
        }
        return visitor.accept(style, String.valueOf(c));
    }

    public static boolean visitForwards(String text, Style style, CharacterVisitor visitor) {
        int i = text.length();
        for (int j = 0; j < i; ++j) {
            char c = text.charAt(j);
            if (Character.isHighSurrogate(c)) {
                if (j + 1 >= i) {
                    if (visitor.accept(style, String.valueOf(65533))) break;
                    return false;
                }
                char d = text.charAt(j + 1);
                if (Character.isLowSurrogate(d)) {
                    if (!visitor.accept(style, String.valueOf(Character.toCodePoint(c, d)))) {
                        return false;
                    }
                    ++j;
                    continue;
                }
                if (visitor.accept(style, String.valueOf(65533))) continue;
                return false;
            }
            if (TextVisitFactory.visitRegularCharacter(style, visitor, j, c)) continue;
            return false;
        }
        return true;
    }

    public static boolean visitBackwards(String text, Style style, CharacterVisitor visitor) {
        int i = text.length();
        for (int j = i - 1; j >= 0; --j) {
            char c = text.charAt(j);
            if (Character.isLowSurrogate(c)) {
                if (j - 1 < 0) {
                    if (visitor.accept(style, String.valueOf(65533))) break;
                    return false;
                }
                char d = text.charAt(j - 1);
                if (!(Character.isHighSurrogate(d) ? !visitor.accept(style, String.valueOf(Character.toCodePoint(d, c))) : !visitor.accept(style, String.valueOf(65533)))) continue;
                return false;
            }
            if (TextVisitFactory.visitRegularCharacter(style, visitor, j, c)) continue;
            return false;
        }
        return true;
    }

    public static boolean visitFormatted(String text, Style style, CharacterVisitor visitor) {
        return TextVisitFactory.visitFormatted(text, 0, style, visitor);
    }

    public static boolean visitFormatted(String text, int startIndex, Style style, CharacterVisitor visitor) {
        return TextVisitFactory.visitFormatted(text, startIndex, style, style, visitor);
    }

    public static boolean visitFormatted(String text, int startIndex, Style startingStyle, Style resetStyle, CharacterVisitor visitor) {
        int j = text.length();
        Style lv = startingStyle;
        for (int k = startIndex; k < j; ++k) {
            char d;
            char c = text.charAt(k);
            if (c == '\u00a7') {
                if (k + 1 >= j) break;
                d = text.charAt(k + 1);
                Formatting lv2 = Formatting.byCode(d);
                if (lv2 != null) {
                    lv = lv2 == Formatting.RESET ? resetStyle : lv.withExclusiveFormatting(lv2);
                }
                ++k;
                continue;
            }
            if (Character.isHighSurrogate(c)) {
                if (k + 1 >= j) {
                    if (visitor.accept(lv, String.valueOf(65533))) break;
                    return false;
                }
                d = text.charAt(k + 1);
                if (Character.isLowSurrogate(d)) {
                    if (!visitor.accept(lv, String.valueOf(Character.toCodePoint(c, d)))) {
                        return false;
                    }
                    ++k;
                    continue;
                }
                if (visitor.accept(lv, String.valueOf(65533))) continue;
                return false;
            }
            if (TextVisitFactory.visitRegularCharacter(lv, visitor, k, c)) continue;
            return false;
        }
        return true;
    }

    public static boolean visitFormatted(StringVisitable text, Style style2, CharacterVisitor visitor) {
        return !text.visit((style, string) -> TextVisitFactory.visitFormatted(string, 0, style, visitor) ? Optional.empty() : VISIT_TERMINATED, style2).isPresent();
    }

    public static String removeFormattingCodes(StringVisitable text) {
        StringBuilder stringBuilder = new StringBuilder();
        TextVisitFactory.visitFormatted(text, Style.EMPTY, (Style style, String chr) -> {
            stringBuilder.append(chr);
            return true;
        });
        return stringBuilder.toString();
    }
}
