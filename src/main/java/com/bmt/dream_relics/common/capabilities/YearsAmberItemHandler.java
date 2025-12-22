package com.bmt.dream_relics.common.capabilities;

import com.bmt.dream_relics.registry.DRCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YearsAmberItemHandler extends ItemStackHandler implements ICapabilitySerializable<CompoundTag> {

    public YearsAmberItemHandler(int size){
        super(size);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return DRCapabilities.YEARS_AMBER_ITEM_HANDLER.orEmpty(cap, LazyOptional.of(() -> this));
    }
}
