package com.projectgamma.ProjectGamma.api;

import com.mojang.brigadier.tree.CommandNode;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A command source for the <a href="https://github.com/Mojang/brigadier">Brigadier library</a>. This library is used to
 * dispatch bot commands. Check the documentation of the library for more on information on how this class is used.
 */
public interface DiscordCommandSource {
    /**
     * Returns the message containing the command.
     *
     * @return The message containing the command
     */
    @NotNull Message getMessage();

    /**
     * Returns the user who sent the command.
     *
     * @return The user who sent the command
     */
    @NotNull User getAuthor();

    /**
     * Returns the message channel where the command was sent.
     * @return The message channel where the command was sent
     */
    @NotNull MessageChannel getMessageChannel();

    /**
     * Returns the whole tree of command suggestions for the command sender.
     *
     * @return The tree of command suggestions
     */
    @NotNull Map<CommandNode<DiscordCommandSource>, String> getSmartUsage();

    /**
     * Returns the tree of command suggestions for the command sender starting from a given node.
     *
     * @param node The node where the command suggestion search starts
     * @return The tree of command suggestions
     */
    @NotNull Map<CommandNode<DiscordCommandSource>, String> getSmartUsage(CommandNode<DiscordCommandSource> node);

    /**
     * Sends a message to the channel where the command was sent using {@link String#format(String, Object...)}.
     *
     * @param format The format of the message
     * @param args The arguments of the message
     * @see String#format(String, Object...)
     */
    void sendMessage(String format, Object... args);

    /**
     * Sends an embed to the channel where the command was sent.
     *
     * @param embed The embed to send
     */
    void sendMessage(MessageEmbed embed);

    /**
     * Replies to the command message using {@link String#format(String, Object...)}
     *
     * @param format The format of the message
     * @param args The arguments of the message
     * @see String#format(String, Object...)
     */
    void replyMessage(String format, Object... args);

    /**
     * Replies with an embed to the command message.
     *
     * @param embed The embed to send
     */
    void replyMessage(MessageEmbed embed);
}
