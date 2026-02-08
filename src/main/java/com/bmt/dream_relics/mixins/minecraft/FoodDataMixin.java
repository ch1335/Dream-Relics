package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.util.DRUtil;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow
    private int foodLevel;

    @Shadow
    private float saturationLevel;

    @Unique
    Player dr$CapturedPlayer = null;

    @Inject(method = "tick", at = @At("HEAD"))
    private void FoodDatAtick(Player player, CallbackInfo ci) {
        if (dr$CapturedPlayer != null && DRUtil.isEquippedNightmareBook(player)) {
            int oldFoodLevel = foodLevel;
            float oldSaturationLevel = saturationLevel;

            this.foodLevel = Math.min(this.foodLevel, 15);
            this.saturationLevel = Math.min(this.saturationLevel, 15);
            if (player == null) {

            }
        }
        dr$CapturedPlayer = player;
    }

    @Inject(remap = false, method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("RETURN"))
    private void onEat(Item p_38713_, ItemStack p_38714_, LivingEntity entity, CallbackInfo ci) {
        if (dr$CapturedPlayer != null && DRUtil.isEquippedNightmareBook(entity)) {
            this.foodLevel = Math.min(this.foodLevel, 15);
            this.saturationLevel = Math.min(this.saturationLevel, 15);
        }
    }

    @WrapMethod(method = "addExhaustion")
    private void onAddExhaustion(float addExhaustion, Operation<Void> original) {
        if (dr$CapturedPlayer != null && DRUtil.isEquippedNightmareBook(dr$CapturedPlayer)) {
            original.call(addExhaustion * 1.6F);
        } else {
            original.call(addExhaustion);
        }
    }
}
