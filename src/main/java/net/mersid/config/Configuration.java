package net.mersid.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.mersid.utils.FileUtils;
import org.apache.commons.io.IOUtils;

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
	public float flyBoostFactor = 4.0F;
	public int keyHoldTicks = 7;

	public Configuration(Path path)
	{
		this.savePath = path;
		//load(path);
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



		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Tries to load a parameter from path. Will default to fallback param if no configuration element can be found.
	 * @param json Json to load from.
	 * @param key Key of the item to load
	 * @param fallback Value to return if key is not found
	 * @param <T> Return type
	 * @return
	 */
	private <T> T tryLoad(JsonObject json, String key, T fallback)
	{
		
	}
}
