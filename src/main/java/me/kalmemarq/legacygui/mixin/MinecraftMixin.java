package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.*;
import com.mojang.minecraft.gamemode.GameMode;
import com.mojang.minecraft.gui.CreativeBuildScreen;
import com.mojang.minecraft.gui.PauseScreen;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.LevelRenderer;
import me.kalmemarq.legacygui.SurvivalMode;
import me.kalmemarq.legacygui.gui.screen.*;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow public GameMode gameMode;
	@Shadow public Screen screen;

	@Shadow public abstract void openScreen(Screen screen);

	@Shadow public User user;

	@Shadow public Level level;

	@Shadow public LevelRenderer levelRenderer;

	@Inject(at = @At("HEAD"), method = "run")
	private void run(CallbackInfo info) {
		this.user.hasPaid = true;
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 4))
	private void tick(CallbackInfo ci){
		if (this.screen == null) {
			if (Keyboard.getEventKey() == Keyboard.KEY_F3) {
				TitleScreen.showF3 = !TitleScreen.showF3;
			}
		}
	}

	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/Minecraft;generateNewLevel(I)V"))
	private void generateNewLevelPrevent(Minecraft instance, int i) {
		this.openScreen(new TitleScreen());
	}

	@ModifyVariable(method = "openScreen", at = @At(value = "FIELD", target = "Lcom/mojang/minecraft/Minecraft;screen:Lcom/mojang/minecraft/gui/Screen;"), argsOnly = true)
	private Screen sopenScreen(Screen screen) {
		return screen instanceof CreativeBuildScreen ? new CreativeInventoryScreen() : screen;
	}

	@Inject(method = "grabMouse", at = @At("HEAD"), cancellable = true)
	private void openScreenCanBeClosed(CallbackInfo ci) {
		if ((this.screen instanceof ExtraScreen && !(((ExtraScreen) this.screen).canBeClosed())) || this.level == null || this.levelRenderer == null) {
			ci.cancel();
		}
	}

	@Inject(method = "openScreen", at = @At("HEAD"), cancellable = true)
	private void openScreenTitle(Screen screen, CallbackInfo ci) {
		if (screen == null && ((this.screen instanceof ExtraScreen && !(((ExtraScreen) this.screen).canBeClosed())) || this.level == null || this.levelRenderer == null)) {
			ci.cancel();
		}

		if (this.level != null && screen instanceof TitleScreen) {
			this.openScreen(null);
			ci.cancel();
		}
	}

	@Inject(method = "pause", at = @At("HEAD"), cancellable = true)
	public void releaseMouse(CallbackInfo ci) {
		if (this.screen == null) {
			this.openScreen(new GameMenuScreen());
		}

		ci.cancel();
	}
}
