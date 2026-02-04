package com.antigravity.drugeffects;

import com.antigravity.drugeffects.network.PacketHandler;
import com.antigravity.drugeffects.network.PacketSyncEffect;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class CommandDrugEffect {
   private static final List<String> EFFECTS = Arrays.asList("drug_wobble", "drug_blur", "drug_saturation", "drug_mouse_delay", "drug_shake", "drug_fov");

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("drugeffect").requires((s) -> {
         return s.m_6761_(2);
      })).then(Commands.m_82127_("set").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).then(Commands.m_82129_("effect", StringArgumentType.word()).suggests((context, builder) -> {
         return SharedSuggestionProvider.m_82970_(EFFECTS, builder);
      }).then(Commands.m_82129_("value", FloatArgumentType.floatArg(0.0F)).executes(CommandDrugEffect::executeSet))))));
   }

   private static int executeSet(CommandContext<CommandSourceStack> context) {
      try {
         Collection<ServerPlayer> players = EntityArgument.m_91477_(context, "targets");
         String effectName = StringArgumentType.getString(context, "effect");
         float value = FloatArgumentType.getFloat(context, "value");
         String nbtKey = "forge:" + effectName;
         Iterator var5 = players.iterator();

         while(var5.hasNext()) {
            ServerPlayer player = (ServerPlayer)var5.next();
            player.getPersistentData().m_128350_(nbtKey, value);
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> {
               return player;
            }), new PacketSyncEffect(nbtKey, value));
            player.m_213846_(Component.m_237113_("Set " + effectName + " to " + value));
         }

         return players.size();
      } catch (Exception var7) {
         var7.printStackTrace();
         return 0;
      }
   }
}
