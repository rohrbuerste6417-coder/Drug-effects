package com.antigravity.drugeffects.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientEffectManager {
   private static final Map<String, Float> EFFECTS = new ConcurrentHashMap();

   public static void setEffect(String key, float value) {
      EFFECTS.put(key, value);
   }

   public static float getEffect(String key) {
      return (Float)EFFECTS.getOrDefault(key, 0.0F);
   }

   static {
      EFFECTS.put("forge:drug_wobble", 0.0F);
      EFFECTS.put("forge:drug_blur", 0.0F);
      EFFECTS.put("forge:drug_saturation", 50.0F);
      EFFECTS.put("forge:drug_mouse_delay", 0.0F);
      EFFECTS.put("forge:drug_shake", 0.0F);
      EFFECTS.put("forge:drug_fov", 0.0F);
   }
}
