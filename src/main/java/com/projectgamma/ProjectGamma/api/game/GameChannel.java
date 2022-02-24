package com.projectgamma.ProjectGamma.api.game;

import net.dv8tion.jda.api.entities.MessageEmbed;

/**
 * An interface representing a channel associated to a game instance.
 */
public interface GameChannel {

    /**
     * Sends a text message to the channel using {@link String#format(String, Object...)}
     *
     * @param format The format of the message
     * @param args The arguments of the message
     * @see String#format(String, Object...)
     */
    void sendMessage(String format, Object... args);

    /**
     * Sends an embed to the channel.
     *
     * @param embed The embed to send
     */
    void sendMessage(MessageEmbed embed);
}
