package me.kalmemarq.legacygui.gui.hud;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.util.Mth;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ChatHud {
    private final Minecraft minecraft;
    public List<String> messages = new ArrayList<>();

    public ChatHud(Minecraft mc) {
        this.minecraft = mc;
    }

    public void render() {
        GL11.glPushMatrix();

        float scale = getScale();
        int var7 = (int)Math.ceil((float)this.getWidth() / scale);
        GL11.glTranslatef(4.0F, 8, 0.0F);

        GL11.glPopMatrix();
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
}
