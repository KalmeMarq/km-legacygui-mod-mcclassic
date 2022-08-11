package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.renderer.Tesselator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Tile.class)
public abstract class TileMixin {
    @Shadow public abstract void renderFace(Tesselator tesselator, int x, int y, int z, int face);

    public void renderAsIcon(Tesselator tesselator) {
        float var2 = 0.6F;
        float var3 = 1.0F;
        float var4 = 0.8F;
        tesselator.color(var2, var2, var2);
        this.renderFace(tesselator, -2, 0, 0, 0);
        tesselator.color(var3, var3, var3);
        this.renderFace(tesselator, -2, 0, 0, 1);
        tesselator.color(var2, var2, var2);
        this.renderFace(tesselator, -2, 0, 0, 2);
        tesselator.color(var2, var2, var2);
        this.renderFace(tesselator, -2, 0, 0, 3);
        tesselator.color(var2, var2, var2);
        this.renderFace(tesselator, -2, 0, 0, 4);
        tesselator.color(var4, var4, var4);
        this.renderFace(tesselator, -2, 0, 0, 5);
    }
}
