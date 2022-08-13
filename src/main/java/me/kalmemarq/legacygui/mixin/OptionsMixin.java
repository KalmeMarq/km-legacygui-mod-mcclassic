package me.kalmemarq.legacygui.mixin;


import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.Options;
import com.mojang.minecraft.input.Keybind;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.util.IOptionsExtra;
import me.kalmemarq.legacygui.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.util.Locals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

@Mixin(Options.class)
abstract class OptionsMixin implements IOptionsExtra {
    @Shadow
    public Keybind[] keybinds;

    @Shadow public boolean music;

    @Shadow private File file;

    @Shadow protected abstract void load();

    @Shadow public abstract String getOptionLabel(int option);

    @Shadow protected abstract void save();

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/Options;load()V"))
    private void onConstruct(Minecraft mc, File file, CallbackInfo info) {
        int size = keybinds.length;
        keybinds = Arrays.copyOf(keybinds, size + 3);
        keybinds[size] = LegacyGUIMod.FLYING_KEYBIND;
        keybinds[size + 1] = LegacyGUIMod.DESCENDING_KEYBIND;
        keybinds[size + 2] = LegacyGUIMod.PLAYERLIST_KEYBIND;
        this.loadX();
    }

    public void saveX() {
        this.save();
    }

    private void loadX() {
        try {
            if (this.file.exists()) {
                BufferedReader var1 = new BufferedReader(new FileReader(this.file));
                String var2 = null;

                while((var2 = var1.readLine()) != null) {
                    String[] var5;
                    if ((var5 = var2.split(":"))[0].equals("music")) {
                        this.music = var5[1].equals("true");
                    }

                    if (var5[0].equals("language")) {
                        if (var5[1].equals("en_us") || var5[1].equals("pt_pt")) {
                            Language.language = var5[1];
                        }
                    }
                }

                var1.close();
            }
        } catch (Exception var4) {
            System.out.println("Failed to load options");
            var4.printStackTrace();
        }
    }

    @Redirect(method = "save", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;println(Ljava/lang/String;)V", ordinal = 0))
    private void saves(PrintWriter instance, String x) {
        instance.println("music:" + this.music);
        instance.println("language:" + Language.language);
    }

//    @Inject(method = "load", at = @At(value = "INVOKE", target = "Ljava/lang/Integer;parseInt(Ljava/lang/String;)I"))
//    private void load(CallbackInfo ci) {
//        Locals.getLocalVariableAt("s", "")

//    }
//    @ModifyVariable(method = "load", "")
}
