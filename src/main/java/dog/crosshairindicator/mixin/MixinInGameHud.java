package dog.crosshairindicator.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;

@Mixin(InGameHud.class)
public class MixinInGameHud {
	@Shadow @Final private MinecraftClient client;
	@Unique Identifier CUSTOM_CROSSHAIR = Identifier.of("crosshairindicator", "crosshair");
	@Unique Identifier SHIELD_CROSSHAIR = Identifier.of("crosshairindicator", "shield_crosshair");

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
	private void drawCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
	DebugHud debugHud = ((MixinInGameHudAccessor) (Object) this).getDebugHud();
        if (!debugHud.shouldShowDebugHud() && this.client.targetedEntity instanceof PlayerEntity player) {
            int scaledWidth = 15;
            int scaledHeight = 15;

	    Identifier texture = player.isBlocking() ? SHIELD_CROSSHAIR : CUSTOM_CROSSHAIR;
		
            context.drawGuiTexture(RenderLayer::getCrosshair, texture, (context.getScaledWindowWidth() - scaledWidth) / 2, (context.getScaledWindowHeight() - scaledHeight) / 2, scaledWidth, scaledHeight);
        }
    }
}
