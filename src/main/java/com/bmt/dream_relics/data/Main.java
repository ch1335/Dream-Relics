package com.bmt.dream_relics.data;

import com.bmt.dream_relics.DreamRelics;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Main {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        DatapackBuiltinEntriesProvider builtinEntriesProvider = generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
                generator.getPackOutput(),
                event.getLookupProvider(),
                new RegistrySetBuilder(),
                Set.of(DreamRelics.MODID)
        ));


        DRBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new DRBlockTagsProvider(generator.getPackOutput(), builtinEntriesProvider.getRegistryProvider(), event.getExistingFileHelper()));


        generator.addProvider(event.includeServer(), new DRItemTagsProvider(generator.getPackOutput(), builtinEntriesProvider.getRegistryProvider(), blockTagsProvider.contentsGetter(), event.getExistingFileHelper()));
    }
}
