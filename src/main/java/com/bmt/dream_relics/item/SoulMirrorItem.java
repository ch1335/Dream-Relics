package com.bmt.dream_relics.item;

import com.bmt.dream_relics.config.Config;
import com.bmt.dream_relics.DreamRelics;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = DreamRelics.MODID)
public class SoulMirrorItem extends Item {

    public SoulMirrorItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        if (level != null && level.isClientSide()) {
            Player player = net.minecraft.client.Minecraft.getInstance().player;
            if (player != null && player.getCooldowns().isOnCooldown(this)) {
                float cooldownPercent = player.getCooldowns().getCooldownPercent(this, 0.0F);
                int remainingTicks = (int) (cooldownPercent * Config.cooldownTime);
                int remainingSeconds = (int) Math.ceil(remainingTicks / 20.0);
                if (remainingSeconds > 0) {
                    list.add(Component.translatable("item.dream_relics.soul_mirror.cooldown", remainingSeconds)
                            .withStyle(ChatFormatting.GOLD));
                }
            }
        }

        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(itemstack);
        }

        player.startUsingItem(hand);
        if (!level.isClientSide) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 0.5F,
                    0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        } else {
            spawnChargingParticles(level, player);
        }
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUseTick(Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int remainingUseTicks) {
        if (level.isClientSide && entity instanceof Player) {
            spawnChargingParticles(level, entity);
        }
    }

    private void spawnChargingParticles(Level level, LivingEntity entity) {
        if (level.isClientSide) {
            for (int i = 0; i < 3; i++) {
                double x = entity.getX() + (level.random.nextDouble() - 0.5) * 2.0;
                double y = entity.getY() + level.random.nextDouble() * 2.0;
                double z = entity.getZ() + (level.random.nextDouble() - 0.5) * 2.0;
                level.addParticle(ParticleTypes.PORTAL, x, y, z, 0, 0, 0);
            }
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                Optional<GlobalPos> deathLocation = player.getLastDeathLocation();
                if (deathLocation.isPresent()) {
                    GlobalPos globalPos = deathLocation.get();
                    ServerLevel targetLevel = player.server.getLevel(globalPos.dimension());

                    if (targetLevel != null) {
                        BlockPos pos = globalPos.pos();
                        player.teleportTo(targetLevel,
                                pos.getX() + 0.5,
                                pos.getY(),
                                pos.getZ() + 0.5,
                                player.getYRot(),
                                player.getXRot());

                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                        player.getCooldowns().addCooldown(this, Config.cooldownTime);
                    } else {
                        player.displayClientMessage(
                                Component.translatable("death.dream_relics.dimension_not_found")
                                        .withStyle(ChatFormatting.RED), true);
                    }
                } else {
                    player.displayClientMessage(
                            Component.translatable("death.dream_relics.no_death_point")
                                    .withStyle(ChatFormatting.RED), true);
                }
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return Config.chargeTime;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }
}