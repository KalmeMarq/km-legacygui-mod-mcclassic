package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Mob;
import com.mojang.minecraft.player.Input;
import com.mojang.minecraft.player.Inventory;
import com.mojang.minecraft.player.Player;
import com.mojang.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Player.class)
public class PlayerMixin extends Mob {
    @Shadow public Inventory inventory;

    @Shadow public float oBob;

    @Shadow public transient Input input;

    @Shadow public float bob;

    public PlayerMixin(Level level) {
        super(level);
    }

    @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
    public void aiStep(CallbackInfo ci) {
        this.inventory.tick();
        this.oBob = this.bob;
        this.input.a();
        super.aiStep();
        float var1 = Mth.sqrt(this.xd * this.xd + this.zd * this.zd);
        float var2 = (float)Math.atan((double)(-this.yd * 0.2F)) * 15.0F;
        if (var1 > 0.1F) {
            var1 = 0.1F;
        }

        if (!this.onGround || this.health <= 0) {
            var1 = 0.0F;
        }

        if (this.onGround || this.health <= 0) {
            var2 = 0.0F;
        }

        this.bob += (var1 - this.bob) * 0.4F;
        this.tilt += (var2 - this.tilt) * 0.8F;
        List var3;
        if (this.health > 0 && (var3 = this.level.findEntities(this, this.bb.grow(1.0F, 0.0F, 1.0F))) != null) {
            for(int var4 = 0; var4 < var3.size(); ++var4) {
                ((Entity)var3.get(var4)).playerTouch(this);
            }
        }

        ci.cancel();
    }
}
