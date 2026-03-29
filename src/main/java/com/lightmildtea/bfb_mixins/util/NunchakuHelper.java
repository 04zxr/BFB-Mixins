package com.lightmildtea.bfb_mixins.util;

import com.kettle.nunchakus.items.LightningDragonBoneNunchaku;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class NunchakuHelper {

    private static LightningDragonBoneNunchaku cachedNunchaku = null;

    // ✅ Static method you can call from anywhere!
    public static void applyChainLightning(LivingEntity target, Player player, boolean modloaded) {
        if (cachedNunchaku == null) {
            Item item = ForgeRegistries.ITEMS
                    .getValue(ResourceLocation.fromNamespaceAndPath(
                            "fn", "lightningdragonbone_nunchakus"));
            if (item instanceof LightningDragonBoneNunchaku n) {
                cachedNunchaku = n;
            }
        }

        if (cachedNunchaku != null) {
            cachedNunchaku.applyChainLightning(target, player, modloaded);
        }
    }
}