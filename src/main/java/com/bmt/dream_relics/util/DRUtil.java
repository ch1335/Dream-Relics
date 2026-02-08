package com.bmt.dream_relics.util;

import com.bmt.dream_relics.registry.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class DRUtil {
    public static boolean isEquippedNightmareBook(LivingEntity entity) {
        return isEquippedCurio(entity, ModItems.NIGHTMARE_BOOK.get());
    }

    public static boolean isEquippedCurio(LivingEntity entity, ItemLike itemLike) {
        LazyOptional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(entity);
        if (optional.isPresent()) {
            return optional.orElseThrow(NullPointerException::new).isEquipped(itemLike.asItem());
        } else {
            return false;
        }
    }
}
