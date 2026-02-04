package com.antigravity.drugeffects.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   bus = Bus.MOD
)
public class ClientModEventHandler {
   @SubscribeEvent
   public static void registerShaders(RegisterShadersEvent event) {
      ShaderManager.getInstance().registerShaders(event);
   }
}
