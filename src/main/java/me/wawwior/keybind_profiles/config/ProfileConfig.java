package me.wawwior.keybind_profiles.config;

import me.wawwior.config.ConfigProvider;
import me.wawwior.config.Configurable;
import me.wawwior.config.IConfig;
import me.wawwior.config.io.impl.FileInfo;

import java.util.ArrayList;

public class ProfileConfig extends Configurable<ProfileConfig.Config, FileInfo> {

    public static class Config implements IConfig {

        public ArrayList<Profile> profiles = new ArrayList<>();

    }

    public ProfileConfig(ConfigProvider<FileInfo> provider) {
        super(Config.class, FileInfo.of("", "keybind_profiles"), provider);
        provider.withExtension(KeybindEntry.Modifiers.class, new KeybindEntry.Modifiers.Codec());
    }

    public Profile newProfile(String name) {
        Profile profile = new Profile(name);
        config.profiles.add(profile);
        return profile;
    }

    public Profile[] getProfiles() {
        return config.profiles.toArray(new Profile[0]);
    }

    public void deleteProfile(Profile profile) {
        config.profiles.remove(profile);
    }

    public Profile getProfile(String name) {
        name = name.trim();
        for (Profile profile : config.profiles) {
            if (profile.getName().trim().equalsIgnoreCase(name)) {
                return profile;
            }
        }
        return null;
    }

    public ArrayList<String> getProfileNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Profile profile : config.profiles) {
            names.add(profile.getName());
        }
        return names;
    }

}
