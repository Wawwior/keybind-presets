package me.wawwior.keybind_profiles.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.wawwior.keybind_profiles.config.Profile;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

public class KeybindCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, context0, environment) -> {
            if (environment.integrated) {
                dispatcher.register(
                        LiteralArgumentBuilder.<ServerCommandSource>literal("keybind").then(
                                        LiteralArgumentBuilder.<ServerCommandSource>literal("load").then(
                                                        RequiredArgumentBuilder.<ServerCommandSource, Profile>argument("profile", ProfileArgumentType.profile())
                                                                .executes(context -> {
                                                                    Profile profile = ProfileArgumentType.getProfile(context, "profile");
                                                                    profile.load();
                                                                    context.getSource().sendFeedback(() -> Text.translatable("keybind_profiles.command.keybind.load.success", Text.of(profile.getName())), false);
                                                                    return 1;
                                                                })
                                        )
                        )
                );
            }
        });
    }

}
