package me.wawwior.keybind_profiles.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.option.KeyBindsScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ProfilesListWidget extends AlwaysSelectedEntryListWidget<ProfilesListWidget.Entry> {

	final KeybindProfilesScreen parent;

	public ProfilesListWidget(KeybindProfilesScreen parent, MinecraftClient client) {
		super(client, parent.width, parent.height, 20, parent.height - 64, 20);
		this.parent = parent;
		//TODO: remove debug entry
		ProfileEntry testEntry = new ProfileEntry(Text.of("Test Entry"));
		addEntry(testEntry);
		setSelected(testEntry);
	}

	@ClientOnly
	public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<ProfilesListWidget.Entry> {
		abstract void update();
	}

	@ClientOnly
	public class ProfileEntry extends Entry {

		private final Text name;

		private final String id;

		private long time;

		private GameOptions gameOptions;

		public ProfileEntry(Text name) {
			this.name = name;
			this.id = name.getString().toLowerCase().replaceAll("^[a-b0-9_]", "_");
		}

		@Override
		void update() {

		}

		@Override
		public Text getNarration() {
			return Text.translatable("narrator.select");
		}

		@Override
		public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			graphics.drawCenteredShadowedText(ProfilesListWidget.this.client.textRenderer, this.name, ProfilesListWidget.this.width / 2, y + 3, 16777215);
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			ProfilesListWidget.this.setSelected(this);
			if (Util.getMeasuringTimeMs() - this.time < 250L) {
				client.setScreen(
						new KeyBindsScreen(
								ProfilesListWidget.this.parent,
								new GameOptions(
										MinecraftClient.getInstance(),
										null
								)
						)
				);
			}
			this.time = Util.getMeasuringTimeMs();
			return true;
		}
	}



}
