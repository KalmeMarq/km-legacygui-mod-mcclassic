package me.kalmemarq.legacygui.util.soundfix;

import com.mojang.minecraft.sound.UrlSoundSource;
import de.jarnbjo.ogg.EndOfOggStreamException;
import me.kalmemarq.legacygui.mixin.UrlSoundSourceAccessor;

import java.nio.ByteBuffer;

public final class LL extends Thread {
    private final XUrlSoundSource a;

    public LL(XUrlSoundSource urlSoundSource) {
        super();
        this.a = urlSoundSource;
        this.setPriority(10);
        this.setDaemon(true);
    }

    public final void run() {
        try {
            while(!((UrlSoundSourceAccessor)(Object)this.a).getG()) {
                ByteBuffer var2;
                if (((UrlSoundSourceAccessor)(Object)this.a).getA() == null && ((UrlSoundSourceAccessor)(Object)this.a).getB() != null) {
                    var2 = ((UrlSoundSourceAccessor)(Object)this.a).getB();
                    ((UrlSoundSourceAccessor)(Object)this.a).setA(var2);
                    var2 = null;
                    ((UrlSoundSourceAccessor)(Object)this.a).setB(null);
                    ((UrlSoundSourceAccessor)(Object)this.a).getA().clear();
                }

                if (((UrlSoundSourceAccessor)(Object)this.a).getA() != null && ((UrlSoundSourceAccessor)(Object)this.a).getA().remaining() != 0) {
                    while(((UrlSoundSourceAccessor)(Object)this.a).getA().remaining() != 0) {
                        var2 = ((UrlSoundSourceAccessor)(Object)this.a).getA();
                        int var1 = ((UrlSoundSourceAccessor)(Object)this.a).getStream().readPcm(var2.array(), var2.position(), var2.remaining());
                        var2.position(var2.position() + var1);
                        boolean var9;
                        if (var9 = var1 <= 0) {
                            ((UrlSoundSourceAccessor)(Object)this.a).setF(true);
                            ((UrlSoundSourceAccessor)(Object)this.a).setG(true);
                            break;
                        }
                    }
                }

                if (((UrlSoundSourceAccessor)(Object)this.a).getA() != null && ((UrlSoundSourceAccessor)(Object)this.a).getC() == null) {
                    ((UrlSoundSourceAccessor)(Object)this.a).getA().flip();
                    var2 = ((UrlSoundSourceAccessor)(Object)this.a).getA();
                    ((UrlSoundSourceAccessor)(Object)this.a).setC(var2);
                    var2 = null;
                    ((UrlSoundSourceAccessor)(Object)this.a).setA(var2);
                }

                Thread.sleep(10L);
                if (!((UrlSoundSourceAccessor)(Object)this.a).getSoundPlayer().running) {
                    return;
                }
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            return;
        } finally {
            ((UrlSoundSourceAccessor)(Object)this.a).setF(true);
        }

    }
}
