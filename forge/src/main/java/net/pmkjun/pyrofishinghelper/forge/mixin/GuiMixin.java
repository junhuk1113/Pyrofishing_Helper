package net.pmkjun.pyrofishinghelper.forge.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeGui.class)
public class GuiMixin extends Gui {

    public GuiMixin(Minecraft mc, ItemRenderer itemRenderer) {
        super(mc, itemRenderer);
    }

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;F)V", at = {@At("HEAD")}, cancellable = false)
    public void renderMixin(GuiGraphics guiGraphics, float partialTick, CallbackInfo info) {
        FishHelperClient.getInstance().renderEvent(guiGraphics);
    }
}