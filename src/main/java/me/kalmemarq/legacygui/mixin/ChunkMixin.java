package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.level.Chunk;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.renderer.Tesselator;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chunk.class)
public class ChunkMixin {
    @Shadow public static int updates;

    @Shadow private int x;

    @Shadow private int y;

    @Shadow private int z;

    @Shadow private int sizeZ;

    @Shadow private int sizeY;

    @Shadow private int sizeX;

    @Shadow private boolean[] dirty;

    @Shadow private Level level;

    @Shadow private int lists;

    @Shadow private static Tesselator t;

    @Inject(method = "rebuild", at = @At("HEAD"), cancellable = true)
    public void rebuild(CallbackInfo ci) {
        ++updates;
        int var1 = this.x;
        int var2 = this.y;
        int var3 = this.z;
        int var4 = this.x + this.sizeX;
        int var5 = this.y + this.sizeY;
        int var6 = this.z + this.sizeZ;

        int var7;
        for(var7 = 0; var7 < 2; ++var7) {
            this.dirty[var7] = true;
        }

        for(var7 = 0; var7 < 2; ++var7) {
            boolean var8 = false;
            boolean var9 = false;
            GL11.glNewList(this.lists + var7, 4864);
            t.begin();

            for(int x = var1; x < var4; ++x) {
                for(int y = var2; y < var5; ++y) {
                    for(int z = var3; z < var6; ++z) {
                        int tileId;
                        if ((tileId = this.level.getTile(x, y, z)) > 0) {
                            Tile tile;
                            if ((tile = Tile.tiles[tileId]).getLayer() != var7) {
                                var8 = true;
                            } else {
                                var9 |= tile.render(this.level, x, y, z, t);
                            }
                        }
                    }
                }
            }

            t.end();
            GL11.glEndList();
            if (var9) {
                this.dirty[var7] = false;
            }

            if (!var8) {
                break;
            }
        }

        ci.cancel();
    }
}
