package me.kalmemarq.legacygui;

import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.NewLevelScreen;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.renderer.Tesselator;
import org.lwjgl.opengl.GL11;

public class TitleScreen extends Screen {
    public static boolean showF3 = false;

    @Override
    public void init() {
        this.buttons.add(new Button(100, this.width / 2 - 100, this.height / 4 + 45, "Create New Level"));
    }

    @Override
    protected void keyPressed(char c, int key) {
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button.id == 100) {
            this.minecraft.openScreen(new NewLevelScreen(this));
        }
    }

    @Override
    public void render(int mouseX, int mouseY) {
//        fill(0, 0, this.width, this.height, 0x000000);
        int var8 = this.minecraft.width * 240 / this.minecraft.height;
        int var3 = this.minecraft.height * 240 / this.minecraft.height;
        GL11.glClear(16640);
        Tesselator var4 = Tesselator.instance;
        int var5 = this.minecraft.textures.getTextureId("/dirt.png");
        GL11.glBindTexture(3553, var5);
        float var9 = 32.0F;
        var4.begin();
        var4.color(4210752);
        var4.vertexUV(0.0F, (float)var3, 0.0F, 0.0F, (float)var3 / var9);
        var4.vertexUV((float)var8, (float)var3, 0.0F, (float)var8 / var9, (float)var3 / var9);
        var4.vertexUV((float)var8, 0.0F, 0.0F, (float)var8 / var9, 0.0F);
        var4.vertexUV(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        var4.end();

        super.render(mouseX, mouseY);

        drawString(this.font, "Minecraft c0.30", 1, 1, 0x555555);
    }
}
