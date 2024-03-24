package net.pmkjun.pyrofishinghelper.util;


import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class FishingRod {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static void updateSpec(ItemStack stack){
        List<Text> ItemText;//Solar Rage, Precision Cutting
        ItemText = stack.getTooltip(mc.player, TooltipContext.BASIC);
        mc.player.sendMessage(Text.literal("낚시대 감지"));
        for(Text text : ItemText){
            mc.player.sendMessage(text);
        }
    }
}

