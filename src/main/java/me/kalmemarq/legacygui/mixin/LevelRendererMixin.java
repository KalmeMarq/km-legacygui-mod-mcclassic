package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.level.Chunk;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.LevelRenderer;
import com.mojang.minecraft.renderer.Tesselator;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow public abstract void setDirty(int x, int y, int z, int width, int height, int depth);

    @Shadow public Level level;

    @Shadow public int surroundLists;

    @Shadow private int xChunks;

    @Shadow private int yChunks;

    @Shadow private int zChunks;

    @Shadow public Chunk[] chunks;

    @Shadow private Chunk[] sortedChunks;

    @Shadow public List e;

    @Shadow private int n;

    @Inject(method = "allChanged", at = @At("HEAD"), cancellable = true)
    public void allChanged(CallbackInfo ci) {
        int var1;
        if (this.chunks != null) {
            for(var1 = 0; var1 < this.chunks.length; ++var1) {
                this.chunks[var1].reset();
            }
        }

        this.xChunks = this.level.width / 16;
        this.yChunks = this.level.height / 16;
        this.zChunks = this.level.depth / 16;
        this.chunks = new Chunk[this.xChunks * this.yChunks * this.zChunks];
        this.sortedChunks = new Chunk[this.xChunks * this.yChunks * this.zChunks];
        var1 = 0;

        int var2;
        int var4;
        for(var2 = 0; var2 < this.xChunks; ++var2) {
            for(int var3 = 0; var3 < this.yChunks; ++var3) {
                for(var4 = 0; var4 < this.zChunks; ++var4) {
                    this.chunks[(var4 * this.yChunks + var3) * this.xChunks + var2] = new Chunk(this.level, var2 << 4, var3 << 4, var4 << 4, 16, this.n + var1);
                    this.sortedChunks[(var4 * this.yChunks + var3) * this.xChunks + var2] = this.chunks[(var4 * this.yChunks + var3) * this.xChunks + var2];
                    var1 += 2;
                }
            }
        }

        for(var2 = 0; var2 < this.e.size(); ++var2) {
            ((Chunk)this.e.get(var2)).c = false;
        }

        this.e.clear();
        GL11.glNewList(this.surroundLists, 4864);
        LevelRenderer var9 = (LevelRenderer) (Object)this;
        float var10 = 0.5F;
        GL11.glColor4f(0.5F, var10, var10, 1.0F);
        Tesselator var11 = Tesselator.instance;
        float var12 = this.level.getGroundLevel();
        int var5 = 128;
        if (128 > this.level.width) {
            var5 = this.level.width;
        }

        if (var5 > this.level.depth) {
            var5 = this.level.depth;
        }

        int var6 = 2048 / var5;
//        var11.begin();
//
//        int var7;
//        for(var7 = -var5 * var6; var7 < var9.level.width + var5 * var6; var7 += var5) {
//            for(int var8 = -var5 * var6; var8 < var9.level.depth + var5 * var6; var8 += var5) {
//                var10 = var12;
//                if (var7 >= 0 && var8 >= 0 && var7 < var9.level.width && var8 < var9.level.depth) {
//                    var10 = 0.0F;
//                }
//
//                var11.vertexUV((float)var7, var10, (float)(var8 + var5), 0.0F, (float)var5);
//                var11.vertexUV((float)(var7 + var5), var10, (float)(var8 + var5), (float)var5, (float)var5);
//                var11.vertexUV((float)(var7 + var5), var10, (float)var8, (float)var5, 0.0F);
//                var11.vertexUV((float)var7, var10, (float)var8, 0.0F, 0.0F);
//            }
//        }
//
//        var11.end();
//        GL11.glColor3f(0.8F, 0.8F, 0.8F);
//        var11.begin();
//
//        for(var7 = 0; var7 < var9.level.width; var7 += var5) {
//            var11.vertexUV((float)var7, 0.0F, 0.0F, 0.0F, 0.0F);
//            var11.vertexUV((float)(var7 + var5), 0.0F, 0.0F, (float)var5, 0.0F);
//            var11.vertexUV((float)(var7 + var5), var12, 0.0F, (float)var5, var12);
//            var11.vertexUV((float)var7, var12, 0.0F, 0.0F, var12);
//            var11.vertexUV((float)var7, var12, (float)var9.level.depth, 0.0F, var12);
//            var11.vertexUV((float)(var7 + var5), var12, (float)var9.level.depth, (float)var5, var12);
//            var11.vertexUV((float)(var7 + var5), 0.0F, (float)var9.level.depth, (float)var5, 0.0F);
//            var11.vertexUV((float)var7, 0.0F, (float)var9.level.depth, 0.0F, 0.0F);
//        }
//
//        GL11.glColor3f(0.6F, 0.6F, 0.6F);
//
//        for(var7 = 0; var7 < var9.level.depth; var7 += var5) {
//            var11.vertexUV(0.0F, var12, (float)var7, 0.0F, 0.0F);
//            var11.vertexUV(0.0F, var12, (float)(var7 + var5), (float)var5, 0.0F);
//            var11.vertexUV(0.0F, 0.0F, (float)(var7 + var5), (float)var5, var12);
//            var11.vertexUV(0.0F, 0.0F, (float)var7, 0.0F, var12);
//            var11.vertexUV((float)var9.level.width, 0.0F, (float)var7, 0.0F, var12);
//            var11.vertexUV((float)var9.level.width, 0.0F, (float)(var7 + var5), (float)var5, var12);
//            var11.vertexUV((float)var9.level.width, var12, (float)(var7 + var5), (float)var5, 0.0F);
//            var11.vertexUV((float)var9.level.width, var12, (float)var7, 0.0F, 0.0F);
//        }
//
//        var11.end();
        GL11.glEndList();
        GL11.glNewList(this.surroundLists + 1, 4864);
        var9 = (LevelRenderer) (Object)this;
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
//        var10 = this.level.getWaterLevel();
//        GL11.glBlendFunc(770, 771);
//        var11 = Tesselator.instance;
//        var4 = 128;
//        if (128 > this.level.width) {
//            var4 = this.level.width;
//        }
//
//        if (var4 > this.level.depth) {
//            var4 = this.level.depth;
//        }

//        var5 = 2048 / var4;
//        var11.begin();
//
//        for(var6 = -var4 * var5; var6 < var9.level.width + var4 * var5; var6 += var4) {
//            for(var7 = -var4 * var5; var7 < var9.level.depth + var4 * var5; var7 += var4) {
//                float var13 = var10 - 0.1F;
//                if (var6 < 0 || var7 < 0 || var6 >= var9.level.width || var7 >= var9.level.depth) {
//                    var11.vertexUV((float)var6, var13, (float)(var7 + var4), 0.0F, (float)var4);
//                    var11.vertexUV((float)(var6 + var4), var13, (float)(var7 + var4), (float)var4, (float)var4);
//                    var11.vertexUV((float)(var6 + var4), var13, (float)var7, (float)var4, 0.0F);
//                    var11.vertexUV((float)var6, var13, (float)var7, 0.0F, 0.0F);
//                    var11.vertexUV((float)var6, var13, (float)var7, 0.0F, 0.0F);
//                    var11.vertexUV((float)(var6 + var4), var13, (float)var7, (float)var4, 0.0F);
//                    var11.vertexUV((float)(var6 + var4), var13, (float)(var7 + var4), (float)var4, (float)var4);
//                    var11.vertexUV((float)var6, var13, (float)(var7 + var4), 0.0F, (float)var4);
//                }
//            }
//        }
//
//        var11.end();
        GL11.glDisable(3042);
        GL11.glEndList();
        this.setDirty(0, 0, 0, this.level.width, this.level.height, this.level.depth);

        ci.cancel();
    }
}
