package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.ErrorScreen;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.screen.TitleScreen;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ErrorScreen.class)
public class ErrorScreenMixin extends Screen {
    @Inject(method = "init", at = @At("TAIL"))
    private void mainmenu_addExis(CallbackInfo ci) {
        this.buttons.add(new Button(100,this.width / 2 - 100, this.height / 4 + 120 + 24, "Exit to menu"));
    }

    @Override
    protected void buttonClicked(Button button) {
        super.buttonClicked(button);

        if (button.id == 100) {
            this.minecraft.level = null;
            this.minecraft.connectionManager = null;

            Screen screen = new TitleScreen();

            if (this.minecraft.mouseGrabbed) {
                this.minecraft.player.releaseAllKeys();
                this.minecraft.mouseGrabbed = false;
                if (this.minecraft.appletMode) {
                    try {
                        Mouse.setNativeCursor(null);
                    } catch (LWJGLException var4) {
                        var4.printStackTrace();
                    }
                } else {
                    Mouse.setGrabbed(false);
                }
            }

            int var2 = this.width * 240 / this.height;
            int var3 = this.height * 240 / this.height;
            screen.init(this.minecraft, var2, var3);
            this.minecraft.screen = screen;
            this.minecraft.x = false;
        }
    }
}
