package com.antigravity.drugeffects.network;

import com.antigravity.drugeffects.client.ClientEffectManager;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSyncEffect {
   private final String key;
   private final float value;

   public PacketSyncEffect(String key, float value) {
      this.key = key;
      this.value = value;
   }

   public static void encode(PacketSyncEffect msg, FriendlyByteBuf buf) {
      buf.m_130070_(msg.key);
      buf.writeFloat(msg.value);
   }

   public static PacketSyncEffect decode(FriendlyByteBuf buf) {
      return new PacketSyncEffect(buf.m_130277_(), buf.readFloat());
   }

   public static void handle(PacketSyncEffect msg, Supplier<Context> ctx) {
      ((Context)ctx.get()).enqueueWork(() -> {
         ClientEffectManager.setEffect(msg.key, msg.value);
      });
      ((Context)ctx.get()).setPacketHandled(true);
   }
}
