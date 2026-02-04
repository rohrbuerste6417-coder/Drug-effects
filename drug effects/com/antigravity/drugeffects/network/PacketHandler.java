package com.antigravity.drugeffects.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
   private static final String PROTOCOL_VERSION = "1";
   public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("drugeffects", "main"), () -> {
      return "1";
   }, "1"::equals, "1"::equals);

   public static void register() {
      int id = 0;
      byte var10001 = id;
      int var1 = id + 1;
      INSTANCE.registerMessage(var10001, PacketSyncEffect.class, PacketSyncEffect::encode, PacketSyncEffect::decode, PacketSyncEffect::handle);
   }
}
