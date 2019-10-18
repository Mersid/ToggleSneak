package net.mersid;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.ModInitializer;
import net.mersid.callbacks.OnChatCallback;
import net.mersid.callbacks.OnRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;

import java.util.Timer;

public class ToggleSneak implements ModInitializer {

	private boolean openConfigNextFrame = false;

	@Override
	public void onInitialize()
	{
        OnChatCallback.EVENT.register(this::onChatMessage);
	}

	private void onChatMessage(String message)
    {
        if (message.equals("zts"))
		{
			Configuration.open();
		}
    }
}
