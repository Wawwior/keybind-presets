package me.wawwior.keybind_profiles.gui;

import com.mojang.blaze3d.platform.InputUtil;
import me.wawwior.keybind_profiles.KeybindProfiles;
import me.wawwior.keybind_profiles.config.Profile;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.*;

@ClientOnly
public class ProfilesListWidget extends AlwaysSelectedEntryListWidget<ProfilesListWidget.ProfileListEntry> {

	final KeybindProfilesScreen parent;

	public ProfilesListWidget(KeybindProfilesScreen parent, MinecraftClient client) {
		super(client, parent.width, parent.height, 20, parent.height - 64, 20);
		this.parent = parent;
		update();
	}

	public void update() {
		clearEntries();
		Arrays.stream(KeybindProfiles.config.getProfiles()).sorted(Comparator.comparing(Profile::getId)).forEach(profile -> addEntry(new ProfileListEntry(profile)));
		for (ProfileListEntry entry : this.children()) {
			entry.update();
		}
	}

	public void closed() {
		Objects.requireNonNull(getSelectedOrNull()).closed();
	}

	@ClientOnly
	public class ProfileListEntry extends AlwaysSelectedEntryListWidget.Entry<ProfileListEntry> {

		private final Profile keybindProfile;

		private final TextFieldWidget nameField;

		private long time;

		public ProfileListEntry(Profile keybindProfile) {
			this.keybindProfile = keybindProfile;
			nameField = new TextFieldWidget(ProfilesListWidget.this.client.textRenderer, 0, 0, 200, 16, Text.of(""));
			nameField.setText(keybindProfile.getName());
		}


		void update() {

		}

		@Override
		public Text getNarration() {
			return Text.translatable("narrator.select");
		}

		@Override
		public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			graphics.drawCenteredShadowedText(ProfilesListWidget.this.client.textRenderer, keybindProfile.getName(), ProfilesListWidget.this.width / 2, y + 3, 16777215);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			ProfilesListWidget.this.setSelected(this);
			if (Util.getMeasuringTimeMs() - this.time < 250L) {
				this.load();
			}
			this.time = Util.getMeasuringTimeMs();
			return true;
		}

		public void load() {
			this.keybindProfile.load();
		}

		public void closed() {
			keybindProfile.save();
			KeybindUtil.applyTemporaryKeybinds();
			keybindProfile.setName(nameField.getText());
		}

		public Profile getKeybindProfile() {
			return keybindProfile;
		}

		public TextFieldWidget getNameField() {
			return nameField;
		}
	}

}
