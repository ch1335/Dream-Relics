package com.bmt.dream_relics.registry;

import com.bmt.dream_relics.DreamRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DreamRelics.MODID);

    public static final RegistryObject<CreativeModeTab> DREAM_RELICS_TAB = CREATIVE_MODE_TABS.register("dream_relics_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.MEMORY_STARDUST.get().getDefaultInstance())
                    .title(Component.translatable("itemGroup.dream_relics.dream_relics_tab"))
                    .displayItems((parameters, output) -> {
//                        ModItems.ITEMS.getEntries().forEach(object -> {
//                            output.accept(object.get());
//                        });
                        output.accept(ModItems.SOUL_MIRROR.get());
                        output.accept(ModItems.DREAM_TOTEM.get());
                        output.accept(ModItems.MOMENT_STONE.get());
                        output.accept(ModItems.ENDLESS_DREAM.get());
                        output.accept(ModItems.YEARS_AMBER.get());
                        output.accept(ModItems.MEMORY_STARDUST.get());
                        output.accept(ModItems.TIME_HOURGLASS.get());
                        output.accept(ModItems.ASTRAL_NECKLACE.get());
                        output.accept(ModItems.TASSEL_RING.get());
                        output.accept(ModItems.OBSERVE_SELF_EYE.get());
                        output.accept(ModItems.DREAM_BALANCE.get());
                        output.accept(ModItems.PAST_RING.get());
                        output.accept(ModItems.ECHO_EARRING.get());
                        output.accept(ModItems.MEMORY_NECKLACE.get());
                        output.accept(ModItems.SLEEPING_STAR_SEED.get());
                        output.accept(ModItems.LIMINAL_KEY.get());
                        output.accept(ModItems.HEART_VOICE_PENDANT.get());
                        output.accept(ModItems.AWAKEN_DREAM_BRACELET.get());
                        output.accept(ModItems.MIST_VEIL_RING.get());
                        output.accept(ModItems.NIGHTMARE_BOOK.get());
                        output.accept(ModItems.VOID_NECKLACE.get());
                        output.accept(ModItems.DARK_WHISPER_RING.get());
                        output.accept(ModItems.DARK_WHISPER_DAGGER.get());
                    }).build());
}