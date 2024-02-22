package net.pmkjun.pyrofishinghelper.fabric.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.fabric.item.FishItems;
import net.pmkjun.pyrofishinghelper.util.ConvertActivateTime;
import net.pmkjun.pyrofishinghelper.util.ConvertCooldown;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemModels.class)
public class ItemModelsMixin {
    @Shadow
    @Final
    private BakedModelManager modelManager;
    @Shadow
    public BakedModel getModel(Item item) {
        return null;
    }

    @Inject(method = {"getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;"},at = {@At("TAIL")}, cancellable = true)
    public void getModelMixin(ItemStack stack, CallbackInfoReturnable<BakedModel> cir){
        Item changed_item;
        List<Text> ItemText;
        String Itemname = null;
        String levelString;
        int levelInt;
        double secondDouble;

        cir.cancel();

        ItemText = stack.getTooltip(MinecraftClient.getInstance().player, TooltipContext.BASIC);
        for(Text text : ItemText){
            if(Itemname == null)
                Itemname = text.getString();

            if(!Itemname.equals("지속시간 업그레이드")&&!Itemname.equals("쿨타임 감소")&&!Itemname.contains("토템 리더 |"))
                break;

            if(text.getString().contains("현재 레벨 ➛ ")){
                levelString = text.getString().replace("현재 레벨 ➛ ","");
                levelInt = Integer.parseInt(levelString);
                //System.out.println(Itemname +" : "+ levelString);
                if(Itemname.equals("지속시간 업그레이드")){
                    if(FishHelperClient.getInstance().data.valueTotemActivetime != ConvertActivateTime.asMinute(levelInt))
                    {
                        FishHelperClient.getInstance().data.valueTotemActivetime = ConvertActivateTime.asMinute(levelInt);
                        FishHelperClient.getInstance().configManage.save();
                    }
                }
                if(Itemname.equals("쿨타임 감소")){
                    if(FishHelperClient.getInstance().data.valueTotemCooldown != ConvertCooldown.asMinute(levelInt))
                    {
                        FishHelperClient.getInstance().data.valueTotemCooldown = ConvertCooldown.asMinute(levelInt);
                        FishHelperClient.getInstance().configManage.save();
                    }
                }
            }
            if(Itemname.contains("토템 리더 |") && text.getString().contains("효과|") && !text.getString().contains("다음 레벨 효과")){
                levelString = text.getString().replace("효과| ", "");
                levelString = levelString.replace(" 초 감소");
                secondDouble = Double.parseDouble(levelString);
                if(FishHelperClient.getInstance().data.valueCooldownReduction!=secondDouble){
                    FishHelperClient.getInstance().data.valueCooldownReduction = secondDouble;
                    FishHelperClient.getInstance().configManage.save();
                }
            }
        }

        if((changed_item = FishItems.getFishItem(stack))!=null) stack = new ItemStack((ItemConvertible) changed_item, stack.getCount());

        BakedModel bakedModel = getModel(stack.getItem());
        cir.setReturnValue((bakedModel == null) ? modelManager.getMissingModel() : bakedModel);
    }

}