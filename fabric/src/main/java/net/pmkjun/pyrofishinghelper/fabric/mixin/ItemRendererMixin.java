package net.pmkjun.pyrofishinghelper.fabric.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pmkjun.pyrofishinghelper.fabric.item.FishItems;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow
    @Final
    private ItemModels models;

    @ModifyArgs(
        method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"
        )
    )
    private void modifyRenderItemArgs(Args args) {
        ItemStack itemStack = args.get(0); // 첫 번째 인수인 ItemStack을 가져옵니다.
        BakedModel originalModel = args.get(7); // 여덟 번째 인수인 BakedModel을 가져옵니다.

        Item changedItem = FishItems.getFishItem(itemStack);

        if (changedItem != null) {
            System.out.println(itemStack.getName().getString());
            ItemStack newStack = new ItemStack((ItemConvertible) changedItem, itemStack.getCount());
            BakedModel modifiedModel = models.getModel(newStack.getItem());
            args.set(7, modifiedModel); // 변경된 BakedModel을 인수로 설정합니다.
        } else {
            args.set(7, originalModel); // 변경 사항이 없으면 원래의 BakedModel을 그대로 사용합니다.
        }
    }
    /*@Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD"))
    private void onRenderItem(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        // 로그 메시지를 출력합니다.
        System.out.println("renderItem 메서드가 호출되었습니다: Item = " + stack.getItem().getName().getString() + ", RenderMode = " + renderMode);
    }*/
}
