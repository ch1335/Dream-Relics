package com.bmt.dream_relics.registry;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.item.NightmareBook;
import com.bmt.dream_relics.item.SoulMirrorItem;
import com.bmt.dream_relics.item.YearsAmber;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DreamRelics.MODID);

    public static final RegistryObject<Item> SOUL_MIRROR = ITEMS.register("soul_mirror",
            () -> new SoulMirrorItem(new Item.Properties()));

    public static final RegistryObject<Item> DREAM_TOTEM = ITEMS.register("dream_totem",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOMENT_STONE = ITEMS.register("moment_stone",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENDLESS_DREAM = ITEMS.register("endless_dream",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> YEARS_AMBER = ITEMS.register("years_amber",
            () -> new YearsAmber(new Item.Properties()));
    public static final RegistryObject<Item> MEMORY_STARDUST = ITEMS.register("memory_stardust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIME_HOURGLASS = ITEMS.register("time_hourglass",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ASTRAL_NECKLACE = ITEMS.register("astral_necklace",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TASSEL_RING = ITEMS.register("tassel_ring",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OBSERVE_SELF_EYE = ITEMS.register("observe_self_eye",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DREAM_BALANCE = ITEMS.register("dream_balance",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PAST_RING = ITEMS.register("past_ring",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ECHO_EARRING = ITEMS.register("echo_earring",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEMORY_NECKLACE = ITEMS.register("memory_necklace",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SLEEPING_STAR_SEED = ITEMS.register("sleeping_star_seed",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LIMINAL_KEY = ITEMS.register("liminal_key",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HEART_VOICE_PENDANT = ITEMS.register("heart_voice_pendant",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AWAKEN_DREAM_BRACELET = ITEMS.register("awaken_dream_bracelet",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MIST_VEIL_RING = ITEMS.register("mist_veil_ring",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NIGHTMARE_BOOK = ITEMS.register("nightmare_book",
            () -> new NightmareBook(new Item.Properties()));
    public static final RegistryObject<Item> VOID_NECKLACE = ITEMS.register("void_necklace",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DARK_WHISPER_RING = ITEMS.register("dark_whisper_ring",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DARK_WHISPER_DAGGER = ITEMS.register("dark_whisper_dagger",
            () -> new Item(new Item.Properties()));
}