package net.pmkjun.pyrofishinghelper.forge.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeGui.class)
public class GuiMixin extends InGameHud {

    public GuiMixin(MinecraftClient mc, ItemRenderer itemRenderer) {
        super(mc, itemRenderer);
    }

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;F)V", at = {@At("HEAD")}, cancellable = false)
    public void renderMixin(DrawContext guiGraphics, float partialTick, CallbackInfo info) {
        FishHelperClient.getInstance().renderEvent(guiGraphics);
    }
}