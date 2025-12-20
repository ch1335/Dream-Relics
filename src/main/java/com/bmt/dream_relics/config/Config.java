package com.bmt.dream_relics.config;

import com.bmt.dream_relics.DreamRelics;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue CHARGE_TIME = BUILDER
            .comment("Charge time in ticks for Soul Mirror (20 ticks = 1 second)")
            .defineInRange("chargeTime", 60, 1, 200);

    public static final ForgeConfigSpec.IntValue COOLDOWN_TIME = BUILDER
            .comment("Cooldown time in ticks for Soul Mirror (20 ticks = 1 second)")
            .defineInRange("cooldownTime", 4800, 1, 36000);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int chargeTime;
    public static int cooldownTime;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        chargeTime = CHARGE_TIME.get();
        cooldownTime = COOLDOWN_TIME.get();
    }
}