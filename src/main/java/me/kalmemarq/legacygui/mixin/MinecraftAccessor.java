package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {
    @Accessor("timer")
    Timer getTimer();
    @Accessor("running")
    boolean getRunning();
}
