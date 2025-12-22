package com.bmt.dream_relics.mixins.minecraft;

import com.bmt.dream_relics.item.YearsAmber;
import com.bmt.dream_relics.registry.DRCapabilities;
import com.bmt.dream_relics.registry.ModItems;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {

    @Shadow
    public int value;

    @Shadow
    protected abstract int durabilityToXp(int p_20794_);

    @Shadow
    protected abstract int repairPlayerItems(Player p_147093_, int p_147094_);

    @Inject(method = "repairPlayerItems", at = @At("RETURN"), cancellable = true)
    private void dr$repairPlayerItems(Player player, int exp, CallbackInfoReturnable<Integer> cir) {
        CuriosApi.getCuriosInventory(player).ifPresent(itemHandler -> {
            List<SlotResult> list = itemHandler.findCurios(ModItems.YEARS_AMBER.get());
            if (!list.isEmpty()) {
                ItemStack amberItem = list.get(0).stack();
                amberItem.getCapability(DRCapabilities.YEARS_AMBER_ITEM_HANDLER).ifPresent(itemHandler2 -> {
                            for (int j = 0; j < itemHandler2.getSlots(); j++) {
                                ItemStack itemStack = itemHandler2.getStackInSlot(j);
                                if (itemStack.isDamaged() && itemStack.getEnchantmentLevel(Enchantments.MENDING) > 0) {
                                    int i = Math.min((int) (this.value * itemStack.getXpRepairRatio()), itemStack.getDamageValue());
                                    itemStack.setDamageValue(itemStack.getDamageValue() - i);
                                    int k = cir.getReturnValue() - this.durabilityToXp(i);
                                    cir.setReturnValue(j > 0 ? this.repairPlayerItems(player, k) : 0);
                                    ((YearsAmber) amberItem.getItem()).updateTag(amberItem, itemHandler2.serializeNBT());
                                }
                            }

                        }
                );
            }
        });
    }
}
