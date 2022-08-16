package me.kalmemarq.legacygui.gui.hud;

import com.mojang.minecraft.ChatMessage;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.util.Mth;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.gui.screen.ExtraScreen;
import me.kalmemarq.legacygui.util.TextRenderer;
import me.kalmemarq.legacygui.util.TextUtil;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ChatHud {
    private final Minecraft minecraft;
    public List<ChatHudLine> messages = new ArrayList<>();
    public List<String> trimmedmessages = new ArrayList<>();

    public ChatHud(Minecraft mc) {
        this.minecraft = mc;
    }

    public void render() {
//        GL11.glPushMatrix();
//
//        float scale = getScale();
//        int var7 = (int)Math.ceil((float)this.getWidth() / scale);
//        GL11.glTranslatef(4.0F, 8, 0.0F);
//
//        GL11.glPopMatrix();

        int var25 = 20;
        for(int var14 = 0; var14 < this.trimmedmessages.size() && var14 < var25; var14++) {
            if (((ChatHudLine)this.messages.get(var14)).age < 200) {
                String msg = ((ChatHudLine)this.messages.get(var14)).content;

                List<String> ss = TextUtil.splitToFit(msg, this.minecraft.font, 320);;

                for (String ssss : ss) {
                    int y = (this.minecraft.height / ExtraScreen.scale) - 8 - var14 * 9 - 20;
                    ExtraDrawableHelper.fillXX(2, y - 1, 2 + 320, y + 9 - 1, 0x66000000);
                    TextRenderer.drawStringShadow(ssss, 2, (this.minecraft.height / ExtraScreen.scale) - 8 - (var14) * 9 - 20, 16777215);
                }
            }
        }
    }

    private float getScale() {
        return 1.0f;
    }

    public int getWidth() {
        return getWidth(1.0f);
    }

    public static int getWidth(float size) {
        return (int)Math.floor(size * 280.0D + 40.0D);
    }

    public static int getHeight(float size) {
        return (int)Math.floor(size * 160.0D + 20.0D);
    }

    public void addMessage(String message) {
        this.messages.add(0, new ChatHudLine(message));

        while(this.messages.size() > 50) {
            this.messages.remove(this.messages.size() - 1);
        }
    }
}
