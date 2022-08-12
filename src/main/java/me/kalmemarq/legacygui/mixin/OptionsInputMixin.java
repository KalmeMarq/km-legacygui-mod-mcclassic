package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.player.Input;
import com.mojang.minecraft.player.OptionsInput;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.util.IFlyExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsInput.class)
public class OptionsInputMixin extends Input implements IFlyExtension {
    @Unique
    private boolean creativeFlight_flying;
    @Unique
    private boolean creativeFlight_descending;

    @Override
    public boolean creativeFlight_isFlying() {
        return creativeFlight_flying;
    }

    @Override
    public boolean creativeFlight_isDescending() {
        return creativeFlight_descending;
    }

    @Inject(method = "setKey", at = @At("RETURN"))
    private void onSetKey(int key, boolean down, CallbackInfo info) {
        if (down && key == LegacyGUIMod.FLYING_KEYBIND.key) {
            creativeFlight_flying = !creativeFlight_flying;
        }

        if (key == LegacyGUIMod.DESCENDING_KEYBIND.key) {
            creativeFlight_descending = down;
        }
    }
}
