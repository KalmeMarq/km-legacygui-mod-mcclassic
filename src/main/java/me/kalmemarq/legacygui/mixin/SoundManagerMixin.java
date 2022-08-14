package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.sound.SoundManager;
import com.mojang.minecraft.sound.SoundPlayer;
import me.kalmemarq.legacygui.util.soundfix.XUrlSoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Shadow private Map music;

    @Shadow public Random random;

    @Inject(method = "a", at = @At("HEAD"), cancellable = true)
    public void aaaa(SoundPlayer name, String par2, CallbackInfoReturnable<Boolean> cir) {
        try {
            List var3 = null;
            synchronized (this.music) {
                var3 = (List) this.music.get(name);
            }

            if (var3 == null) {
                cir.setReturnValue(false);
            } else {
                File var4 = (File) var3.get(this.random.nextInt(var3.size()));

                try {
                    name.play(new XUrlSoundSource(name, var4.toURI().toURL()));
                } catch (MalformedURLException var5) {
                    var5.printStackTrace();
                } catch (IOException var6) {
                    var6.printStackTrace();
                }

                cir.setReturnValue(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cir.setReturnValue(false);

        cir.cancel();
    }
}
