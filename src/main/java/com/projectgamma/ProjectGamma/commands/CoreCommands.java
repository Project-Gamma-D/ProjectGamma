package com.projectgamma.ProjectGamma.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.projectgamma.ProjectGamma.api.CommandProvider;
import com.projectgamma.ProjectGamma.api.DiscordCommandSource;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.util.function.Consumer;

/**
 * The basic commands provided by the bot
 */
public class CoreCommands implements CommandProvider {

    public static CommandProvider INSTANCE = new CoreCommands();

    private CoreCommands() {}

    @Override
    public void registerCommands(Consumer<LiteralArgumentBuilder<DiscordCommandSource>> commandRegisterer) {
        commandRegisterer.accept(CommandProvider.literal("hello").executes(CoreCommands::hello));
        commandRegisterer.accept(CommandProvider.literal("commands").executes(CoreCommands::commands));
    }

    /**
     * This command greets the user that sends it.
     */
    static int hello(CommandContext<DiscordCommandSource> context) {
        User user = context.getSource().getAuthor();
        context.getSource().sendMessage("Hello, %s!", user.getAsMention());

        return 0;
    }

    /**
     * This command shows a list of registered commands.
     */
    static int commands(CommandContext<DiscordCommandSource> context) {
        StringBuilder usage = new StringBuilder();
        for (String s : context.getSource().getSmartUsage().values()) {
            usage.append("!");
            usage.append(s);
            usage.append('\n');
        }

        MessageEmbed helpMessage = new EmbedBuilder()
                .setTitle("Gamma project command list")
                .setDescription(usage)
                .build();

        context.getSource().replyMessage(helpMessage);
        return 0;
    }
}
