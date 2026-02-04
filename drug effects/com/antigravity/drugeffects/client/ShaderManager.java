package com.antigravity.drugeffects.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterShadersEvent;
import org.joml.Matrix4f;

public class ShaderManager {
   private static final ShaderManager INSTANCE = new ShaderManager();
   private boolean isShaderActive = false;
   private Minecraft mc = Minecraft.m_91087_();
   private ShaderInstance drugShader;
   private RenderTarget auxBuffer;

   public ShaderManager() {
      System.out.println("DrugEffects: ShaderManager Initialized");
   }

   public static ShaderManager getInstance() {
      return INSTANCE;
   }

   public void registerShaders(RegisterShadersEvent event) {
      try {
         event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("drugeffects", "drug_program"), DefaultVertexFormat.f_85817_), (shader) -> {
            this.drugShader = shader;
         });
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public boolean isActive() {
      return this.isShaderActive;
   }

   public void render(float partialTicks) {
      if (this.drugShader != null) {
         float wobble = ClientEffectManager.getEffect("forge:drug_wobble");
         float blur = ClientEffectManager.getEffect("forge:drug_blur");
         float saturation = ClientEffectManager.getEffect("forge:drug_saturation");
         boolean shouldBeActive = wobble > 0.0F || blur > 0.0F || saturation != 50.0F;
         this.isShaderActive = shouldBeActive;
         if (this.isShaderActive) {
            try {
               this.renderEffect(partialTicks);
            } catch (Exception var7) {
            }

         }
      }
   }

   private void renderEffect(float partialTicks) {
      RenderTarget main = this.mc.m_91385_();
      int width = main.f_83915_;
      int height = main.f_83916_;
      if (this.auxBuffer == null) {
         this.auxBuffer = new TextureTarget(width, height, true, Minecraft.f_91002_);
         this.auxBuffer.m_83931_(0.0F, 0.0F, 0.0F, 0.0F);
      }

      if (this.auxBuffer.f_83915_ != width || this.auxBuffer.f_83916_ != height) {
         this.auxBuffer.m_83941_(width, height, Minecraft.f_91002_);
      }

      RenderSystem.backupProjectionMatrix();
      Matrix4f ortho = (new Matrix4f()).setOrtho(0.0F, (float)width, (float)height, 0.0F, -1000.0F, 3000.0F);
      RenderSystem.setProjectionMatrix(ortho, VertexSorting.f_276450_);
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      this.auxBuffer.m_83947_(true);
      RenderSystem.setShaderTexture(0, main.m_83975_());
      RenderSystem.setShader(GameRenderer::m_172817_);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawFullScreenQuad(width, height);
      main.m_83947_(true);
      RenderSystem.setShader(() -> {
         return this.drugShader;
      });
      float wobble = ClientEffectManager.getEffect("forge:drug_wobble");
      float blur = ClientEffectManager.getEffect("forge:drug_blur");
      float saturation = ClientEffectManager.getEffect("forge:drug_saturation");
      this.safeSetUniform("DrugWobble", wobble);
      this.safeSetUniform("DrugBlur", blur);
      this.safeSetUniform("DrugSaturation", saturation);
      this.safeSetUniform("Time", (float)(System.currentTimeMillis() % 100000L) / 1000.0F);
      this.safeSetUniform("OutSize", (float)width, (float)height);
      RenderSystem.setShaderTexture(0, this.auxBuffer.m_83975_());
      this.drawFullScreenQuad(width, height);
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.restoreProjectionMatrix();
   }

   private void safeSetUniform(String name, float... values) {
      if (this.drugShader != null && this.drugShader.m_173348_(name) != null) {
         this.drugShader.m_173348_(name).m_5941_(values);
      }

   }

   private void safeSetUniform(String name, int value) {
      if (this.drugShader != null && this.drugShader.m_173348_(name) != null) {
         this.drugShader.m_173348_(name).m_142617_(value);
      }

   }

   private void drawFullScreenQuad(int width, int height) {
      Tesselator tess = Tesselator.m_85913_();
      BufferBuilder buf = tess.m_85915_();
      buf.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85817_);
      buf.m_5483_(0.0D, (double)height, 0.0D).m_7421_(0.0F, 1.0F).m_5752_();
      buf.m_5483_((double)width, (double)height, 0.0D).m_7421_(1.0F, 1.0F).m_5752_();
      buf.m_5483_((double)width, 0.0D, 0.0D).m_7421_(1.0F, 0.0F).m_5752_();
      buf.m_5483_(0.0D, 0.0D, 0.0D).m_7421_(0.0F, 0.0F).m_5752_();
      tess.m_85914_();
   }
}
