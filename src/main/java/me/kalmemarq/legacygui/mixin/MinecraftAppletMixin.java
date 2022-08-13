package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.MinecraftApplet;
import me.kalmemarq.legacygui.LegacyGUIMod;
import me.kalmemarq.legacygui.util.IMinecraftExtra;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.applet.Applet;

@Mixin(MinecraftApplet.class)
public class MinecraftAppletMixin extends Applet {
    @Shadow private Minecraft minecraft;

//    @Override
//    public void resize(int width, int height) {
//        super.resize(width, height);
//
//        if (this.minecraft != null) {
//            LegacyGUIMod.LOGGER.info("RESIZED STUFF");
//
//            ((IMinecraftExtra)(Object)this.minecraft).resizeX(width, height);
//        }
//    }
}
