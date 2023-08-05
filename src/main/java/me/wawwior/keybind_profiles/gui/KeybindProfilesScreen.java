package me.wawwior.keybind_profiles.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.CommonTexts;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Objects;

@ClientOnly
public class KeybindProfilesScreen extends GameOptionsScreen {

	private ProfilesListWidget profilesListWidget;

	final GameOptions gameOptions;

	public KeybindProfilesScreen(Screen parent, GameOptions gameOptions) {
		super(parent, gameOptions, Text.translatable("keybind_profiles.screen.title"));
		this.gameOptions = gameOptions;
	}

	public void init() {
		profilesListWidget = new ProfilesListWidget(this, this.client);
		this.addSelectableChild(profilesListWidget);
		this.addDrawableChild(
			ButtonWidget
				.builder(CommonTexts.DONE, button -> Objects.requireNonNull(this.client).setScreen(this.parent))
				.positionAndSize(this.width / 2 - 155 + 160, this.height - 29, 150, 20)
				.build()
		);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

		this.renderBackground(graphics);
		this.profilesListWidget.render(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredShadowedText(this.textRenderer, this.title, this.width / 2, 8, 16777215);

		super.render(graphics, mouseX, mouseY, delta);
	}
}
