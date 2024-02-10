package net.pmkjun.pyrofishinghelper.fabric;

import net.pmkjun.pyrofishinghelper.FishHelperMod;
import net.fabricmc.api.ModInitializer;
import net.pmkjun.pyrofishinghelper.fabric.input.KeyMappings;

public class PyrofishingHelperFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FishHelperMod.init();
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();
    }
}