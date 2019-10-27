package net.mersid.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.mersid.ToggleSneak;
import net.mersid.callbacks.OnTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An instance of the Configuration screen. Each opening will cause a memory leak, so be careful!
 */
public class ConfigurationScreen {

	public ConfigBuilder configBuilder;

	// Config cannot open on same frame as chat menu, so do it next tick (or frame, but we don't need to be fast).
	private boolean openConfigNextTick;

	public ConfigurationScreen(ToggleSneak ts)
	{
		// Set up builders, title, and category.
		configBuilder = ConfigBuilder.create();
		ConfigEntryBuilder configEntryBuilder = ConfigEntryBuilder.create();

		ConfigCategory category = configBuilder.getOrCreateCategory("Toggle Sneak");
		configBuilder.setTitle("Configuration");

		category.addEntry(configEntryBuilder.startBooleanToggle(
				"Toggle sneak", ts.configuration.toggleSneak)
				.setTooltip("Sets whether or not to toggle sneak when the sneak key", "is pressed (rather than held down). Default is true")
				.setDefaultValue(true)
		.       setSaveConsumer(e -> ts.configuration.toggleSneak = e)
				.build());

		category.addEntry(configEntryBuilder.startBooleanToggle(
				"Toggle sprint", ts.configuration.toggleSprint)
				.setTooltip("Sets whether or not to toggle sprint when the sprint key", "is pressed (rather than held down). Default is true")
				.setDefaultValue(true)
				.setSaveConsumer(e -> ts.configuration.toggleSprint = e)
				.build());

		category.addEntry(configEntryBuilder.startBooleanToggle(
				"Fly boost", ts.configuration.flyBoost)
				.setTooltip("Sets whether or not to fly at a faster rate when sprinting.", "Default is true")
				.setDefaultValue(true)
				.setSaveConsumer(e -> ts.configuration.flyBoost = e)
				.build());

		category.addEntry(configEntryBuilder.startDoubleField(
				"Fly boost factor", ts.configuration.flyBoostFactor)
				.setTooltip("If fly boost is on, multiply fly speed by this.", "Default is 10.0")
				.setDefaultValue(10)
				.setSaveConsumer(e -> ts.configuration.flyBoostFactor = e)
				.build());

		category.addEntry(configEntryBuilder.startIntField(
				"Key hold ticks", ts.configuration.keyHoldTicks)
				.setTooltip("If the sneak or sprint key is held for less than this", "many ticks (20 ticks per second), do not toggle. Default is 7.")
				.setDefaultValue(7)
				.setSaveConsumer(e -> ts.configuration.keyHoldTicks = e)
				.build());

		category.addEntry(configEntryBuilder.startEnumSelector(
				"Display style", Configuration.DisplayStyle.class, ts.configuration.displayStyle)
				.setTooltip("Sets the display style for the GUI.")
				.setDefaultValue(Configuration.DisplayStyle.COLOR_CODED)
				.setSaveConsumer(e -> ts.configuration.displayStyle = (Configuration.DisplayStyle)e)
				.build());

		category.addEntry(configEntryBuilder.startEnumSelector(
				"Horizontal anchor", Configuration.HAnchor.class, ts.configuration.hAnchor)
				.setTooltip("Sets where the GUI will anchor to along the horizontal axis.")
				.setDefaultValue(Configuration.HAnchor.LEFT)
				.setSaveConsumer(e -> ts.configuration.hAnchor = (Configuration.HAnchor)e)
				.build());

		category.addEntry(configEntryBuilder.startEnumSelector(
				"Vertical anchor", Configuration.VAnchor.class, ts.configuration.vAnchor)
				.setTooltip("Sets where the GUI will anchor to along the vertical axis.")
				.setDefaultValue(Configuration.VAnchor.TOP)
				.setSaveConsumer(e -> {ts.configuration.vAnchor = (Configuration.VAnchor)e; ts.configuration.save(); ts.guiDrawer.computeDrawPosIfChanged();}) // Last entry should save the config to disk.
				.build());

		/*
		category.addEntry(configEntryBuilder.startTextDescription(
				"*NOTE: When changing the anchors, graphical bugs may occur, or your changes may fail to reflect. Just move and resize the Minecraft Client to fix it.")
				.setColor(0xFFFFA0)
				.build());

		 */

	}
}

