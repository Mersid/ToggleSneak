package net.mersid.mixins;

import com.mojang.authlib.GameProfile;
import net.mersid.callbacks.OnChatCallback;
import net.mersid.callbacks.OnTickCallback;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity
{
	public ClientPlayerEntityMixin(ClientWorld clientWorld_1, GameProfile gameProfile_1) {
		super(clientWorld_1, gameProfile_1);
	}

	@Inject(at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
			ordinal = 0), method = "tick()V")
	private void onTick(CallbackInfo ci)
	{
		OnTickCallback.EVENT.invoker().onTick();
	}

	@Inject(at = @At("HEAD"),
			method = "sendChatMessage(Ljava/lang/String;)V")
	private void onSendChatMessage(String message, CallbackInfo ci)
	{
		OnChatCallback.EVENT.invoker().onChat(message);
	}
}
