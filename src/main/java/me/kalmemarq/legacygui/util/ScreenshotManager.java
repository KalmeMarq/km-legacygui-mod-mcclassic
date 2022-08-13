package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotManager {
    private static final DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static ByteBuffer buffer;
    private static byte[] dstArray;
    private static int[] array;

    public static void takeScreenshot(Minecraft mc, File file, int width, int height) {
        try {
            File screenshotDir = new File(file, "screenshots");

            screenshotDir.mkdir();

            BufferedImage bufferedImage = take(width, height);

            File fl = getScreenshotFile(screenshotDir);

            if (bufferedImage != null) {
                ImageIO.write(bufferedImage, "png", fl);

                mc.hud.addMessage("Saved screenshot as " + fl.getName());
            } else {
                mc.hud.addMessage("Failed to save: " + fl.getName());
            }
        } catch (Exception e) {
            mc.hud.addMessage("Failed to create screenshots folder");
        }
    }

    public static File getScreenshotFile(File screenshotDir) {
        String filename = "" + DateFormat.format(new Date());

        File file;
        for (int i = 1; (file = new File(screenshotDir, filename + (i == 1 ? "" : "_" + i) + ".png")).exists(); ++i) {
        }

        return file;
    }

    public static BufferedImage take(int width, int height) {
        try {
            if (buffer == null || buffer.capacity() < width * height) {
                buffer = BufferUtils.createByteBuffer(width * height * 3);
            }

            if (array == null || array.length < width * height * 3) {
                dstArray = new byte[width * height * 3];
                array = new int[width * height];
            }

            GL11.glPixelStorei(GlConst.GL_PACK_ALIGNMENT, 1);
            GL11.glPixelStorei(GlConst.GL_UNPACK_ALIGNMENT, 1);
            buffer.clear();
            GL11.glReadPixels(0, 0, width, height, GlConst.GL_RGB, GlConst.GL_UNSIGNED_BYTE, buffer);
            buffer.clear();

            buffer.get(dstArray);

            for(int x = 0; x < width; ++x) {
                for(int y = 0; y < height; ++y) {
                    int i = x + (height - y - 1) * width;
                    int r = dstArray[i * 3] & 0xFF;
                    int g = dstArray[i * 3 + 1] & 0xFF;
                    int b = dstArray[i * 3 + 2] & 0xFF;
                    int color = -16777216 | r << 16 | g << 8 | b;
                    array[x + y * width] = color;
                }
            }

            BufferedImage image = new BufferedImage(width, height, 1);
            image.setRGB(0, 0, width, height, array, 0, width);

            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
