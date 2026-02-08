package com.bmt.dream_relics.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

public class NightmareBook extends DreamRelicItemBase implements ICurioItem {

    public NightmareBook(Properties properties) {
        super(properties);
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        multimap.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "nightmare_book_max_health", 0.6, AttributeModifier.Operation.MULTIPLY_TOTAL));

        return ICurioItem.super.getAttributeModifiers(slotContext, uuid, stack);
    }
}
