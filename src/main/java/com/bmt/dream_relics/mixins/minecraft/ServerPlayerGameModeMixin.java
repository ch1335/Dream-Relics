package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.item.YearsAmber;
import com.bmt.dream_relics.registry.DRCapabilities;
import com.bmt.dream_relics.registry.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

@Debug(export = true)
@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {
    @Shadow
    @Final
    protected ServerPlayer player;

    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"))
    private void destroyBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir, @Local() LocalRef<ItemStack> itemStack, @Local BlockState blockState) {
        @Nullable Pair<Float, ItemStack> pair = YearsAmber.findBestCorrectTool(player, itemStack.get().copy(), blockState);
        if (pair != null) {
            itemStack.set(pair.getSecond());

        }
    }

    @Inject(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;mineBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)V", shift = At.Shift.AFTER))
    private void afterMineBlock(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) ItemStack itemStack, @Local BlockState blockState) {
        if (itemStack != player.getMainHandItem()) {
            CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                List<SlotResult> list = iCuriosItemHandler.findCurios(ModItems.YEARS_AMBER.get());
                if (!list.isEmpty()) {
                    ItemStack itemStack1 = list.get(0).stack();
                    if (itemStack1.getItem() instanceof YearsAmber yearsAmber) {
                        itemStack1.getCapability(DRCapabilities.YEARS_AMBER_ITEM_HANDLER).ifPresent(iItemHandler -> {
                                    yearsAmber.updateTag(itemStack1, iItemHandler.serializeNBT());
                                }
                        );
                    }
                }
            });
        }
    }

    @WrapOperation(method = "destroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popExperience(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;I)V"))
    private void checkExp(Block instance, ServerLevel serverLevel, BlockPos blockPos, int exp, Operation<Void> original, @Local(ordinal = 0) ItemStack itemStack) {
        if (itemStack != player.getMainHandItem() && itemStack.getEnchantmentLevel(Enchantments.SILK_TOUCH) > 0) {
            return;
        }
        original.call(instance, serverLevel, blockPos, exp);
    }
}
