package xyz.elijahb.autototem;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.elijahb.autototem.events.TotemUse;

import java.awt.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AutoTotem.MOD_ID)
public class AutoTotem
{
    public static final String MOD_ID = "elijbautototem";
    public AutoTotem(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();




        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ClientModEvents.class); // For static methods


        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            // Check if the entity is a player

            if (event.getEntity() instanceof ServerPlayer) {

                ServerPlayer player = (ServerPlayer) event.getEntity();

                // Calculate the health after taking damage
                float totalEffectiveHealth = player.getHealth() + player.getAbsorptionAmount();

                // Calculate health after damage
                float healthAfterDamage = totalEffectiveHealth - event.getAmount();

                // Check if the damage would be fatal
                if (healthAfterDamage <= 0.0F) {

                    // Check if the player has a Totem of Undying in their inventory
                    boolean hasTotem = player.getMainHandItem().is(Items.TOTEM_OF_UNDYING)
                            || player.getOffhandItem().is(Items.TOTEM_OF_UNDYING);

                    if (hasTotem) {
                        // Triggered when a Totem of Undying would save the player
                        System.out.println("used a Totem of Undying!!!");
                        Minecraft.getInstance().player.displayClientMessage(Component.literal("You used a totem."), false);

                        // Allow the totem's default behavior (don't cancel the event)
                    }
                }
            }
        }
    }
}
