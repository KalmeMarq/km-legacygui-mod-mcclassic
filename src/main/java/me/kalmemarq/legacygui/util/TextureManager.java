package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.b.a.c;
import com.mojang.minecraft.renderer.Textures;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TextureManager {
    public HashMap<String, Integer> idMap = new HashMap<>();
    public HashMap<Integer, BufferedImage> bIMap = new HashMap<>();

    public IntBuffer idBuffer = BufferUtils.createIntBuffer(1);
    public ByteBuffer textureBuffer = BufferUtils.createByteBuffer(262144);
    public List e = new ArrayList();

    private final Minecraft minecraft;

    public TextureManager(Minecraft mc) {
        this.minecraft = mc;
    }

    public void bind(String path) {
        GL11.glBindTexture(GlConst.GL_TEXTURE_2D, getTextureId(path));
    }

    public final int getTextureId(String path) {
        Integer var2;
        if ((var2 = (Integer)this.idMap.get(path)) != null) {
            return var2;
        } else {
            try {
                this.idBuffer.clear();
                GL11.glGenTextures(this.idBuffer);
                int var4 = this.idBuffer.get(0);
                if (path.startsWith("##")) {
                    this.putTexture(a(ImageIO.read(Objects.requireNonNull(TextureManager.class.getResourceAsStream(path.substring(2))))), var4);
                } else {
                    this.putTexture(ImageIO.read(Objects.requireNonNull(TextureManager.class.getResourceAsStream(path))), var4);
                }

                this.idMap.put(path, var4);
                return var4;
            } catch (IOException var3) {
                throw new RuntimeException("!!");
            }
        }
    }

    public static BufferedImage a(BufferedImage image) {
        int var1 = image.getWidth() / 16;
        BufferedImage var2;
        Graphics var3 = (var2 = new BufferedImage(16, image.getHeight() * var1, 2)).getGraphics();

        for(int var4 = 0; var4 < var1; ++var4) {
            var3.drawImage(image, -var4 << 4, var4 * image.getHeight(), (ImageObserver)null);
        }

        var3.dispose();
        return var2;
    }

    public final int addTexture(BufferedImage image) {
        this.idBuffer.clear();
        GL11.glGenTextures(this.idBuffer);
        int var2 = this.idBuffer.get(0);
        this.putTexture(image, var2);
        this.bIMap.put(var2, image);
        return var2;
    }

    public void putTexture(BufferedImage image, int id) {
        GL11.glBindTexture(3553, id);
        GL11.glTexParameteri(3553, 10241, 9728);
        GL11.glTexParameteri(3553, 10240, 9728);
        id = image.getWidth();
        int var3 = image.getHeight();
        int[] var4 = new int[id * var3];
        byte[] var5 = new byte[id * var3 << 2];
        image.getRGB(0, 0, id, var3, var4, 0, id);

        for(int i = 0; i < var4.length; ++i) {
            int var6 = var4[i] >>> 24;
            int var7 = var4[i] >> 16 & 255;
            int var8 = var4[i] >> 8 & 255;
            int var9 = var4[i] & 255;
            if (this.minecraft.options.anaglyph3d) {
                int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
                var8 = (var7 * 30 + var8 * 70) / 100;
                var9 = (var7 * 30 + var9 * 70) / 100;
                var7 = var10;
                var8 = var8;
                var9 = var9;
            }

            var5[i << 2] = (byte)var7;
            var5[(i << 2) + 1] = (byte)var8;
            var5[(i << 2) + 2] = (byte)var9;
            var5[(i << 2) + 3] = (byte)var6;
        }

        this.textureBuffer.clear();
        this.textureBuffer.put(var5);
        this.textureBuffer.position(0).limit(var5.length);
        GL11.glTexImage2D(3553, 0, 6408, id, var3, 0, 6408, 5121, this.textureBuffer);
    }

    public final void a(c arg) {
        this.e.add(arg);
        arg.a();
    }
}
