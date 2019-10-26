package net.mersid;

import net.fabricmc.api.ModInitializer;
import net.mersid.callbacks.OnChatCallback;
import net.mersid.callbacks.OnTickCallback;
import net.mersid.config.Configuration;
import net.mersid.config.ConfigurationScreen;
import net.minecraft.client.MinecraftClient;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ToggleSneak implements ModInitializer {

	public Configuration configuration;
	private Path cfgPath;

	private KeyBinding sneakBinding;
	private KeyBinding sprintBinding;
	private List<KeyBinding> kbList;

	private final MinecraftClient mc = MinecraftClient.getInstance();
	private final InputModded mim = new InputModded(mc.gameSettings, this);
	public final GuiDrawer guiDrawer = new GuiDrawer(this, mim);

	@Override
	public void onInitialize()
	{
        OnChatCallback.EVENT.register(this::onChatMessage);
        OnTickCallback.EVENT.register(this::onTick);
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

    private void onTick()
    {
	    if ((mc.player != null) && (!(mc.player.input instanceof InputModded))) {
		    mc.player.input = inputM;
	    }
    }
}
