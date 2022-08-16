package me.kalmemarq.legacygui.gui.widget;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

public class EditTextWidget extends AbstractWidget {
    private static final int DEFAULT_COLOR = 0xFFBBBBBB;
    private static final int SELECTED_COLOR = 0xFFFFFFFF;

    public String text = "";
    public String placeholder = "";
    public boolean selected = false;
    private int maxLength = 32;
    @Nullable
    private EditBoxListener onChangelistener;

    private int ticks;

    public void tick() {
        ++this.ticks;
    }

    public EditTextWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Text.empty());
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setOnChangeistener(EditBoxListener listener) {
        this.onChangelistener = listener;
    }

    @Override
    public void renderButton(int mouseX, int mouseY) {
        Minecraft mc = LegacyGUIMod.getMCInstance();

        fillXX(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, this.selected ? SELECTED_COLOR : DEFAULT_COLOR);
        fillXX(this.x, this.y, this.x + this.width, this.y + this.height, 0xFF000000);

        boolean bl = (ticks / 6 % 2 == 0);

        if (!this.text.isEmpty()) {
            drawStringShadow(this.text + (bl && this.selected ? "_" : ""), this.x + 5, this.y + (this.height - 8) / 2, 0xFFFFFF);
        } else if (this.selected) {
            drawStringShadow("" + (bl ? "_" : ""), this.x + 5, this.y + (this.height - 8) / 2, 0xFFFFFF);
        } else {
            drawStringShadow(this.placeholder, this.x + 5, this.y + (this.height - 8) / 2, 0x777777);
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        return button == 0 && this.clicked(mouseX, mouseY);
    }

    public boolean keyPressed(char key, int code) {
        if (this.visible && this.active && this.selected) {
            if (code == Keyboard.KEY_V && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                try {
                    for (char letter : ((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)).toCharArray()) {
                        if (this.validate(letter)) {
                            this.text = this.text + letter;
                        }
                    }

                    if (this.onChangelistener != null) {
                        this.onChangelistener.onChange(this.text);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return true;
            }

            if (code == Keyboard.KEY_BACK && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                this.setText("");
                return true;
            }

            if (code == Keyboard.KEY_BACK && this.text.length() > 0) {
                this.setText(this.text.substring(0, this.text.length() - 1));
                return true;
            }

            if (this.validate(key)) {
                this.setText(this.text + key);
                return true;
            }
        }

        return false;
    }

    private void setText(String text) {
        this.text = text;
        if (this.onChangelistener != null) {
            this.onChangelistener.onChange(this.text);
        }
    }

    public void setValue(String value) {
        this.setText(value);
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public interface EditBoxListener {
        void onChange(String newValue);
    }

    public String getValue() {
        return this.text;
    }

    public boolean validate(char letter) {
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_'*!\\\"#%/()=+?[]{}<>@|$;".indexOf(letter) >= 0 && (text.length() < this.maxLength);
    }
}
