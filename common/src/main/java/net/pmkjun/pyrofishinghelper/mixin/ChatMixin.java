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
    private final FishHelperClient client = FishHelperClient.getInstance();
    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/text/Text;)V")
    private void addMessageMixin(Text message, CallbackInfo ci) {
        if(client.data.toggleChattinglog)
            FishHelperMod.LOGGER.info(message.getString());
        if(message.getString().contains("\uE2F8 ") && message.getString().contains("을 낚았습니다!")){
            for(String fishName : FishItemList.COMMMON_FISH_LIST){
                if(message.getString().contains(fishName)){
                    FishHelperMod.LOGGER.info("커먼 등급 물고기");
                }
            }
            for(String fishName : FishItemList.UNCOMMON_FISH_LIST){
                if(message.getString().contains(fishName)){
                    FishHelperMod.LOGGER.info("언커먼 등급 물고기");
                }
            }
            for(String fishName : FishItemList.RARE_FISH_LIST){
                if(message.getString().contains(fishName)){
                    FishHelperMod.LOGGER.info("레어 등급 물고기");
                }
            }
            for(String fishName : FishItemList.EPIC_FISH_LIST){
                if(message.getString().contains(fishName)){
                    FishHelperMod.LOGGER.info("에픽 등급 물고기");
                }
            }
            for(String fishName : FishItemList.LEGENDARY_FISH_LIST){
                if(message.getString().contains(fishName)){
                    FishHelperMod.LOGGER.info("레전더리 등급 물고기");
                }
            }
            for(String fishName : FishItemList.MYTHIC_FISH_LIST){
                if(message.getString().contains(fishName)){
                    FishHelperMod.LOGGER.info("신화 등급 물고기");
                }
            }
        }
    }
}