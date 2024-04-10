package me.wawwior.keybind_profiles.gui;

import me.wawwior.keybind_profiles.config.Profile;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.KeyBindOptionsScreen;

public class GuiUtil {

	public static void openKeybindScreen(Profile profile, Screen parent) {
		KeybindUtil.updateTemporaryKeybinds();
		profile.load();
		MinecraftClient.getInstance().setScreen(new KeyBindOptionsScreen(parent, MinecraftClient.getInstance().options));
	}

}
