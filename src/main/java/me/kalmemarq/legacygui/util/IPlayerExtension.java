package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.gamemode.GameMode;

public interface IPlayerExtension extends IFlyExtension {
    default boolean creativeFlight_wasFlying() {
        return creativeFlight_isFlying();
    }
    void creativeFlight_setGameMode(GameMode gameMode);
}
