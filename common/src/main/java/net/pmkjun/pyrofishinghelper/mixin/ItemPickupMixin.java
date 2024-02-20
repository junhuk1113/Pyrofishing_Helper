package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.FishHelperMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.Set;

@Mixin(ScreenHandler.class)
public class ItemPickupMixin {
	private static final Logger LOGGER = LogManager.getLogger("ItemPickupMixin");
	private final FishHelperClient client = FishHelperClient.getInstance();
	@Shadow
	private ItemStack cursorStack;
	@Inject(method = "onSlotClick", at = @At("RETURN"))
	private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
		if (player instanceof ClientPlayerEntity) {
			if (!cursorStack.isEmpty()&&cursorStack.hasNbt()) {
				System.out.println(cursorStack.getTooltip(MinecraftClient.getInstance().player, TooltipContext.BASIC));
				//oh please
				System.out.println("ok good");
				if(cursorStack.getName().getString().equals("토템 발동")){
					//LOGGER.info("토템 발동 버튼 눌림");
					client.updateTotemtime();
				}
			}
		}
	}
}