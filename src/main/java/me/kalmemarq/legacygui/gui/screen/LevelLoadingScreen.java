package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.LevelLoaderListener;
import me.kalmemarq.legacygui.mixin.LevelLoaderAcessor;
import org.lwjgl.opengl.Display;

public class LevelLoadingScreen extends ExtraScreen {
    private LevelLoaderListener levelLoaderListener;

    public LevelLoadingScreen(LevelLoaderListener levelLoaderListener) {
        this.levelLoaderListener = levelLoaderListener;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderDirtBackground();
        String title = ((LevelLoaderAcessor)(Object)this.levelLoaderListener).getTitle();
        String stage = ((LevelLoaderAcessor)(Object)this.levelLoaderListener).getStage();
        this.minecraft.font.drawShadow(title, (this.width - this.minecraft.font.width(title)) / 2, this.height / 2 - 4 - 16, 0xFFFFFF);
        this.minecraft.font.drawShadow(stage, (this.width - this.minecraft.font.width(stage)) / 2, this.height / 2 - 4 + 8, 0xFFFFFF);
        super.render(mouseX, mouseY);
    }
}
