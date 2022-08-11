package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.gamemode.CreativeMode;
import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.NewLevelScreen;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.SurvivalMode;
import me.kalmemarq.legacygui.gui.screen.TitleScreen;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NewLevelScreen.class)
public class NewLevelScreenMixin extends Screen {
    @Shadow private Screen parent;
    private Button gameModeBtn;
    private boolean isCreative = true;
    private boolean isInGame = true;

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        isInGame = !(this.parent instanceof TitleScreen);
        gameModeBtn = new Button(100, this.width / 2 - 100, this.height / 4 + 72, "Game Mode: " + (isCreative ? "Creative" : "Survival"));
        this.buttons.add(gameModeBtn);
    }

    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    private void buttonClicked(Button button, CallbackInfo ci) {
        if (button.id == 100) {
            isCreative = !isCreative;
            gameModeBtn.msg = "Game Mode: " + (isCreative ? "Creative" : "Survival");
        } else if (button.id == 3) {
            this.minecraft.openScreen(this.parent);
        } else {
            this.minecraft.gameMode = isCreative ? new CreativeMode(this.minecraft) : new SurvivalMode(this.minecraft);
            this.minecraft.generateNewLevel(button.id);
            this.minecraft.openScreen((Screen)null);
            this.minecraft.grabMouse();
        }

        ci.cancel();
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public final void render(int mouseX, int mouseY, CallbackInfo ci) {
        if (isInGame) {
            fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            int var8 = this.minecraft.width * 240 / this.minecraft.height;
            int var3 = this.minecraft.height * 240 / this.minecraft.height;
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
        }

        drawCenteredString(this.font, "Generate new level", this.width / 2, 40, 16777215);
        super.render(mouseX, mouseY);

        ci.cancel();
    }

//    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/NewLevelScreen;fillGradient(IIIIII)V"), index = 4)
//    private int injectedBgColor1(int color) {
//        return -1072689136;
//    }
//
//    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/NewLevelScreen;fillGradient(IIIIII)V"), index = 5)
//    private int injectedBgColor2(int color) {
//        return -804253680;
//    }
}
