/*
 * BFB Mixins - A balancing and tweaking mod for BFB modpack
 * Copyright (C) 2026 LightMildTea
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
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