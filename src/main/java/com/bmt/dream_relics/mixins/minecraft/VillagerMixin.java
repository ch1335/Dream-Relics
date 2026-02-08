package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.util.DRUtil;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    @Shadow
    protected abstract void setUnhappy();

    @Inject(method = "startTrading", at = @At("HEAD"), cancellable = true)
    private void onStartTrading(Player player, CallbackInfo ci) {
        if (DRUtil.isEquippedNightmareBook(player)) {
            ci.cancel();
            this.setUnhappy();
        }
    }
}
