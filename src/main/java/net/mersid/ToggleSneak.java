package net.mersid;

import net.fabricmc.api.ModInitializer;
import net.mersid.callbacks.OnChatCallback;
import net.mersid.config.ConfigurationScreen;

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
			ConfigurationScreen.open();
		}
    }
}
