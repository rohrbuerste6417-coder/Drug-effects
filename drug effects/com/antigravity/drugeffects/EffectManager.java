package com.antigravity.drugeffects;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "drugeffects"
)
public class EffectManager {
   public static final String KEY_DRUG_WOBBLE = "forge:drug_wobble";
   public static final String KEY_DRUG_BLUR = "forge:drug_blur";
   public static final String KEY_DRUG_SATURATION = "forge:drug_saturation";
   public static final String KEY_MOUSE_DELAY = "forge:drug_mouse_delay";
   public static final String KEY_DRUG_SHAKE = "forge:drug_shake";
   public static final String KEY_DRUG_FOV = "forge:drug_fov";

   @SubscribeEvent
   public static void onRegisterCommands(RegisterCommandsEvent event) {
      CommandDrugEffect.register(event.getDispatcher());
   }
}
