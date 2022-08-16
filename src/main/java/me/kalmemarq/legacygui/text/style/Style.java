package me.kalmemarq.legacygui.text.style;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class Style {
    public static final Style EMPTY = new Style(null, null, null, null, null);

    @Nullable
    private final TextColor color;
    @Nullable
    private final Boolean bold;
    @Nullable
    private final Boolean italic;
    @Nullable
    private final Boolean underlined;
    @Nullable
    private final Boolean strikethrough;

    private Style(@Nullable TextColor color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough) {
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
    }

    private static Style of(@Nullable TextColor color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough) {
        return new Style(color, bold, italic, underlined, strikethrough);
    }

    @Nullable
    public TextColor getColor() {
        return this.color;
    }

    public boolean isBold() {
        return this.bold == Boolean.TRUE;
    }

    public boolean isItalic() {
        return this.italic == Boolean.TRUE;
    }

    public boolean isStrikethrough() {
        return this.strikethrough == Boolean.TRUE;
    }

    public boolean isUnderlined() {
        return this.underlined == Boolean.TRUE;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public Style withColor(@Nullable TextColor color) {
        return new Style(color, this.bold, this.italic, this.underlined, this.strikethrough);
    }

    public Style withColor(@Nullable Formatting color) {
        return this.withColor(color != null ? TextColor.fromFormatting(color) : null);
    }

    public Style withColor(int rgbColor) {
        return this.withColor(TextColor.fromRgb(rgbColor));
    }

    public Style withBold(@Nullable Boolean bold) {
        return new Style(this.color, bold, this.italic, this.underlined, this.strikethrough);
    }

    public Style withItalic(@Nullable Boolean italic) {
        return new Style(this.color, this.bold, italic, this.underlined, this.strikethrough);
    }

    public Style withUnderline(@Nullable Boolean underline) {
        return new Style(this.color, this.bold, this.italic, underline, this.strikethrough);
    }

    public Style withStrikethrough(@Nullable Boolean strikethrough) {
        return new Style(this.color, this.bold, this.italic, this.underlined, strikethrough);
    }

    public Style withFormatting(Formatting formatting) {
        TextColor color = this.color;
        Boolean bold = this.bold;
        Boolean italic = this.italic;
        Boolean strikethrough = this.strikethrough;
        Boolean underlined = this.underlined;
        switch (formatting) {
            case BOLD: {
                bold = true;
                break;
            }
            case STRIKETHROUGH: {
                strikethrough = true;
                break;
            }
            case UNDERLINE: {
                underlined = true;
                break;
            }
            case ITALIC: {
                italic = true;
                break;
            }
            case RESET: {
                return EMPTY;
            }
            default: {
                color = TextColor.fromFormatting(formatting);
            }
        }
        return new Style(color, bold, italic, underlined, strikethrough);
    }

    public Style withFormatting(Formatting ... formattings) {
        TextColor color = this.color;
        Boolean bold = this.bold;
        Boolean italic = this.italic;
        Boolean strikethrough = this.strikethrough;
        Boolean underlined = this.underlined;
        for (Formatting formatting : formattings) {
            switch (formatting) {
                case BOLD: {
                    bold = true;
                    break;
                }
                case STRIKETHROUGH: {
                    strikethrough = true;
                    break;
                }
                case UNDERLINE: {
                    underlined = true;
                    break;
                }
                case ITALIC: {
                    italic = true;
                    break;
                }
                case RESET: {
                    return EMPTY;
                }
                default: {
                    color = TextColor.fromFormatting(formatting);
                }
            }
        }
        return new Style(color, bold, italic, underlined, strikethrough);
    }

    public Style withExclusiveFormatting(Formatting formatting) {
        TextColor color = this.color;
        Boolean bold = this.bold;
        Boolean italic = this.italic;
        Boolean strikethrough = this.strikethrough;
        Boolean underlined = this.underlined;
        switch (formatting) {
            case BOLD: {
                bold = true;
                break;
            }
            case STRIKETHROUGH: {
                strikethrough = true;
                break;
            }
            case UNDERLINE: {
                underlined = true;
                break;
            }
            case ITALIC: {
                italic = true;
                break;
            }
            case RESET: {
                return EMPTY;
            }
            default: {
                bold = false;
                strikethrough = false;
                underlined = false;
                italic = false;
                color = TextColor.fromFormatting(formatting);
            }
        }
        return new Style(color, bold, italic, underlined, strikethrough);
    }

    public Style withParent(Style parent) {
        if (this == EMPTY) return parent;
        if (parent == EMPTY) return this;

        return new Style(this.color != null ? this.color : parent.color, this.bold != null ? this.bold : parent.bold, this.italic != null ? this.italic : parent.italic, this.underlined != null ? this.underlined : parent.underlined, this.strikethrough != null ? this.strikethrough : parent.strikethrough);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Style) {
            Style lv = (Style)o;
            return this.isBold() == lv.isBold() && Objects.equals(this.getColor(), lv.getColor()) && this.isItalic() == lv.isItalic() && this.isStrikethrough() == lv.isStrikethrough() && this.isUnderlined() == lv.isUnderlined();
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.color, this.bold, this.italic, this.underlined, this.strikethrough);
    }
}
