package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.gui.Font;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Font.class)
public interface FontAccessor {
    @Accessor("charWidths")
    int[] getCharWidths();
}
