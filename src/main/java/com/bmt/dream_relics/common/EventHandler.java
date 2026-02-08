package com.bmt.dream_relics.common;

import com.bmt.dream_relics.DreamRelics;
import com.bmt.dream_relics.common.capabilities.PlayerData;
import com.bmt.dream_relics.common.capabilities.YearsAmberItemHandler;
import com.bmt.dream_relics.item.YearsAmber;
import com.bmt.dream_relics.registry.DRCapabilities;
import com.bmt.dream_relics.registry.ModItems;
import com.bmt.dream_relics.util.DRUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class EventHandler {
    @Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventHandler {
        @SubscribeEvent
        public static void PlayerXpEvent(PlayerXpEvent.PickupXp event) {
            if (DRUtil.isEquippedNightmareBook(event.getEntity())) {
                event.getOrb().value = (int) (0.5 * event.getOrb().value);
            }
        }

        @SubscribeEvent
        public static void HarvestCheck(PlayerEvent.HarvestCheck event) {
            if (!event.canHarvest()) {
                if (YearsAmber.findBestCorrectTool(event.getEntity(), event.getEntity().getMainHandItem(), event.getTargetBlock()) != null) {
                    event.setCanHarvest(true);
                }
            }
        }

        @SubscribeEvent
        public static void LivingHurtEvent(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(ModItems.NIGHTMARE_BOOK.get())) {
                        event.setAmount(event.getAmount() * 0.5F);
                    }
                });
            }
        }

        @SubscribeEvent
        public static void MobEffectEvent$Add(MobEffectEvent.Added event) {
            if (event.getEntity() instanceof Player player) {
                CuriosApi.getCuriosInventory(player).ifPresent(iCuriosItemHandler -> {
                    if (iCuriosItemHandler.isEquipped(ModItems.NIGHTMARE_BOOK.get())) {
                        @NotNull MobEffectInstance effectInstance = event.getEffectInstance();
                        effectInstance.update(new MobEffectInstance(
                                effectInstance.getEffect(),
                                effectInstance.getDuration() * 2,
                                effectInstance.getAmplifier(),
                                effectInstance.isAmbient(),
                                effectInstance.isVisible(),
                                effectInstance.showIcon()
                        ));
                    }
                });
            }
        }

        @SubscribeEvent
        public static void AnvilUpdateEvent(AnvilUpdateEvent event) {
            if (DRUtil.isEquippedNightmareBook(event.getPlayer())) {
                event.setCost(event.getCost() * 2);
            }
        }

        @SubscribeEvent
        public static void PlayerTickEvent(TickEvent.PlayerTickEvent event) {
            Player player = event.player;
            if (!player.isLocalPlayer()) {
                List<? extends Player> players = player.level().players();
                for (Player otherPlayer : players) {
                    if (player.distanceToSqr(otherPlayer) <= 25) {
                        player.getCapability(DRCapabilities.SERVER_SIDE_PLAYER_DATA).ifPresent(playerData -> {

                        });
                    }
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

        @SubscribeEvent
        public static void AttachPlayerCapabilitiesEvent(AttachCapabilitiesEvent<Player> event) {
            event.addCapability(DreamRelics.id("player_data"), new PlayerData());
        }
    }


    @Mod.EventBusSubscriber(modid = DreamRelics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventHandler {
        @SubscribeEvent
        public static void RegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
            event.register(YearsAmberItemHandler.class);
            event.register(PlayerData.class);
        }
    }
}
