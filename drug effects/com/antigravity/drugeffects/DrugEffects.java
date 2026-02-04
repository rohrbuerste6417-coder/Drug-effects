package com.antigravity.drugeffects;

import com.antigravity.drugeffects.network.PacketHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("drugeffects")
public class DrugEffects {
   public static final String MODID = "drugeffects";
   public static final Logger LOGGER = LogUtils.getLogger();

   public DrugEffects() {
      IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
      PacketHandler.register();
      MinecraftForge.EVENT_BUS.register(this);
   }
}
