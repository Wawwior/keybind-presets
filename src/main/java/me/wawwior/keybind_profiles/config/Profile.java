package me.wawwior.keybind_profiles.config;

import me.wawwior.keybind_profiles.KeybindProfiles;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
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
		MinecraftClient.getInstance().getToastManager().add(new SystemToast(
			KeybindProfiles.SYSTEM_TOAST_PROFILE_TYPE, // ???
			Text.of("Profile Loaded!"),
			Text.of("\"" + name + "\" active.")
		));
	}

	public void save() {
		KeybindUtil.storeKeybinds(keyBinds);
	}
}
