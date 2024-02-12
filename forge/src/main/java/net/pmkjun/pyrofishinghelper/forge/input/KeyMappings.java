package net.pmkjun.pyrofishinghelper.forge.input;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.pmkjun.pyrofishinghelper.FishHelperClient;
import net.pmkjun.pyrofishinghelper.gui.screen.ConfigScreen;
import net.pmkjun.pyrofishinghelper.input.IKeyMappings;
import net.minecraftforge.common.MinecraftForge;

public class KeyMappings implements IKeyMappings {
    public static KeyBinding openSettingScreen =
            new KeyBinding("fishhelper.key.open_settings", 72, "fishhelper.key.category");

    @Override
    public void register() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(KeyMappings::registerKeyBindings);
    }

    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(openSettingScreen);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        MinecraftClient mc = MinecraftClient.getInstance();
        FishHelperClient client = FishHelperClient.getInstance();

        if(event.phase == TickEvent.Phase.END) {
            while(openSettingScreen.wasPressed()) {
                mc.setScreen(new ConfigScreen(mc.currentScreen));
            }
        }
    }
}
