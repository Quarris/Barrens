package dev.quarris.barrens.mixins.client;

import dev.quarris.barrens.BarrensConfigs;
import dev.quarris.barrens.setup.WorldPresetSetup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    @Inject(method = "openFresh", at = @At(value = "RETURN"))
    private static void defaultWastelandWorldPreset(Minecraft minecraft, Screen lastScreen, CallbackInfo ci) {
        if (!BarrensConfigs.INSTANCE.getUseBarrensDefaultPreset()) {
            return;
        }

        if (!(Minecraft.getInstance().screen instanceof CreateWorldScreen screen)) {
            return;
        }

        for (WorldCreationUiState.WorldTypeEntry worldTypeEntry : screen.getUiState().getNormalPresetList()) {
            if (worldTypeEntry.preset().is(WorldPresetSetup.INSTANCE.getBarrens())) {
                screen.getUiState().setWorldType(worldTypeEntry);
            }
        }
    }
}
