package me.kalmemarq.legacygui;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.LevelLoaderListener;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gamemode.GameMode;
import com.mojang.minecraft.gamemode.SurvivalMobSpawner;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.mob.Mob;
import com.mojang.minecraft.player.Player;

public final class SurvivalMode extends GameMode {
    private int breakingX;
    private int breakingY;
    private int breakingZ;
    private int f;
    private int currentBreakTime;
    private int h;
    private SurvivalMobSpawner spawner;

    public SurvivalMode(Minecraft minecraft) {
        super(minecraft);
    }

    public final void init(Player player) {
        player.inventory.slots[8] = Tile.tnt.id;
        player.inventory.count[8] = 10;
    }

    public final void breakTile(int x, int y, int z) {
        int var4 = this.minecraft.level.getTile(x, y, z);
        Tile.tiles[var4].drop(this.minecraft.level, x, y, z);
        super.breakTile(x, y, z);
    }

    public final boolean a(int i) {
        return this.minecraft.player.inventory.removeResource(i);
    }

    public final void a(int i, int j, int k) {
        int var4;
        if ((var4 = this.minecraft.level.getTile(i, j, k)) > 0 && Tile.tiles[var4].getBreakTime() == 0) {
            this.breakTile(i, j, k);
        }

    }

    public final void c() {
        this.f = 0;
        this.h = 0;
    }

    public final void tileBreakTick(int x, int y, int z, int i) {
        if (this.h > 0) {
            --this.h;
        } else if (x == this.breakingX && y == this.breakingY && z == this.breakingZ) {
            int var5;
            if ((var5 = this.minecraft.level.getTile(x, y, z)) != 0) {
                Tile var6 = Tile.tiles[var5];
                this.currentBreakTime = var6.getBreakTime();
                var6.spawnBreakParticle(this.minecraft.level, x, y, z, i, this.minecraft.particleEngine);
                ++this.f;
                if (this.f == this.currentBreakTime + 1) {
                    this.breakTile(x, y, z);
                    this.f = 0;
                    this.h = 5;
                }

            }
        } else {
            this.f = 0;
            this.breakingX = x;
            this.breakingY = y;
            this.breakingZ = z;
        }
    }

    public final void a(float f) {
        if (this.f <= 0) {
            this.minecraft.levelRenderer.i = 0.0F;
        } else {
            this.minecraft.levelRenderer.i = ((float)this.f + f - 1.0F) / (float)this.currentBreakTime;
        }
    }

    public final float d() {
        return 4.0F;
    }

    public final boolean a(Player player, int i) {
        Tile var3;
        if ((var3 = Tile.tiles[i]) == Tile.redMushroom && this.minecraft.player.inventory.removeResource(i)) {
            player.hurt((Entity)null, 3);
            return true;
        } else if (var3 == Tile.brownMushroom && this.minecraft.player.inventory.removeResource(i)) {
            player.heal(5);
            return true;
        } else {
            return false;
        }
    }

    public void applyTo(Level level) {
        super.applyTo(level);
        this.spawner = new SurvivalMobSpawner(level);
    }

    public void e() {
        SurvivalMobSpawner var3;
        int var1 = (var3 = this.spawner).level.width * var3.level.depth * var3.level.height / 64 / 64 / 64;
        if (var3.level.random.nextInt(100) < var1 && var3.level.countInstanceOf(Mob.class) < var1 * 20) {
            var3.spawn(var1, var3.level.player, (LevelLoaderListener)null);
        }

    }

    public void method_308(Level level) {
        this.spawner = new SurvivalMobSpawner(level);
        this.minecraft.levelLoaderListener.levelLoadUpdate("Spawning..");
        int levell = level.width * level.depth * level.height / 800;
        this.spawner.spawn(levell, (Entity)null, this.minecraft.levelLoaderListener);
    }
}
