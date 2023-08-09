package me.wawwior.keybind_profiles.mixin;

import me.wawwior.keybind_profiles.gui.ProfileEditScreen;
import me.wawwior.keybind_profiles.util.KeybindUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {GameOptionsScreen.class})
public class GameOptionsScreenMixin {

    @Final @Shadow protected Screen parent;

    @Redirect(
            method = "removed()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/GameOptions;write()V"
            )
    )
    public void write$applyKeybinds(GameOptions instance) {
        if (this.parent instanceof ProfileEditScreen) {
            KeybindUtil.applyTemporaryKeybinds();
        }
    }

}
