package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.util.DRUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.theillusivec4.curios.common.event.CuriosEventHandler;

@Mixin(CuriosEventHandler.class)
public class CuriosEventHandlerMixin {
    @WrapOperation(method = "lambda$playerDrops$20", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private static boolean wrapRule(GameRules instance, GameRules.Key<GameRules.BooleanValue> key, Operation<Boolean> original, @Local(argsOnly = true) LivingDropsEvent event) {
        if (key == GameRules.RULE_KEEPINVENTORY && DRUtil.isEquippedNightmareBook(event.getEntity())) {
            return false;
        }

        return original.call(instance, key);
    }
}
