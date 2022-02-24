package com.projectgamma.ProjectGamma;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.projectgamma.ProjectGamma.api.DiscordCommandSource;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * The implementation of {@link DiscordCommandSource}. Mostly boilerplate code.
 */
public class DiscordCommandSourceImpl implements DiscordCommandSource {

    private final @NotNull Message message;
    private final @NotNull User author;
    private final @NotNull MessageChannel messageChannel;
    private final @NotNull CommandDispatcher<DiscordCommandSource> dispatcher;

    public DiscordCommandSourceImpl(MessageReceivedEvent event, @NotNull CommandDispatcher<DiscordCommandSource> dispatcher) {
        this.message = event.getMessage();
        this.author = event.getAuthor();
        this.messageChannel = event.getChannel();
        this.dispatcher = dispatcher;
    }

    @Override
    public @NotNull Message getMessage() {
        return message;
    }

    @Override
    public @NotNull User getAuthor() {
        return author;
    }

    @Override
    public @NotNull MessageChannel getMessageChannel() {
        return messageChannel;
    }

    @Override
    public @NotNull Map<CommandNode<DiscordCommandSource>, String> getSmartUsage() {
        return getSmartUsage(dispatcher.getRoot());
    }

    @Override
    public @NotNull Map<CommandNode<DiscordCommandSource>, String> getSmartUsage(CommandNode<DiscordCommandSource> node) {
        return dispatcher.getSmartUsage(node, this);
    }

    @Override
    public void sendMessage(String format, Object... args) {
        this.messageChannel.sendMessage(String.format(format, args)).queue();
    }

    @Override
    public void sendMessage(MessageEmbed embed) {
        this.messageChannel.sendMessageEmbeds(embed).queue();
    }

    @Override
    public void replyMessage(String format, Object... args) {
        this.message.replyFormat(format, args).queue();
    }

    @Override
    public void replyMessage(MessageEmbed embed) {
        this.message.replyEmbeds(embed).queue();
    }
}
