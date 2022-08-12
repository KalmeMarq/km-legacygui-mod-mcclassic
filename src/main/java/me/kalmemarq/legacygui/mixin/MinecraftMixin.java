package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.*;
import com.mojang.minecraft.gamemode.GameMode;
import com.mojang.minecraft.gui.CreativeBuildScreen;
import com.mojang.minecraft.gui.InGameHud;
import com.mojang.minecraft.gui.PauseScreen;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.LevelRenderer;
import com.mojang.minecraft.player.Player;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.SurvivalMode;
import me.kalmemarq.legacygui.gui.ITickable;
import me.kalmemarq.legacygui.gui.screen.*;
import me.kalmemarq.legacygui.util.IPlayerExtension;
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

	@Shadow public InGameHud hud;

	@Shadow public Player player;

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

			if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
				TitleScreen.hideHud = !TitleScreen.hideHud;
			}
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void ticka(CallbackInfo ci) {
		((ITickable)(Object) this.hud).tick();
	}

		@Redirect(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/Minecraft;generateNewLevel(I)V"))
	private void generateNewLevelPrevent(Minecraft instance, int i) {
		this.openScreen(new TitleScreen());
	}

	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/Options;<init>(Lcom/mojang/minecraft/Minecraft;Ljava/io/File;)V", shift = At.Shift.AFTER))
	private void injectAfterOptionsLoaded(CallbackInfo ci) {
		LegacyGUIMod.onMinecraftInitalized((Minecraft) (Object)this);
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

	@Inject(method = "tick", at = @At("HEAD"))
	private void onTickHead(CallbackInfo info) {
		if (this.player != null) {
			((IPlayerExtension) player).creativeFlight_setGameMode(gameMode);
		}
	}

	@Inject(method = "tick", at = @At("RETURN"))
	private void onTickReturn(CallbackInfo info) {
		if (this.player != null) {
			IPlayerExtension ext = (IPlayerExtension) player;
			if (ext.creativeFlight_wasFlying() != ext.creativeFlight_isFlying()) {
				String message = ext.creativeFlight_isFlying() ? "Started flying!" : "Stopped flying!";
				hud.addMessage(message);
			}
		}
	}
}
