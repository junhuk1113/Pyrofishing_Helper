package net.pmkjun.pyrofishinghelper.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.pmkjun.pyrofishinghelper.FishHelperMod;
import net.minecraftforge.fml.common.Mod;
import net.pmkjun.pyrofishinghelper.forge.input.KeyMappings;
import net.pmkjun.pyrofishinghelper.forge.item.FishItems;

@Mod(FishHelperMod.MOD_ID)
public class PyrofishingHelperForge {
    public PyrofishingHelperForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FishItems.register();
        FishItems.ITEMS.register(eventBus);

        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();
        FishHelperMod.init();
    }
}