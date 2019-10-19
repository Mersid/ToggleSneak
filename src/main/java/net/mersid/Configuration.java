package net.mersid;

import javafx.scene.control.Slider;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import net.mersid.callbacks.OnTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;

public class Configuration {

    private ConfigBuilder configBuilder;

    // Config cannot open on same frame as chat menu, so do it next tick (or frame, but we don't need to be fast).
    private static boolean openConfigNextTick = false;

    private Configuration()
    {
        // Set up builders, title, and category.
        configBuilder = ConfigBuilder.create();
        ConfigEntryBuilder configEntryBuilder = ConfigEntryBuilder.create();
        ConfigCategory category = configBuilder.getOrCreateCategory("Category 1");
        configBuilder.setTitle("Titles and stuff");


        category.addEntry(configEntryBuilder.startIntSlider(I18n.translate("Slider"), 0, 0, 10).build());
        OnTickCallback.EVENT.register(this::onTick);
    }

    public static void open()
    {
        Configuration cfg = new Configuration();
        openConfigNextTick = true;
    }

    private void onTick()
    {
        if (openConfigNextTick)
        {
            MinecraftClient.getInstance().openScreen(new Configuration().configBuilder.build());
            openConfigNextTick = false;
        }
        System.out.println("Hi Jack!");
    }
}

