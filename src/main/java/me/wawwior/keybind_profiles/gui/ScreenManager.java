package me.wawwior.keybind_profiles.gui;

import me.wawwior.keybind_profiles.config.Profile;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.KeyBindsScreen;

public class ScreenManager {

	public static void openKeybindScreen(Profile profile, Screen parent) {
		KeybindUtil.updateTemporaryKeybinds();
		KeybindUtil.loadKeybinds(profile.getKeyBinds());
		MinecraftClient.getInstance().setScreen(new KeyBindsScreen(parent, MinecraftClient.getInstance().options));
	}

}
