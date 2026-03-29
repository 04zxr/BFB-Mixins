package com.lightmildtea.bfb_mixins.mixin;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemAlchemySword;
import com.lightmildtea.bfb_mixins.util.NunchakuHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemAlchemySword.class, remap = false)
public abstract class DragonBoneSwordMixin extends SwordItem {

    public DragonBoneSwordMixin(
            Tier tier,
            int damage,
            float speed,
            Item.Properties props) {
        super(tier, damage, speed, props);
    }

    /**
     * @author LightMildTea
     * @reason Replace Lightning Dragonbone Sword's vanilla lightning bolt
     *         with Forgotten Nunchaku's chain lightning effect.
     */
    @Overwrite(remap = false)
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity source) {

        // FIRE SWORD - Unchanged from Ice and Fire
        if (this == IafItemRegistry.DRAGONBONE_SWORD_FIRE.get() && IafConfig.dragonWeaponFireAbility) {
            if (target instanceof EntityIceDragon) {
                target.hurt(source.level().damageSources().inFire(), 13.5F);
            }
            target.setSecondsOnFire(5);
            target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());
        }

        // ICE SWORD - Unchanged from Ice and Fire
        if (this == IafItemRegistry.DRAGONBONE_SWORD_ICE.get() && IafConfig.dragonWeaponIceAbility) {
            if (target instanceof EntityFireDragon) {
                target.hurt(source.level().damageSources().drown(), 13.5F);
            }
            EntityDataProvider.getCapability(target).ifPresent(data -> data.frozenData.setFrozen(target, 200));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
            target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());
        }

        // LIGHTNING SWORD - Replaced with chain lightning
        if (this == IafItemRegistry.DRAGONBONE_SWORD_LIGHTNING.get() && IafConfig.dragonWeaponLightningAbility) {
            if (source instanceof Player player && !source.level().isClientSide && player.attackAnim <= 0.2) {
                boolean modloaded = ModList.get().isLoaded("iceandfire");
                NunchakuHelper.applyChainLightning(target, player, modloaded);
            }
            if (target instanceof EntityFireDragon || target instanceof EntityIceDragon) {
                target.hurt(source.level().damageSources().lightningBolt(), 9.5F);
            }
            target.knockback(1F, source.getX() - target.getX(), source.getZ() - target.getZ());
        }

        return super.hurtEnemy(stack, target, source);
    }
}