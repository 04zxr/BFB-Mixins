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
package com.lightmildtea.bfb_mixins;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BfbMixins.MOD_ID)
public class BfbMixins {

    public static final String MOD_ID = "bfb_mixins";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public BfbMixins(FMLJavaModLoadingContext context) {
        LOGGER.info("BFB Mixins loaded!");

        // Register config (optional, can remove if you don't need config)
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Register event listeners
        MinecraftForge.EVENT_BUS.register(this);
    }
}