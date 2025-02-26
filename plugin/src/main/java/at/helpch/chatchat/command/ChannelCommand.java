package at.helpch.chatchat.command;

import at.helpch.chatchat.ChatChatPlugin;
import at.helpch.chatchat.api.channel.Channel;
import at.helpch.chatchat.api.user.ChatUser;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Collection;
import java.util.List;

import static net.kyori.adventure.text.format.NamedTextColor.DARK_GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.GOLD;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;

@Command("channel")
public class ChannelCommand extends BaseCommand {

    private final ChatChatPlugin plugin;
    private final static String CHANNEL_PERMISSION = "chatchat.channel";

    public ChannelCommand(final ChatChatPlugin plugin) {
        this.plugin = plugin;
    }

    @SubCommand("leave")
    @Permission(CHANNEL_PERMISSION)
    public void leaveChannel(ChatUser sender, String channel) {
        Channel channelObj = plugin.configManager().channels().channels().get(channel);

        if (channelObj == null) {
            sender.sendMessage(Component.text("Channel " + channel + " not found!", YELLOW));
            return;
        }

        if (sender.hiddenChannels().contains(channelObj)) {
            sender.sendMessage(Component.text("You already left this channel.", YELLOW));
            return;
        }

        sender.addHiddenChannel(channelObj);
        sender.sendMessage(Component.text("You have left the channel " + channelObj.name(), GREEN));
    }

    @SubCommand("join")
    @Permission(CHANNEL_PERMISSION)
    public void joinChannel(ChatUser sender, String channel) {
        Channel channelObj = plugin.configManager().channels().channels().get(channel);

        if (channelObj == null) {
            sender.sendMessage(Component.text("Channel ", YELLOW)
                .append(Component.text(channel, GOLD))
                .append(Component.text(" not found!", YELLOW)));

            return;
        }

        if (!sender.hiddenChannels().contains(channelObj)) {
            sender.sendMessage(Component.text("You are already in this channel!", YELLOW));
            return;
        }

        sender.removeHiddenChannel(channelObj);
        sender.sendMessage(Component.text("You have joined the channel ", GREEN)
            .append(Component.text(channelObj.name(), DARK_GREEN)));
    }

    @SubCommand("list")
    @Permission(CHANNEL_PERMISSION)
    public void listChannels(ChatUser sender) {
        Collection<Channel> hiddenChannels = sender.hiddenChannels();

        Channel currentUserChannel = sender.channel();

        List<TextComponent> channelsList = plugin.configManager().channels().channels().values().stream()
            .filter(channel -> channel.isUsableBy(sender))
            .map(channel -> {
                boolean hidden = hiddenChannels.contains(channel);
                boolean isCurrent = currentUserChannel.equals(channel);

                return Component.text(channel.name(), hidden ? NamedTextColor.RED : DARK_GREEN)
                    .append(Component.text(" " + (isCurrent ? "(current)" : ""), GREEN))
                    .hoverEvent(Component.text("Click to " + (hidden ? "join" : "leave") + " this channel.", WHITE))
                    .clickEvent(ClickEvent.runCommand("/channel " + (hidden ? "join" : "leave") + " " + channel.name()));
            })
            .toList();


        Component joined = Component.join(JoinConfiguration.builder().separator(Component.text(", ", GREEN)).build(), channelsList);

        sender.sendMessage(Component.text("You can chat in the following channels: <channels>.", GREEN)
            .replaceText(builder -> builder.matchLiteral("<channels>").replacement(joined)));
    }

    @Default
    @Permission(CHANNEL_PERMISSION)
    public void showHelpMessage(ChatUser sender) {
        TextComponent text = Component.text("Usage: /channel <join|leave|list> [channel]", GREEN);

        sender.sendMessage(text);
    }

}
