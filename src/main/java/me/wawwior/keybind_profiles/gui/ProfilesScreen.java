package me.wawwior.keybind_profiles.gui;

import me.wawwior.keybind_profiles.KeybindProfiles;
import me.wawwior.keybind_profiles.config.Profile;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.NavigationAxis;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenArea;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.gui.widget.container.LayoutSettings;
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

		profilesListWidget = this.addDrawableChild(new ProfilesListWidget(this, this.client));

		if (selectedProfile != null) {
			profilesListWidget.setSelectedProfile(selectedProfile);
		}


		ButtonWidget doneButton = this.addDrawableChild(
			ButtonWidget.builder(CommonTexts.DONE, button -> Objects.requireNonNull(this.client).setScreen(this.parent))
					.width(152)
					.build()
		);

		ButtonWidget createButton = this.addDrawableChild(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.create"), button -> {
							Profile profile = KeybindProfiles.config.newProfile("New Profile");
							KeybindUtil.storeKeybinds(profile.getKeyBinds());
							profilesListWidget.update();
				})
						.width(152)
						.build()
		);

		deleteButton = this.addDrawableChild(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.delete"), button -> {
					if (selectedProfile != null) {
						KeybindProfiles.config.deleteProfile(selectedProfile);
						profilesListWidget.update();
					}
				})
						.width(100)
						.build()
		);

		editButton = this.addDrawableChild(
			ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.edit"), button -> Objects.requireNonNull(client).setScreen(new ProfileEditScreen(this, Objects.requireNonNull(selectedProfile))))
					.width(100)
					.build()
		);

		loadButton = this.addDrawableChild(
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
		layoutWidget0.addChild(loadButton);
		layoutWidget0.addChild(editButton);
		layoutWidget0.addChild(deleteButton);

		additionHelper.add(SpacerWidget.withHeight(4));

		LinearLayoutWidget layoutWidget1 = additionHelper.add(
				new LinearLayoutWidget(308, 20, LinearLayoutWidget.Orientation.HORIZONTAL),
				LayoutSettings.create().alignHorizontallyCenter()
		);

		layoutWidget1.addChild(createButton);
		layoutWidget1.addChild(doneButton);

		gridWidget.arrangeElements();
		FrameWidget.align(gridWidget, ScreenArea.create(NavigationAxis.HORIZONTAL, 0, this.height - 64, this.width, 64));

	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

		this.renderBackground(graphics);
		graphics.drawCenteredShadowedText(this.textRenderer, this.title, this.width / 2, 8, 16777215);;

		selectedProfile = Optional.ofNullable(profilesListWidget.getSelectedOrNull()).map(ProfilesListWidget.ProfileListEntry::getKeybindProfile).orElse(null);

		boolean hasSelection = selectedProfile != null;

		deleteButton.active = hasSelection;
		editButton.active = hasSelection;
		loadButton.active = hasSelection;

		super.render(graphics, mouseX, mouseY, delta);
	}
}
