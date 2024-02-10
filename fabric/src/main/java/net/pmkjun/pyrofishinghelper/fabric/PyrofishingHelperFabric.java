package net.pmkjun.pyrofishinghelper.fabric;

import net.pmkjun.pyrofishinghelper.FishHelperMod;
import net.fabricmc.api.ModInitializer;

public class PyrofishingHelperFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FishHelperMod.init();
    }
}