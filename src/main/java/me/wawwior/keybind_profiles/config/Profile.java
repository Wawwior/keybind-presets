package me.wawwior.keybind_profiles.config;

import com.mojang.blaze3d.platform.InputUtil;
import me.wawwior.keybind_profiles.compat.AmecsCompat;
import me.wawwior.keybind_profiles.gui.ScreenManager;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class Profile {

    private final HashMap<String, KeybindEntry> keyBinds = new HashMap<>();

    private final UUID id = UUID.randomUUID();

    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public HashMap<String, KeybindEntry> getKeyBinds() {
        return keyBinds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

	public void load() {
		KeybindUtil.loadKeybinds(keyBinds);
		KeybindUtil.updateTemporaryKeybinds();
	}

	public void save() {
		KeybindUtil.storeKeybinds(keyBinds);
	}
}
