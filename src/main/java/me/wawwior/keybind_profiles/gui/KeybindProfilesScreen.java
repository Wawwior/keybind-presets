package me.wawwior.keybind_profiles.gui;

import me.wawwior.keybind_profiles.KeybindProfiles;
import me.wawwior.keybind_profiles.compat.AmecsCompat;
import me.wawwior.keybind_profiles.config.KeybindEntry;
import me.wawwior.keybind_profiles.config.Profile;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.CommonTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Objects;

@ClientOnly
public class KeybindProfilesScreen extends GameOptionsScreen {

	private ProfilesListWidget profilesListWidget;

	final GameOptions gameOptions;

	@Nullable // Only in active buttons
	private ProfilesListWidget.ProfileListEntry selectedProfileEntry;

	private ButtonWidget deleteButton;
	private ButtonWidget editButton;
	private ButtonWidget loadButton;

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

		this.addDrawableChild(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.create"), button -> {
							Profile profile = KeybindProfiles.config.newProfile("New Profile");
							KeybindUtil.storeKeybinds(profile.getKeyBinds());
							profilesListWidget.update();
				})
						.positionAndSize(this.width / 2 - 155, this.height - 29, 150, 20)
						.build()
		);

		deleteButton = this.addDrawableChild(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.delete"), button -> {
					Profile profile = Objects.requireNonNull(selectedProfileEntry).getKeybindProfile();
					if (profile != null) {
						KeybindProfiles.config.deleteProfile(profile);
						profilesListWidget.update();
					}
				})
					.positionAndSize(this.width / 2 - 155 + 214, this.height - 29 - 24, 96, 20)
					.build()
		);

		editButton = this.addDrawableChild(
			ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.edit"), button -> ScreenManager.openKeybindScreen(Objects.requireNonNull(selectedProfileEntry).getKeybindProfile(), this))
				.positionAndSize(this.width / 2 - 155 + 107, this.height - 29 - 24, 96, 20)
				.build()
		);

		loadButton = this.addDrawableChild(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.load"), button -> {
					Objects.requireNonNull(selectedProfileEntry).load();
				})
					.positionAndSize(this.width / 2 - 155, this.height - 29 - 24, 96, 20)
					.build()
		);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

		this.renderBackground(graphics);
		this.profilesListWidget.render(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredShadowedText(this.textRenderer, this.title, this.width / 2, 8, 16777215);;

		if (profilesListWidget.getSelectedOrNull() != null) {
			selectedProfileEntry = profilesListWidget.getSelectedOrNull();
			deleteButton.active = true;
			editButton.active = true;
			loadButton.active = true;
		};

		super.render(graphics, mouseX, mouseY, delta);
	}

	public ProfilesListWidget getProfilesListWidget() {
		return profilesListWidget;
	}
}
