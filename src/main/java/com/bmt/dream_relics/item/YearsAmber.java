package com.bmt.dream_relics.item;

import com.bmt.dream_relics.client.DRClient;
import com.bmt.dream_relics.client.inventory.tooltip.YearsAmberTooltip;
import com.bmt.dream_relics.common.capabilities.YearsAmberItemHandler;
import com.bmt.dream_relics.registry.DRCapabilities;
import com.bmt.dream_relics.registry.ModItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YearsAmber extends DreamRelicItemBase {
    public YearsAmber(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        if (!DRClient.IS_ALT_DOWN) {
            list.add(Component.translatable("item.dream_relics.years_amber.show").withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, @NotNull Slot slot, @NotNull ClickAction clickAction, @NotNull Player player) {
        if (stack.getCount() != 1 || clickAction != ClickAction.SECONDARY) {
            return false;
        } else {

            stack.getCapability(DRCapabilities.YEARS_AMBER_ITEM_HANDLER).ifPresent(iItemHandler -> {

                ItemStack itemstack = slot.getItem();
                if (itemstack.isEmpty()) {
                    for (int i = iItemHandler.getSlots() - 1; i >= 0; i--) {
                        if (!iItemHandler.getStackInSlot(i).isEmpty()) {
                            @NotNull ItemStack extract = iItemHandler.extractItem(i, 1, false);
                            slot.safeInsert(extract.copy());
                            updateTag(stack, iItemHandler.serializeNBT());
                            break;
                        }
                    }
                } else if (itemstack.is(ItemTags.TOOLS) || itemstack.is(Tags.Items.SHEARS)) {
                    for (int i = 0; i < iItemHandler.getSlots(); i++) {
                        if (iItemHandler.getStackInSlot(i).isEmpty()) {
                            iItemHandler.insertItem(i, itemstack.copy(), false);
                            itemstack.shrink(1);
                            updateTag(stack, iItemHandler.serializeNBT());
                            break;
                        }
                    }
                }
            });
            return true;
        }
    }

    public void updateTag(ItemStack stack, CompoundTag compoundTag) {
        stack.getOrCreateTag().put("Items", compoundTag);
    }

    public static List<ItemStack> getContents(boolean isClient, ItemStack stack) {
        YearsAmberItemHandler handler = null;
        if (isClient) {
            handler = new YearsAmberItemHandler(4);
            handler.deserializeNBT(stack.getOrCreateTag().getCompound("Items"));
        } else {
            @NotNull LazyOptional<YearsAmberItemHandler> lazyOptional = stack.getCapability(DRCapabilities.YEARS_AMBER_ITEM_HANDLER);
            if (lazyOptional.isPresent()) {
                handler = lazyOptional.orElseGet(null);
            }
        }
        if (handler == null) {
            return List.of();
        }
        List<ItemStack> itemStacks = new ArrayList<>();
        for (int i = 0; i < handler.getSlots(); i++) {
            itemStacks.add(handler.getStackInSlot(i));
        }
        return itemStacks;
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack itemStack) {
        NonNullList<ItemStack> nonnulllist = NonNullList.create();
        getContents(true, itemStack).forEach(nonnulllist::add);
        return Optional.of(new YearsAmberTooltip.Component(nonnulllist));
    }

    @Nullable
    public static Pair<Float, ItemStack> findBestCorrectTool(Player player, ItemStack old, BlockState blockState) {
        LazyOptional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(player);
        float oldSpeed = old.getDestroySpeed(blockState);
        if (optional.isPresent()) {
            ICuriosItemHandler itemHandler = optional.orElse(null);
            List<SlotResult> list = itemHandler.findCurios(ModItems.YEARS_AMBER.get());
            if (!list.isEmpty()) {
                SlotResult amberItem = list.get(0);
                float speed = 0;
                ItemStack newItem = null;
                for (ItemStack itemStack : YearsAmber.getContents(player.isLocalPlayer(), amberItem.stack())) {
                    if (itemStack.isCorrectToolForDrops(blockState)) {
                        float newSpeed = itemStack.getDestroySpeed(blockState);
                        if (newSpeed > speed) {
                            int efficiency = itemStack.getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY);
                            if (efficiency > 0) {
                                speed = newSpeed + efficiency * efficiency + 1;
                            } else {
                                speed = newSpeed;
                            }

                            newItem = itemStack;
                        }
                    }
                }


                if (speed > oldSpeed && newItem != null) {

                    return Pair.of(speed, newItem);
                }
            }
        }
        return null;
    }
}
