package at.helpch.chatchat.api.channel;

import net.kyori.adventure.text.Component;

public interface IChannelMessage {

    String message();

    Component component();

    long timestamp();

    String toString();
}
