package me.wawwior.keybind_profiles.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.wawwior.keybind_profiles.config.Profile;
import net.minecraft.text.Text;
import org.quiltmc.qsl.command.api.client.ClientCommandRegistrationCallback;
import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource;

public class KeybindCommand {

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context0, environment) -> dispatcher.register(
				LiteralArgumentBuilder.<QuiltClientCommandSource>literal("keybind").then(
								LiteralArgumentBuilder.<QuiltClientCommandSource>literal("load").then(
												RequiredArgumentBuilder.<QuiltClientCommandSource, Profile>argument("profile", ProfileArgumentType.profile())
														.executes(context -> {
															Profile profile = ProfileArgumentType.getProfile(context, "profile");
															profile.load();
															context.getSource().sendFeedback(Text.translatable("keybind_profiles.command.keybind.load.success").append(Text.of(profile.getName())));
															return 1;
														})
								)
				)
		));
    }

}
