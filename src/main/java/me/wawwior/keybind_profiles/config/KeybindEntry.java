package me.wawwior.keybind_profiles.config;
import com.google.gson.*;
import com.mojang.blaze3d.platform.InputUtil;
import me.wawwior.config.ConfigProvider;


public class KeybindEntry {

	private final String key;

	private final Modifiers modifiers = new Modifiers();

	public KeybindEntry(String key, Modifiers modifiers) {
		this.key = key;
		this.modifiers.SHIFT = modifiers.SHIFT;
		this.modifiers.CTRL = modifiers.CTRL;
		this.modifiers.ALT = modifiers.ALT;
	}

	public InputUtil.Key getKey() {
		return InputUtil.fromTranslationKey(key);
	}

	public Modifiers getModifiers() {
		return modifiers;
	}

	public static class Modifiers {

		public boolean SHIFT = false;
		public boolean CTRL = false;
		public boolean ALT = false;

		public static class Codec extends ConfigProvider.JsonAdapterPair<Modifiers> {
			public Codec() {
				super(
                        (src, typeOfSrc, context) -> {
							int shift = 0;
							int ctrl = 0;
							int alt = 0;
							if (src.SHIFT) shift = 1;
							if (src.CTRL) ctrl = 1;
							if (src.ALT) alt = 1;
							String modifiers = shift + "," + ctrl + "," + alt;
							return new JsonPrimitive(modifiers);
						},
                        (json, typeOfT, context) -> {
							Modifiers modifiers = new Modifiers();

							if (json.isJsonPrimitive()) {
								String[] modifiersArray = json.getAsString().split(",");
								if (modifiersArray.length == 3) {
									modifiers.SHIFT = modifiersArray[0].equals("1");
									modifiers.CTRL = modifiersArray[1].equals("1");
									modifiers.ALT = modifiersArray[2].equals("1");
								}
							}

							return modifiers;
						}
				);
			}
		}

		@Override
		public String toString() {
			return "Modifiers{" +
				"SHIFT=" + SHIFT +
				", CTRL=" + CTRL +
				", ALT=" + ALT +
				'}';
		}
	}

}
