package com.bmt.dream_relics.common.capabilities;

import com.bmt.dream_relics.registry.DRCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerData implements ICapabilitySerializable<CompoundTag> {

    public int SocialAnxietyLevel = 0;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return DRCapabilities.SERVER_SIDE_PLAYER_DATA.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("SocialAnxietyLevel", SocialAnxietyLevel);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        SocialAnxietyLevel = nbt.getInt("SocialAnxietyLevel");
    }
}
