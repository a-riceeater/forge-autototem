package xyz.elijahb.autototem.events;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.elijahb.autototem.AutoTotem;

import java.awt.*;

@Mod.EventBusSubscriber(modid = AutoTotem.MOD_ID)
public class TotemUse {
    @SubscribeEvent
    public static void totemUsed(LivingEntityUseItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            // Check if the item used is a Totem of Undying
            if (event.getItem().getItem() == Items.TOTEM_OF_UNDYING) {
                // Your custom logic here
                PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), "You used a totem. You have x left.");
                player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));

                // Example: Log to console
                System.out.println(player.getName().getString() + " used a Totem of Undying!");
            }
        }
    }
}
