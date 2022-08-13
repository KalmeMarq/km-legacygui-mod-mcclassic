package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.gui.screen.ExtraScreen;
import me.kalmemarq.legacygui.util.IScreenExtra;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin implements IScreenExtra {
    @Shadow protected List buttons;

    @Shadow protected int width;

    @Shadow protected int height;

    @Shadow public abstract void init(Minecraft minecraft, int width, int height);

    @Shadow protected Minecraft minecraft;

    @Shadow protected abstract void mouseClicked(int x, int y, int button);

    @Inject(method = "init(Lcom/mojang/minecraft/Minecraft;II)V", at = @At("HEAD"))
    public void inita(Minecraft width, int height, int par3, CallbackInfo ci) {
        this.buttons.clear();

        if (((Screen)(Object)this) instanceof ExtraScreen) {
            ((ExtraScreen)(Object)this).widgets.clear();
        }
    }

    @Override
    public void resizeX(Minecraft mc, int width, int height) {
        this.init(mc, width, height);
    }

    @Inject(method = "e", at = @At("HEAD"), cancellable = true)
    public final void e(CallbackInfo ci) {
        if (Mouse.getEventButtonState()) {
            int var1 = Mouse.getEventX() / ExtraScreen.scale;
            int var2 = this.height - Mouse.getEventY() / ExtraScreen.scale;
            this.mouseClicked(var1, var2, Mouse.getEventButton());
        }

        ci.cancel();
    }
}
