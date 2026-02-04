package com.antigravity.drugeffects.mixin;

import com.antigravity.drugeffects.client.ClientEffectManager;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({MouseHandler.class})
public class MixinMouseHandler {
   @Redirect(
      method = {"turnPlayer"},
      at = @At(
   value = "FIELD",
   target = "Lnet/minecraft/client/Options;smoothCamera:Z"
)
   )
   private boolean onGetSmoothCamera(Options options) {
      float delay = ClientEffectManager.getEffect("forge:drug_mouse_delay");
      return delay > 0.0F ? true : options.f_92067_;
   }
}
