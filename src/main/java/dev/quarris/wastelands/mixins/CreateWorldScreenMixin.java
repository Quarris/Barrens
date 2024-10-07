package dev.quarris.wastelands.mixins;

import dev.quarris.wastelands.WastelandsConfigs;
import dev.quarris.wastelands.setup.WorldPresetSetup;
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

    @Inject(method = "openFresh(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "RETURN"))
    private static void defaultWastelandWorldPreset(Minecraft minecraft, Screen lastScreen, CallbackInfo ci) {
        if (!WastelandsConfigs.INSTANCE.getUseWastelandsDefaultPreset()) {
            return;
        }

        if (!(Minecraft.getInstance().screen instanceof CreateWorldScreen screen)) {
            return;
        }

        for (WorldCreationUiState.WorldTypeEntry worldTypeEntry : screen.getUiState().getNormalPresetList()) {
            if (worldTypeEntry.preset().is(WorldPresetSetup.INSTANCE.getWASTELANDS())) {
                screen.getUiState().setWorldType(worldTypeEntry);
            }
        }
    }
}
