package com.bmt.dream_relics.common;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.common.capabilities.YearsAmberItemHandler;
import com.bmt.dream_relics.item.YearsAmber;
import com.bmt.dream_relics.registry.DRCapabilities;
import com.bmt.dream_relics.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EventHandler {
    @Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventHandler {
        @SubscribeEvent
        public static void HarvestCheck(PlayerEvent.HarvestCheck event) {
            if (!event.canHarvest()) {
                if (YearsAmber.findBestCorrectTool(event.getEntity(), event.getEntity().getMainHandItem(), event.getTargetBlock()) != null) {
                    event.setCanHarvest(true);
                }
            }
        }

        @SubscribeEvent
        public static void AttachItemStackCapabilitiesEvent(AttachCapabilitiesEvent<ItemStack> event) {
            ItemStack itemStack = event.getObject();
            if (itemStack.is(ModItems.YEARS_AMBER.get())) {
                event.addCapability(DreamRelics.id("years_amber_item_handler"), new YearsAmberItemHandler(4));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventHandler{
        @SubscribeEvent
        public static void RegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
            event.register(YearsAmberItemHandler.class);
        }
    }
}
