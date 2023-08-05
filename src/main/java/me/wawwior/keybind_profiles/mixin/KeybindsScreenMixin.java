package me.wawwior.keybind_profiles.mixin;

import me.wawwior.keybind_profiles.gui.KeybindProfilesScreen;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.KeyBindsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
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
		if (this.parent instanceof KeybindProfilesScreen) return;
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
			buttonWidget -> {
				Objects.requireNonNull(this.client).setScreen(new KeybindProfilesScreen(this, gameOptions));
			}
		));
	}

	@Redirect(
			method = "init",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/screen/option/KeyBindsScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;",
					ordinal = 0
			)
	)
	protected <T extends Element & Drawable & Selectable> Element init$addDrawableChild(KeyBindsScreen keyBindsScreen, Element element) {
		if (!(this.parent instanceof KeybindProfilesScreen))
			//noinspection unchecked
			return this.addDrawableChild((T)element);
		else
			return this.addDrawableChild(ButtonWidget.builder(Text.translatable("keybind_profiles.screen.more"), buttonWidget -> {
			//TODO: More Options
		}).positionAndSize(this.width / 2 - 155, this.height - 29, 150, 20).build());
	}

	@Override
	public void removed() {
		if (this.parent instanceof KeybindProfilesScreen) return;
		super.removed();
	}
}
