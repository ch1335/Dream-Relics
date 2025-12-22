package com.bmt.dream_relics.client;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.client.inventory.tooltip.YearsAmberTooltip;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

public class ClientEventHandler {
    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventHandler {
        @SubscribeEvent
        public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(YearsAmberTooltip.Component.class, YearsAmberTooltip::new);
        }
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventHandler {
        @SubscribeEvent
        public static void InputEvent(InputEvent.Key event) {
            if (event.getKey() == GLFW.GLFW_KEY_LEFT_ALT) {
                if (event.getAction() == InputConstants.PRESS) {
                    DRClient.IS_ALT_DOWN = true;
                } else if (event.getAction() == InputConstants.RELEASE) {
                    DRClient.IS_ALT_DOWN = false;
                }
            }
        }
    }
}
