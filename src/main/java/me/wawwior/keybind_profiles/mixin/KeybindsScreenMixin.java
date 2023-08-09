package me.wawwior.keybind_profiles.mixin;

import me.wawwior.keybind_profiles.gui.ProfileEditScreen;
import me.wawwior.keybind_profiles.gui.ProfilesScreen;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.KeyBindsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@ClientOnly
@Mixin(KeyBindsScreen.class)
public class KeybindsScreenMixin extends GameOptionsScreen {

	public KeybindsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	@Inject(method = "init", at = @At("TAIL"))
	protected void init$addButton(CallbackInfo ci) {
        if (!(this.parent instanceof ProfileEditScreen)) {
            this.addDrawableChild(new TexturedButtonWidget(
                    this.width / 2 - 155 + 150 + 170,
                    this.height - 29,
                    20,
                    20,
                    0,
                    0,
                    20,
                    new Identifier("keybind_profiles", "textures/gui/buttons.png"),
                    20,
                    40,
                    buttonWidget -> Objects.requireNonNull(this.client).setScreen(new ProfilesScreen(this, gameOptions))
            ));
        }
    }

	@Override
	public void removed() {
		if (this.parent instanceof ProfileEditScreen) {
			KeybindUtil.applyTemporaryKeybinds();
		} else {
			super.removed();
		}
	}

	@Redirect(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredShadowedText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V",
					ordinal = 0
			)
	)
	protected void render$drawCenteredShadowedText(GuiGraphics graphics, TextRenderer textRenderer, Text text, int x, int y, int color) {
		if (this.parent instanceof ProfileEditScreen p) {
			text = Text.translatable("keybind_profiles.screen.title.edit").append(Text.literal(p.getProfile().getName()).formatted(Formatting.YELLOW));
		}
		graphics.drawCenteredShadowedText(textRenderer, text, x, y, color);
	}
}
