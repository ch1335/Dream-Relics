package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.util.DRUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin extends AbstractGolem {
    protected IronGolemMixin(EntityType<? extends AbstractGolem> p_27508_, Level p_27509_) {
        super(p_27508_, p_27509_);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void addGoals(CallbackInfo ci) {
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, DRUtil::isEquippedNightmareBook));
    }
}
