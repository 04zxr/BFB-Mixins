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