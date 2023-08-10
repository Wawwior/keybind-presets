package me.wawwior.keybind_profiles.config;

import me.wawwior.keybind_profiles.util.KeybindUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Profile {

    @NotNull
    private final HashMap<String, KeybindEntry> keyBinds = new HashMap<>();

    private String name;

    public Profile(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public void load() {
		KeybindUtil.loadKeybinds(keyBinds);
	}

	public void save() {
		KeybindUtil.storeKeybinds(keyBinds);
	}
}
