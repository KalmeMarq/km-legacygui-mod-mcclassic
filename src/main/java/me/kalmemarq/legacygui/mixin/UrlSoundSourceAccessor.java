package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.sound.SoundPlayer;
import com.mojang.minecraft.sound.UrlSoundSource;
import de.jarnbjo.vorbis.VorbisStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.nio.ByteBuffer;

@Mixin(UrlSoundSource.class)
public interface UrlSoundSourceAccessor {
    @Accessor("a")
    ByteBuffer getA();
    @Accessor("a")
    void setA(ByteBuffer a);
    @Accessor("b")
    ByteBuffer getB();
    @Accessor("b")
    void setB(ByteBuffer b);
    @Accessor("c")
    ByteBuffer getC();
    @Accessor("c")
    void setC(ByteBuffer c);
    @Accessor("h")
    ByteBuffer getH();
    @Accessor("stream")
    VorbisStream getStream();
    @Accessor("player")
    SoundPlayer getSoundPlayer();
    @Accessor("f")
    boolean getF();
    @Accessor("g")
    boolean getG();
    @Accessor("f")
    void setF(boolean f);
    @Accessor("g")
    void setG(boolean g);
}
