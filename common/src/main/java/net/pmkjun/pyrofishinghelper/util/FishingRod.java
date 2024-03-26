package net.pmkjun.pyrofishinghelper.util;

import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.pmkjun.pyrofishinghelper.FishHelperClient;

public class FishingRod {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final FishHelperClient client = FishHelperClient.getInstance();

    public static void updateSpec(ItemStack stack){
        List<Text> ItemText;//Solar Rage, Precision Cutting
        String tooltipString;
        int Augmentlevel;

        ItemText = stack.getTooltip(mc.player, TooltipContext.BASIC);
        mc.player.sendMessage(Text.literal("낚시대 감지"));
        for(Text text : ItemText){
            mc.player.sendMessage(text);
            tooltipString = text.getString();
            if(tooltipString.contains("Solar Rage")){
                tooltipString = tooltipString.replace("Solar Rage ", "");
                Augmentlevel = RomanNum.toInt(tooltipString);
                if(Augmentlevel != client.data.valueSolarRage)
                {
                    mc.player.sendMessage(Text.literal("Solar Rage Lv : "+client.data.valueSolarRage+" >> "+Augmentlevel));
                    client.data.valueSolarRage = Augmentlevel;
                    client.configManage.save();
                }
            }
            else if(tooltipString.contains("Precision Cutting")){
                tooltipString = tooltipString.replace("Precision Cutting ", "");
                Augmentlevel = RomanNum.toInt(tooltipString);
                if(Augmentlevel != client.data.valuePrecisionCutting)
                {
                    mc.player.sendMessage(Text.literal("Precision Cutting Lv : "+client.data.valuePrecisionCutting+" >> "+Augmentlevel));
                    client.data.valuePrecisionCutting = Augmentlevel;
                    client.configManage.save();
                }
            }
        }
    }
}

