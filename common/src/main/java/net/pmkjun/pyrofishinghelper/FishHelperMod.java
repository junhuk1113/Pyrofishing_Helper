package net.pmkjun.pyrofishinghelper;

import com.mojang.logging.LogUtils;
import net.pmkjun.pyrofishinghelper.item.FishItems;
import org.slf4j.Logger;

public class FishHelperMod
{
	public static final String MOD_ID = "pyrofishing_helper";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static FishHelperClient client;
	public static void init() {
		client = new FishHelperClient();
		client.init();
		FishItems.register();
	}
}
