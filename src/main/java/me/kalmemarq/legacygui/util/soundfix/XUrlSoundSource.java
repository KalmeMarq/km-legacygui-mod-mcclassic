package me.kalmemarq.legacygui.util.soundfix;

import com.mojang.minecraft.sound.SoundPlayer;
import com.mojang.minecraft.sound.SoundSource;
import de.jarnbjo.ogg.LogicalOggStreamImpl;
import de.jarnbjo.ogg.OnDemandUrlStream;
import de.jarnbjo.vorbis.VorbisStream;

import java.net.URL;
import java.nio.ByteBuffer;

public class XUrlSoundSource implements SoundSource {
    ByteBuffer a = ByteBuffer.allocate(176400);
    ByteBuffer b = ByteBuffer.allocate(176400);
    private ByteBuffer h = null;
    ByteBuffer c = null;
    VorbisStream stream;
    SoundPlayer player;
    boolean f = false;
    boolean g = false;

    public XUrlSoundSource(SoundPlayer soundPlayer, URL uRL) {
        this.player = soundPlayer;
        LogicalOggStreamImpl soundPlayer1 = (LogicalOggStreamImpl) (new OnDemandUrlStream(uRL)).logicalStreams.values().iterator().next();
        this.stream = new VorbisStream(soundPlayer1);
        (new LL(this)).start();
    }

    public final boolean a(int[] is, int[] js, int i) {
        if (!this.player.options.music) {
            this.g = true;
            return false;
        } else {
            i = i;
            int var4 = 0;

            while(i > 0 && (this.h != null || this.c != null)) {
                if (this.h == null && this.c != null) {
                    this.h = this.c;
                    this.c = null;
                }

                if (this.h != null && this.h.remaining() > 0) {
                    int var5;
                    if ((var5 = this.h.remaining() / 4) > i) {
                        var5 = i;
                    }

                    for(int var6 = 0; var6 < var5; ++var6) {
                        is[var4 + var6] += this.h.getShort();
                        js[var4 + var6] += this.h.getShort();
                    }

                    var4 += var5;
                    i -= var5;
                }

                if (this.b == null && this.h != null && this.h.remaining() == 0) {
                    this.b = this.h;
                    this.h = null;
                }
            }

            return this.h != null || this.c != null || !this.f;
        }
    }
}
