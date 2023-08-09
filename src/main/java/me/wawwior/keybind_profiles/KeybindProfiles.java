package me.wawwior.keybind_profiles;

import me.wawwior.config.ConfigProvider;
import me.wawwior.config.io.impl.FileInfo;
import me.wawwior.config.io.impl.JsonFileAdapter;
import me.wawwior.keybind_profiles.command.KeybindCommand;
import me.wawwior.keybind_profiles.command.ProfileArgumentType;
import me.wawwior.keybind_profiles.compat.AmecsCompat;
import me.wawwior.keybind_profiles.config.ProfileConfig;
import net.minecraft.command.argument.SingletonArgumentInfo;
import net.minecraft.registry.Registries;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.command.mixin.ArgumentTypeInfosAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientOnly
public class KeybindProfiles implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Keybind Profiles");

	public static ConfigProvider<FileInfo> configProvider;

	private static ProfileConfig config;

	@Override
	public void onInitializeClient(ModContainer mod) {

		configProvider = new ConfigProvider<>(new JsonFileAdapter("config"), true);

		config = new ProfileConfig(configProvider);
		config.load();

		AmecsCompat.init();

		ArgumentTypeInfosAccessor.callRegister(Registries.COMMAND_ARGUMENT_TYPE, "keybind_profiles:profile", ProfileArgumentType.class, SingletonArgumentInfo.contextFree(ProfileArgumentType::profile));

		KeybindCommand.register();

		Runtime.getRuntime().addShutdownHook(new Thread(this::onShutdown));

	}

	private void onShutdown() {
		config.save();
	}

	public static ProfileConfig getConfig() {
		return config;
	}
}
