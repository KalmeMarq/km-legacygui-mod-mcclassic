package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {
    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/phys/AABB;clipXCollide(Lcom/mojang/minecraft/phys/AABB;F)F"))
    public float move(AABB instance, AABB xa, float v) {
        return v;
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/phys/AABB;clipZCollide(Lcom/mojang/minecraft/phys/AABB;F)F"))
    public float movea(AABB instance, AABB za, float v) {
        return v;
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lcom/mojang/minecraft/phys/AABB;clipYCollide(Lcom/mojang/minecraft/phys/AABB;F)F"))
    public float moveaa(AABB instance, AABB za, float v) {
        return v;
    }
}
