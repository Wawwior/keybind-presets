package me.wawwior.keybind_profiles.gui;

import me.wawwior.keybind_profiles.KeybindProfiles;
import me.wawwior.keybind_profiles.config.Profile;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenArea;
import net.minecraft.client.gui.screen.navigation.NavigationAxis;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.SpacerWidget;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.client.gui.widget.layout.FrameWidget;
import net.minecraft.client.gui.widget.layout.GridWidget;
import net.minecraft.client.gui.widget.layout.LayoutSettings;
import net.minecraft.client.gui.widget.layout.LinearLayoutWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.CommonTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Objects;
import java.util.Optional;

@ClientOnly
public class ProfilesScreen extends GameOptionsScreen {

	private ProfilesListWidget profilesListWidget;

	final GameOptions gameOptions;

	@Nullable // Most buttons are disabled when no profile is selected
	private Profile selectedProfile;

	private ButtonWidget deleteButton;
	private ButtonWidget editButton;
	private ButtonWidget loadButton;

	public ProfilesScreen(Screen parent, GameOptions gameOptions) {
		super(parent, gameOptions, Text.translatable("keybind_profiles.screen.title"));
		this.gameOptions = gameOptions;
	}

	public void init() {

		profilesListWidget = this.addDrawableSelectableElement(new ProfilesListWidget(this, this.client));
		profilesListWidget.setRenderBackground(false);

		if (selectedProfile != null) {
			profilesListWidget.setSelectedProfile(selectedProfile);
		}


		ButtonWidget doneButton = this.addDrawableSelectableElement(
			ButtonWidget.builder(CommonTexts.DONE, button -> Objects.requireNonNull(this.client).setScreen(this.parent))
					.width(152)
					.build()
		);

		ButtonWidget createButton = this.addDrawableSelectableElement(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.create"), button -> {
							String name = "New Profile";

							if (KeybindProfiles.getConfig().getProfile(name) != null) {
								int i = 2;
								while (KeybindProfiles.getConfig().getProfile(name + " #" + i) != null) {
									i++;
								}
								name = name + " #" + i;
							}

							Profile profile = KeybindProfiles.getConfig().newProfile(name);
							profile.save();
							profilesListWidget.update();
							profilesListWidget.setSelectedProfile(selectedProfile);
				})
						.width(152)
						.build()
		);

		deleteButton = this.addDrawableSelectableElement(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.delete"), button -> {
					if (selectedProfile != null) {
						KeybindProfiles.getConfig().deleteProfile(selectedProfile);
						profilesListWidget.update();
					}
				})
						.width(100)
						.build()
		);

		editButton = this.addDrawableSelectableElement(
			ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.edit"), button -> Objects.requireNonNull(client).setScreen(new ProfileEditScreen(this, Objects.requireNonNull(selectedProfile))))
					.width(100)
					.build()
		);

		loadButton = this.addDrawableSelectableElement(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.load"), button -> Objects.requireNonNull(selectedProfile).load())
						.width(100)
						.build()
		);

		GridWidget gridWidget = new GridWidget();
		GridWidget.AdditionHelper additionHelper = gridWidget.createAdditionHelper(1);

		LinearLayoutWidget layoutWidget0 = additionHelper.add(
				new LinearLayoutWidget(308, 20, LinearLayoutWidget.Orientation.HORIZONTAL),
				LayoutSettings.create().alignHorizontallyCenter()
		);
		layoutWidget0.add(loadButton);
		layoutWidget0.add(editButton);
		layoutWidget0.add(deleteButton);

		additionHelper.add(SpacerWidget.withHeight(4));

		LinearLayoutWidget layoutWidget1 = additionHelper.add(
				new LinearLayoutWidget(308, 20, LinearLayoutWidget.Orientation.HORIZONTAL),
				LayoutSettings.create().alignHorizontallyCenter()
		);

		layoutWidget1.add(createButton);
		layoutWidget1.add(doneButton);

		gridWidget.arrangeElements();
		FrameWidget.align(gridWidget, ScreenArea.create(NavigationAxis.HORIZONTAL, 0, this.height - 64, this.width, 64));

	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

		this.renderBackground(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredShadowedText(this.textRenderer, this.title, this.width / 2, 8, 16777215);

		selectedProfile = Optional.ofNullable(profilesListWidget.getSelectedOrNull()).map(ProfilesListWidget.ProfileListEntry::getKeybindProfile).orElse(null);

		boolean hasSelection = selectedProfile != null;

		deleteButton.active = hasSelection;
		editButton.active = hasSelection;
		loadButton.active = hasSelection;

		super.render(graphics, mouseX, mouseY, delta);
	}
}
