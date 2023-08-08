package me.wawwior.keybind_profiles.compat;

import de.siphalor.amecs.api.KeyBindingUtils;
import de.siphalor.amecs.api.KeyModifiers;
import me.wawwior.keybind_profiles.KeybindProfiles;
import me.wawwior.keybind_profiles.config.KeybindEntry;
import net.minecraft.client.option.KeyBind;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class AmecsCompat {


	private static Function<KeyBind, KeybindEntry.Modifiers> getModifiers = k -> new KeybindEntry.Modifiers();
	private static BiConsumer<KeyBind, KeybindEntry.Modifiers> setModifiers = (k, m) -> {};


	public static void init() {
		if (!isLoaded()) {
			KeybindProfiles.LOGGER.info("Amecs not detected, disabling compat");
		} else {
			KeybindProfiles.LOGGER.info("Amecs detected, enabling compat");

			getModifiers = k -> {
				KeyModifiers amecsModifiers = KeyBindingUtils.getBoundModifiers(k);

				KeybindEntry.Modifiers modifiers = new KeybindEntry.Modifiers();

				modifiers.SHIFT = amecsModifiers.getShift();
				modifiers.CTRL = amecsModifiers.getControl();
				modifiers.ALT = amecsModifiers.getAlt();

				return modifiers;
			};

			setModifiers = (k, m) -> {
				KeyModifiers amecsModifiers = KeyBindingUtils.getBoundModifiers(k);

				amecsModifiers.setShift(m.SHIFT);
				amecsModifiers.setControl(m.CTRL);
				amecsModifiers.setAlt(m.ALT);
			};

		}
	}

	public static boolean isLoaded() {
		return QuiltLoader.isModLoaded("amecs");
	}

	public static KeybindEntry.Modifiers getModifiers(KeyBind keyBind) {
		return getModifiers.apply(keyBind);
	}

	public static void setModifiers(KeyBind keyBind, KeybindEntry.Modifiers modifiers) {
		setModifiers.accept(keyBind, modifiers);
	}

}
