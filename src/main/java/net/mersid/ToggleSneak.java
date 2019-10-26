package net.mersid;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.mersid.callbacks.OnChatCallback;
import net.mersid.callbacks.OnTickCallback;
import net.mersid.config.Configuration;
import net.mersid.config.ConfigurationScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ToggleSneak implements ModInitializer {

	public Configuration configuration;
	private Path cfgPath;

	// These keys set whether toggle for sneak/sprint is on or off.
	private FabricKeyBinding sneakBinding;
	private FabricKeyBinding sprintBinding;
	private boolean keybindPressed = false;

	private final MinecraftClient mc = MinecraftClient.getInstance();
	private final InputModded mim = new InputModded(mc.options, this);
	public final GuiDrawer guiDrawer = new GuiDrawer(this, mim);

	@Override
	public void onInitialize()
	{
        OnChatCallback.EVENT.register(this::onChatMessage);
        OnTickCallback.EVENT.register(this::onTick);
		cfgPath = Paths.get("config", "ToggleSneak.json");
		configuration = new Configuration(cfgPath);

		sneakBinding = FabricKeyBinding.Builder.create(new Identifier("abc"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "Toggle Sneak").build();
		sprintBinding = FabricKeyBinding.Builder.create(new Identifier("def"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "Toggle Sprint").build();

		KeyBindingRegistry.INSTANCE.register(sneakBinding);
		KeyBindingRegistry.INSTANCE.register(sprintBinding);
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
		    mc.player.input = mim;
	    }

	    if (sneakBinding.isPressed())
	    {
		    if (!keybindPressed)
		    {
			    configuration.toggleSneak = !configuration.toggleSneak;
		    	keybindPressed = true;
		    }
	    }
	    else if (sprintBinding.isPressed())
	    {
		    if (!keybindPressed)
		    {
			    configuration.toggleSprint = !configuration.toggleSprint;
			    keybindPressed = true;
		    }
	    }
	    else
	    {
			keybindPressed = false;
	    }
    }
	public int displayStatus() {/*
		for (int i=0; i < configuration.statusDisplayOpts.length; i++)
			if (configuration.statusDisplayOpts[i].equals(configuration.statusDisplay)) return i;*/
		return 0;
	}

}
