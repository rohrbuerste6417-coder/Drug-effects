package com.antigravity.drugeffects.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   bus = Bus.FORGE
)
public class ClientEventHandler {
   @SubscribeEvent
   public static void onRenderLevelStage(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_LEVEL) {
         if (Minecraft.m_91087_().f_91074_ != null && Minecraft.m_91087_().f_91074_.f_19797_ % 200 == 0) {
            System.out.println("DrugEffects: RenderLevelStageEvent FIRED. Calling ShaderManager...");
         }

         ShaderManager.getInstance().render(event.getPartialTick());
      }

   }

   static {
      System.out.println("DrugEffects: ClientEventHandler Loaded");
   }
}
