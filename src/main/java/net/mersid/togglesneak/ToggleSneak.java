package net.mersid.togglesneak;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.mersid.togglesneak.callbacks.OnTickCallback;
import net.mersid.togglesneak.config.Configuration;
import net.mersid.togglesneak.config.ConfigurationScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ToggleSneak implements ModInitializer {

	public Configuration configuration;
	private Path cfgPath;

	// These keys set whether toggle for sneak/sprint is on or off.
	private  FabricKeyBinding configBinding;
	private FabricKeyBinding sneakBinding;
	private FabricKeyBinding sprintBinding;
	private boolean keybindPressed = false;

	private final MinecraftClient mc = MinecraftClient.getInstance();
	private final InputModded mim = new InputModded(mc.options, this);
	public final GuiDrawer guiDrawer = new GuiDrawer(this, mim);

	@Override
	public void onInitialize()
	{
        OnTickCallback.EVENT.register(this::onTick);
		cfgPath = Paths.get("config", "ToggleSneak.json");
		configuration = new Configuration(cfgPath);

		configBinding = FabricKeyBinding.Builder.create(new Identifier("togglesneak", "config"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "Toggle Sneak").build();
		sneakBinding = FabricKeyBinding.Builder.create(new Identifier("togglesneak", "sneak"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "Toggle Sneak").build();
		sprintBinding = FabricKeyBinding.Builder.create(new Identifier("togglesneak", "sprint"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "Toggle Sneak").build();

		KeyBindingRegistry.INSTANCE.register(configBinding);
		KeyBindingRegistry.INSTANCE.register(sneakBinding);
		KeyBindingRegistry.INSTANCE.register(sprintBinding);
	}

    private void onTick()
    {
	    if ((mc.player != null) && (!(mc.player.input instanceof InputModded))) {
		    mc.player.input = mim;
	    }

		if (configBinding.isPressed())
		{
			MinecraftClient.getInstance().openScreen(new ConfigurationScreen(this).configBuilder.build());
		}

	    if (sneakBinding.isPressed())
	    {
		    if (!keybindPressed)
		    {
			    configuration.toggleSneak = !configuration.toggleSneak;
		    	keybindPressed = true;
				configuration.save();
				configuration.load();
		    }
	    }
	    else if (sprintBinding.isPressed())
	    {
		    if (!keybindPressed)
		    {
			    configuration.toggleSprint = !configuration.toggleSprint;
			    keybindPressed = true;
			    configuration.save();
			    configuration.load();
		    }
	    }
	    else
	    {
			keybindPressed = false;
	    }
    }
}
