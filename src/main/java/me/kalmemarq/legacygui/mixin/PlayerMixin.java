package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.gamemode.CreativeMode;
import com.mojang.minecraft.gamemode.GameMode;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.mob.Mob;
import com.mojang.minecraft.player.Input;
import com.mojang.minecraft.player.Inventory;
import com.mojang.minecraft.player.Player;
import com.mojang.minecraft.util.Mth;
import me.kalmemarq.legacygui.util.IFlyExtension;
import me.kalmemarq.legacygui.util.IPlayerExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Player.class)
abstract class PlayerMixin implements IPlayerExtension {
    @Shadow public transient Input input;
    @Unique
    private transient boolean creativeFlight_flying;
    @Unique
    private transient boolean creativeFlight_wasFlying;
    @Unique
    private transient boolean creativeFlight_descending;
    @Unique
    private transient GameMode creativeFlight_gameMode;

    @Override
    public boolean creativeFlight_isFlying() {
        return creativeFlight_flying;
    }

    @Override
    public boolean creativeFlight_wasFlying() {
        boolean value = creativeFlight_wasFlying;
        creativeFlight_wasFlying = creativeFlight_flying;
        return value;
    }

    @Override
    public boolean creativeFlight_isDescending() {
        return creativeFlight_descending;
    }

    @Override
    public void creativeFlight_setGameMode(GameMode gameMode) {
        creativeFlight_gameMode = gameMode;
    }

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/player/Input;tick()V"))
    private void onAiStep(CallbackInfo info) {
        if (creativeFlight_gameMode instanceof CreativeMode) {
            if (input instanceof IFlyExtension) {
                creativeFlight_wasFlying = creativeFlight_flying;
                creativeFlight_flying = ((IFlyExtension) input).creativeFlight_isFlying();
                creativeFlight_descending = ((IFlyExtension) input).creativeFlight_isDescending();
            }
        } else {
            creativeFlight_flying = creativeFlight_wasFlying = creativeFlight_descending = false;
        }
    }
}