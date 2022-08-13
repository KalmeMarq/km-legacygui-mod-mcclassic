package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.LevelLoaderListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelLoaderListener.class)
public interface LevelLoaderAcessor {
    @Accessor("stage")
    String getStage();
    @Accessor("title")
    String getTitle();
}
