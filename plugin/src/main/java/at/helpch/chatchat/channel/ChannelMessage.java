package at.helpch.chatchat.channel;

import at.helpch.chatchat.api.channel.Channel;
import at.helpch.chatchat.api.channel.IChannelMessage;
import net.kyori.adventure.text.Component;


public class ChannelMessage implements IChannelMessage {

    private final Channel channel;
    private final String message;
    private final Component component;
    private final long timestamp;

    public ChannelMessage(final Channel channel, final String message, final Component component, final long timestamp) {
        this.channel = channel;
        this.message = message;
        this.component = component;
        this.timestamp = timestamp;
    }

    public Channel channel() {
        return channel;
    }

    public String message() {
        return message;
    }

    public Component component() {
        return component;
    }

    public long timestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
            "message='" + message + '\'' +
            ", component=" + component +
            ", timestamp=" + timestamp +
            '}';
    }
}
