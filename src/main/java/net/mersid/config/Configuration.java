package net.mersid.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.mersid.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration {
	private Path savePath;

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
	public boolean toggleSprint = true;
	public boolean flyBoost = true;
	public double flyBoostFactor = 10.0D;
	public int keyHoldTicks = 7;
	public DisplayStyle displayStyle = DisplayStyle.COLOR_CODED;
	public HAnchor hAnchor = HAnchor.LEFT;
	public VAnchor vAnchor = VAnchor.TOP;

	public Configuration(Path path)
	{
		this.savePath = path;
		load(path);
		save(path);

		System.out.println(toggleSneak);
		System.out.println(toggleSprint);
		System.out.println(flyBoost);
		System.out.println(flyBoostFactor);
		System.out.println(keyHoldTicks);
		System.out.println(displayStyle);
		System.out.println(hAnchor);
		System.out.println(vAnchor);
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
		jsonObject.add(DISPLAY_STYLE_KEY, new JsonPrimitive(displayStyle.toString()));
		jsonObject.add(H_ANCHOR_KEY, new JsonPrimitive(hAnchor.toString()));
		jsonObject.add(V_ANCHOR_KEY, new JsonPrimitive(vAnchor.toString()));

		String prettyjson = PRETTY_GSON.toJson(jsonObject);
		FileUtils.write(prettyjson, path);

	}


	private void load(Path path)
	{

		// Load JsonObject from file
		String jsonString = FileUtils.read(path);

		JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);

		toggleSneak = jsonObject.get(TOGGLE_SNEAK_KEY) == null ? toggleSneak : jsonObject.get(TOGGLE_SNEAK_KEY).getAsBoolean();
		toggleSprint = jsonObject.get(TOGGLE_SPRINT_KEY) == null ? toggleSprint : jsonObject.get(TOGGLE_SPRINT_KEY).getAsBoolean();
		flyBoost = jsonObject.get(FLY_BOOST_KEY) == null ? flyBoost : jsonObject.get(FLY_BOOST_KEY).getAsBoolean();
		flyBoostFactor = jsonObject.get(FLY_BOOST_FACTOR_KEY) == null ? flyBoostFactor : jsonObject.get(FLY_BOOST_FACTOR_KEY).getAsDouble();
		keyHoldTicks = jsonObject.get(KEY_HOLD_TICKS_KEY) == null ? keyHoldTicks : jsonObject.get(KEY_HOLD_TICKS_KEY).getAsInt();
		displayStyle = jsonObject.get(DISPLAY_STYLE_KEY) == null ? displayStyle : DisplayStyle.valueOf(jsonObject.get(DISPLAY_STYLE_KEY).getAsString());
		vAnchor = jsonObject.get(V_ANCHOR_KEY) == null ? vAnchor : VAnchor.valueOf(jsonObject.get(V_ANCHOR_KEY).getAsString());
		hAnchor = jsonObject.get(H_ANCHOR_KEY) == null ? hAnchor : HAnchor.valueOf(jsonObject.get(H_ANCHOR_KEY).getAsString());
	}

	public void save()
	{
		save(savePath);
	}

	public void load()
	{
		load(savePath);
	}

	public enum DisplayStyle
	{
		NO_DISPLAY,
		COLOR_CODED,
		TEXT_ONLY
	}

	public enum HAnchor
	{
		LEFT,
		CENTER,
		RIGHT
	}

	public enum VAnchor
	{
		TOP,
		CENTER,
		BOTTOM
	}
}
