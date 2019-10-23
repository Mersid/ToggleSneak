package net.mersid.config;

import com.google.gson.*;
import net.mersid.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration {
	Path savePath;

	// Property names for json key-value pairs
	private static final String
	TOGGLE_SNEAK_KEY = "toggleSneak",
	TOGGLE_SPRINT_KEY = "toggleSprint,",
	FLY_BOOST_KEY = "flyBoost",
	FLY_BOOST_FACTOR_KEY = "flyBoostFactor",
	KEY_HOLD_TICKS_KEY = "keyHoldTicks",
	DISPLAY_STYLE_KEY = "displayStyle",
	H_ANCHOR_KEY = "hAnchor",
	V_ANCHOR_KEY = "vAnchor";



	// All equals are default values; they will remain so only if not overwritten at loadtime.
	public boolean toggleSneak = true;
	public boolean toggleSprint = false;
	public boolean flyBoost = false;
	public double flyBoostFactor = 4.0D;
	public int keyHoldTicks = 7;

	public Configuration(Path path)
	{
		this.savePath = path;
		load(path);

		System.out.println(toggleSneak);
		System.out.println(toggleSprint);
		System.out.println(flyBoost);
		System.out.println(flyBoostFactor);
		System.out.println(keyHoldTicks);

	}


	private void save(Path path)
	{
		Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

		JsonObject jsonObject = new JsonObject();
		jsonObject.add(TOGGLE_SNEAK_KEY, new JsonPrimitive(toggleSneak));
		jsonObject.add(TOGGLE_SPRINT_KEY, new JsonPrimitive(toggleSprint));
		jsonObject.add(FLY_BOOST_KEY, new JsonPrimitive(flyBoost));
		jsonObject.add(FLY_BOOST_FACTOR_KEY, new JsonPrimitive(flyBoostFactor));
		jsonObject.add(KEY_HOLD_TICKS_KEY, new JsonPrimitive(keyHoldTicks));

		String prettyjson = PRETTY_GSON.toJson(jsonObject);
		FileUtils.write(prettyjson, path);

	}


	private void load(Path path)
	{
		try
		{
			// Load JsonObject from file
			String jsonString = Files.readString(path);
			JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);

			toggleSneak = jsonObject.get(TOGGLE_SNEAK_KEY) == null ? toggleSneak : jsonObject.get(TOGGLE_SNEAK_KEY).getAsBoolean();
			toggleSprint = jsonObject.get(TOGGLE_SPRINT_KEY) == null ? toggleSprint : jsonObject.get(TOGGLE_SPRINT_KEY).getAsBoolean();
			flyBoost = jsonObject.get(FLY_BOOST_KEY) == null ? flyBoost : jsonObject.get(FLY_BOOST_KEY).getAsBoolean();
			flyBoostFactor = jsonObject.get(FLY_BOOST_FACTOR_KEY) == null ? flyBoostFactor : jsonObject.get(FLY_BOOST_FACTOR_KEY).getAsDouble();
			keyHoldTicks = jsonObject.get(KEY_HOLD_TICKS_KEY) == null ? keyHoldTicks : jsonObject.get(KEY_HOLD_TICKS_KEY).getAsInt();


		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
