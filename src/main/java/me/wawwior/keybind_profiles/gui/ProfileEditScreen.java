package me.wawwior.keybind_profiles.gui;

import me.wawwior.keybind_profiles.config.Profile;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.NavigationAxis;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenArea;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.gui.widget.container.LayoutSettings;
import net.minecraft.text.CommonTexts;
import net.minecraft.text.Text;

public class ProfileEditScreen extends Screen {

	private final ProfilesScreen parent;
	private final Profile profile;

	private TextFieldWidget nameField;

	protected ProfileEditScreen(ProfilesScreen parent, Profile profile) {
		super(Text.of("Edit Profile"));
		this.parent = parent;
		this.profile = profile;
	}

	@Override
	protected void init() {

		nameField = this.addDrawableChild(
				new TextFieldWidget(textRenderer, 0, 0, 200, 20, Text.of(""))
		);

		nameField.setText(profile.getName());

		ButtonWidget doneButton = this.addDrawableChild(
				ButtonWidget.builder(CommonTexts.DONE, button -> closeScreen())
						.width(100)
						.build()
		);

		ButtonWidget moreButton = this.addDrawableChild(
				ButtonWidget.builder(Text.translatable("keybind_profiles.screen.button.more"), button -> {
							GuiUtil.openKeybindScreen(profile, this);
						})
						.width(100)
						.build()
		);

		GridWidget gridWidget = new GridWidget();
		GridWidget.AdditionHelper additionHelper = gridWidget.createAdditionHelper(1);

		LinearLayoutWidget layoutWidget0 = additionHelper.add(
				new LinearLayoutWidget(200, 20, LinearLayoutWidget.Orientation.HORIZONTAL),
				LayoutSettings.create().alignVerticallyCenter().alignHorizontallyCenter()
		);
		layoutWidget0.addChild(nameField);

		additionHelper.add(SpacerWidget.withHeight(8));

		LinearLayoutWidget layoutWidget2 = additionHelper.add(
				new LinearLayoutWidget(204, 20, LinearLayoutWidget.Orientation.HORIZONTAL),
				LayoutSettings.create().alignVerticallyCenter().alignHorizontallyCenter()
		);
		layoutWidget2.addChild(moreButton);
		layoutWidget2.addChild(doneButton);


		gridWidget.arrangeElements();
		FrameWidget.align(gridWidget, ScreenArea.create(NavigationAxis.HORIZONTAL, 0, 0, width, height));

	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

		renderBackground(graphics);
		graphics.drawCenteredShadowedText(textRenderer, title, width / 2, 8, 16777215);

		super.render(graphics, mouseX, mouseY, delta);
	}

	@Override
	public void closeScreen() {
		if (this.client != null) {
			this.client.setScreen(parent);
		}
		profile.save();
		profile.setName(nameField.getText());
	}

	public Profile getProfile() {
		return profile;
	}
}
