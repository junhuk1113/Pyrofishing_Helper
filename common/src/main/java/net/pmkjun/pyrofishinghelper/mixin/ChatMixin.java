package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.FishHelperMod;
import net.pmkjun.pyrofishinghelper.item.FishItemList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class ChatMixin {
    private static final int COMMON = 0;
    private static final int UNCOMMON = 1;
    private static final int RARE = 2;
    private static final int EPIC = 3;
    private static final int LEGENDARY = 4;
    private static final int MYTHIC = 5;

    private final FishHelperClient client = FishHelperClient.getInstance();
    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    private void addMessageMixin(Text message, CallbackInfo ci) {
        if(client.data.toggleChattinglog)
            FishHelperMod.LOGGER.info(message.getString());
        
        if((message.getString().contains("\uE2F8 ") && (message.getString().contains("을(를) 낚았습니다.")||message.getString().contains("You caught a")))||
        (message.getString().contains("\uE2F8 ") && message.getString().contains("로 변환되었습니다."))){
        FISH:
        {
            for(String fishName : FishItemList.MYTHIC_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[MYTHIC]++;
                    this.client.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.LEGENDARY_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[LEGENDARY]++;
                    this.client.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.EPIC_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[EPIC]++;
                    this.client.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.RARE_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[RARE]++;
                    this.client.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.UNCOMMON_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[UNCOMMON]++;
                    this.client.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.COMMMON_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.client.data.fish_Count[COMMON]++;
                    this.client.configManage.save();
                }
            }
        }
        }
    }
}