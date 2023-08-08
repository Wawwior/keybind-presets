package me.wawwior.keybind_profiles.config;

import me.wawwior.config.ConfigProvider;
import me.wawwior.config.Configurable;
import me.wawwior.config.IConfig;
import me.wawwior.config.io.impl.FileInfo;

import java.util.HashMap;
import java.util.UUID;

public class ProfileConfig extends Configurable<ProfileConfig.Config, FileInfo> {

    public static class Config implements IConfig {

        public HashMap<UUID, Profile> profiles = new HashMap<>();

    }

    public ProfileConfig(ConfigProvider<FileInfo> provider) {
        super(Config.class, FileInfo.of("", "keybind_profiles"), provider);
    }

    public Profile newProfile(String name) {
        Profile profile = new Profile(name);
        config.profiles.put(profile.getId(), profile);
        return profile;
    }

    public Profile[] getProfiles() {
        return config.profiles.values().toArray(new Profile[0]);
    }

    public void deleteProfile(Profile profile) {
        config.profiles.remove(profile.getId());
    }

}
