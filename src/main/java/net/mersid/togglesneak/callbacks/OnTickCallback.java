package net.mersid.togglesneak.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface OnTickCallback {
    Event<OnTickCallback> EVENT = EventFactory.createArrayBacked(OnTickCallback.class,
            (listeners) -> () -> {
                for (OnTickCallback event : listeners) {
                    event.onTick();
                }
            });

    void onTick();
}
