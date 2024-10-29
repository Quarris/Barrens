package dev.quarris.barrens.mixins.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.quarris.barrens.setup.TagSetup;
import dev.quarris.barrens.weather.WeatherEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    private boolean shouldRenderAcidRain = false;

    @Inject(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void acidRainStart(LightTexture pLightTexture, float pPartialTick, double pCamX, double pCamY, double pCamZ, CallbackInfo ci, float f, Level level, int i, int j, int k, Tesselator tesselator, BufferBuilder bufferbuilder, int l, int i1, float f1, BlockPos.MutableBlockPos blockpos$mutableblockpos, int j1, int k1, int l1, double d0, double d1, Biome biome, int i2, int j2, int k2, int l2, RandomSource randomsource) {
        shouldRenderAcidRain = false;
        level.registryAccess().registry(Registries.BIOME).ifPresent(biomes -> {
            if (biomes.wrapAsHolder(biome).is(TagSetup.Biomes.INSTANCE.getIsDead())) {
                shouldRenderAcidRain = true;
            }
        });
    }

    @Redirect(
        method = "renderSnowAndRain",
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;color(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"),
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;vertex(DDD)Lcom/mojang/blaze3d/vertex/VertexConsumer;", ordinal = 0),
            to = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;endVertex()V", ordinal = 3)
        ))
    private VertexConsumer modifyRainColor(VertexConsumer instance, float pRed, float pGreen, float pBlue, float pAlpha) {
        if (shouldRenderAcidRain) {
            var color = WeatherEffects.INSTANCE.getAcidColor();
            instance.color(color.x, color.y, color.z, color.w);
        } else {
            instance.color(1f, 1f, 1f, pAlpha);
        }
        return instance;
    }
}
