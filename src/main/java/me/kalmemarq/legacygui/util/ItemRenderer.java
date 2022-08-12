package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.renderer.Tesselator;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
    public static void renderGuiTile(TileID tile, int x, int y) {
        RenderHelper.bindTexture("/terrain.png");
        BufferBuilder builder = ExtraTesselator.getInstance().getBuilder();
        renderGuiTile(tile, builder, x, y);
    }

    public static void renderGuiTile(TileID tile, BufferBuilder builder, int x, int y) {
        float leftSideB = 0.5F;
        float topSideB = 1.0F;
        float sideB = 0.8F;
        float rightSideB = 0.7F;

        if (tile == TileID.DANDELION || tile == TileID.ROSE || tile == TileID.BROWN_MUSHROOM || tile == TileID.RED_MUSHROOM) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, 0.0F);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 8.0F);

            GL11.glTranslatef(-1.6F, 0.9F, 0.5F);
            GL11.glScalef(-1.0F, -1.0F, -1.0F);

            builder.begin();
            int texture = tile.getTextureSide(0);
            Tile t = tile.getTile();
            int iX = texture % 16 << 4;
            int iY = texture / 16 << 4;
            float u0 = (float)iX / 256.0F;
            float u1 = ((float)iX + 16) / 256.0F;
            float v0 = (float)iY / 256.0F;
            float v1 = ((float)iY + 16) / 256.0F;

            int z = 0;

            int x0 = x;
            int x1 = x + 16;
            int y0 = y;
            int y1 = y + 16;

            builder.vertex(x0, y1, z).uv(u0, v1).color(rightSideB, rightSideB, rightSideB).endVertex();
            builder.vertex(x1, y1, z).uv(u1, v1).color(rightSideB, rightSideB, rightSideB).endVertex();
            builder.vertex(x1, y0, z).uv(u1, v0).color(rightSideB, rightSideB, rightSideB).endVertex();
            builder.vertex(x0, y0, z).uv(u0, v0).color(rightSideB, rightSideB, rightSideB).endVertex();
            ExtraTesselator.endAndDraw();
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, 0.0F);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 8.0F);
            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

            GL11.glTranslatef(-1.6F, 0.9F, 0.5F);
            GL11.glScalef(-1.0F, -1.0F, -1.0F);
            builder.begin();

            renderFace(tile, builder, -2, 0, 0, 0, sideB);
            renderFace(tile, builder, -2, 0, 0, 1, topSideB);
            renderFace(tile, builder, -2, 0, 0, 2, leftSideB);
            renderFace(tile, builder, -2, 0, 0, 3, sideB);
            renderFace(tile, builder, -2, 0, 0, 4, sideB);
            renderFace(tile, builder, -2, 0, 0, 5, rightSideB);

            ExtraTesselator.endAndDraw();
            GL11.glPopMatrix();
        }
    }

    public static void renderFace(TileID tile, BufferBuilder builder, int x, int y, int z, int face, float brightness) {
        int texture = tile.getTextureSide(face);
        Tile t = tile.getTile();

        float minX = t.minX;
        float maxX = t.maxX;
        float maxY = t.maxY;
        float minY = t.minY;
        float minZ = t.minZ;
        float maxZ = t.maxZ;

        int var7 = texture % 16 << 4;
        int var8 = texture / 16 << 4;
        float var9 = (float)var7 / 256.0F;
        float var17 = ((float)var7 + 15.99F) / 256.0F;
        float var10 = (float)var8 / 256.0F;
        float var11 = ((float)var8 + 15.99F) / 256.0F;
        if (face >= 2 && texture < 240) {
            if (!(minY < 0.0F) && !(maxY > 1.0F)) {
                var10 = ((float)var8 + minY * 15.99F) / 256.0F;
                var11 = ((float)var8 + maxY * 15.99F) / 256.0F;
            } else {
                var10 = (float)var8 / 256.0F;
                var11 = ((float)var8 + 15.99F) / 256.0F;
            }
        }

        float x0 = (float)x + minX;
        float x1 = (float)x + maxX;
        float var18 = (float)y + minY;
        float y1 = (float)y + maxY;
        float var12 = (float)z + minZ;
        float var13 = (float)z + maxZ;
        if (face == 0) {
            builder.vertex(x0, var18, var13).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x0, var18, var13).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, var18, var13).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, var18, var13).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
        } else if (face == 1) {
            builder.vertex(x1, y1, var13).uv(var17, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, y1, var12).uv(var17, var10).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x0, y1, var12).uv(var9, var10).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x0, y1, var13).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
        } else if (face == 2) {
            builder.vertex(x0, y1, var12).uv(var17, var10).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, y1, var12).uv(var9, var10).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, var18, var12).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x0, var18, var12).uv(var17, var11).color(brightness, brightness, brightness).endVertex();
        } else if (face == 3) {
            builder.vertex(x0, y1, var13).uv(var9, var10).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x0, var18, var13).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, var18, var13).uv(var17, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, y1, var13).uv(var17, var10).color(brightness, brightness, brightness).endVertex();
        } else if (face == 4) {
            builder.vertex(x0, y1, var13).uv(var17, var10).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x0, y1, var12).uv(var9, var10).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x0, var18, var12).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x0, var18, var13).uv(var17, var11).color(brightness, brightness, brightness).endVertex();
        } else if (face == 5) {
            builder.vertex(x1, var18, var13).uv(var9, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, var18, var12).uv(var17, var11).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, y1, var12).uv(var17, var10).color(brightness, brightness, brightness).endVertex();
            builder.vertex(x1, y1, var13).uv(var9, var10).color(brightness, brightness, brightness).endVertex();
        }
    }
}
