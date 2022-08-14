package me.kalmemarq.legacygui.mixin;

//import com.mojang.minecraft.c.l;
import com.mojang.minecraft.sound.SoundPlayer;
import com.mojang.minecraft.sound.UrlSoundSource;
import de.jarnbjo.ogg.LogicalOggStreamImpl;
import de.jarnbjo.vorbis.VorbisStream;
import me.kalmemarq.legacygui.util.soundfix.LL;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URL;

@Mixin(UrlSoundSource.class)
public class UrlSoundSourceMixin {
//    @Shadow private VorbisStream stream;
//
//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lde/jarnbjo/vorbis/VorbisStream;<init>(Lde/jarnbjo/ogg/LogicalOggStreamImpl;)V", shift = At.Shift.AFTER), cancellable = true)
//    private void nnnn(SoundPlayer uRL, URL par2, CallbackInfo ci) {
//        (new LL((UrlSoundSource) (Object)this)).start();
//    }
//
//    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/c/l;<init>(Lcom/mojang/minecraft/sound/UrlSoundSource;)V"))
//    private void iiii(l instance, UrlSoundSource urlSoundSource) {
//
//    }
}
