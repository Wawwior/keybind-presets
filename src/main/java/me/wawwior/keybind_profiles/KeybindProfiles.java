package me.wawwior.keybind_profiles;

import me.wawwior.config.ConfigProvider;
import me.wawwior.config.io.impl.FileInfo;
import me.wawwior.config.io.impl.JsonFileAdapter;
import me.wawwior.keybind_profiles.compat.AmecsCompat;
import me.wawwior.keybind_profiles.config.KeybindEntry;
import me.wawwior.keybind_profiles.config.ProfileConfig;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientOnly
public class KeybindProfiles implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Keybind Profiles");

	public static ConfigProvider<FileInfo> configProvider;

	public static ProfileConfig config;

	@Override
	public void onInitializeClient(ModContainer mod) {

		configProvider = new ConfigProvider<>(new JsonFileAdapter("config"), true);
		configProvider.withExtension(KeybindEntry.Modifiers.class, new KeybindEntry.Modifiers.Serializer());

		config = new ProfileConfig(configProvider);
		config.load();

		AmecsCompat.init();

		Runtime.getRuntime().addShutdownHook(new Thread(this::onShutdown));

	}

	private void onShutdown() {
		config.save();
	}
}
