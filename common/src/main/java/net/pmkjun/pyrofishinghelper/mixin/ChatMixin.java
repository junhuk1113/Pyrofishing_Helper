package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.FishHelperMod;
import net.pmkjun.pyrofishinghelper.item.FishItemList;
import net.pmkjun.pyrofishinghelper.util.Earning;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Arrays;

@Mixin(ChatHud.class)
public abstract class ChatMixin {
    private static final int COMMON = 0;
    private static final int UNCOMMON = 1;
    private static final int RARE = 2;
    private static final int EPIC = 3;
    private static final int LEGENDARY = 4;
    private static final int MYTHIC = 5;
    private final MinecraftClient mc = MinecraftClient.getInstance();

    private final FishHelperClient client = FishHelperClient.getInstance();
    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    private void addMessageMixin(Text message, CallbackInfo ci) {
        if(client.data.toggleChattinglog)
            FishHelperMod.LOGGER.info(message.getString());
        if(message.getString().contains("\uE2F8 ") && message.getString().contains("을 낚았습니다!")){
            for(String fishName : FishItemList.COMMMON_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[COMMON]++;
                    this.client.configManage.save();
                }
            }
            for(String fishName : FishItemList.UNCOMMON_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[UNCOMMON]++;
                    this.client.configManage.save();
                }
            }
            for(String fishName : FishItemList.RARE_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[RARE]++;
                    this.client.configManage.save();
                }
            }
            for(String fishName : FishItemList.EPIC_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[EPIC]++;
                    this.client.configManage.save();
                }
            }
            for(String fishName : FishItemList.LEGENDARY_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[LEGENDARY]++;
                    this.client.configManage.save();
                }
            }
            for(String fishName : FishItemList.MYTHIC_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[MYTHIC]++;
                    this.client.configManage.save();
                }
            }
            if(client.data.toggleFishCounter) {
                assert mc.player != null;
                mc.player.sendMessage(Text.literal(Arrays.toString(client.data.fish_Count)));
                mc.player.sendMessage(Text.literal("번 수익 : "+Earning.getMoney()));
                mc.player.sendMessage(Text.literal("번 엔트로피 : "+Earning.getEntropy()));
            }
        }
    }
}