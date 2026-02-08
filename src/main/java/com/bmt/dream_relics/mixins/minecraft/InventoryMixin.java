package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.item.YearsAmber;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Shadow
    @Final
    public NonNullList<ItemStack> items;

    @Shadow
    public int selected;

    @Shadow
    @Final
    public Player player;

    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void getDestroySpeed(BlockState blockState, CallbackInfoReturnable<Float> cir) {
        @Nullable Pair<Float, ItemStack> pair = YearsAmber.findBestCorrectTool(player, this.items.get(this.selected), blockState);
        if (pair != null) {
            cir.setReturnValue(pair.getFirst());
        }
    }
}
