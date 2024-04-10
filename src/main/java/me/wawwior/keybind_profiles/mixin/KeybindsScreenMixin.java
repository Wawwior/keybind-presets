package me.wawwior.keybind_profiles.mixin;

import me.wawwior.keybind_profiles.gui.ProfileEditScreen;
import me.wawwior.keybind_profiles.gui.ProfilesScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.KeyBindOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidgetStateTextures;
import net.minecraft.client.gui.widget.button.TexturedButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@ClientOnly
@Mixin(value = {KeyBindOptionsScreen.class})
public class KeybindsScreenMixin extends GameOptionsScreen {

	public KeybindsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	@Unique
	private static final ClickableWidgetStateTextures textures = new ClickableWidgetStateTextures(
		new Identifier("keybind_profiles", "button_unfocused"),
		new Identifier("keybind_profiles", "button_focused")
	);

	@Inject(method = "init", at = @At("TAIL"))
	protected void init$addButton(CallbackInfo ci) {
        if (!(this.parent instanceof ProfileEditScreen)) {
            this.addDrawableSelectableElement(new TexturedButtonWidget(
                    this.width / 2 - 155 + 150 + 170,
                    this.height - 29,
                    20,
                    20,
                    textures,
                    buttonWidget -> Objects.requireNonNull(this.client).setScreen(new ProfilesScreen(this, gameOptions))
            ));
        }
    }

	@ModifyArg(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredShadowedText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V",
					ordinal = 0
			),
			index = 1
	)
	private Text render$drawCenteredShadowText$Text(Text text) {
		if (this.parent instanceof ProfileEditScreen p) {
			text = Text.translatable("keybind_profiles.screen.title.edit").append(Text.literal(p.getProfile().getName()).formatted(Formatting.YELLOW));
		}
		return text;
	}
}
