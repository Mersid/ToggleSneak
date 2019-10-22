package net.mersid;

import net.fabricmc.api.ModInitializer;
import net.mersid.callbacks.OnChatCallback;
import net.mersid.config.Configuration;
import net.mersid.config.ConfigurationScreen;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ToggleSneak implements ModInitializer {

	private boolean openConfigNextFrame = false;
	public Configuration configuration;
	private Path cfgPath;

	@Override
	public void onInitialize()
	{
        OnChatCallback.EVENT.register(this::onChatMessage);
		cfgPath = Paths.get("config", "ToggleSneak.json");
		configuration = new Configuration(cfgPath);
	}

	private void onChatMessage(String message)
    {
        if (message.equals("zts"))
		{
			ConfigurationScreen.open();
		}
    }
}
