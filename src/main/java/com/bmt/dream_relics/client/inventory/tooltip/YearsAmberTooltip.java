package com.bmt.dream_relics.client.inventory.tooltip;

import com.bmt.dream_relics.client.DRClient;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class YearsAmberTooltip implements ClientTooltipComponent {
    private final Component component;

    public YearsAmberTooltip(Component component) {
        this.component = component;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics guiGraphics) {
        if (DRClient.IS_ALT_DOWN) {

            for (int i = 0; i < component.items.size(); i++) {
                ItemStack itemStack = component.items().get(i);
                if (!itemStack.isEmpty()) {
                    guiGraphics.renderItem(itemStack, x + 18 * i, y);
                    guiGraphics.renderItemDecorations(font, itemStack, x + 18 * i, y);
                }
            }
        }
    }

    @Override
    public int getHeight() {
        if (DRClient.IS_ALT_DOWN) {
            return 18;
        }
        return 0;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return 18 * 4 + 2;
    }


    public record Component(NonNullList<ItemStack> items) implements TooltipComponent {
    }
}
