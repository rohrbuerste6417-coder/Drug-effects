package com.antigravity.drugeffects.mixin;

import com.antigravity.drugeffects.client.ClientEffectManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GameRenderer.class})
public abstract class MixinGameRenderer {
   @Inject(
      method = {"bobView"},
      at = {@At("HEAD")}
   )
   private void onBobView(PoseStack pPoseStack, float pPartialTicks, CallbackInfo ci) {
      float shake = ClientEffectManager.getEffect("forge:drug_shake");
      if (shake > 0.0F) {
         float time = (float)(System.currentTimeMillis() % 10000L) / 100.0F;
         float intensity = shake * 0.02F;
         float dx = (float)Math.sin((double)time * 15.0D) * intensity;
         float dy = (float)Math.cos((double)time * 23.0D) * intensity;
         pPoseStack.m_252880_(dx, dy, 0.0F);
      }

   }

   @Inject(
      method = {"getFov"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void onGetFov(Camera pActiveRenderInfo, float pPartialTicks, boolean pUseFOVSetting, CallbackInfoReturnable<Double> cir) {
      float fovModifier = ClientEffectManager.getEffect("forge:drug_fov");
      if (fovModifier != 0.0F) {
         double original = (Double)cir.getReturnValue();
         cir.setReturnValue(original + (double)fovModifier);
      }

   }
}
