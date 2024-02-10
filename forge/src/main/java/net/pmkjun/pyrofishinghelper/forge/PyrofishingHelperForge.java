package net.pmkjun.pyrofishinghelper.forge;

import net.pmkjun.pyrofishinghelper.FishHelperMod;
import net.minecraftforge.fml.common.Mod;
import net.pmkjun.pyrofishinghelper.forge.input.KeyMappings;

@Mod(FishHelperMod.MOD_ID)
public class PyrofishingHelperForge {
    public PyrofishingHelperForge() {
        FishHelperMod.init();
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();
    }
}