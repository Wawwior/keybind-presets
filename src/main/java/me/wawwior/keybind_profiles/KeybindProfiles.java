package me.wawwior.keybind_profiles;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientOnly
public class KeybindProfiles implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Keybind Profiles");

	@Override
	public void onInitializeClient(ModContainer mod) {

	}
}
