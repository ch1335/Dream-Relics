package com.bmt.dream_relics;

import com.bmt.dream_relics.config.Config;
import com.bmt.dream_relics.registry.ModCreativeTabs;
import com.bmt.dream_relics.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DreamRelics.MODID)
public class DreamRelics
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "dream_relics";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public DreamRelics(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("Dream Relics mod initialized");
    }

    // Add items to the appropriate creative tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == ModCreativeTabs.DREAM_RELICS_TAB.getKey()) {
            event.accept(ModItems.SOUL_MIRROR);
            event.accept(ModItems.DREAM_TOTEM);
            event.accept(ModItems.MOMENT_STONE);
            event.accept(ModItems.ENDLESS_DREAM);
            event.accept(ModItems.YEARS_AMBER);
            event.accept(ModItems.MEMORY_STARDUST);
            event.accept(ModItems.TIME_HOURGLASS);
            event.accept(ModItems.ASTRAL_NECKLACE);
            event.accept(ModItems.TASSEL_RING);
            event.accept(ModItems.OBSERVE_SELF_EYE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Dream Relics server starting");
    }
}