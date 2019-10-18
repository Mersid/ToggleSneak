/*
 * Copyright (C) 2014 - 2019 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.mersid.mixins;

import net.mersid.callbacks.OnRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class IngameHudMixin extends DrawableHelper
{
	@Inject(at = {@At(value = "INVOKE",
		target = "Lcom/mojang/blaze3d/platform/GlStateManager;enableBlend()V",
		ordinal = 4)}, method = {"render(F)V"})
	//@Inject(at = @At(value = "HEAD", target = "net/minecraft/client/gui/hud/InGameHud;", ordinal = 0), method = "render()V")
	private void onRender(float partialTicks, CallbackInfo ci)
	{
		if(MinecraftClient.getInstance().options.debugEnabled)
			return;
		
		OnRenderCallback.EVENT.invoker().onRender();
	}
	
	@Inject(at = @At("HEAD"), method = { "renderStatusEffectOverlay()V" }, cancellable = true)
	private void onRenderStatusEffectOverlay(CallbackInfo ci)
	{
		ci.cancel();
	}
}
