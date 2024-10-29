package dev.quarris.barrens.mixins.client;

import dev.quarris.barrens.setup.TagSetup;
import dev.quarris.barrens.weather.WeatherEffects;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterDropParticle.class)
public abstract class WaterDropParticleMixin extends TextureSheetParticle {

    protected WaterDropParticleMixin(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void acidRainColor(ClientLevel pLevel, double pX, double pY, double pZ, CallbackInfo ci) {
        if (pLevel.getBiome(BlockPos.containing(pX, pY, pZ)).is(TagSetup.Biomes.INSTANCE.getIsDead())) {
            var color = WeatherEffects.INSTANCE.getAcidColor();
            this.setColor(color.x, color.y, color.z);
            this.setAlpha(color.w);
        }
    }
}
