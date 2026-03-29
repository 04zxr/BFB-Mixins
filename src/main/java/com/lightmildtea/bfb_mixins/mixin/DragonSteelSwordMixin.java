package com.lightmildtea.bfb_mixins.mixin;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.item.DragonSteelOverrides;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.lightmildtea.bfb_mixins.util.NunchakuHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = DragonSteelOverrides.class, remap = false)
public interface DragonSteelSwordMixin {
    @Shadow
    float getAttackDamage(TieredItem item);

    @Shadow
    boolean isDragonsteelFire(net.minecraft.world.item.Tier tier);

    @Shadow
    boolean isDragonsteelIce(net.minecraft.world.item.Tier tier);

    @Shadow
    boolean isDragonsteelLightning(net.minecraft.world.item.Tier tier);

    /**
     * @author LightMildTea
     * @reason Replace Lightning Dragonsteel weapon lightning bolts with Forgotten Nunchaku's chain lightning.
     */
    @Overwrite(remap = false)
    default void hurtEnemy(TieredItem item, ItemStack stack, LivingEntity target, LivingEntity attacker) {

        // SILVER WEAPONS - Unchanged
        if (item.getTier() == IafItemRegistry.SILVER_TOOL_MATERIAL) {
            if (target.getMobType() == MobType.UNDEAD) {
                target.hurt(attacker.level().damageSources().magic(), getAttackDamage(item) + 3.0F);
            }
        }

        // MYRMEX WEAPONS - Unchanged
        if (item.getTier() == IafItemRegistry.MYRMEX_CHITIN_TOOL_MATERIAL) {
            if (target.getMobType() != MobType.ARTHROPOD) {
                target.hurt(attacker.level().damageSources().generic(), getAttackDamage(item) + 5.0F);
            }
            if (target instanceof EntityDeathWorm) {
                target.hurt(attacker.level().damageSources().generic(), getAttackDamage(item) + 5.0F);
            }
        }

        // FIRE DRAGONSTEEL - Unchanged
        if (isDragonsteelFire(item.getTier()) && IafConfig.dragonWeaponFireAbility) {
            target.setSecondsOnFire(15);
            target.knockback(1F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        }

        // ICE DRAGONSTEEL - Unchanged
        if (isDragonsteelIce(item.getTier()) && IafConfig.dragonWeaponIceAbility) {
            EntityDataProvider.getCapability(target).ifPresent(data -> data.frozenData.setFrozen(target, 300));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 2));
            target.knockback(1F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        }

        // LIGHTNING DRAGONSTEEL - Replaced with chain lightning
        if (isDragonsteelLightning(item.getTier()) && IafConfig.dragonWeaponLightningAbility) {

            if (attacker instanceof Player player
                    && !attacker.level().isClientSide
                    && player.attackAnim <= 0.2) {

                boolean modloaded = ModList.get().isLoaded("iceandfire");
                NunchakuHelper.applyChainLightning(target, player, modloaded);
            }

            target.knockback(1F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        }
    }
}
