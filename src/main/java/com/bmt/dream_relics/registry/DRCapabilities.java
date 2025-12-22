package com.bmt.dream_relics.registry;

import com.bmt.dream_relics.common.capabilities.YearsAmberItemHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.items.IItemHandler;

public class DRCapabilities {
    public static Capability<YearsAmberItemHandler> YEARS_AMBER_ITEM_HANDLER = CapabilityManager.get(new CapabilityToken<>() {
    });
}
