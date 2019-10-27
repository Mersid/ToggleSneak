package net.mersid.togglesneak.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface OnChatCallback {
    Event<OnChatCallback> EVENT = EventFactory.createArrayBacked(OnChatCallback.class,
            (listeners) -> (message) -> {
                for (OnChatCallback event : listeners) {
                    event.onChat(message);
                }
            });

    void onChat(String message);
}
