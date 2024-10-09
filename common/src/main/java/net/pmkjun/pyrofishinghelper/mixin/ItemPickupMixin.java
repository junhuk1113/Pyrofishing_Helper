package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class ItemPickupMixin {
	private static final Logger LOGGER = LogManager.getLogger("ItemPickupMixin");
	private final FishHelperClient client = FishHelperClient.getInstance();
	private final Minecraft mc = Minecraft.getInstance();
	@Shadow
	private ItemStack cursorStack;
	@Inject(method = "clicked", at = @At("RETURN"))
	private void onSlotClick(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
		if (player instanceof LocalPlayer) {
			if (!cursorStack.isEmpty()&&cursorStack.hasTag()) {
				System.out.println(cursorStack.getTooltipLines(mc.player, TooltipFlag.NORMAL));
				if(cursorStack.getHoverName().getString().equals("토템 발동")){
					LOGGER.info("토템 발동 버튼 눌림");
					client.updateTotemtime();
				}
			}
		}
	}
}