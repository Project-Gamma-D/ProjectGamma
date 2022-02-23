package com.projectgamma.ProjectGamma.api;

import com.mojang.brigadier.tree.CommandNode;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface DiscordCommandSource {
    @NotNull Message getMessage();

    @NotNull User getAuthor();

    @NotNull MessageChannel getMessageChannel();

    @NotNull Map<CommandNode<DiscordCommandSource>, String> getSmartUsage();
    @NotNull Map<CommandNode<DiscordCommandSource>, String> getSmartUsage(CommandNode<DiscordCommandSource> node);

    void sendMessage(String text, Object... args);
    void sendMessage(MessageEmbed embed);

    void replyMessage(String text, Object... args);
    void replyMessage(MessageEmbed embed);
}
