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
import com.mojang.minecraft.renderer.Textures;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.SurvivalMode;
import me.kalmemarq.legacygui.Util;
import me.kalmemarq.legacygui.gui.ITickable;
import me.kalmemarq.legacygui.gui.screen.*;
import me.kalmemarq.legacygui.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.awt.*;
import java.io.File;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements IMinecraftExtra {
	@Shadow public GameMode gameMode;
	@Shadow public Screen screen;

	@Shadow public abstract void openScreen(Screen screen);

	@Shadow public User user;

	@Shadow public Level level;

	@Shadow public LevelRenderer levelRenderer;

	@Shadow public InGameHud hud;

	@Shadow public Player player;

	@Shadow public int width;

	@Shadow public int height;

	@Shadow public HitResult hitResult;

	@Shadow private MinecraftApplet applet;

	@Shadow public String host;

	@Shadow public Textures textures;

	@Shadow public Canvas parent;

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/level/LevelIO;<init>(Lcom/mojang/minecraft/LevelLoaderListener;)V"))
	private void onConstructs(Canvas minecraftApplet, MinecraftApplet i, int j, int bl, boolean par5, CallbackInfo ci) {
		LegacyGUIMod.onMCConstructor((Minecraft)(Object) this);
	}

	public void resizeX(int width, int height) {
		try {
			this.width = width;
			this.height = height;
			int scale = calculateScale(3);
			ExtraScreen.scale = scale;
			Display.setDisplayMode(new DisplayMode(width, height));

			if (this.screen != null) {
				((IScreenExtra)(Object)this.screen).resizeX(((Minecraft) (Object) this), width / scale, height / scale);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Inject(at = @At("HEAD"), method = "run")
	private void run(CallbackInfo info) {
		this.user.hasPaid = true;

		ExtraScreen.scale = this.calculateScale(3);
	}

	@Redirect(at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/InGameHud;render(FZII)V"), method = "run")
	private void runaaa(InGameHud instance, float hasScreen, boolean mouseX, int mouseY, int i) {
		LegacyGUIMod.inGameHud.render(hasScreen);
	}

//	@Redirect(at = @At(value = "INVOKE", target = "in"), method = "run")
//	private void runaaa(InGameHud instance, float hasScreen, boolean mouseX, int mouseY, int i) {
//		LegacyGUIMod.inGameHud.render(hasScreen);
//	}



//	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/Screen;render(II)V"), method = "run")
//	private void run(CallbackInfo info) {
//		this.user.hasPaid = true;
//
//		ExtraScreen.scale = this.calculateScale(3);
//	}

	private boolean takingScreenshot;

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 4))
	private void tick(CallbackInfo ci){
		if (this.screen == null) {
			if (Keyboard.getEventKey() == Keyboard.KEY_F3) {
				TitleScreen.showF3 = !TitleScreen.showF3;
			}

			if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
				TitleScreen.hideHud = !TitleScreen.hideHud;
			}
		} else {
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
			if (!this.takingScreenshot) {
				this.takingScreenshot = true;
				ScreenshotManager.takeScreenshot((Minecraft)(Object)this, Util.getGameDirectory(), this.width, this.height);
			}
		} else {
			this.takingScreenshot = false;
		}
	}


	private long lastResizeTime;
	private int tempWidth;
	private int tempHeight;

	@Inject(method = "tick", at = @At("TAIL"))
	private void ticka(CallbackInfo ci) {
		if (LegacyGUIMod.inGameHud != null) {
			LegacyGUIMod.inGameHud.tick();
		}

		if (this.screen != null) {
			int s = Mouse.getDWheel();

			if (s != 0) {
				if (this.screen instanceof ExtraScreen) {
					((ExtraScreen)this.screen).mouseScroll(s);
				}
			}
		}

		if (this.parent != null && /*!this.isFullscreen &&*/ (this.parent.getWidth() != this.width || this.parent.getHeight() != this.height)) {
			this.width = this.parent.getWidth();
			this.height = this.parent.getHeight();
			if (this.width <= 0) {
				this.width = 1;
			}

			if (this.height <= 0) {
				this.height = 1;
			}

			this.updateScreenResolution(this.width, this.height);
		}


	}

	private void updateScreenResolution(int i, int j) {
		if (i <= 0) {
			i = 1;
		}

		if (j <= 0) {
			j = 1;
		}

		this.width = i;
		this.height = j;
		if (this.screen != null) {
			int scale = calculateScale(3);
			ExtraScreen.scale = scale;
			int var4 = this.width / scale;
			int var5 = this.height / scale;
			this.screen.init((Minecraft) (Object)this, var4, var5);
		}

	}



	public int calculateScale(int var1) {
		int var3;
		for(var3 = 1; var3 != var1 && var3 < this.width && var3 < this.height && this.width / (var3 + 1) >= 320 && this.height / (var3 + 1) >= 240; ++var3) {
		}

		return var3;
	}

		@Redirect(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/Minecraft;generateNewLevel(I)V"))
	private void generateNewLevelPrevent(Minecraft instance, int i) {
		this.openScreen(new TitleScreen());
	}

	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/Options;<init>(Lcom/mojang/minecraft/Minecraft;Ljava/io/File;)V", shift = At.Shift.AFTER))
	private void injectAfterOptionsLoaded(CallbackInfo ci) {
		LegacyGUIMod.onMinecraftInitalized((Minecraft) (Object)this);
	}

	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;create()V"))
	private void daaaaa(CallbackInfo ci) {
		GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
	}



	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/renderer/Textures;a(Lcom/mojang/minecraft/b/a/c;)V", ordinal = 0))
	private void splash(CallbackInfo ci) {
		int scaledWidth = this.width / calculateScale(3);
		int scaledHeight = this.height / calculateScale(3);

		GL11.glClear(16640);
		GL11.glMatrixMode(5889);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, scaledWidth, scaledHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(5888);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glViewport(0, 0, this.width, this.height);
		GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
		ExtraTesselator tesselator = ExtraTesselator.getInstance();
		BufferBuilder builder = tesselator.getBuilder();
		GL11.glDisable(2896);
		GL11.glEnable(3553);
		GL11.glDisable(2912);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		builder.begin();
		builder.vertex(0.0f, (float) this.height, 0.0f).color(255, 255, 255, 255).endVertex();
		builder.vertex((float) this.width, (float)this.height, 0.0f).color(255, 255, 255, 255).endVertex();
		builder.vertex((float)this.width, 0.0f, 0.0f).color(255, 255, 255, 255).endVertex();
		builder.vertex(0.0f, 0.0f, 0.0f).color(255, 255, 255, 255).endVertex();
		tesselator.end();

		GL11.glBindTexture(3553, this.textures.getTextureId("/gui/mojang.png"));
		int var3 = 256;
		int var4 = 256;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawLogo((scaledWidth - var3) / 2, (scaledHeight - var4) / 2, 0, 0, var3, var4);
		GL11.glDisable(2896);
		GL11.glDisable(2912);
		GL11.glEnable(3008);
		GL11.glAlphaFunc(516, 0.1F);

		try {
			Display.swapBuffers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void drawLogo(int x, int y, int u, int v, int textureWidth, int textureHeight) {
		float u0 = u / (float)textureWidth;
		float v0 = u / (float)textureHeight;
		float u1 = (u + textureWidth) / (float)textureWidth;
		float v1 = (v + textureHeight) / (float)textureHeight;
		int x1 = x + textureWidth;
		int y1 = y + textureHeight;

		BufferBuilder builder = ExtraTesselator.getInstance().getBuilder();
		builder.begin();
		builder.vertex((float)(x), (float)(y1), 0.0f).uv(u0, v1).endVertex();
		builder.vertex((float)(x1), (float)(y1), 0.0f).uv(u1, v1).endVertex();
		builder.vertex((float)(x1), (float)(y), 0.0f).uv(u1, v0).endVertex();
		builder.vertex((float)(x), (float)(y), 0.0f).uv(u0, v0).endVertex();
		ExtraTesselator.endAndDraw();
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

	@ModifyArgs(method = "openScreen", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/Screen;init(Lcom/mojang/minecraft/Minecraft;II)V"))
	private void sssss(Args args) {
		args.set(1, this.width / ExtraScreen.scale);
		args.set(2, this.height / ExtraScreen.scale);
	}

	@ModifyArgs(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/Screen;render(II)V"))
	private void sssssaa(Args args) {
		args.set(0, Mouse.getX() / ExtraScreen.scale);
		args.set(1, (this.height - Mouse.getY()) / ExtraScreen.scale);
	}

	@Redirect(method = "run", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/gui/Screen;render(II)V"))
	private void rrrrr(Screen instance, int mouseY, int i) {
		if (instance instanceof ExtraScreen) {
			int x = (int)(Mouse.getX() / (float)ExtraScreen.scale);
			int y = (int)((this.height - Mouse.getY()) / (float)ExtraScreen.scale);
			((ExtraScreen) instance).render(x, y);
		} else {
			instance.render(Mouse.getX() / ExtraScreen.scale, (this.height - Mouse.getY()) / ExtraScreen.scale);
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
//
//	@Inject(method = "destroy", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;destroy()V"))
//	private void destroys(CallbackInfo ci) {
////		try {
////			if (LegacyGUIMod.getMCInstance() != null) {
////				LegacyGUIMod.textureManager.close();
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//	}
}
