package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.*;
import com.mojang.minecraft.gamemode.GameMode;
import com.mojang.minecraft.gui.CreativeBuildScreen;
import com.mojang.minecraft.gui.PauseScreen;
import com.mojang.minecraft.gui.Screen;
import me.kalmemarq.legacygui.SurvivalMode;
import me.kalmemarq.legacygui.gui.screen.CreateNewLevelScreen;
import me.kalmemarq.legacygui.gui.screen.CreativeInventoryScreen;
import me.kalmemarq.legacygui.gui.screen.GameMenuScreen;
import me.kalmemarq.legacygui.gui.screen.TitleScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow public GameMode gameMode;
	@Shadow public Screen screen;

	@Shadow public abstract void openScreen(Screen screen);

	@Inject(at = @At("HEAD"), method = "run")
	private void run(CallbackInfo info) {
		this.gameMode = new SurvivalMode((Minecraft) (Object)this);
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 4))
	private void tick(CallbackInfo ci){
		if (this.screen == null) {
			if (Keyboard.getEventKey() == Keyboard.KEY_F3) {
				TitleScreen.showF3 = !TitleScreen.showF3;
			}
		}
	}

	@ModifyVariable(method = "openScreen", at = @At(value = "FIELD", target = "Lcom/mojang/minecraft/Minecraft;screen:Lcom/mojang/minecraft/gui/Screen;"), argsOnly = true)
	private Screen sopenScreen(Screen screen) {
		return screen instanceof CreativeBuildScreen ? new CreativeInventoryScreen() : screen;
	}

	@Inject(method = "releaseMouse", at = @At("HEAD"), cancellable = true)
	public void releaseMouse(CallbackInfo ci) {
		if (this.screen == null) {
			this.openScreen(new GameMenuScreen());
		}

		ci.cancel();
	}
}
