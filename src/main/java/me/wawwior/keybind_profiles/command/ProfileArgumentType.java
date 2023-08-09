package me.wawwior.keybind_profiles.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.wawwior.keybind_profiles.KeybindProfiles;
import me.wawwior.keybind_profiles.config.Profile;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ProfileArgumentType implements ArgumentType<Profile> {

    public static ProfileArgumentType profile() {
        return new ProfileArgumentType();
    }

    @NotNull
    public static Profile getProfile(CommandContext<?> context, String name) {
        return context.getArgument(name, Profile.class);
    }

    @NotNull
    @Override
    public Profile parse(StringReader reader) throws CommandSyntaxException {
        String name = reader.getRemaining();
        reader.setCursor(reader.getTotalLength());
        Profile profile = KeybindProfiles.getConfig().getProfile(name);
        if (profile == null) {
            throw new SimpleCommandExceptionType(Text.of("Invalid profile name: " + name)).create();
        }
        return profile;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        KeybindProfiles.getConfig().getProfileNames().forEach(builder::suggest);
        return builder.buildFuture();
    }
}
